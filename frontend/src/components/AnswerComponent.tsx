import AnswerItem from "@/ui/asnwer/AnswerItem";
import {FC, useState} from "react";
import {QuestionsDataSingle} from "@/interfaces/question.interface";
import answer from "@/scss/_answer.module.scss"
import {CurrentNumberQuestionInterface} from "@/interfaces/currentNumberQuestion.interface";

const AnswerComponent:FC<QuestionsDataSingle & CurrentNumberQuestionInterface> =
    ({question,setCurrentNumberQuestion,currentNumberQuestion,countQuestions}) => {

    const [isClick, setIsClick] = useState<boolean|null>(null);
    const handlerClickButton = () =>{
            setCurrentNumberQuestion(currentNumberQuestion+1);
    }

    return (
        <div style={{position:"relative"}}>
            <div style={{position:"relative"}}>
                <div style={{display:isClick?"block" : "none" , position:"absolute", width:"100%", height:"100%", top:"0", left:"0"}}></div>
                {question.answers.map((answer, index) => (
                    <AnswerItem questionIndex={currentNumberQuestion+1} answer={answer} key={index} index={index} setIsClick={setIsClick} />
                ))}
            </div>
            <div className={answer.buttonBox} style={{display:isClick?"flex" : "none"}}>
                <button onClick={() => handlerClickButton()}>Дальше</button>
            </div>
        </div>
    );
}

export default AnswerComponent;