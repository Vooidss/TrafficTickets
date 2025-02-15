import type { Metadata } from "next";
import "../../../scss/_globals.scss";
import Header from "@/components/Header";
import quetions from "@/scss/_questions.module.scss";
import LeftArrow from "@/ui/ticket/LeftArrow";

export async function generateMetadata({ params }: { params: { id: string } }): Promise<Metadata> {
    if(params.id == null){
        return {
            title: `Билеты`,
            description: `Пдд билеты`
        }
    }

    return {
        title: `Билеты ${params.id}`,
        description: `Пдд билет номер ${params.id}`
    };
}

export default function RootLayout({ children, params }: { children: React.ReactNode, params: { id: string } }) {
    return (
            <div className={quetions.module}>
                <div className={quetions.module__top}>
                    <div className={quetions.module__top__back}>
                        <LeftArrow/>
                    </div>
                    <div className={quetions.module__top__name}>
                    <span>
                        Билет {params.id}
                    </span>
                    </div>
                    <div className={quetions.module__top__time}>
                    </div>
                </div>
                {children}
            </div>
    );
}
