'use client'

import {FC, use, useEffect, useState} from "react";
import Image from "next/image";
import quetions from "@/scss/_questions.module.scss"

const ResultTicket:FC<{ticketId:string}> = ({ticketId}) => {

    const [isCompleted,setIsCompleted] = useState<boolean>();
    const [correctAnswers, setCorrectAnswers] = useState<number>();
    const [countQuetions, setCountQuetions] = useState<number>();

    let array:boolean[] = [];

    function countTrue(arr:boolean[]):number {
        return arr.filter(value => value).length;
    }

    function countFalse(arr:boolean[]):number {
        return arr.filter(value => value === false).length;
    }


    useEffect(() => {
        // @ts-ignore
        array = JSON.parse(localStorage.getItem('arrayDecidedQuestions'));

        setIsCompleted(countFalse(array) > 1)
        setCorrectAnswers(countTrue(array))
        setCountQuetions(array.length-1)

        // @ts-ignore
        let tickets = JSON.parse(localStorage.getItem('isDecided')) || [];

        tickets[ticketId] = !isCompleted;

        localStorage.setItem('isDecided', JSON.stringify(tickets));

    }, []);


    return(
        <div className={quetions.module__result}>
            <span>{isCompleted ? "Вы не сдали билет!" : "Вы сдали билет!"}</span>
            <Image src={isCompleted ? "/3.1.svg" : "/2.1.svg"} alt={"Знак дорожного движения"} width="200" height="200"/>
            <div className={quetions.module__result__correctAnswers}>
                <div>{correctAnswers} из {countQuetions}</div>
                <div></div>
            </div>
        </div>
    )
}

export default ResultTicket;
