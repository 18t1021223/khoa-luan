package com.blogads.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

import static com.blogads.exception.BlogAdsException.UNKNOWN_ERROR_MESSAGE;

/**
 * @author NhatPA
 * @since 25/02/2022 - 12:05
 */
@ControllerAdvice
@Slf4j
public class HandleException {

    /**
     * error logic
     *
     * @param e
     * @return {@link ModelAndView}
     * @author NhatPA
     */
    @ExceptionHandler(value = {BlogAdsException.class})
    public ModelAndView exceptionLogic(Exception e) {
        ModelAndView mav = new ModelAndView("error_page");
        mav.addObject("cause", e);
        return mav;
    }

    /**
     * error logic
     *
     * @param e
     * @return {@link String}
     * @author NhatPA
     */
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {BlogAdsApiException.class})
    public String exceptionLogic(BlogAdsApiException e) {
        return e.getMessage();
    }

    /**
     * validate data request
     *
     * @param ex
     * @return {@link List}
     * @author NhatPA
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public List<?> handleConstraintViolation(ConstraintViolationException ex) {
        log.error("validate - error: " + ex.getMessage());
        return ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
    }

    /**
     * error system
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = {Exception.class})
    public ModelAndView exceptionSystem(Exception e) {
        ModelAndView mav = new ModelAndView("error_page");
        log.error("exception: {}", e.getMessage());
        mav.addObject("cause",
                new BlogAdsException(HttpStatus.INTERNAL_SERVER_ERROR.value(), UNKNOWN_ERROR_MESSAGE));
        return mav;
    }
}
