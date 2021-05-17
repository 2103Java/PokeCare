package com.revature.pokecare.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;

@Service
public class FileService {
    private final S3Client s3 = S3Client.builder().region(Region.US_EAST_1).build(); //TODO replace with Spring?
    private final static Logger loggy = Logger.getLogger(FileService.class);

    public void putFile(String name, InputStream input) {
        try {
            s3.putObject(PutObjectRequest.builder().bucket("pokecare").key(name).build(), RequestBody.fromInputStream(input, input.available()));
        } catch (IOException e) {
            loggy.warn(e);
            e.printStackTrace();
        }
    }
}