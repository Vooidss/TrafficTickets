import ListQuestions from "@/components/ListQuestions";
import { TicketService } from "@/service/ticket.service";

const Home = async () => {
    const tickets = await TicketService.getAll();
    return(
        <ListQuestions tickets={tickets || []} />
    )
};

export default Home;