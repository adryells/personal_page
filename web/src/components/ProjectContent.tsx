import React from 'react';
import DOMPurify from 'dompurify';

interface ProjectContentProps {
  content: string | null;
}

const ProjectContent: React.FC<ProjectContentProps> = ({ content }) => {
  const cleanHTML = content != null ? DOMPurify.sanitize(content) : "";

  return (
    <div dangerouslySetInnerHTML={{ __html: cleanHTML }} />
  );
};

export default ProjectContent;
