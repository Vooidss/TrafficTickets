import {Questions} from "@/interfaces/question.interface";

export interface Ticket{
    id:number,
    countQuestions:number,
    questions:Questions[]
}

export interface TicketsData{
    tickets:Ticket[]
}

export interface TicketsDataSingle{
    ticket:Ticket
}