package tn.espritDigachiAhmed4iet4.Domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;   
import jakarta.persistence.OneToMany;
import java.math.BigDecimal;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@JsonIgnoreProperties({"maintenances", "reservations", "hibernateLazyInitializer", "handler"})
@Entity 
@Getter 
@Setter 
@AllArgsConstructor
@NoArgsConstructor 
@Builder 
@ToString 

public class Vehicule {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idVehicule ;
    private String immatriculation ;
    private String marque ;
    private String modele ;


    @Enumerated ( value = EnumType.STRING)
    private CategorieVehicule categorie ;

    
    private BigDecimal tarifJournalier ;


    @Enumerated ( value = EnumType.STRING)
    private StatutVehicule statut ;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "vehicule_equipement",
            joinColumns = @JoinColumn(name = "id_vehicule"),
            inverseJoinColumns = @JoinColumn(name = "id_equipement"))
    @ToString.Exclude
    private Set<Equipement> equipements ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_agence")
    @ToString.Exclude
    private Agence agence ;

    @OneToMany(mappedBy = "vehicule", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @ToString.Exclude
    private List<Maintenance> maintenances ;

    @OneToMany(mappedBy = "vehicule", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Reservation> reservations ;
}
