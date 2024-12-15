import { useState } from 'react';
import styles from './Home.module.css';
import SocialArea from '../components/SocialArea';

const Home = () => {
  const [darkMode, setDarkMode] = useState(false);

  const toggleDarkMode = () => {
    setDarkMode((prevMode) => !prevMode);
  };

  return (
    <div className={`${styles.bodyMain} ${darkMode ? styles.dark : styles.light}`}>
      <button onClick={toggleDarkMode} className={styles.toggleButton}>
        {darkMode ? 'Light Mode' : 'Dark Mode'}
      </button>

      <div className={styles.leftColumn}>
        <img
          src="assets/pp/pp1.jpg"
          alt="Paulo Adryell"
          className={styles.profileImage}
        />
        <h1 className={styles.name}>Paulo Adryell</h1>
        <h2 className={styles.profession}>Backend Developer</h2>
        <a href="mailto:adryellpaulo@gmail.com" className={`${styles.contactMe} ${darkMode ? styles.dark : styles.light}`}>
          Contact me
        </a>
        <SocialArea className={styles.socialArea} />
      </div>

      <div className={styles.rightColumn}>
        <p className={styles.aboutMe}>
          I'm a backend developer with over 3 years of professional experience
          delivering solutions for platforms such as course management, boat rentals,
          social networks, financial systems, and automation tools. My journey began in 2018
          during a technical IT course, and since 2021, I've been seriously focused on expanding
          my knowledge and skills. This foundation includes databases, algorithms, OOP, functional
          programming, and more. My expertise spans Python, Java, REST, GraphQL, Docker, Linux, Redis,
          among other technologies and tools I've worked with throughout the years.
        </p>
        <a
          href="https://github.com/adryells?tab=repositories"
          target="_blank"
          rel="noopener noreferrer"
          className={styles.projectsLink}
        >
          View My Projects on GitHub
        </a>
      </div>
    </div>
  );
};

export default Home;
