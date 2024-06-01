import styles from "./HomeInfo.module.css";

const HomeInfo = (
    props: any
) => {
    return (
      <p className={styles.p}>
        {props.text}
      </p>
    );
  };
  
  export default HomeInfo;
  