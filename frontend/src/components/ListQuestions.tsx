'use client'

import questions from "../scss/_tickets.module.scss"
import {TicketsData} from "@/interfaces/ticket.interface";
import TicketItem from "@/ui/ticket/TicketItem";

export default function ListQuestions({tickets}:TicketsData){

    // @ts-ignore
    if(JSON.parse(localStorage.getItem("isDecided")) == []){
        let isDecided = new Array<boolean>(tickets.length+1)
        localStorage.setItem("isDecided", JSON.stringify(isDecided));
    }

    return (
        <div className={questions.module}>
            {tickets.length ? tickets.map((ticket) => <TicketItem ticket={ticket} key={ticket.id}/>) : <div>Билетов пока нет</div>}
       </div>
    )
}