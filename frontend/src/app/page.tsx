import ListTickets from "@/components/ticket/ListTickets";
import { TicketService } from "@/service/ticket.service";

const Home = async () => {
    const tickets = await TicketService.getAll();
    return(
        <ListTickets tickets={tickets || []} />
    )
};

export default Home;