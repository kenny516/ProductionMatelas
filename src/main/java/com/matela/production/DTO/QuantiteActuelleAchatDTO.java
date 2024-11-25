package com.matela.production.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Immutable
public class QuantiteActuelleAchatDTO {
    private Long idAchat;
    private Long matierePremiereId;
    private Double quantiteActuelle;
    private Double prixAchat;
    private LocalDate dateAchat;
}



