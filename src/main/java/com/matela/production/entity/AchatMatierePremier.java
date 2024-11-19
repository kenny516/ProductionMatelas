package com.matela.production.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "achatmatierepremier")
public class AchatMatierePremier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ColumnDefault("CURRENT_DATE")
    @Column(name = "date_achat")
    private LocalDate dateAchat;

    @OneToMany(mappedBy = "achatMatiere")
    private Set<AchatMatierePremierDetail> achatMatierePremierDetails = new LinkedHashSet<>();

}