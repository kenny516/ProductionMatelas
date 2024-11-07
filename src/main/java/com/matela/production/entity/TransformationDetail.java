package com.matela.production.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "transformation_detail")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransformationDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "transformation_id")
    Transformation transformation;
    @ManyToOne
    @JoinColumn(name = "produit_id", nullable = false)
    private Produit produit;
    private Integer quantite;
    private Double prixRevient;
}
