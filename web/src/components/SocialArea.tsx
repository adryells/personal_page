import { Link } from "react-router-dom";
import { FaGithub, FaLinkedin, FaEnvelope, FaInstagram } from "react-icons/fa";
import styles from "./SocialArea.module.css";

const SocialArea = (props: any) => {
    return (
        <ul className={`${styles.socialArea} ${props.className}`}>
            <li>
                <Link to="https://github.com/adryells" target="_blank" rel="noopener noreferrer">
                    <FaGithub size={30} />
                </Link>
            </li>
            <li>
                <Link to="https://www.linkedin.com/in/pauloadryell/" target="_blank" rel="noopener noreferrer">
                    <FaLinkedin size={30} />
                </Link>
            </li>
            <li>
                <Link to="mailto:adryellpaulo@gmail.com" target="_blank" rel="noopener noreferrer">
                    <FaEnvelope size={30} />
                </Link>
            </li>
            <li>
                <Link to="https://www.instagram.com/_adryell.md/" target="_blank" rel="noopener noreferrer">
                    <FaInstagram size={30} />
                </Link>
            </li>
        </ul>
    );
}

export default SocialArea;
