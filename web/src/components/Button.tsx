import { Link } from "react-router-dom";
import styles from "./Button.module.css";

interface ButtonProps {
  value: any;
  className?: string;
  route?: string;
  onClick?: () => void;
}

const Button: React.FC<ButtonProps> = ({ value, className, route, onClick }) => {
  if (route) {
    return (
      <div className={`${styles.buttonContainer} ${className}`}>
        <Link to={route} className={styles.link}>
          <button className={styles.button}>{value}</button>
        </Link>
      </div>
    );
  }

  return (
    <div className={`${styles.buttonContainer} ${className}`}>
      <button onClick={onClick} className={styles.button}>{value}</button>
    </div>
  );
}

export default Button;
