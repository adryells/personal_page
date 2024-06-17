import { Link } from "react-router-dom";
import styles from "./Button.module.css";

const Button = (props: any) => {
    return (
        <div className={`${styles.buttonContainer} ${props.className}`}>
            <Link to={props.route} className={styles.link}>
                <button className={styles.button}>{props.value}</button>
            </Link>
        </div>
    );
}

export default Button;
