package com.tickets.Converters;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Component
public class ConvertImageToMultipartFile implements Convert<MultipartFile, String> {
    @Override
    public MultipartFile convert(String hash) {
        File file = new File(String.format("tickets/src/main/resources/static/images/%s",hash));
        try(FileInputStream input = new FileInputStream(file);){
            return new MockMultipartFile(
                    file.getName(),
                    FileCopyUtils.copyToByteArray(input)
            );
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }
}
