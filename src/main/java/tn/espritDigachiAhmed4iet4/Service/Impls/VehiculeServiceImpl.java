package tn.espritDigachiAhmed4iet4.Service.Impls;
import tn.espritDigachiAhmed4iet4.Domain.Vehicule;
import tn.espritDigachiAhmed4iet4.Repository.IVehiculeRepo;
import tn.espritDigachiAhmed4iet4.Service.IVehiculeService;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import tn.espritDigachiAhmed4iet4.Domain.Equipement;
import tn.espritDigachiAhmed4iet4.Service.EntityLookup;
import tn.espritDigachiAhmed4iet4.Service.IAgenceService;
import tn.espritDigachiAhmed4iet4.Service.IEquipementService;


@Service
@RequiredArgsConstructor
@Transactional
public class VehiculeServiceImpl implements IVehiculeService {


    private final IVehiculeRepo iVehiculeRepo;
    private final IAgenceService agenceService;
    private final IEquipementService equipementService;

    @Override
    @Transactional(readOnly = true)
    public Vehicule getById(Long id) {
        return EntityLookup.getById(iVehiculeRepo, id, "Vehicule");
    }

    @Override
    public Vehicule add(Vehicule vehicule) {
        EntityLookup.requireNew(vehicule.getIdVehicule());
        resolveAssociations(vehicule);
        return iVehiculeRepo.save(vehicule);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Vehicule> findAll() {
        return iVehiculeRepo.findAll();
    }

    @Override
    public Vehicule update(Vehicule vehicule) {
        Vehicule existing = getById(vehicule.getIdVehicule());
        resolveAssociations(vehicule);
        existing.setImmatriculation(vehicule.getImmatriculation());
        existing.setMarque(vehicule.getMarque());
        existing.setModele(vehicule.getModele());
        existing.setCategorie(vehicule.getCategorie());
        existing.setTarifJournalier(vehicule.getTarifJournalier());
        existing.setStatut(vehicule.getStatut());
        existing.setAgence(vehicule.getAgence());
        existing.getEquipements().clear();
        existing.getEquipements().addAll(vehicule.getEquipements());
        return iVehiculeRepo.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        iVehiculeRepo.delete(getById(id));
    }

    private void resolveAssociations(Vehicule vehicule) {
        if (vehicule.getAgence() != null) {
            vehicule.setAgence(agenceService.getById(vehicule.getAgence().getIdAgence()));
        }
        Set<Equipement> equipements = new HashSet<>();
        if (vehicule.getEquipements() != null) {
            for (Equipement equipement : vehicule.getEquipements()) {
                if (equipement == null) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "An equipement reference must not be null");
                }
                equipements.add(equipementService.getById(equipement.getIdEquipement()));
            }
        }
        vehicule.setEquipements(equipements);
    }

}
