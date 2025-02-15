import {Answer} from "@/interfaces/answer.interface";

export interface Question{
    id:number,
    question:string,
    hashImage:string,
    answers:Answer[]
}

export interface QuestionsData{
    questions:Question[];
}

export interface QuestionsDataSingle{
    question:Question
}

export interface QuestionItemProps {
    index: number;
    currentNumberQuestion:number;
    setCurrentNumberQuestion: (index: number) => void;
}