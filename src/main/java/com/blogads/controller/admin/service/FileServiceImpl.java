package com.blogads.controller.admin.service;

import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.microsoft.azure.storage.blob.ListBlobItem;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * use azure
 *
 * @author NhatPA
 * @since 02/03/2022 - 02:51
 */
@Service("azure")
@Slf4j
public class FileServiceImpl implements FileService {

    public static final String CONTAINER_NAME = "imgs";

    @Autowired
    private CloudBlobClient cloudBlobClient;

    @Autowired
    private CloudBlobContainer cloudBlobContainer;

//    public boolean createContainer(String containerName) {
//        boolean containerCreated = false;
//        CloudBlobContainer container = null;
//        try {
//            container = cloudBlobClient.getContainerReference(containerName);
//        } catch (URISyntaxException e) {
//            logger.error(e.getMessage());
//            e.printStackTrace();
//        } catch (StorageException e) {
//            logger.error(e.getMessage());
//            e.printStackTrace();
//        }
//        try {
//            containerCreated = container.createIfNotExists(BlobContainerPublicAccessType.CONTAINER, new BlobRequestOptions(), new OperationContext());
//        } catch (StorageException e) {
//            logger.error(e.getMessage());
//            e.printStackTrace();
//        }
//        return containerCreated;
//    }

    /**
     * upload file
     *
     * @param multipartFile {@link MultipartFile}
     * @return String url file
     * @author NhatPA
     */
    @SneakyThrows
    public String upload(MultipartFile multipartFile, String prefix) {
        CloudBlockBlob blob = cloudBlobContainer.getBlockBlobReference(prefix + UUID.randomUUID());
        blob.getProperties().setContentType(multipartFile.getContentType());
        blob.upload(multipartFile.getInputStream(), multipartFile.getSize());
        return blob.getUri().toString();
    }

    /**
     * update file byte[]
     *
     * @param data
     * @param contentType
     * @param prefix
     * @return {@link String} url file
     */
    @Override
    public String upload(byte[] data, String contentType, String prefix) {
        try {
            CloudBlockBlob blob = cloudBlobContainer.getBlockBlobReference(prefix + UUID.randomUUID());
            blob.getProperties().setContentType(contentType);
            blob.uploadFromByteArray(data, 0, data.length);
            return blob.getUri().toString();
        } catch (Exception e) {
            log.error("upload image {}", e.getMessage());
        }
        return null;
    }

    /**
     * get list url images
     * <p style='color:yellow'><b>If prefix is do not used, set it is null</b></p>
     *
     * @param prefix        prefix name image
     * @param containerName container
     * @return {@link URI}
     * @author NhatPA
     */
    @SneakyThrows
    public List<String> listBlobs(String prefix, String containerName) {
        List<String> uris = new ArrayList<>();
        CloudBlobContainer container = cloudBlobClient.getContainerReference(containerName);
        for (ListBlobItem blobItem : container.listBlobs(prefix)) {
            uris.add(blobItem.getUri().toString());
        }
        return uris;
    }

    /**
     * delete image
     *
     * @param blobName name
     * @author NhatPA
     */
    @SneakyThrows
    public void deleteBlob(String blobName) {
        CloudBlobContainer container = cloudBlobClient.getContainerReference(CONTAINER_NAME);
        CloudBlockBlob blobToBeDeleted = container.getBlockBlobReference(blobName);
        blobToBeDeleted.deleteIfExists();
    }

    /**
     * undelete image
     *
     * @param blobName {@link String} name image
     * @author NhatPA
     */
    @SneakyThrows
    public void restoreBlob(String blobName) {
        CloudBlobContainer container = cloudBlobClient.getContainerReference(CONTAINER_NAME);
        container.getBlockBlobReference(blobName).undelete();
    }
}
