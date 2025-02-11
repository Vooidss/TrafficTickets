"use client"

import questions from "../scss/questions.module.scss"
import {useEffect} from "react";
export default function ListQuestions(){

    useEffect(() => {
        findTickets();
    }, []);

    async function findTickets() {
        const response = await fetch("http://localhost:8010/tickets/get/all");
        const data = await response.json();
        console.log(data);
    }

    return(
        <div className={questions.module}>

        </div>
    )
}