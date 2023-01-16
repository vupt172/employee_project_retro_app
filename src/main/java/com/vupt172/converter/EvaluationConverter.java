package com.vupt172.converter;

import com.vupt172.dto.EvaluationDTO;
import com.vupt172.entity.Evaluation;

import java.util.Date;


public class EvaluationConverter {
    public String message="this is evaluation converter";
    public EvaluationDTO toDTO(Evaluation evaluation) {
        EvaluationDTO evaluationDTO = new EvaluationDTO();
        evaluationDTO.setId(evaluation.getId());
        evaluationDTO.setProjectId(evaluation.getProject().getId());
        evaluationDTO.setEvaluatorId(evaluation.getEvaluator().getId());
        evaluationDTO.setEvaluateeId(evaluation.getEvaluatee().getId());
        evaluationDTO.setPoint(evaluation.getPoint());
        evaluationDTO.setComment(evaluation.getComment());
        evaluationDTO.setCreatedDate(evaluation.getCreatedDate());
        evaluationDTO.setUpdatedDate(evaluation.getUpdatedDate());
        return evaluationDTO;
    }

    public Evaluation toEntity(EvaluationDTO evaluationDTO) {
        Evaluation evaluation = new Evaluation();
        evaluation.setPoint(evaluationDTO.getPoint());
        evaluation.setCreatedDate(new Date());
        evaluation.setUpdatedDate(evaluation.getCreatedDate());
        evaluation.setComment(evaluationDTO.getComment());
        return evaluation;
    }
    /**
     * merge new dto and db employee to updating info
     * */
    public Evaluation toEntity(EvaluationDTO dto,Evaluation dbEvaluation){
        Evaluation evaluation=new Evaluation();
        // cannot update project,evaluator,evaluatee
        evaluation.setId(dto.getId());
        evaluation.setProject(dbEvaluation.getProject());
        evaluation.setEvaluator(dbEvaluation.getEvaluator());
        evaluation.setEvaluatee(dbEvaluation.getEvaluatee());
        //
        evaluation.setPoint(dto.getPoint());
        evaluation.setComment(dto.getComment());
        evaluation.setCreatedDate(dbEvaluation.getCreatedDate());
        evaluation.setUpdatedDate(new Date());
        return evaluation;
    }
}
