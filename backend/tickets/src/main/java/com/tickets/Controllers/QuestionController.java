package com.tickets.Controllers;

import com.tickets.DTO.Request.ImageRequest;
import com.tickets.DTO.Response.Response;
import com.tickets.Services.QuestionService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/questions")
@AllArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping("/addImge")
    public ResponseEntity<Response> addImage(@ModelAttribute ImageRequest imageRequest){
        return questionService.addImage(imageRequest);
    }

    @GetMapping("/get/image/{questionId}")
    public ResponseEntity<ByteArrayResource> getImage(@PathVariable String questionId){
        return questionService.getImage(questionId);
    }

}
