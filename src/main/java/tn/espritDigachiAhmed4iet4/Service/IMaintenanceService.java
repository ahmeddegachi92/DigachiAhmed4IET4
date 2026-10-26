package tn.espritDigachiAhmed4iet4.Service;

import java.util.List;
import tn.espritDigachiAhmed4iet4.Domain.Maintenance;

public interface IMaintenanceService {
    Maintenance getById(Long id);
    Maintenance add(Maintenance maintenance);
    List<Maintenance> findAll();
    Maintenance update(Maintenance maintenance);
    void deleteById(Long id);
}
