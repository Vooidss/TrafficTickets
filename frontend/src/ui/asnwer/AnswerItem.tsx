'use client'

import {FC, useEffect, useState} from "react";
import {AnswerData} from "@/interfaces/answer.interface";
import asnwers from "@/scss/_answer.module.scss";



const AnswerItem:FC<AnswerData> = ({answer,index}) => {

    const [isCorrently, setIsCorrently] = useState<boolean|null>(null);
    const [isClick, setIsClick] = useState<boolean|null>(null);

    const handleClickBox = () =>{
        setIsClick(true)
        setIsCorrently(answer.isCorrect)
    }

    return(
        <div className={
            isCorrently == null ? asnwers.answers__answer
                : isCorrently == true
                    ? asnwers.answers__answerTrue
                    : asnwers.answers__answerFalse}
             onClick={() => handleClickBox()}>
            <div style={{display:isClick?"block" : "none" , position:"absolute", width:"100%", height:"100%"}}></div>
            <span>{index+1}. {answer.answer}</span>
        </div>
    )
}

export default AnswerItem;