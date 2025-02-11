package com.tickets.DTO.Response;

import com.tickets.Models.Ticket;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class GetObjectResponse extends Response{
    private Ticket ticket;
}
