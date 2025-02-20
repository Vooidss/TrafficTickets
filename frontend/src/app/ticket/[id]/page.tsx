import {TicketService} from "@/service/ticket.service";
import {Ticket} from "@/interfaces/ticket.interface";
import {Question} from "@/interfaces/question.interface";
import QuestionElement from "@/components/question/QuestionElement";
import quetions from "@/scss/_questions.module.scss";
import LeftArrow from "@/ui/ticket/LeftArrow";
import Timer from "@/components/Timer";

const TicketPage = async ({params} : {params: Promise<{id:string}>}) => {

    const id = (await params).id;
    const ticket:Ticket =  await TicketService.getById(id);
    const questions:Question[] = ticket.questions;

    return (
        <div className={quetions.module}>
            <div className={quetions.module__top}>
                <div className={quetions.module__top__back}>
                    <LeftArrow/>
                </div>
                <div className={quetions.module__top__name}>
                    <span>
                        Билет {id}
                    </span>
                </div>
                <div className={quetions.module__top__time}>
                    <Timer ticketId={id} questions={questions}/>
                </div>
            </div>
            <QuestionElement ticketId={id} questions={questions}/>
        </div>
    )
}

export default TicketPage;