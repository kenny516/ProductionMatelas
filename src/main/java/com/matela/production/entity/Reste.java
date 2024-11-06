package com.matela.production.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "reste")
public class Reste extends Dimension {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "block_id")
    private Block block;


    @Column(name = "cout_production")
    private Double coutProduction;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "date_creation")
    private LocalDateTime dateCreation;

}