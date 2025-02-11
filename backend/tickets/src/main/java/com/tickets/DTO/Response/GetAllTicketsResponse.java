package com.tickets.DTO.Response;

import com.tickets.Models.Ticket;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class GetAllTicketsResponse extends Response{
    private List<Ticket> tickets;
}
