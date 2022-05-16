package com.blogads.annotation.valid;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author NhatPA
 * @since 02/03/2022 - 02:33
 */
public class FileConstraintValidator implements ConstraintValidator<File, MultipartFile> {

    private List<String> format;

    public void initialize(File constraint) {
        format = Arrays.asList(constraint.format());
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
//        if (multipartFile.isEmpty()) {
//            return false;
//        }
        if (multipartFile == null) {
            return false;
        }
        String extension = this.getExtension(multipartFile.getOriginalFilename());
        return extension != null && format.contains(extension.toUpperCase());
    }

    private String getExtension(String fileName) {
        return Optional.ofNullable(fileName)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(fileName.lastIndexOf(".") + 1))
                .orElse(null);
    }
}
