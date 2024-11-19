package com.matela.production.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "matierepremiere")
public class MatierePremiere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 100)
    @NotNull
    @Column(name = "nom")
    private String nom;

    @NotNull
    @Column(name = "prix_achat")
    private Double prixAchat;

    @OneToMany(mappedBy = "matierePremiere")
    private Set<AchatMatierePremierDetail> achatMatierePremierDetails = new LinkedHashSet<>();

}