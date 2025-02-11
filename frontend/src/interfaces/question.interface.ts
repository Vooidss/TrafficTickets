import {Answer} from "@/interfaces/answer.interface";

export interface Questions{
    id:number,
    question:string,
    hashImage:string,
    answers:Answer[]
}