package com.seshwoods.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="employee")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Transient
    private Long organizationId;
    @Transient
    private Long departmentId;
    private String name;
    private int age;
    private String position;
}