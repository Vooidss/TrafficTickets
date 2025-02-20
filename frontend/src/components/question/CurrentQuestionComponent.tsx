'use client'

import {FC} from "react";
import {QuestionsDataSingle} from "@/interfaces/question.interface";
import AnswerComponent from "@/components/AnswerComponent";
import {CurrentNumberQuestionInterface} from "@/interfaces/currentNumberQuestion.interface";

const CurrentQuestionComponent:FC<QuestionsDataSingle & CurrentNumberQuestionInterface>
    = ({question,setCurrentNumberQuestion,currentNumberQuestion,countQuestions}) =>{

    return(
        <div>
            <span>{question.question}</span>
            <AnswerComponent setCurrentNumberQuestion={setCurrentNumberQuestion} currentNumberQuestion={currentNumberQuestion} countQuestions={countQuestions} question={question}/>
        </div>
    )
}

export default CurrentQuestionComponent;