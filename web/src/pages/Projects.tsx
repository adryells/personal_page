import React from 'react';
import { useTranslation } from 'react-i18next';
import Layout from '../components/Layout';
import Project from '../components/Project';
import projectsData from '../services/projectsData.json';
import styles from './Projects.module.css';

const Projects: React.FC = () => {
  const { i18n } = useTranslation();

  const currentLang = i18n.language || 'en';
  const currentProjects = projectsData[currentLang as keyof typeof projectsData];

  if (!Array.isArray(currentProjects)) {
    throw new Error('Invalid projects data format'); 
  }

  return (
    <Layout>
      <div className={styles.projectsPage}>
        {currentProjects.map((project, index) => (
          <Project
            key={index}
            title={project.title}
            overview={project.overview}
            technologies={project.technologies}
            functionalities={project.functionalities}
            hosting={project.hosting}
            projectLink={project.projectLink}
            codeLink={project.codeLink}
            contributors={project.contributors}
          />
        ))}
      </div>
    </Layout>
  );
};

export default Projects;
