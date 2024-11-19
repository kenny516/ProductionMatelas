package com.matela.production.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "formuledetail")
public class FormuleDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "formule_id")
    private Formule formule;

    @NotNull
    @Column(name = "quantite", nullable = false, precision = 10, scale = 2)
    private Double quantite;

    @Size(max = 50)
    @NotNull
    @Column(name = "unite", nullable = false, length = 50)
    private String unite;

}