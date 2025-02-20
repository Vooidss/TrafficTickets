'use client'

import {FC, useState} from "react";
import {AnswerData} from "@/interfaces/answer.interface";
import asnwers from "@/scss/_answer.module.scss";


const AnswerItem:FC<AnswerData & {setIsClick(isClick:boolean): void}> = ({answer,index,setIsClick,questionIndex}) => {

    const [isCorrently, setIsCorrently] = useState<boolean|null>(null);

    const handleClick = () => {
        setIsCorrently(answer.isCorrect);
        setIsClick(true);

        // @ts-ignore
        let array = JSON.parse(localStorage.getItem('arrayDecidedQuestions')) || [];

        if (array.length > questionIndex) {
            array[questionIndex] = answer.isCorrect;
        }

        localStorage.setItem('arrayDecidedQuestions', JSON.stringify(array));

    }

    return(
        <div className={
            isCorrently == null ? asnwers.answers__answer
                : isCorrently == true
                    ? asnwers.answers__answerTrue
                    : asnwers.answers__answerFalse}
             onClick={() => handleClick()}>
            <span>{index+1}. {answer.answer}</span>
        </div>
    )
}

export default AnswerItem;