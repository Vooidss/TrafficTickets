import {TicketsDataSingle} from "@/interfaces/ticket.interface";
import questions from "../../scss/_tickets.module.scss"
import Link from "next/link";


export default function TicketItem({ticket}:TicketsDataSingle){

    // @ts-ignore
    const isDecideds = JSON.parse(localStorage.getItem("isDecided")) || [];
    const isDecided = isDecideds[ticket.id];
    console.log(isDecided)



    return (
        <Link href={`/ticket/${ticket.id}`} className={isDecided == null ? questions.module__box : isDecided == true ? questions.module__boxTrue : questions.module__boxFalse}>
            <p className={questions.module__box__num}>{ticket.id}</p>
        </Link>
    )
}