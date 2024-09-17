package com.jackson.personal_finance_manager.model;

package com.example.demo.entity;

import jakarta.persistence.*;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Goal")
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long goalID;

    @ManyToOne
    @JoinColumn(name = "UserID", nullable = false)
    private User user;

    private String name;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal targetAmount;

    @Column(precision = 10, scale = 2)
    private BigDecimal savedAmount = BigDecimal.ZERO;

    @Column(nullable = false)
    private LocalDateTime deadline;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

}

