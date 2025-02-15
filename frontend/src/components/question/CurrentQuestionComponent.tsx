'use client'

import {FC, useState} from "react";
import {QuestionsDataSingle} from "@/interfaces/question.interface";
import AnswerItem from "@/ui/asnwer/AnswerItem";
import AnswerComponent from "@/components/AnswerComponent";

const CurrentQuestionComponent:FC<QuestionsDataSingle> = ({question}) =>{

    return(
        <div>
            <span>{question.question}</span>
            <AnswerComponent question={question}/>
        </div>
    )
}

export default CurrentQuestionComponent;