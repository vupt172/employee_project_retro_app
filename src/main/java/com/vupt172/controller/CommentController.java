package com.vupt172.controller;

import com.vupt172.converter.EvaluationConverter;
import com.vupt172.dto.EvaluationDTO;
import com.vupt172.entity.EmployeeInProject;
import com.vupt172.entity.Evaluation;
import com.vupt172.entity.Project;
import com.vupt172.exception.ElementNotExistException;
import com.vupt172.repository.EmployeeInProjectRepository;
import com.vupt172.repository.EvaluationRepository;
import com.vupt172.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects/{projectId}/employees/{employeeId}/evaluations")
public class CommentController {
    @Autowired
    EvaluationConverter evaluationConverter;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    EmployeeInProjectRepository employeeInProjectRepository;
    @Autowired
    EvaluationRepository evaluationRepository;
    @PostMapping("")
    public Object addEvaluationToEmployeeInProject(@PathVariable Long projectId, @PathVariable Long employeeId, @RequestBody EvaluationDTO evaluationDTO){
        evaluationDTO.setProjectId(projectId);
        evaluationDTO.setEmployeeId(employeeId);
        Evaluation evaluation=evaluationConverter.toEntity(evaluationDTO);
        Project project=projectRepository.findById(projectId).orElseThrow(
                ()->new ElementNotExistException("Project not exist with id ="+projectId)
        );
        EmployeeInProject employeeInProject=employeeInProjectRepository.findByProject_IdAndEmployee_Id(projectId,employeeId).orElseThrow(
                ()->new ElementNotExistException("Employee with id = "+employeeId+" not exist with in project")
        );
        EmployeeInProject evaluateeInProject=employeeInProjectRepository.findByProject_IdAndEmployee_Id(projectId,evaluationDTO.getEvaluateeId()).orElseThrow(
                ()->new ElementNotExistException("Evaluatee with id = "+evaluationDTO.getEvaluateeId()+" not exist in project")
        );
        evaluation.setProject(project);
        evaluation.setEvaluateeInProject(evaluateeInProject);
        evaluation.setEmployeeInProject(employeeInProject);
        evaluation=evaluationRepository.save(evaluation);

        return evaluation
                ;
    }
}
