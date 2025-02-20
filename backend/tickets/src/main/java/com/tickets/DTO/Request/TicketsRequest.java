package com.tickets.DTO.Request;

import com.tickets.Models.Ticket;
import lombok.Data;

import java.util.List;

@Data
public class TicketsRequest {
    private List<Ticket> tickets;
}
