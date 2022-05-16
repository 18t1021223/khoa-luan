package com.blogads.controller.admin.service;

import com.blogads.configuration.security.AdminDetail;
import com.blogads.controller.admin.dto.*;
import com.blogads.controller.admin.model.MailInfo;
import com.blogads.entity.mysql.Admin;
import com.blogads.entity.mysql.Category;
import com.blogads.entity.mysql.Post;
import com.blogads.entity.mysql.Tag;
import com.blogads.exception.BlogAdsApiException;
import com.blogads.exception.BlogAdsException;
import com.blogads.repository.elasticsearch.PostElasticSearchRepository;
import com.blogads.repository.mysql.*;
import com.blogads.utils.*;
import com.blogads.vo.AdminRole;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.blogads.exception.BlogAdsApiException.*;
import static com.blogads.exception.BlogAdsException.*;
import static com.blogads.utils.BlogAdsUtils.toEntity;
import static com.blogads.utils.BlogAdsUtils.validPageable;
import static com.blogads.utils.Constants.*;

/**
 * @author NhatPA
 * @since 28/02/2022 - 03:11
 */
@Service
public class AdminService {
    @Autowired
    private PostElasticSearchRepository postElasticSearchRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    @Qualifier("azure")
    private FileService fileService;

    @Autowired
    private PostService postService;

    @Autowired
    private PostTagRepository postTagRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private EmailProvider emailProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * @return {@link StatisticDto}
     * @author NhatPA
     */
    @Cacheable(cacheNames = ADMIN_STATISTIC_CACHE_NAME, keyGenerator = "keyGenerator")
    public StatisticDto statistic() {
        AdminDetail adminDetail = SecurityUtils.getCurrentAdminOrElseThrow();
        if (BlogAdsUtils.getRole(adminDetail).equalsIgnoreCase(AdminRole.ADMIN.name())) {
            return new StatisticDto((int) postRepository.count(), (int) categoryRepository.count(), (int) tagRepository.count());
        }
        return new StatisticDto((int) postRepository.countByAdmin_AdminId(adminDetail.getAdminId()),
                (int) categoryRepository.count(), (int) tagRepository.count());
    }

    /**
     * @return {@link Page}
     * @author NhatPA
     */
    public PageRes<Post> findLatestPosts() {
        return PaginationUtils.buildPageRes(
                postRepository.findByAdmin_AdminId(
                        SecurityUtils.getCurrentAdminOrElseThrow().getAdminId(),
                        PageRequest.of(0, RECENT_POSTS_DEFAULT, Sort.Direction.DESC, Constants.CREATED_AT)
                )
        );
    }

    //region category

    /**
     * insert category if name not exists
     *
     * @param name category name
     * @author NhatPA
     */
    @Transactional(rollbackFor = Exception.class)
    public void insertCategory(String name) {
        if (categoryRepository.existsByNameEqualsIgnoreCase(name)) {
            throw new BlogAdsApiException(CATEGORY_NAME_EXISTS);
        } else {
            categoryRepository.save(new Category(name));
        }
    }

    /**
     * update category if name exists
     *
     * @param name category name
     * @author NhatPA
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateCategory(int id, String name) {
        Category category = categoryRepository.findById(id).orElseThrow(() ->
                new BlogAdsApiException(CATEGORY_ID_NOT_EXISTS));
        if (category.getName().equals(name)) {
            return;
        }
        if (categoryRepository.existsByNameEqualsIgnoreCaseAndCategoryIdNot(name, category.getCategoryId())) {
            throw new BlogAdsApiException(CATEGORY_NAME_EXISTS);
        }
        category.setName(name);
        categoryRepository.save(category);
    }

    /**
     * delete category by id
     *
     * @param id category id
     * @author NhatPA
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteCategory(int id) {
        Category category = categoryRepository
                .findById(id).orElseThrow(() -> new BlogAdsException(404, CATEGORY_ID_NOT_EXISTS));
        if (!category.getPosts().isEmpty()) {
            throw new BlogAdsException(404, CATEGORY_WAS_USED);
        }
        categoryRepository.delete(category);
    }

    /**
     * find by name and paging
     *
     * @param search   {@link String
     * @param pageable {@link Pageable}
     * @return {@link Category}
     * @author NhatPA
     */
    public PageRes<Category> findCategory(String search, Pageable pageable) {
        return PaginationUtils.buildPageRes(
                categoryRepository.findByNameContaining(search, BlogAdsUtils.validPageable(pageable))
        );
    }
    //endregion

    //region post

    /**
     * find posts by {@link Post#getTitle()}}
     *
     * @param search
     * @param pageable
     * @return {@link Page}
     * @author NhatPA
     */
    public PageRes<Post> findPost(String search, Pageable pageable) {
        AdminDetail admin = SecurityUtils.getCurrentAdminOrElseThrow();
        if (admin.getRoles().get(0).getAuthority().equalsIgnoreCase(AdminRole.ADMIN.name())) {
            return PaginationUtils.buildPageRes(
                    postRepository.findAllByTitleContainingOrAdminContaining(search, validPageable(pageable))
            );
        }
        return PaginationUtils.buildPageRes(
                postRepository.findAllByTitleContainingAndAdmin_AdminId(search,
                        admin.getAdminId(),
                        validPageable(pageable))
        );
    }

    /**
     * find post by id
     *
     * @param id post id
     * @return {@link Post}
     * @author NhatPA
     */
    public Post findPost(int id) {
        return postRepository.findById(id).orElse(null);
    }

    /**
     * insert post
     *
     * @param dto {@link PostRequestDto}
     * @author NhatPA
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public Post insertPost(PostRequestDto dto) {
        Post post = toEntity(dto);
        // UPDATE CATEGORY
        post = postService.updateCategory(post, dto.getCategory());
        post = postRepository.save(post);
        /*upload image*/
        insertImageContentAndConvert(post);
        // UPDATE RELATIONSHIP
        post = postService.updateTag(post, dto.getHashtag());
        // SAVE IMAGE
        if (dto.getImage() == null || dto.getImage().isEmpty()) {
            post.setImage(IMAGE_DEFAULT);
        } else {
            post.setImage(fileService.upload(dto.getImage(), post.getPostId() + IMAGE_POST_CONTENT_PREFIX));
        }
        return post;
    }

    /**
     * delete post by id`
     *
     * @param id post id
     * @author NhatPA
     */
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(cacheNames = ADMIN_POST_CACHE_NAME, allEntries = true)
    public void deletePost(int id) {
        AdminDetail adminDetail = SecurityUtils.getCurrentAdminOrElseThrow();
        String role = adminDetail.getRoles().get(0).getAuthority();
        postRepository.findById(id)
                .filter(i -> role.equalsIgnoreCase(String.valueOf(AdminRole.ADMIN)) ||
                        adminDetail.getAdminId() == (int) i.getAdmin().getAdminId()
                )
                .ifPresent((i) -> {
                    postRepository.delete(i);
                    /*DELETE IMG POST*/
                    postService.deleteImageIfNotDefault(i);
                    /*DELETE IMAGE IN CONTENT*/
                    fileService.listBlobs(i.getPostId() + IMAGE_IN_CONTENT_PREFIX, FileServiceImpl.CONTAINER_NAME)
                            .forEach(item -> fileService.deleteBlob(item.substring(item.lastIndexOf("/") + 1)));
                });
        postElasticSearchRepository.deleteByPostId(id);
    }

    /**
     * update post
     *
     * @param id  post id
     * @param dto new post
     * @author NhatPA
     */
    @Transactional(rollbackFor = Exception.class)
    public Post updatePost(int id, PostRequestDto dto) {
        postTagRepository.deleteByPost(id);
        Optional<Post> postOptional = postRepository.findById(id);
        if (!postOptional.isPresent()) {
            throw new BlogAdsException(400, POST_NOT_FOUND);
        }
        AdminDetail adminDetail = SecurityUtils.getCurrentAdminOrElseThrow();
        String role = adminDetail.getRoles().get(0).getAuthority();
        if (!role.equalsIgnoreCase(String.valueOf(AdminRole.ADMIN)) &&
                adminDetail.getAdminId() != (int) postOptional.get().getAdmin().getAdminId()) {
            throw new BlogAdsException(400, POST_NOT_FOUND);
        }
        return postOptional
                .map(i -> {
                    BlogAdsUtils.updateEntity(dto, i);
                    i = postRepository.save(i);
                    /*UPDATE LIST IMAGE TYPE HTTP*/
                    updateImageContentAndConvert(i);
                    /*INSERT LIST IMAGE TYPE BASE64 TO CLOUD*/
                    insertImageContentAndConvert(i);
                    /*Update category and tag*/
                    if (dto.getCategory() != i.getCategory().getCategoryId()) {
                        i = postService.updateCategory(i, dto.getCategory());
                    }
                    i = postService.updateTag(i, dto.getHashtag());
                    /*If update image, delete image in cloud and set new image */
                    if (dto.getImage() != null && !dto.getImage().isEmpty()) {
                        // IF CURRENT IMAGE IS IMAGE DEFAULT
                        postService.deleteImageIfNotDefault(i);
                        i.setImage(fileService.upload(dto.getImage(), i.getPostId() + IMAGE_POST_CONTENT_PREFIX));
                    } else {
                        /*If use image default*/
                        if (dto.getCurrentUrlImage() == null || dto.getCurrentUrlImage().equals("")) {
                            postService.deleteImageIfNotDefault(i);
                            i.setImage(IMAGE_DEFAULT);
                        }
                    }
                    return i;
                }).orElse(null);
    }

    /**
     * update view post
     *
     * @param p {@link Post}
     * @author NhatPA
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateViewPost(Post p) {
        postRepository.updateView(p.getPostId(), p.getViewNumber() + 1);
    }

    /**
     * insert img type base64
     *
     * @param post
     * @return
     */
    private void insertImageContentAndConvert(Post post) {
        if (post.getContent() == null || post.getContent().equals("")) {
            return;
        }
        /*INSERT POST*/
        Map<String, String> map = postService.getImgBase64(post.getContent());
        String url;
        for (String s : map.keySet()) {
            url = fileService.upload(Base64.getDecoder().decode(s),
                    map.get(s), post.getPostId() + IMAGE_IN_CONTENT_PREFIX);
            /*SET URL*/
            post.setContent(post.getContent().replace(String.format(BASE64_PREFIX, map.get(s), s), url));
        }
    }

    /**
     * delete img http not use
     *
     * @param post {@link Post}
     */
    private void updateImageContentAndConvert(Post post) {
        if (post.getContent() == null || post.getContent().equals("")) {
            return;
        }
        /*Current Img Http*/
        List<String> currentImgHttp = fileService.listBlobs(post.getPostId() + IMAGE_IN_CONTENT_PREFIX, FileServiceImpl.CONTAINER_NAME);
        if (currentImgHttp.isEmpty()) {
            return;
        }
        List<String> newImgHttp = postService.getImgHttp(post.getContent());
        /*UPDATE POST*/
        currentImgHttp.forEach(i -> {
            if (!newImgHttp.contains(i)) {
                fileService.deleteBlob(i.substring(i.lastIndexOf("/") + 1));
            }
        });
    }
    //endregion

    //region tag

    /**
     * insert tag
     *
     * @param name tag name
     * @author NhatPA
     */
    @Transactional(rollbackFor = Exception.class)
    public void insertTag(String name) {
        if (tagRepository.existsByNameEqualsIgnoreCase(name)) {
            throw new BlogAdsApiException(HASHTAG_NAME_EXISTS);
        } else {
            tagRepository.save(new Tag(name));
        }
    }

    /**
     * update tag if name exists
     *
     * @param name tag name
     * @author NhatPA
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateTag(int id, String name) {
        Tag tag = tagRepository.findById(id).orElseThrow(() ->
                new BlogAdsApiException(HASHTAG_ID_NOT_EXISTS));
        if (tag.getName().equals(name)) {
            return;
        }
        if (tagRepository.existsByNameEqualsIgnoreCaseAndTagIdNot(name, tag.getTagId())) {
            throw new BlogAdsApiException(HASHTAG_NAME_EXISTS);
        }
        tag.setName(name);
        tagRepository.save(tag);
    }

    /**
     * delete category by id
     *
     * @param id category id
     * @author NhatPA
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteTag(int id) {
        tagRepository.findById(id).ifPresent(i -> tagRepository.delete(i));
    }

    /**
     * find by name and paging
     *
     * @param search   key search
     * @param pageable {@link Pageable}
     * @return {@link Tag}
     * @author NhatPA
     */
    public PageRes<Tag> findTag(String search, Pageable pageable) {
        return PaginationUtils.buildPageRes(
                tagRepository.findByNameContaining(search, BlogAdsUtils.validPageable(pageable))
        );
    }
    //endregion

    //region forgot password
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    @SneakyThrows
    public void forgotPassword(String mail) {
        Admin admin = adminRepository.findByUsernameIgnoreCase(mail)
                .orElseThrow(() -> new BlogAdsApiException(MAIL_NOT_FOUND));
        admin.setVerifyToken(UUID.randomUUID().toString());
        admin.setExpired(new Date(new Date().getTime() + (1000 * 5 * 60)));
        MailInfo info = MailInfo.builder()
                .emailSubject("Đặt lại mật khẩu")
                .emailTo(mail)
                .content("Link thay đổi mật khẩu "
                        + "<a href='http://localhost:8080/admin/change-password/verify/" + admin.getVerifyToken() + " '>click</a>")
                .build();
        /*Send mail*/
        emailProvider.sendSimpleMail(Collections.singletonList(info));
    }
    //endregion

    //region change password
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public Admin changePassword(ChangePasswordDto request) {
        AdminDetail adminDetail = SecurityUtils.getCurrentAdminOrElseThrow();
        if (!request.getNewPass().equals(request.getConfirmPass())) {
            throw new BlogAdsException(400, CONFIRM_PASSWORD_NOT_MATCH);
        }
        if (!passwordEncoder.matches(request.getCurrentPass(), adminDetail.getPassword())) {
            throw new BlogAdsException(400, PASSWORD_INCORRECT);
        }
        Admin admin = adminRepository.findByUsernameIgnoreCase(adminDetail.getUsername()).get();
        admin.setPassword(passwordEncoder.encode(request.getNewPass()));
        return adminRepository.save(admin);
    }
    //endregion

    //region admin
    public PageRes<Admin> getHosts(Pageable pageable, String search) {
        return PaginationUtils.buildPageRes(
                adminRepository.findByHost(AdminRole.HOST, search, pageable)
        );
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public void register(RegisterDto request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new BlogAdsException(400, CONFIRM_PASSWORD_NOT_MATCH);
        }
        if (adminRepository.findByUsernameIgnoreCase(request.getEmail()).isPresent()) {
            throw new BlogAdsException(400, EMAIL_ALREADY_EXISTS);
        }
        Admin admin = new Admin();
        admin.setPassword(passwordEncoder.encode(request.getPassword()));
        admin.setEnable(true);
        admin.setRole(AdminRole.HOST);
        admin.setUsername(request.getEmail());
        admin.setFullName(request.getFullName());
        adminRepository.save(admin);
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public void updateHost(UpdateAdminDto request) {
        Admin admin = adminRepository.findById(request.getAdminId())
                .orElseThrow(() -> new BlogAdsException(400, ADMIN_NOT_FOUND));
        admin.setEnable(request.getEnable());
    }

    public Admin verifyToken(String token) {
        Admin admin = adminRepository.findByVerifyToken(token).orElseThrow(() -> new BlogAdsException(400, ADMIN_NOT_FOUND));
        if (admin.getExpired() != null && admin.getExpired().after(new Date())) {
            return admin;
        }
        throw new BlogAdsException(400, UNKNOWN_ERROR_MESSAGE);
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public void changePassword(NewPasswordDto request) {
        Admin admin = verifyToken(request.getVerifyToken());
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new BlogAdsException(400, CONFIRM_PASSWORD_NOT_MATCH);
        }
        admin.setPassword(passwordEncoder.encode(request.getPassword()));
        admin.setVerifyToken(null);
        admin.setExpired(null);
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public void updateHost(int id, boolean b) {
        Admin admin = adminRepository.findById(id).orElseThrow(() -> new BlogAdsException(400, ADMIN_NOT_FOUND));
        admin.setEnable(b);
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.SERIALIZABLE)
    public void updateInfo(UpdateInfoAdminDto request) {
        AdminDetail adminDetail = SecurityUtils.getCurrentAdminOrElseThrow();
        Admin admin = adminRepository.findById(adminDetail.getAdminId())
                .orElseThrow(() -> new BlogAdsException(400, ADMIN_NOT_FOUND));
        admin.setFullName(request.getFullName());
        admin.setAddress(request.getAddress());
        SecurityUtils.setCurrentAdmin(admin);
    }
    //endregion
}
