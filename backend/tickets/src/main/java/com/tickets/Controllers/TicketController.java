package com.tickets.Controllers;

import com.tickets.DTO.Request.TicketRequest;
import com.tickets.DTO.Response.Response;
import com.tickets.Services.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @GetMapping("/get/{number}")
    public ResponseEntity<Response> getTicketByNumber(@PathVariable String number) {
        return ticketService.getTicketByNumber(number);
    }

    @GetMapping("/get/all")
    public ResponseEntity<Response> getAllTickets(){
        return ticketService.getAllTickets();
    }

    @PostMapping("/add")
    public ResponseEntity<Response> addTicket(@RequestBody TicketRequest ticketRequest){
        return ticketService.addTicket(ticketRequest);
    }

}
