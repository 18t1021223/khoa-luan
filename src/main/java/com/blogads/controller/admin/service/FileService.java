package com.blogads.controller.admin.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author NhatPA
 * @since 03/03/2022 - 09:21
 */
public interface FileService {

    /**
     * upload image
     *
     * @param multipartFile
     * @return {@link String} url image
     * @author NhatPA
     */
    String upload(MultipartFile multipartFile, String prefix);

    String upload(byte[] data, String contentType, String prefix);

    /**
     * get list image
     *
     * @param prefix        prefix name image
     * @param containerName container
     * @return {@link List}
     * @author NhatPA
     */
    List<String> listBlobs(String prefix, String containerName);

    /**
     * delete image by name
     *
     * @param blobName image name
     */
    void deleteBlob(String blobName);

    /**
     * undelete image
     *
     * @param blobName {@link String} name image
     * @author NhatPA
     */
    void restoreBlob(String blobName);
}
