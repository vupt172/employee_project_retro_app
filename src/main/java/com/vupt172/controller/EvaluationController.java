package com.vupt172.controller;

import com.vupt172.converter.EvaluationConverter;
import com.vupt172.dto.EvaluationDTO;
import com.vupt172.entity.Evaluation;
import com.vupt172.repository.EvaluationRepository;
import com.vupt172.service.IEvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.sql.Date;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/evaluations")
public class EvaluationController {
    @Autowired
    IEvaluationService evaluationService;
    @Autowired
    EvaluationRepository evaluationRepository;
    @Autowired
    EvaluationConverter evaluationConverter;
/*    @GetMapping("")
    public ResponseEntity<Object> getAll(){
        return ResponseEntity.ok(evaluationService.findAll());
    }*/
    @GetMapping("")
    public ResponseEntity<Object> filterByOptions(@RequestParam(required = false) Long projectId,@RequestParam(required = false) Long evaluatorId,@RequestParam(required = false) Long evaluateeId,@RequestParam(required = false) Date from,@RequestParam(required=false) Date to){
        System.out.println(from);
        System.out.println(to);
        if(projectId==null&&evaluatorId==null&&evaluateeId==null&&from==null&&to==null){
            return ResponseEntity.ok(evaluationService.findAll());
        }
        List<Evaluation> filterList=evaluationRepository.filterByOptions(projectId, evaluatorId, evaluateeId, from, to);
        List<EvaluationDTO> dtos=filterList.stream().map(e->evaluationConverter.toDTO(e)).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<EvaluationDTO> getEvaluationById(@PathVariable Long id){
        EvaluationDTO evaluationDTO=evaluationService.findById(id).orElse(null);
        if(evaluationDTO==null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(evaluationDTO);
    }
    @PostMapping("")
    public ResponseEntity<EvaluationDTO> createEvaluation(@RequestBody EvaluationDTO evaluationDTO){
        return ResponseEntity.ok(evaluationService.createEvaluation(evaluationDTO));
    }
    @PutMapping("/{id}")
    public ResponseEntity<EvaluationDTO> updateEvaluation(@PathVariable Long id,@RequestBody EvaluationDTO evaluationDTO){
        evaluationDTO.setId(id);
        return ResponseEntity.ok(evaluationService.updateEvaluation(evaluationDTO));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<EvaluationDTO> deleteEvaluation(@PathVariable Long id){
        return ResponseEntity.ok(evaluationService.deleteEvaluation(id));
    }
}
