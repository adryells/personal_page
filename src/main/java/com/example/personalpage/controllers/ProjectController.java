package com.example.personalpage.controllers;

import com.example.personalpage.dtos.ProjectRecordDTO;
import com.example.personalpage.models.ProjectModel;
import com.example.personalpage.services.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    ProjectService projectService;

    @GetMapping("/")
    public ResponseEntity<List<ProjectModel>> getProjects(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int perPage
    ){
        List<ProjectModel> projects = projectService.getProjects(page, perPage);

        return ResponseEntity.status(HttpStatus.OK).body(projects);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Object>> getProjectById(@PathVariable(name = "id") UUID id){
        Optional<ProjectModel> project = projectService.getProjectById(id);

        if (project.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Optional.of("Product not found."));
        }

        return ResponseEntity.status(HttpStatus.OK).body(Optional.of(project));
    }

    @PostMapping("/")
    public ResponseEntity<ProjectModel> createProject(@RequestBody @Valid ProjectRecordDTO projectData){
        ProjectModel projectModel = new ProjectModel();

        BeanUtils.copyProperties(projectData, projectModel);
        projectService.createProject(projectModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(projectModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectModel> updateProject(
            @PathVariable(name = "id") UUID id,
            @RequestBody @Valid ProjectRecordDTO projectData
    ){
        ProjectModel projectModel = projectService.updateProject(id, projectData);

        return ResponseEntity.status(HttpStatus.OK).body(projectModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable(name="id") UUID id){
        projectService.deleteProject(id);

        return ResponseEntity.status(HttpStatus.OK).body("Project deleted successfully!");
    }

}
