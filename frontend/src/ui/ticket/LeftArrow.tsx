import quetions from "@/scss/_questions.module.scss";
import Image from "next/image";
import Link from "next/link";

export default function LeftArrow(){
    return(
        <Link href="/">
            <Image className={quetions.module__top__back__leftArrow}
                   src="/left-arrow.svg" width={25} height={25} alt={"leftArrow"}/>
        </Link>
    )
}