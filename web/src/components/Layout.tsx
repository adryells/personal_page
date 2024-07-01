import React from 'react';
import { useTranslation } from 'react-i18next';
import Button from '../components/Button';
import SocialArea from '../components/SocialArea';
import styles from './Layout.module.css';
import buttonStyles from '../components/Button.module.css';

interface LayoutProps {
  children: React.ReactNode;
}

const Layout: React.FC<LayoutProps> = ({ children }) => {
  const { t, i18n } = useTranslation();

  const toggleLanguage = () => {
    const newLanguage = i18n.language === 'en' ? 'pt' : 'en';
    i18n.changeLanguage(newLanguage);
  };

  return (
    <div className={styles.container}>
      <header className={styles.header}>
        <div className={styles.title}>
          <h2>{t('title')}</h2>
        </div>
        <div className={styles.buttons}>
          <div className={buttonStyles.buttonContainer}>
            <Button value="Home" className={buttonStyles.button} route="/" />
          </div>
          {/*<div className={buttonStyles.buttonContainer}>
            <Button value={t('blog')} className={buttonStyles.button} route="/blog" />
          </div>*/}
          <div className={buttonStyles.buttonContainer}>
            <Button value={t('projects')} className={buttonStyles.button} route="/projects" />
          </div>
          <div className={buttonStyles.buttonContainer}>
            <Button value={i18n.language === 'en' ? 'PT-BR' : 'EN-US'} className={buttonStyles.button} onClick={toggleLanguage} />
          </div>
        </div>
      </header>
      <main className={styles.mainContent}>
        {children}
      </main>
      <SocialArea className={styles.socialArea} />
    </div>
  );
};

export default Layout;