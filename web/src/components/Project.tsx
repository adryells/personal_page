import React, { useState } from 'react';
import styles from './Project.module.css';

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
          <p><strong>Visão Geral:</strong> {overview}</p>
          <p><strong>Tecnologias Utilizadas:</strong> {technologies}</p>
          <p><strong>Funcionalidades:</strong> {functionalities}</p>
          {hosting && <p><strong>Hospedagem:</strong> {hosting}</p>}
          {projectLink && <p><strong>Link para o Projeto:</strong> <a href={projectLink}>{projectLink}</a></p>}
          {codeLink && <p><strong>Link para o Código:</strong> <a href={codeLink}>{codeLink}</a></p>}
          {contributors && (
            <div>
              <p><strong>Contribuidores:</strong></p>
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
