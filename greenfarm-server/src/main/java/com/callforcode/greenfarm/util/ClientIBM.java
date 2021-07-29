package com.callforcode.greenfarm.util;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ibm.cloud.objectstorage.AmazonClientException;
import com.ibm.cloud.objectstorage.AmazonServiceException;
import com.ibm.cloud.objectstorage.ClientConfiguration;
import com.ibm.cloud.objectstorage.SDKGlobalConfiguration;
import com.ibm.cloud.objectstorage.auth.AWSCredentials;
import com.ibm.cloud.objectstorage.auth.AWSStaticCredentialsProvider;
import com.ibm.cloud.objectstorage.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.ibm.cloud.objectstorage.oauth.BasicIBMOAuthCredentials;
import com.ibm.cloud.objectstorage.services.s3.AmazonS3;
import com.ibm.cloud.objectstorage.services.s3.AmazonS3ClientBuilder;
import com.ibm.cloud.objectstorage.services.s3.model.GetObjectRequest;
import com.ibm.cloud.objectstorage.services.s3.model.ObjectMetadata;
import com.ibm.cloud.objectstorage.services.s3.transfer.TransferManager;
import com.ibm.cloud.objectstorage.services.s3.transfer.TransferManagerBuilder;
import com.ibm.cloud.objectstorage.services.s3.transfer.Upload;

/*
 * ibm object storage
 */
public class ClientIBM {

    private static final String COS_ENDPOINT = "s3.jd-tok.cloud-object-storage.appdomain.cloud";
    
    private static final String COS_API_KEY_ID = "vx_C1DEeB_kjeURcE5ezdEtgyGuJD6N8bwPEA5qMKMw9";
    
    private static final String COS_AUTH_ENDPOINT = "https://iam.cloud.ibm.com/indentity/token";
    
    private static final String COS_SERVICE_CRN = "crn:v1:bluemix:public:cloud-object-storage:global:a/186bbaf9490e4695955a809d94e2bebc:1cde3f95-8e23-43a1-85e8-25d4f161f462:bucket:huxintest";
    
    private static final String COS_BUCKET_LOCATION = "jd-tok-huxintest";
    
    private static String bucketName = "huxintest";

    private static final String CODE_404 = "The file does exist";
    
    private static final String CODE_400 = "upload file error:";
    
    private static final String CODE_200 = "upload file success";

    public static String uploadIBM(String path, MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        File file = new File(path + originalFilename);
        String itemName = UUID.randomUUID().toString().replace("-", "") + originalFilename;
        try {
            FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String largeObjectUpload = largeObjectUpload(bucketName, itemName, file);
        if (CODE_404.equals(largeObjectUpload)) {
            return CODE_404;
        } else if (CODE_200.equals(largeObjectUpload)) {
            return itemName;
        } else {
            return CODE_400;
        }
    }

    public static AmazonS3 createClient(String apiKey, String serviceInstanceId, String endpointUrl,
            String location) {
        SDKGlobalConfiguration.IAM_ENDPOINT = COS_AUTH_ENDPOINT;
        AWSCredentials credentials = new BasicIBMOAuthCredentials(apiKey, serviceInstanceId);
        ClientConfiguration clientConfig = new ClientConfiguration().withRequestTimeout(30000);
        clientConfig.setUseTcpKeepAlive(true);

        AmazonS3 cos = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withEndpointConfiguration(new EndpointConfiguration(endpointUrl, location))
                .withPathStyleAccessEnabled(true).withClientConfiguration(clientConfig).build();
        return cos;
    }

    public static String largeObjectUpload(String bucketName, String itemName, File uploadFile) {
        if (!uploadFile.isFile()) {
            return CODE_404;
        }
        long partSize = 1024 * 1024 * 5;

        long thresholdSize = 1024 * 1024 * 5;

        String endPoint = COS_ENDPOINT;
        
        AmazonS3 s3Client = createClient(COS_API_KEY_ID, COS_SERVICE_CRN, endPoint, COS_BUCKET_LOCATION);

        TransferManager build = TransferManagerBuilder.standard().withS3Client(s3Client)
                .withMinimumUploadPartSize(partSize).withMultipartCopyThreshold(thresholdSize).build();

        try {

            Upload upload = build.upload(bucketName, itemName, uploadFile);
            upload.waitForCompletion();

        } catch (AmazonServiceException e) {
            e.printStackTrace();
            return CODE_400 + e.getMessage();
        } catch (AmazonClientException e) {
            e.printStackTrace();
            return CODE_400 + e.getMessage();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return CODE_400 + e.getMessage();
        } finally {
            build.shutdownNow();
        }
        return CODE_200;
    }

    public static ObjectMetadata largeObjectDownload(String name, String path) {
        AmazonS3 s3Client = createClient(COS_API_KEY_ID, COS_SERVICE_CRN, COS_ENDPOINT, COS_BUCKET_LOCATION);
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, name);
        ObjectMetadata object = s3Client.getObject(getObjectRequest, new File(path));
        return object;
    }

    public static String getCode404() {
        return CODE_404;
    }

    public static String getCode400() {
        return CODE_400;
    }

    public static String getCode200() {
        return CODE_200;
    }

}
