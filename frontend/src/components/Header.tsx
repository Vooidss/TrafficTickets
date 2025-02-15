import header from "../scss/_header.module.scss";

export default function Header(){
    return(
        <header className={header.header}>
            <span className={header.name}>ПДД БИЛЕТЫ</span>
        </header>
    )
}