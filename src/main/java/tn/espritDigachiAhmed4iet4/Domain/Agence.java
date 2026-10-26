package tn.espritDigachiAhmed4iet4.Domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;

import lombok.*;
import lombok.experimental.FieldDefaults;

@JsonIgnoreProperties({"vehicules", "employes", "hibernateLazyInitializer", "handler"})
@Entity 
@Getter 
@Setter 
@AllArgsConstructor
@NoArgsConstructor 
@Builder 
@ToString
public class Agence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAgence ;
    private String nom ;
    private String ville ;
    private String adresse ;
    private String telephone ;


    @OneToMany(mappedBy = "agence", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Vehicule> vehicules ;

    @OneToMany(mappedBy = "agence", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Employe> employes ;


}
