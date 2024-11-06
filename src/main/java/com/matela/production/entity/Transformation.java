package com.matela.production.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "transformation")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "block_id", nullable = false)
    private Block block;
    @ManyToOne
    @JoinColumn(name = "reste_id", nullable = false)
    private Reste reste;
    @Column(name = "date_transformation", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime dateTransformation;


}
