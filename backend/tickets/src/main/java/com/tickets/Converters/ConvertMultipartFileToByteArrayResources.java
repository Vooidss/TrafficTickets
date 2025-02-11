package com.tickets.Converters;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class ConvertMultipartFileToByteArrayResources implements Convert<ByteArrayResource, MultipartFile> {


    @Override
    public ByteArrayResource convert(MultipartFile multipartFile) {
        ByteArrayResource resource = null;
        try {
            resource = new ByteArrayResource(multipartFile.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return resource;
    }
}
