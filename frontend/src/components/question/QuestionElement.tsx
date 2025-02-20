'use client'

import {Question, QuestionsData} from "@/interfaces/question.interface";
import quetions from "@/scss/_questions.module.scss";
import asnwers from "@/scss/_answer.module.scss";
import QuestionItem from "@/ui/question/QuestionItem";
import {FC, useEffect, useState} from "react";
import CurrentQuestionComponent from "@/components/question/CurrentQuestionComponent";
import ResultTicket from "@/components/ticket/ResultTicket";

const QuestionElement:FC<QuestionsData & {ticketId:string}> = ({questions,ticketId}) => {

    useEffect(() => {
        const arrayDecidedQuestions:boolean[] = new Array<boolean>(questions.length+1);
        localStorage.setItem("arrayDecidedQuestions", JSON.stringify(arrayDecidedQuestions));
    }, []);

    const [currentNumberQuestion, setCurrentNumberQuestion] = useState<number>(0);
    const [currentQuestion, setCurrentQuestion] = useState<Question | null>(questions[0] || null);

    useEffect(() => {
        setCurrentQuestion({...questions[currentNumberQuestion]});
    }, [currentNumberQuestion]);

    return (
        <div className={quetions.module__main}>
            {currentNumberQuestion == questions.length ?
                <ResultTicket ticketId={ticketId}/> :
                (
                    <>
                    <div className={quetions.module__main__listNumber}>
                        {questions.map((quetion, index) =>
                            <QuestionItem key={index} index={index} currentNumberQuestion={currentNumberQuestion}/>
                        )}
                    </div>
                    <div className={asnwers.answers}>
                        {currentQuestion && (
                            <CurrentQuestionComponent
                                key={currentQuestion.id}
                                question={currentQuestion}
                                countQuestions={questions.length}
                                setCurrentNumberQuestion={setCurrentNumberQuestion}
                                currentNumberQuestion={currentNumberQuestion}
                            />
                        )}
                    </div>
                </>
            )}
        </div>
    );
}


export default QuestionElement;