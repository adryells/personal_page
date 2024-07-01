import React, { useState } from 'react';
import styles from './CreateProjectModal.module.css';
import { CreateProjectDTO, Project, createProject } from '../services/projectService';

interface CreateProjectModalProps {
  onClose: () => void;
}

const CreateProjectModal: React.FC<CreateProjectModalProps> = ({ onClose }) => {
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [message, setMessage] = useState<string | null>(null);

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();

    const newProject: CreateProjectDTO = {
      title,
      description,
    };

    try {
      const createdProject: Project = await createProject(newProject);
      setMessage(`Project ${createdProject.title} created successfully!`);
      setTitle('');
      setDescription('');
    } catch (error) {
      setMessage('Failed to create project');
    }
  };

  return (
    <div className={styles.modalOverlay}>
      <div className={styles.modalContent}>
        <h2>Create Project</h2>
        <form onSubmit={handleSubmit}>
          <div className={styles.formGroup}>
            <label htmlFor="title">Title</label>
            <input
              type="text"
              id="title"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              required
            />
          </div>
          <div className={styles.formGroup}>
            <label htmlFor="description">Description</label>
            <textarea
              id="description"
              value={description}
              onChange={(e) => setDescription(e.target.value)}
              required
            ></textarea>
          </div>
          <button type="submit" className={styles.submitButton}>
            Create
          </button>
          <button type="button" className={styles.cancelButton} onClick={onClose}>
            Cancel
          </button>
        </form>
        {message && <p className={styles.message}>{message}</p>}
      </div>
    </div>
  );
};

export default CreateProjectModal;
