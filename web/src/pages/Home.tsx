import { useTranslation } from 'react-i18next';
import Layout from '../components/Layout';
import styles from './Home.module.css';

const Home = () => {
  const { t } = useTranslation();

  return (
    <Layout>
      <div className={styles.mainContent}>
        <p>{t('text_1')}</p>
        <br />
        <p>{t('text_2')}</p>
      </div>
    </Layout>
  );
};

export default Home;
