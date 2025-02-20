import type { Metadata } from "next";
import "../../../scss/_globals.scss";

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
        <>
            {children}
        </>
    );
}
