package com.blogads.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author NhatPA
 * @since 28/02/2022 - 02:04
 */
@Controller
public class GuestController {

    @GetMapping("/admin/login")
    public ModelAndView login(@RequestParam(value = "message", required = false) String message) {
        ModelAndView mav = new ModelAndView("admin-login");
        mav.addObject("message", message);
        return mav;
    }
}
