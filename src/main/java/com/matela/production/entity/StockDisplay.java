package com.matela.production.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockDisplay {
    private String nomProduit;
    private int quantite;
    private double volume;
    private double prixRevient;
    private double prixVente;
    private boolean estChoisit;
}
