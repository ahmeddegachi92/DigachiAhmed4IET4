package tn.espritDigachiAhmed4iet4.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.espritDigachiAhmed4iet4.Domain.Contrat;

public interface IContratRepo extends JpaRepository<Contrat, Long> {
}
