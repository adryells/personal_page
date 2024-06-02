import styles from "./Me.module.css";

const Me = (props: any) => {
    return (
        <div className={`${styles.my_pfp} ${props.className}`}>
            <img src="pfp.jpg" alt="EU" />
        </div>
    );
}

export default Me;
