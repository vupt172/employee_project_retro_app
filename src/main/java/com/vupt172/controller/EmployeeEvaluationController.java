package com.vupt172.controller;

import com.vupt172.dto.EvaluationDTO;
import com.vupt172.service.itf.IEvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects/{projectId}/employees/{employeeId}/evaluations")
public class EmployeeEvaluationController {
    @Autowired
    IEvaluationService evaluationService;

    @PostMapping("")
    public ResponseEntity<EvaluationDTO> createEvaluation(@PathVariable Long projectId, @PathVariable Long employeeId, @RequestBody EvaluationDTO evaluationDTO) {
        evaluationDTO.setProjectId(projectId);
        evaluationDTO.setEvaluatorId(employeeId);
        return ResponseEntity.ok(evaluationService.createEvaluation(evaluationDTO));
    }


}
