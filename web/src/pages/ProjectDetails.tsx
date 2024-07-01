import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { getProjectById, Project } from '../services/projectService';
import styles from './ProjectDetails.module.css';
import ProjectContent from '../components/ProjectContent';

const ProjectDetails: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const [project, setProject] = useState<Project | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchProject = async () => {
      try {
        if (id) {
          const fetchedProject = await getProjectById(Number(id));
          setProject(fetchedProject);
        }
      } catch (error) {
        setError('Failed to fetch project details');
      } finally {
        setLoading(false);
      }
    };

    fetchProject();
  }, [id]);

  if (loading) return <p>Loading...</p>;
  if (error) return <p>{error}</p>;

  return (
    <div className={styles.projectDetails}>
      {project ? (
        <>
          <h1>{project.title}</h1>
          <p>{project.description}</p>
          <ProjectContent content={project.content} />
          {project.thumbnailURL && <img src={project.thumbnailURL} alt={project.title} />}
        </>
      ) : (
        <p>Project not found</p>
      )}
    </div>
  );
};

export default ProjectDetails;
