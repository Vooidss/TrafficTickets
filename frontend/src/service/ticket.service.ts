import axios from "axios";
import {Ticket} from "@/interfaces/ticket.interface";

axios.defaults.baseURL = process.env.API_TICKETS_URL;

export const TicketService = {
    async getAll(){
        const {data} = await axios.get<Ticket[]>('get/all');
        // @ts-ignore
        return data.tickets;
    },
    async getById(id:string){
        const {data} = await axios.get<Ticket[]>(`/get/${id}`)
        // @ts-ignore
        return data.ticket;
    }
}