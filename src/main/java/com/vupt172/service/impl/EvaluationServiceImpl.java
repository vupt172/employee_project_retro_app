package com.vupt172.service.impl;

import com.vupt172.converter.EvaluationConverter;
import com.vupt172.dto.EvaluationDTO;
import com.vupt172.entity.Employee;
import com.vupt172.entity.Evaluation;
import com.vupt172.entity.Project;
import com.vupt172.exception.ElementNotExistException;
import com.vupt172.exception.EvaluationException;
import com.vupt172.repository.EmployeeInProjectRepository;
import com.vupt172.repository.EmployeeRepository;
import com.vupt172.repository.EvaluationRepository;
import com.vupt172.repository.ProjectRepository;
import com.vupt172.service.IEvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EvaluationServiceImpl implements IEvaluationService {

    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    EmployeeInProjectRepository employeeInProjectRepository;
    @Autowired
    EvaluationConverter evaluationConverter;
    @Autowired
    EvaluationRepository evaluationRepository;
    @Override
    public List<EvaluationDTO> findAll() {
        List<EvaluationDTO> dtos=evaluationRepository.findAll().stream().map(
                e->evaluationConverter.toDTO(e)
        ).collect(Collectors.toList());
        return dtos;
    }

    @Override
    public Optional<EvaluationDTO> findById(Long evaluationId) {
        EvaluationDTO evaluationDTO=null;
        Evaluation evaluation=evaluationRepository.findById(evaluationId).orElse(null);
        if(evaluation!=null)evaluationDTO=evaluationConverter.toDTO(evaluation);
        return Optional.ofNullable(evaluationDTO);
    }

    @Override
    public EvaluationDTO createEvaluation(EvaluationDTO evaluationDTO) {
       checkValidation(evaluationDTO.getProjectId(),evaluationDTO.getEvaluatorId());
       Project project=projectRepository.findById(evaluationDTO.getProjectId()).get();
       Employee evaluator=employeeRepository.findById(evaluationDTO.getEvaluatorId()).get();
       Employee evaluatee=employeeRepository.findById(evaluationDTO.getEvaluateeId()).orElseThrow(
               ()->new ElementNotExistException("Evaluatee with id = "+evaluationDTO.getEvaluateeId()+" not exist")
       );
       if(evaluator==evaluatee){
           throw new EvaluationException("Cannot evaluate yourself");
       }
       boolean isEvaluateeInProject=employeeInProjectRepository.existsByProject_IdAndEmployee_Id(evaluationDTO.getProjectId(),evaluationDTO.getEvaluateeId());
       if(!isEvaluateeInProject){
           throw new ElementNotExistException("Evaluatee with id = "+evaluationDTO.getEvaluatorId()+" not exist in project");
       }
       Evaluation evaluation=evaluationConverter.toEntity(evaluationDTO);
       evaluation.setProject(project);
       evaluation.setEvaluator(evaluator);
       evaluation.setEvaluatee(evaluatee);
       evaluation=evaluationRepository.save(evaluation);
        return evaluationConverter.toDTO(evaluation);
    }

    @Override
    public EvaluationDTO updateEvaluation(EvaluationDTO evaluationDTO) {
        Evaluation dbEvaluation=evaluationRepository.findById(evaluationDTO.getId()).orElseThrow(
                ()->new ElementNotExistException("Evaluation not exist with id ="+evaluationDTO.getId())
        );
        Evaluation updatingEvaluation=evaluationConverter.toEntity(evaluationDTO,dbEvaluation);
        updatingEvaluation=evaluationRepository.save(updatingEvaluation);
        return evaluationConverter.toDTO(updatingEvaluation);
    }

    @Override
    public EvaluationDTO deleteEvaluation(Long evaluationId) {
        Evaluation evaluation=evaluationRepository.findById(evaluationId).orElseThrow(
                ()->new ElementNotExistException("Evaluation not exist with id ="+evaluationId)
        );
        evaluationRepository.delete(evaluation);
        return evaluationConverter.toDTO(evaluation);
    }

    public void checkValidation(Long projectId,Long employeeId){
        if(!projectRepository.existsById(projectId)){
            throw new ElementNotExistException("Project not exist with id ="+projectId);
        }
        if(!employeeInProjectRepository.existsByProject_IdAndEmployee_Id(projectId,employeeId)){
            throw new ElementNotExistException("Employee with id = "+employeeId+" not exist in project");
        }
    }
}
