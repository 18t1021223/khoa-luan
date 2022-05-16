package com.blogads.controller.admin.service;

import com.blogads.entity.mysql.Post;
import com.blogads.entity.mysql.PostTag;
import com.blogads.entity.mysql.Tag;
import com.blogads.exception.BlogAdsException;
import com.blogads.repository.mysql.CategoryRepository;
import com.blogads.repository.mysql.TagRepository;
import com.blogads.vo.ImgType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.blogads.exception.BlogAdsApiException.CATEGORY_ID_NOT_EXISTS;
import static com.blogads.utils.Constants.IMAGE_DEFAULT;

/**
 * @author NhatPA
 * @since 03/03/2022 - 21:06
 */
@Service
public class PostService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    @Qualifier("azure")
    private FileService fileService;

    /**
     * update category
     *
     * @param post
     * @param CategoryId
     * @author NhatPA
     */
    public Post updateCategory(Post post, int CategoryId) {
        post.setCategory(categoryRepository.findById(CategoryId).orElseThrow(() ->
                new BlogAdsException(404, CATEGORY_ID_NOT_EXISTS))
        );
        return post;
    }

    /**
     * update tags
     *
     * @param post
     * @param tagsRequest
     * @author NhatPA
     */
    public Post updateTag(Post post, Integer[] tagsRequest) {
        if (tagsRequest != null && tagsRequest.length > 0) {
            List<Tag> tags = tagRepository.findAllById(Arrays.asList(tagsRequest));
            if (!tags.isEmpty()) {
                List<PostTag> postTags = tags.stream().map(i -> new PostTag(post, i)).collect(Collectors.toList());
                post.setPostTags(postTags);
            }
        }
        return post;
    }

    /**
     * delete image if not default
     *
     * @param i {@link Post}
     * @author NhatPA
     */
    public void deleteImageIfNotDefault(Post i) {
        if (!i.getImage().equals(IMAGE_DEFAULT)) {
            fileService.deleteBlob(this.getNameImage(i));
        }
    }

    /**
     * get image name
     *
     * @param i Post
     * @return {@link String}
     * @author Nhatpa
     */
    public String getNameImage(Post i) {
        return i.getImage().substring(i.getImage().lastIndexOf("/") + 1);
    }

    //region image

    /**
     * get list tag img
     *
     * @param content {@link Post#getContent()}
     * @return {@link List}
     * @author NhatPA
     */
    private List<String> getListTagImgInContent(String content) {
        String startFind = "<img";
        String endFind = "/>";
        List<String> list = new ArrayList<>();
        int nextIndex = 0;
        int startCopy;
        int endCopy;
        while (true) {
            startCopy = content.indexOf(startFind, nextIndex);
            if (startCopy == -1) {
                break;
            }
            endCopy = content.indexOf(endFind, startCopy + startFind.length() + 1);
            if (endCopy == -1) {
                throw new BlogAdsException(404, BlogAdsException.LINK_IMAGE_INVALID);
            }
            /*CHECK: <img src="" <img src="" />*/
            if (content.substring(startCopy + startFind.length() + 1, endCopy).contains(startFind)) {
                throw new BlogAdsException(404, BlogAdsException.LINK_IMAGE_INVALID);
            }
            list.add(content.substring(startCopy, endCopy));
            nextIndex = endCopy + 1;
        }
        return list;
    }

    /**
     * get value src from tag img
     *
     * @param str {@link String} tag img
     * @return {@link List} String value of img
     * @author NhatPA
     */
    public List<String> getSrcImg(String str) {
        List<String> listImg = this.getListTagImgInContent(str);
        String startFind = "src=\"";
        String endFind = "\"";
        int startCopy;
        int endCopy;
        String content;
        for (int i = 0; i < listImg.size(); i++) {
            content = listImg.get(i);
            startCopy = content.indexOf(startFind);
            if (startCopy == -1) {
                throw new BlogAdsException(404, BlogAdsException.LINK_IMAGE_INVALID);
            }
            endCopy = content.indexOf(endFind, startCopy + startFind.length() + 1);
            if (endCopy == -1) {
                throw new BlogAdsException(404, BlogAdsException.LINK_IMAGE_INVALID);
            }
            listImg.set(i, content.substring(startCopy + startFind.length(), endCopy));
        }
        return listImg;
    }

    /**
     * get list image http or https
     *
     * @param str @link Post#getContent()}
     * @return {@link List}
     * @author NhatPA
     */
    public List<String> getImgHttp(String str) {
        List<String> rs = new ArrayList<>();
        for (String content : this.getSrcImg(str)) {
            /* CHECK SRC IS TYPE HTTP*/
            if (content.startsWith(ImgType.HTTP_OR_HTTPS.getType())) {
                rs.add(content);
            }
        }
        return rs;
    }

    /**
     * get list image base64
     *
     * @param str @link Post#getContent()}
     * @return {@link Map} key base64, value contentType
     * @author NhatPA
     */
    public Map<String, String> getImgBase64(String str) {
        Map<String, String> map = new LinkedHashMap<>();
        int indexCopy;
        for (String content : this.getSrcImg(str)) {
            /* CHECK SRC IS TYPE BASE64*/
            indexCopy = content.indexOf(ImgType.BASE64.getType());
            /* data:image/png;base64, */
            if (indexCopy != -1) {
                map.put(content.substring(indexCopy + ImgType.BASE64.getType().length()),
                        content.substring(content.indexOf(":") + 1, content.indexOf(";")));
            }
        }
        return map;
    }
    //endregion
}
