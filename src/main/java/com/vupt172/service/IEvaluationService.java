package com.vupt172.service;

import com.vupt172.dto.EvaluationDTO;
import java.util.*;
public interface IEvaluationService {
 List<EvaluationDTO> findAll();
 Optional<EvaluationDTO> findById(Long evaluationId);
 EvaluationDTO createEvaluation(EvaluationDTO evaluationDTO);
 EvaluationDTO updateEvaluation(EvaluationDTO evaluationDTO);
 EvaluationDTO deleteEvaluation(Long evaluationId);
}
