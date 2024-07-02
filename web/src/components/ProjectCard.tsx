import React from 'react';
import { Link } from 'react-router-dom';
import { Project } from '../services/projectService';
import styles from './ProjectCard.module.css';

interface ProjectCardProps {
  project: Project;
}

const ProjectCard: React.FC<ProjectCardProps> = ({ project }) => {
  return (
    <div className={styles.projectCard}>
      <Link to={`/projects/${project.id}`} className={styles.projectLink}>
        <div className={styles.projectCardContent}>
            <h2 className={styles.projectTitle}>{project.title}</h2>
        </div>
      </Link>
    </div>
  );
};

export default ProjectCard;
+58