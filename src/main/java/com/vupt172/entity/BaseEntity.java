package com.vupt172.entity;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
/*    @Column
    @CreatedDate
    private Date createdAt;
    @Column
    @CreatedBy
    private String createdBy;
    @Column
    @LastModifiedDate
    private Date updatedAt;
    @Column
    @LastModifiedBy
    private String updatedBy;*/
}
