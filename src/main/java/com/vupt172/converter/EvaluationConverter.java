package com.vupt172.converter;

import com.vupt172.dto.EvaluationDTO;
import com.vupt172.entity.Evaluation;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class EvaluationConverter {
    public EvaluationDTO toDTO(Evaluation evaluation){
        EvaluationDTO evaluationDTO=new EvaluationDTO();
        evaluationDTO.setId(evaluation.getId());
        evaluationDTO.setProjectId(evaluation.getProject().getId());
        evaluationDTO.setEmployeeId(evaluation.getEmployeeInProject().getEmployee().getId());
        evaluationDTO.setEvaluateeId(evaluation.getEvaluateeInProject().getEmployee().getId());
        evaluationDTO.setPoint(evaluation.getPoint());
        evaluationDTO.setComment(evaluation.getComment());
        evaluationDTO.setDate(evaluation.getDate());
        return evaluationDTO;
    }
    public Evaluation toEntity(EvaluationDTO evaluationDTO){
        Evaluation evaluation=new Evaluation();
        evaluation.setPoint(evaluationDTO.getPoint());
        evaluation.setDate(new Date());
        evaluation.setComment(evaluation.getComment());
        return evaluation;
    }
}
