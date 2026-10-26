package tn.espritDigachiAhmed4iet4.Service.Impls;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.espritDigachiAhmed4iet4.Domain.Equipement;
import tn.espritDigachiAhmed4iet4.Repository.IEquipementRepo;
import tn.espritDigachiAhmed4iet4.Service.EntityLookup;
import tn.espritDigachiAhmed4iet4.Service.IEquipementService;

@Service
@RequiredArgsConstructor
@Transactional
public class EquipementServiceImpl implements IEquipementService {

    private final IEquipementRepo equipementRepo;

    @Override
    @Transactional(readOnly = true)
    public Equipement getById(Long id) {
        return EntityLookup.getById(equipementRepo, id, "Equipement");
    }

    @Override
    public Equipement add(Equipement equipement) {
        EntityLookup.requireNew(equipement.getIdEquipement());
        return equipementRepo.save(equipement);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Equipement> findAll() {
        return equipementRepo.findAll();
    }

    @Override
    public Equipement update(Equipement equipement) {
        Equipement existing = getById(equipement.getIdEquipement());
        existing.setLibelle(equipement.getLibelle());
        return equipementRepo.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        equipementRepo.delete(getById(id));
    }
}
