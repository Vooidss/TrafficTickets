"use client";


import {FC, useEffect, useState} from "react";
import {QuestionsData} from "@/interfaces/question.interface";

const Timer:FC<QuestionsData & {ticketId:string}> = ({questions,ticketId}) => {
    const [time, setTime] = useState(60 * questions.length);

    useEffect(() => {

        const timer = setInterval(() => {
            setTime((prevTime) => (prevTime > 0 ? prevTime - 1 : 0));
            if(time == 0){
                // @ts-ignore
                let tickets = JSON.parse(localStorage.getItem('isDecided')) || [];

                tickets[ticketId] = false;

                localStorage.setItem('isDecided', JSON.stringify(tickets));
            }
        }, 1000);

        return () => clearInterval(timer);
    }, []);

    const formatTime = (seconds: number) => {
        const minutes = Math.floor(seconds / 60);
        const secs = seconds % 60;
        return `${String(minutes).padStart(2, "0")}:${String(secs).padStart(2, "0")}`;
    };

    return(
        <span>{formatTime(time)}</span>
    )
}

export default Timer;