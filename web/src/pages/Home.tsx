import Button from "../components/Button";
import HomeInfo from "../components/HomeInfo";
import Me from "../components/Me";
import SocialArea from "../components/SocialArea";
import styles from "./Home.module.css";

const Home = () => {
    return (
      <div className={styles.container}>
        <div className={styles.horizontalContainer}>
          <HomeInfo text="Não sei que porras colocarei aqui mas uma porra será colocada."/>
          <Me/>
          <HomeInfo text="ABLABLAEUUBALEAEBELEBAEELEBEELEBLEJBELBELJBEALAEJEBLABJELELJEBL" />
        </div>
        <Button value="Click here to see my projects" route="/projects"/>
        <SocialArea />
      </div>
    );
  };
  
  export default Home;
  