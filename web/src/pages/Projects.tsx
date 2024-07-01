import React, { useState } from 'react';
import ProjectList from '../components/ProjectList';
import Layout from '../components/Layout';
import styles from './Projects.module.css';
import CreateProjectModal from '../components/CreateProjectModal';

const Projects: React.FC = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const token = sessionStorage.getItem('token');

  const openModal = () => {
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };

  return (
    <Layout>
      <div className={styles.projectsPage}>
        {token && (
          <button onClick={openModal} className={styles.createButton}>
            Create Project
          </button>
        )}
        <ProjectList />
        {isModalOpen && <CreateProjectModal onClose={closeModal} />}
      </div>
    </Layout>
  );
};

export default Projects;
