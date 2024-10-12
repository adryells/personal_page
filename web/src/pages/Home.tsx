import { useTranslation } from 'react-i18next';
import Layout from '../components/Layout';
import styles from './Home.module.css';

const Home = () => {
  const { t } = useTranslation();

  return (
    <Layout>
      <div className={styles.bodyMain}>
        <div className={styles.profilePicture}>
          <img src="/assets/pp/pp.jpg" alt="Me" loading='lazy'/>
        </div>
        <div className={styles.texts}>
          <p>{t('text_1')}</p>
          <br />
          <p>{t('text_2')}</p>
        </div>
      </div>
    </Layout>
  );
};

export default Home;
