package com.matela.production.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "block")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Block extends Dimension{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double coutProduction;
    @ManyToOne
    @JoinColumn(name = "block_mere")
    private Block blockMere;
    @Column(name = "date_production", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dateProduction;

}
