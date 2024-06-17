import Button from "../components/Button";
import SocialArea from "../components/SocialArea";
import styles from "./Home.module.css";

const Home = () => {    
    return (
      <div className={styles.container}>
          <header className={styles.header}>
            <div className={styles.title}>
              <h2>Adryell</h2>
            </div>
            <div className={styles.buttons}>
              <Button value="Home" className={styles.button} route="/" />
              <Button value="Blog" className={styles.button} route="/blog" />
              <Button value="Projetos" className={styles.button} route="/projects" />
              <Button value="PT-BR" className={styles.button} route="/r" />
            </div>
          </header>
          <main className={styles.mainContent}>
            <p>
              I'm Adryell, a developer with a background in backend web development. In 3 years of professional experience, I have already contributed to projects on various topics such as: Course platforms (including individual sales and subscriptions), boat rentals, social networks, financial management systems and a bot for the general administration of a course platform.
            </p>
            <br />
            <p>
              In my education, I spent 3 years on a technical IT course integrated into high school where I gained knowledge on various topics such as databases, operating systems, algorithms/data structures, systems development methodology and IT entrepreneurship. Then, I entered computer science college, where I spent a few periods. In addition to those already seen in the technical course, I was immersed in OOP, human-computer interface and functional programming. In all these years, I have had experience with various technologies such as Python (my main), Java, C, C#, JavaScript, PHP, Dart, GraphQL, REST, Docker, GIT, Linux and others.
            </p>
          </main>
          <SocialArea className={styles.socialArea} />
      </div>
    );
};

export default Home;
