package com.tickets.Services;

import ch.qos.logback.core.util.ContentTypeUtil;
import com.tickets.Converters.ConvertImageToMultipartFile;
import com.tickets.Converters.ConvertMultipartFileToByteArrayResources;
import com.tickets.DTO.Request.ImageRequest;
import com.tickets.DTO.Response.DefaultResponse;
import com.tickets.DTO.Response.Response;
import com.tickets.Hashing.HashingNameImage;
import com.tickets.Models.Questions;
import com.tickets.Models.Ticket;
import com.tickets.Repositories.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final AnswerService answerService;
    private final HashingNameImage hashing;
    private final ConvertMultipartFileToByteArrayResources convertMultipartFileToByteArrayResources;
    private final ConvertImageToMultipartFile convertImageToMultipartFile;

    @Transactional
    protected void save(Ticket ticket){
        for (Questions q : ticket.getQuestions()) {
            q.setTicket(ticket);
            Questions question = questionRepository.save(q);
            answerService.save(question);
        }
    }

    public ResponseEntity<Response> addImage(ImageRequest imageRequest) {
        if(imageRequest.getImage() == null || imageRequest.getQuestionId() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    DefaultResponse
                            .builder()
                            .status(HttpStatus.BAD_REQUEST)
                            .code(HttpStatus.BAD_REQUEST.value())
                            .message("Не была получена картинка или Id вопроса билета.")
                            .build()
            );
        }

        Questions questions = questionRepository.findById(imageRequest.getQuestionId()).orElseThrow();

        MultipartFile savedFile = imageRequest.getImage();
        String nameFile = imageRequest.getImage().getOriginalFilename();

        try {
            assert nameFile != null;
            nameFile = hashing.SHA256hashing(nameFile);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        String directory = "tickets/src/main/resources/static/images";
        File uploadDir = new File(directory);

        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        Path filePath = Path.of(directory,nameFile);

        try {
            Files.copy(savedFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        questions.setHashImage(nameFile);

        questionRepository.save(questions);

        return ResponseEntity.status(HttpStatus.OK).body(
                DefaultResponse
                        .builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .message("Картинка успешно сохранена.")
                        .build()
        );
    }

        public ResponseEntity<ByteArrayResource> getImage(String questionId) {
        try {
            Questions question = questionRepository.findById(Long.parseLong(questionId)).orElseThrow();
            MultipartFile multipartFile = convertImageToMultipartFile.convert(question.getHashImage());
            ByteArrayResource resource = convertMultipartFileToByteArrayResources.convert(multipartFile);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="
                            + Objects.requireNonNull(
                            question.getHashImage())
                    )
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
                    .body(resource);
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
