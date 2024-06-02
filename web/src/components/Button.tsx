import { Link } from "react-router-dom";
import styles from "./Button.module.css";

const Button = (props: any) => {
    return (
        <div className={`${styles.button} ${props.className}`}>
            <Link to={props.route}>
                <button>{props.value}</button>
            </Link>
        </div>
    );
}

export default Button;
