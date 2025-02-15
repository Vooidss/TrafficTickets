import AnswerItem from "@/ui/asnwer/AnswerItem";
import {FC, useState} from "react";
import {QuestionsDataSingle} from "@/interfaces/question.interface";

const AnswerComponent:FC<QuestionsDataSingle> = ({question}) => {

    return (
        <div style={{position:"relative"}}>
            {question.answers.map((answer,index) => <AnswerItem answer={answer} key={index} index={index}/>)}
        </div>
    )
}

export default AnswerComponent;