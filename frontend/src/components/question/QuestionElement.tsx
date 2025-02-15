'use client'

import {Question, QuestionsData} from "@/interfaces/question.interface";
import quetions from "@/scss/_questions.module.scss";
import asnwers from "@/scss/_answer.module.scss";
import QuestionItem from "@/ui/question/QuestionItem";
import {FC, useEffect, useState} from "react";
import CurrentQuestionComponent from "@/components/question/CurrentQuestionComponent";

const QuestionElement:FC<QuestionsData> = ({questions}) => {

    let decided = new Array<boolean>(questions.length);

    const [currentNumberQuestion, setCurrentNumberQuestion] = useState<number>(1);
    const [currentQuestion,setCurrentQuestion] = useState<Question | null>(questions[1] || null);

    useEffect(() => {
        setCurrentQuestion(questions[currentNumberQuestion])
    }, [currentNumberQuestion]);

    return(
        <div className={quetions.module__main}>
            <div className={quetions.module__main__listNumber}>
                {questions.map((quetion,index) =>
                    <QuestionItem key={index} index={index} setCurrentNumberQuestion={setCurrentNumberQuestion} currentNumberQuestion={currentNumberQuestion}/>
                )}
            </div>
            <div className={asnwers.answers}>
                {currentQuestion && <CurrentQuestionComponent question={currentQuestion}/>}
            </div>
        </div>
    )
}

export default QuestionElement;