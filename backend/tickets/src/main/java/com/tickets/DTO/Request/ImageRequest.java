package com.tickets.DTO.Request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImageRequest {
    private Long questionId;
    private MultipartFile image;
}
