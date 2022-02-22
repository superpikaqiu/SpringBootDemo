package com.lwb.demo.util;


import cn.hutool.core.util.ZipUtil;
import io.minio.*;
import io.minio.http.Method;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lwb
 * @date 2021/12/24 16:38
 * @description TODO
 */
public class MinioUtil {
    private static MinioClient minioClient;

    private static MinioClient getMinioClient(){
        if(minioClient == null){
            minioClient = MinioClient.builder()
                    .endpoint("http://192.168.21.151:9001")
                    .credentials("admin","admin123456").build();
        }
        return minioClient;
    }

    private static void makeBucket(String bucketName){
        try{
            MinioClient minioClient = getMinioClient();
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if(!found){
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void putObject(InputStream inputStream, String bucketName, String fileName){
        try{
            MinioClient minioClient = getMinioClient();
            makeBucket(bucketName);
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .stream(inputStream, inputStream.available(), -1)
                    .build());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void putFolder(String bucketName, String folderName){
        try{
            MinioClient minioClient = getMinioClient();
            makeBucket(bucketName);
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(folderName)
                    .stream(new ByteArrayInputStream(new byte[]{}), 0, -1)
                    .build());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static byte[] getObject(String bucketName, String fileName){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try{
            MinioClient minioClient = getMinioClient();
            GetObjectResponse object = minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(fileName).build());

            byte[] buf = new byte[4096];
            int length;
            while ((length = object.read(buf, 0, buf.length)) != -1){
                outputStream.write(buf, 0, length);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return outputStream.toByteArray();
    }

    public static String getObjectUrl(String bucketName, String fileName){
        String url = null;
        try{
            MinioClient minioClient = getMinioClient();
            url = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(bucketName)
                    .object(fileName)
                    .expiry(60)
                    .build());
            System.out.println("url:" + url);
        } catch (Exception e){
            e.printStackTrace();
        }
        return url;
    }

    public static void composeObject(){
        try{
            List<InputStream> list = new ArrayList<>();
            list.add(new ByteArrayInputStream(getObject("test", "test.docx")));
            list.add(new ByteArrayInputStream(getObject("test", "新建 XLSX 工作表.xlsx")));
            List<String> pathList = new ArrayList<>();
            pathList.add("路径1/test.docx");
            pathList.add("路径2/新建 XLSX 工作表.xlsx");
            ZipUtil.zip(new File("D:/test.zip"), pathList.toArray(new String[0]), list.toArray(new InputStream[0]));
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
