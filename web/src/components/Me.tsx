import styles from "./Me.module.css"

const Me = () => {
    return (
        <div className={styles.my_pfp}>
            <img src="pfp.jpg" alt="EU" />
        </div>
    );
}

export default Me;