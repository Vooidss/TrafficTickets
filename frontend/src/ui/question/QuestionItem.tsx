import quetions from "@/scss/_questions.module.scss";
import {FC} from "react";
import {QuestionItemProps} from "@/interfaces/question.interface"

const QuestionItem:FC<QuestionItemProps> = ({index,currentNumberQuestion}) => {

    index = index + 1
    // @ts-ignore

    const isDecideds = JSON.parse(localStorage.getItem("arrayDecidedQuestions")) || [];

    const isDecided = isDecideds[index];
    const itemStyle = currentNumberQuestion==index-1 ? quetions.module__main__listNumber__boxActive : isDecided == null ? quetions.module__main__listNumber__box :
        isDecided == true ? quetions.module__main__listNumber__boxTrue : quetions.module__main__listNumber__boxFalse;

    return(
            <div className={itemStyle}>
                <span>
                    {index}
                </span>
            </div>
    )
}

export default QuestionItem;