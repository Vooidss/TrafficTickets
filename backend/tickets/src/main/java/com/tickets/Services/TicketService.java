package com.tickets.Services;

import com.tickets.DTO.Request.TicketRequest;
import com.tickets.DTO.Request.TicketsRequest;
import com.tickets.DTO.Response.DefaultResponse;
import com.tickets.DTO.Response.GetAllTicketsResponse;
import com.tickets.DTO.Response.GetObjectResponse;
import com.tickets.DTO.Response.Response;
import com.tickets.Models.Ticket;
import com.tickets.Repositories.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final QuestionService questionService;

    public ResponseEntity<Response> getTicketByNumber(String number) {
         Ticket ticket = ticketRepository.findById(Long.parseLong(number)).orElseThrow();

         return ResponseEntity.ok().body(
                 GetObjectResponse.builder()
                         .ticket(ticket)
                         .message("Билет возвращён")
                         .status(HttpStatus.OK)
                         .code(HttpStatus.OK.value())
                         .build()
         );

    }

    public ResponseEntity<Response> getAllTickets() {
        List<Ticket> tickets = ticketRepository.findAll();
        tickets.sort(Comparator.comparing(Ticket::getId));

        return ResponseEntity.ok().body(
                GetAllTicketsResponse.builder()
                        .tickets(tickets)
                        .message("Все билеты успещно возвращены") 
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .build()
        );

    }

    @Transactional
    protected Ticket save(Ticket ticket){
        Ticket result = ticketRepository.save(ticket);

        if(ticket.getCountQuestions() != ticket.getQuestions().size()){
            return null;
        }

        questionService.save(ticket);
        return result;
    }

    @Transactional
    public ResponseEntity<Response> addTicket(Ticket ticketRequest) {
        try {

            Ticket ticket = save(ticketRequest);

            if(ticket == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(DefaultResponse.builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message(String.format("Нужно количества вопросов в билете: %d. Отправлено: %d",ticket.getCountQuestions(),ticket.getQuestions().size()))
                        .build());
            }
            questionService.save(ticket);
        }catch (IllegalAccessError e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(DefaultResponse.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .code(HttpStatus.BAD_REQUEST.value())
                    .message("Ошибка при добавлении билета")
                    .build());
        }
        return ResponseEntity.ok().body(DefaultResponse.builder()
                        .status(HttpStatus.OK)
                        .code(HttpStatus.OK.value())
                        .message("Чиназес")
                .build());

    }

    @Transactional
    public ResponseEntity<Response> addTicketSeveral(TicketsRequest ticketsRequest) {

        for(Ticket ticket : ticketsRequest.getTickets()){
              Ticket result = save(ticket);
              if(result == null){
                  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(DefaultResponse.builder()
                          .status(HttpStatus.BAD_REQUEST)
                          .code(HttpStatus.BAD_REQUEST.value())
                          .message(String.format("В билете номер %d дожлно быть %d вопросов в билете. Отправлено: %d",result.getId(),result.getCountQuestions(),result.getQuestions().size()))
                          .build());
              }
        }

        return ResponseEntity.ok().body(DefaultResponse.builder()
                .status(HttpStatus.OK)
                .code(HttpStatus.OK.value())
                .message("Все билеты успешно добавлены")
                .build());
    }
}
