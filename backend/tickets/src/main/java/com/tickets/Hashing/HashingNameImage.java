package com.tickets.Hashing;

import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;

@Component
public class HashingNameImage extends Hashing{
    @Override
    public String SHA256hashing(String nameImage) throws NoSuchAlgorithmException {
        String fileExtension = nameImage.substring(nameImage.lastIndexOf("."));
        String fileName = nameImage.substring(0,nameImage.lastIndexOf("."));
        return super.SHA256hashing(fileName) + fileExtension;
    }
}
