import React, { useState } from 'react';
import styles from './Project.module.css';
import { useTranslation } from 'react-i18next';

interface Contributor {
  name: string;
  url: string;
}

interface ProjectProps {
  title: string;
  overview: string;
  technologies: string;
  functionalities: string;
  hosting?: string;
  projectLink?: string;
  codeLink?: string;
  contributors?: Contributor[];
}

const Project: React.FC<ProjectProps> = ({
  title,
  overview,
  technologies,
  functionalities,
  hosting,
  projectLink,
  codeLink,
  contributors
}) => {
  const [isOpen, setIsOpen] = useState(false);
  const { t } = useTranslation();

  const toggleOpen = () => {
    setIsOpen(!isOpen);
  };

  return (
    <div className={styles.project}>
      <div className={styles.projectHeader} onClick={toggleOpen}>
        <h3>{title}</h3>
        <span>{isOpen ? '-' : '+'}</span>
      </div>
      {isOpen && (
        <div className={styles.projectDetails}>
          <p><strong>{t('overview')}:</strong> {overview}</p>
          <p><strong>{t('usedTech')}:</strong> {technologies}</p>
          <p><strong>{t('functionalities')}:</strong> {functionalities}</p>
          {hosting && <p><strong>{t('hosting')}:</strong> {hosting}</p>}
          {projectLink && <p><strong>{t('projectLink')}:</strong> <a href={projectLink}>{projectLink}</a></p>}
          {codeLink && <p><strong>{t('codeLink')}:</strong> <a href={codeLink}>{codeLink}</a></p>}
          {contributors && (
            <div>
              <p><strong>{t('contributors')}:</strong></p>
              <ul>
                {contributors.map((contributor, index) => (
                  <li key={index}>
                    <a href={contributor.url} target="_blank" rel="noopener noreferrer">{contributor.name}</a>
                  </li>
                ))}
              </ul>
            </div>
          )}
        </div>
      )}
    </div>
  );
};

export default Project;
