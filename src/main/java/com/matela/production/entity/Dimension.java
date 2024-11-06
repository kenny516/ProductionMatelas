package com.matela.production.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class Dimension {
    protected Double longueur;
    protected Double largeur;
    protected Double epaisseur;

    public Double getVolume() {
        return longueur * largeur * epaisseur;
    }
}
