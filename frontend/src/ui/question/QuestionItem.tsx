import quetions from "@/scss/_questions.module.scss";
import {FC} from "react";
import {QuestionItemProps} from "@/interfaces/question.interface"

const QuestionItem:FC<QuestionItemProps> = ({index,setCurrentNumberQuestion,currentNumberQuestion}) => {

    const itemStyle = currentNumberQuestion==index ? quetions.module__main__listNumber__boxActive : quetions.module__main__listNumber__box

    return(
            <div className={itemStyle} onClick={() => setCurrentNumberQuestion(index)}>
                <span>
                    {index+1}
                </span>
            </div>
    )
}

export default QuestionItem;