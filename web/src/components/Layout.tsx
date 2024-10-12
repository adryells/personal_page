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
    const languages = ['en', 'pt', 'es'];
    const nextLanguage = languages[(languages.indexOf(i18n.language) + 1) % languages.length];
    i18n.changeLanguage(nextLanguage);
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
          <div className={buttonStyles.buttonContainer}>
            <Button value={t('projects')} className={buttonStyles.button} route="/projects" />
          </div>
          <div className={buttonStyles.buttonContainer}>
            <div className={styles.languageContainer}>
            {i18n.language === 'pt' ? (
              <img className={styles.flagIcon} src="assets/flags/br.png" alt="PT-BR" onClick={toggleLanguage} />
            ) : i18n.language === 'es' ? (
              <img className={styles.flagIcon} src="assets/flags/es.webp" alt="ES" onClick={toggleLanguage} />
            ) : (
              <img className={styles.flagIcon} src="/assets/flags/us.png" alt="EN-US" onClick={toggleLanguage} />
            )}
          </div>
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
