package com.matela.production.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "achatmatierepremierdetail")
public class AchatMatierePremierDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "achat_matiere_id")
    private AchatMatierePremier achatMatiere;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "matiere_premiere_id")
    private MatierePremiere matierePremiere;

    @NotNull
    @Column(name = "quantite")
    private Double quantite;

    @NotNull
    @Column(name = "prix_achat")
    private Double prixAchat;

}