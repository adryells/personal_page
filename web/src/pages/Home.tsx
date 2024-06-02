import Button from "../components/Button";
import HomeInfo from "../components/HomeInfo";
import Me from "../components/Me";
import SocialArea from "../components/SocialArea";
import styles from "./Home.module.css";

const Home = () => {    
    return (
      <div className={styles.container}>
        <Me className={styles.me}/>
        <div className={styles.topRow}>
          <HomeInfo text="Who Am I?
          I'm [Your Name], a [Your Profession/Interest]. With a background in [Your Field or Experience], I have spent the past [X] years diving into [Your Key Interests or Projects]. My work and hobbies span across various domains, and I love sharing my experiences and insights with others." className={styles.homeInfo}/>
          <HomeInfo text="I invite you to explore my page and join me on this journey. Your thoughts and feedback are always welcome, so feel free to reach out or leave a comment. Together, let's inspire and be inspired." className={styles.homeInfo}/>
        </div>
        <div className={styles.bottomRow}>
          <div className={styles.buttonContainer}>
            <Button value="Clique aqui para acessar meus projetos" route="/projects" className={styles.button}/>
            <Button value="Clique aqui para acessar meu blog" route="/blog" className={styles.button}/>
          </div>
          <SocialArea />
        </div>
      </div>
    );
};
  
export default Home;
