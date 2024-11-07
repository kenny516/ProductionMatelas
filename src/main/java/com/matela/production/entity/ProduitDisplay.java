package com.matela.production.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProduitDisplay {
    private Produit produit;
    private double prixRevient;
    private Block blockInitial;
    private double quantite;
}

