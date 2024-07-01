import api from './api';

export interface Project {
  id: number;
  title: string;
  description: string;
  active: boolean;
  creatorId: string;
  tags: string[];
  thumbnailURL: string | null;
  content: string | null;
}

export interface CreateProjectDTO {
  title: string;
  description: string;
  tagIds?: number[];
  thumbnailId?: number;
}

export interface UpdateProjectDTO {
  title?: string;
  description?: string;
  active?: boolean;
  creatorId?: string;
  tagIds?: number[];
  thumbnailId?: number;
}

export const createProject = async (projectData: CreateProjectDTO): Promise<Project> => {
  const response = await api.post<Project>('/project/create', projectData);
  return response.data;
};

export const getProjectById = async (id: number): Promise<Project> => {
  const response = await api.get<Project>(`/project/${id}`);
  return response.data;
};

interface ProjectApiResponse {
    content: Project[];
}
  
export const getProjects = async (): Promise<Project[]> => {
    const response = await api.get<ProjectApiResponse>('/project/');
    return response.data.content;
  };

export const updateProject = async (id: number, projectData: UpdateProjectDTO): Promise<Project> => {
  const response = await api.put<Project>(`/project/update/${id}`, projectData);
  return response.data;
};

export const deleteProject = async (id: number): Promise<void> => {
  await api.delete(`/project/delete/${id}`);
};