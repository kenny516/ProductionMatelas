package com.matela.production.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sortie")
public class Sortie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_achatmateriel", nullable = false)
    private Long achatMatierePremierId;

    @Column(name = "quantite",nullable = false)
    private Double quantite;

    @Column(name = "date_sortie", nullable = false)
    private LocalDate dateSortie = LocalDate.now();

}
