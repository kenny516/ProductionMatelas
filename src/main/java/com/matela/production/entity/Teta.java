package com.matela.production.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "teta")
public class Teta {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teta_id_gen")
    @SequenceGenerator(name = "teta_id_gen", sequenceName = "teta_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    private Double value;
}