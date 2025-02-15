import {TicketService} from "@/service/ticket.service";
import {Ticket} from "@/interfaces/ticket.interface";
import {Question} from "@/interfaces/question.interface";
import QuestionElement from "@/components/question/QuestionElement";

const TicketPage = async ({params} : {params: Promise<{id:string}>}) => {

    const id = (await params).id;
    const ticket:Ticket =  await TicketService.getById(id);
    const questions:Question[] = ticket.questions;

    return (
            <QuestionElement questions={questions}/>
    )
}

export default TicketPage;