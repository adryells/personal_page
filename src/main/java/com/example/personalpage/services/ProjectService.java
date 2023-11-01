package com.example.personalpage.services;

import com.example.personalpage.dtos.ProjectRecordDTO;
import com.example.personalpage.models.ProjectModel;
import com.example.personalpage.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    public Optional<ProjectModel> getProjectById(UUID id){
        return projectRepository.findById(id);
    }

    public List<ProjectModel> getProjects(int page, int perPage){
        PageRequest pageRequest = PageRequest.of(page - 1, perPage);
        Page<ProjectModel> projectPage = projectRepository.findAll(pageRequest);

        return projectPage.getContent();
    }


    public void createProject(ProjectModel projectModel) {
        projectRepository.save(projectModel);
    }


    public ProjectModel updateProject(UUID id, ProjectRecordDTO projectData) {
        Optional<ProjectModel> projectModel = this.getProjectById(id);

        if (projectModel.isPresent()) {
            ProjectModel project = projectModel.get();

            if (projectData.title() != null) {
                project.setTitle(projectData.title());
            }

//            if (projectData.projectLink() != null){
//                project.setProjectLink(projectData.projectLink());
//            }
//
//            if (projectData.description() != null){
//                project.setDescription(projectData.description());
//            }
//
//            if (projectData.codeLink() != null){
//                project.setCodeLink(projectData.codeLink());
//            }
//
//            if (projectData.developmentYear() != null){
//                project.setDevelopmentYear(projectData.developmentYear());
//            }
//
//            if (projectData.shortDescription() != null){
//                project.setShortDescription(projectData.shortDescription());
//            }

            projectRepository.save(project);

            return project;

        } else {
            return null;
        }
    }

    public void deleteProject(UUID id){
        Optional<ProjectModel> projectModel = this.getProjectById(id);

        if (projectModel.isPresent()){
            ProjectModel project = projectModel.get();
            projectRepository.delete(project);
        }
    }

}
