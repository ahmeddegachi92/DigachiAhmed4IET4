package tn.espritDigachiAhmed4iet4.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.espritDigachiAhmed4iet4.Domain.Maintenance;

public interface IMaintenanceRepo extends JpaRepository<Maintenance, Long> {
}
