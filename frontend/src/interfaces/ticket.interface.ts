import {Question} from "@/interfaces/question.interface";

export interface Ticket{
    id:number,
    countQuestions:number,
    questions:Question[]
}

export interface TicketsData{
    tickets:Ticket[]
}

export interface TicketsDataSingle{
    ticket:Ticket
}