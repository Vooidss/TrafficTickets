import axios from "axios";
import {Ticket} from "@/interfaces/ticket.interface";

const API_URL = 'http://localhost:4200/tickets';

axios.defaults.baseURL = API_URL;

export const TicketService = {
    async getAll(){
        const {data} = await axios.get<Ticket[]>('get/all');
        return data;
    },
    async getById(id:string){
        const {data} = await axios.get<Ticket[]>('/get',{
            params: {
                id,
            },
        })
        return data[0];
    }
}