package com.blogads.controller.admin;

import com.blogads.controller.admin.dto.*;
import com.blogads.controller.admin.service.AdminService;
import com.blogads.entity.mysql.Post;
import com.blogads.utils.BlogAdsUtils;
import com.blogads.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.blogads.exception.BlogAdsApiException.CATEGORY_NAME_MUST_NOT_BLANK;
import static com.blogads.exception.BlogAdsApiException.TAG_NAME_MUST_NOT_BLANK;
import static com.blogads.utils.BlogAdsUtils.getFullName;
import static com.blogads.utils.Constants.*;

/**
 * @author NhatPA
 * @since 28/02/2022 - 02:02
 */
@Controller
@RequestMapping("/admin")
@Validated
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * admin home page
     *
     * @return {@link ModelAndView}
     * @author NhatPA
     */
    @GetMapping(value = {"/home", ""})
    public ModelAndView home() {
        ModelAndView mav = new ModelAndView("admin-home");
        mav.addObject("posts", adminService.findLatestPosts());
        mav.addObject("statistic", adminService.statistic());
        return mav;
    }

    //region post

    /**
     * @param pageable {@link Pageable}
     * @param search   {@link String}
     * @return {@link ModelAndView}
     * @author NhatPA
     */
    @GetMapping("/posts")
    public ModelAndView posts(@PageableDefault(
            size = POST_SIZE_ADMIN,
            sort = CREATED_AT,
            direction = Sort.Direction.DESC) Pageable pageable,
                              @RequestParam(name = "search", defaultValue = "", required = false) String search) {
        ModelAndView mav = new ModelAndView("admin-post");
        mav.addObject("paging", adminService.findPost(search, pageable));
        mav.addObject("search", search);
        return mav;
    }

    /**
     * find post by id or return page insert post
     *
     * @param postId post id
     * @return {@link ModelAndView}
     * @author NhatPA
     */
    @GetMapping(value = {"/posts/{id}", "/post"})
    public ModelAndView posts(@PathVariable(value = "id", required = false) Integer postId) {
        ModelAndView mav = new ModelAndView("admin-post-single");
        mav.addObject("post", postId == null ?
                new Post(getFullName()) :
                adminService.findPost(postId)
        );
        Pageable pageable = BlogAdsUtils.unPaged();
        mav.addObject("categories", adminService.findCategory("", pageable).getContent());
        mav.addObject("hashtags", adminService.findTag("", pageable).getContent());
        return mav;
    }

    /**
     * delete post
     *
     * @param id post id
     * @author NhatPA
     */
    @GetMapping("/delete-post/{id}")
    public String deletePost(@PathVariable int id) {
        adminService.deletePost(id);
        return "redirect:/admin/posts";
    }

    /**
     * insert post
     *
     * @param dto
     * @return {@link String}
     * @author NhatPA
     */
    @PostMapping("/post")
    public String insertPost(PostRequestDto dto) {
        return "redirect:/admin/posts/" + adminService.insertPost(dto).getPostId();
    }

    /**
     * update post
     *
     * @param dto {@link PostRequestDto}
     * @return {@link String}
     * @author NhatPA
     */
    @PostMapping("/update-post/{id}")
    public String updatePost(@PathVariable("id") Integer id, PostRequestDto dto) {
        Post p = adminService.updatePost(id, dto);
        if (p != null) {
            return "redirect:/admin/posts/" + p.getPostId();
        }
        return "redirect:/admin/posts";
    }
    //endregion

    //region category
    @GetMapping("/categories")
    public ModelAndView categories(@PageableDefault(
            size = CATEGORY_SIZE_ADMIN,
            direction = Sort.Direction.DESC) Pageable pageable,
                                   @RequestParam(name = "search", defaultValue = "", required = false) String search) {
        ModelAndView mav = new ModelAndView("admin-category");
        mav.addObject("paging", adminService.findCategory(search, pageable));
        mav.addObject("search", search);
        return mav;
    }

    /**
     * API insert category
     *
     * @param name
     * @return {@link String}
     * @author NhatPA
     */
    @PostMapping("/insert-category")
    @ResponseBody
    public String insertCategory(@RequestParam("name")
                                 @NotBlank(message = CATEGORY_NAME_MUST_NOT_BLANK)
                                 @Size(min = 1, max = 50) @NotNull String name) {
        adminService.insertCategory(name);
        return "success";
    }

    /**
     * delete category
     *
     * @param id
     * @return {@link String}
     * @author NhatPA
     */
    @GetMapping("/delete-category/{id}")
    public String deleteCategory(@PathVariable int id) {
        adminService.deleteCategory(id);
        return "redirect:/admin/categories";
    }

    /**
     * API update category
     *
     * @param id
     * @param name
     * @return {@link String}
     * @author NhatPA
     */
    @PostMapping("/update-category")
    @ResponseBody
    public String updateCategory(@RequestParam("id") @Min(1) @NotNull Integer id,
                                 @RequestParam("name")
                                 @NotBlank(message = CATEGORY_NAME_MUST_NOT_BLANK)
                                 @Size(min = 1, max = 50) @NotNull String name) {
        adminService.updateCategory(id, name);
        return "success";
    }
    //endregion

    //region tag
    @GetMapping("/hashtags")
    public ModelAndView hashtags(@PageableDefault(
            size = HASHTAG_SIZE_ADMIN,
            direction = Sort.Direction.DESC) Pageable pageable,
                                 @RequestParam(name = "search", defaultValue = "", required = false) String search) {
        ModelAndView mav = new ModelAndView("admin-tag");
        mav.addObject("paging", adminService.findTag(search, pageable));
        mav.addObject("search", search);
        return mav;
    }

    /**
     * API insert tag
     *
     * @param name
     * @return {@link String}
     * @author NhatPA
     */
    @PostMapping("/insert-hashtag")
    @ResponseBody
    public String insertTag(@RequestParam("name")
                            @NotBlank(message = TAG_NAME_MUST_NOT_BLANK)
                            @Size(min = 1, max = 50)
                            @NotNull String name) {
        adminService.insertTag(name);
        return "success";
    }

    /**
     * delete category
     *
     * @param id
     * @return {@link String}
     * @author NhatPA
     */
    @GetMapping("/delete-hashtag/{id}")
    public String deleteTag(@PathVariable int id) {
        adminService.deleteTag(id);
        return "redirect:/admin/hashtags";
    }

    /**
     * API update category
     *
     * @param id
     * @param name
     * @return {@link String}
     * @author NhatPA
     */
    @PostMapping("/update-hashtag")
    @ResponseBody
    public String updateTag(@RequestParam("id") @Min(1) @NotNull Integer id,
                            @RequestParam("name")
                            @Size(min = 1, max = 50)
                            @NotBlank(message = TAG_NAME_MUST_NOT_BLANK)
                            @NotNull String name) {
        adminService.updateTag(id, name);
        return "success";
    }
    //endregion

    //region register
    @GetMapping("/register")
    public ModelAndView register(@RequestParam(required = false) String message) {
        ModelAndView mav = new ModelAndView("admin-register");
        mav.addObject("message", message);
        return mav;
    }

    @PostMapping("/register")
    public String register(@Valid RegisterDto request, BindingResult b) {
        if (b.hasErrors()) {
            return "redirect:/admin/register?message=" + b.getAllErrors().get(0).getDefaultMessage();
        }
        adminService.register(request);
        return "redirect:/admin/login";
    }

    //endregion
    //region forgot password
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    //region forgot
    @GetMapping("/forgot-password")
    public ModelAndView forgotPasswordd(@RequestParam(required = false) String message,
                                        @RequestParam(required = false) String email) {
        ModelAndView mav = new ModelAndView("admin-forgot-password");
        mav.addObject("message", message);
        mav.addObject("email", email);
        return mav;
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@Email @NotBlank @RequestParam(value = "mail") String mail) throws UnsupportedEncodingException {
        executorService.execute(() -> adminService.forgotPassword(mail));
        return "redirect:/admin/forgot-password?message=" +
                URLEncoder.encode("đã gửi tin nhắn", "UTF-8") +
                "&email=" + mail;
    }
    //endregion
    //endregion

    //region change password
    @GetMapping("/change-password")
    public String changePassword() {
        return "admin-change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(@Valid ChangePasswordDto request) {
        SecurityUtils.setCurrentAdmin(adminService.changePassword(request));
        return "redirect:/admin/home";
    }

    @GetMapping("/change-password/verify/{token}")
    public String changePassword(@PathVariable String token) {
        return "redirect:/admin/new-password/" + adminService.verifyToken(token).getVerifyToken();
    }

    @GetMapping("/new-password/{token}")
    public ModelAndView newPass(@PathVariable String token) {
        ModelAndView mav = new ModelAndView("admin-new-password");
        mav.addObject("verifyToken", token);
        return mav;
    }

    @PostMapping("/new-password")
    public String newPass(@Valid NewPasswordDto request) {
        adminService.changePassword(request);
        return "redirect:/admin/login";
    }
    //endregion

    //region admin host
    @PreAuthorize(value = "hasAnyAuthority('ADMIN')")
    @GetMapping("/hosts")
    public ModelAndView adminHost(@PageableDefault(
            size = POST_SIZE_ADMIN,
            direction = Sort.Direction.DESC) Pageable pageable,
                                  @RequestParam(name = "search", defaultValue = "", required = false) String search) {
        ModelAndView mav = new ModelAndView("host");
        mav.addObject("hosts", adminService.getHosts(pageable, search));
        mav.addObject("search", search);
        mav.addObject("admin", SecurityUtils.getCurrentAdminOrElseThrow());
        return mav;
    }

    @PreAuthorize(value = "hasAnyAuthority('ADMIN')")
    @GetMapping("/hosts/{id}")
    public String updateHost(@PathVariable int id, @NotNull @RequestParam boolean b) {
        adminService.updateHost(id, b);
        return "redirect:/admin/hosts";
    }

    @GetMapping("/hosts/update")
    public String updateHost(@Valid UpdateInfoAdminDto request) {
        adminService.updateInfo(request);
        return "redirect:/admin/";
    }
    //endregion
}
