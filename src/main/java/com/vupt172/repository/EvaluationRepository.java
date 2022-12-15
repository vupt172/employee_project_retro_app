package com.vupt172.repository;

import com.vupt172.entity.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation,Long> {
    @Query(value = "SELECT * FROM evaluation e" +
            " WHERE (" +
            "((:project_id is null)or(project_id=:project_id)) AND" +
            "((:evaluator_id is null)or(evaluator_id=:evaluator_id)) AND" +
            "((:evaluatee_id is null)or(evaluatee_id=:evaluatee_id)) AND" +
            "((:from is null)or(updated_date>=:from)) AND" +
            "((:to is null)or(DATE(updated_date)<=:to)) " +
            ")",nativeQuery = true)
    List<Evaluation> filterByOptions(
            @Param("project_id") Long projectId,
            @Param("evaluator_id") Long evaluatorId,
            @Param("evaluatee_id") Long evaluateeId,
            @Param("from") Date from,
            @Param("to") Date to);
    boolean existsByProject_IdAndEvaluator_Id(Long projectId,Long evaluatorId);
    boolean existsByProject_IdAndEvaluatee_Id(Long projectId,Long evaluateeId);
    @Query(value="SELECT COUNT(*) FROM evaluation WHERE (" +
            "project_id=:project_id AND " +
            "evaluator_id=:evaluator_id AND " +
            "evaluatee_id=:evaluatee_id AND " +
            "MONTH(updated_date)=:month AND " +
            "YEAR(updated_date)=:year)"
            ,nativeQuery = true)
    int existsByMonthInYear(@Param("project_id") Long projectId,
                                   @Param("evaluator_id") Long evaluatorId,
                                   @Param("evaluatee_id") Long evaluateeId,
                                   @Param("month") int month,
                                   @Param("year") int year);
}
