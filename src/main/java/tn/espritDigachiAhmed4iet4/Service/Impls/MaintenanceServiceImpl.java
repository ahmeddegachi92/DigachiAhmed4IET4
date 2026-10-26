package tn.espritDigachiAhmed4iet4.Service.Impls;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.espritDigachiAhmed4iet4.Domain.Maintenance;
import tn.espritDigachiAhmed4iet4.Repository.IMaintenanceRepo;
import tn.espritDigachiAhmed4iet4.Service.EntityLookup;
import tn.espritDigachiAhmed4iet4.Service.IMaintenanceService;
import tn.espritDigachiAhmed4iet4.Service.IVehiculeService;

@Service
@RequiredArgsConstructor
@Transactional
public class MaintenanceServiceImpl implements IMaintenanceService {

    private final IMaintenanceRepo maintenanceRepo;
    private final IVehiculeService vehiculeService;

    @Override
    @Transactional(readOnly = true)
    public Maintenance getById(Long id) {
        return EntityLookup.getById(maintenanceRepo, id, "Maintenance");
    }

    @Override
    public Maintenance add(Maintenance maintenance) {
        EntityLookup.requireNew(maintenance.getIdMaintenance());
        resolveVehicule(maintenance);
        return maintenanceRepo.save(maintenance);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Maintenance> findAll() {
        return maintenanceRepo.findAll();
    }

    @Override
    public Maintenance update(Maintenance maintenance) {
        Maintenance existing = getById(maintenance.getIdMaintenance());
        resolveVehicule(maintenance);
        existing.setDateDebut(maintenance.getDateDebut());
        existing.setDateFin(maintenance.getDateFin());
        existing.setDescription(maintenance.getDescription());
        existing.setVehicule(maintenance.getVehicule());
        return maintenanceRepo.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        maintenanceRepo.delete(getById(id));
    }

    private void resolveVehicule(Maintenance maintenance) {
        if (maintenance.getVehicule() != null) {
            maintenance.setVehicule(vehiculeService.getById(maintenance.getVehicule().getIdVehicule()));
        }
    }
}
