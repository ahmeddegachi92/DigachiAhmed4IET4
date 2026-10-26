package tn.espritDigachiAhmed4iet4.Service.Impls;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.espritDigachiAhmed4iet4.Domain.Agence;
import tn.espritDigachiAhmed4iet4.Repository.IAgenceRepo;
import tn.espritDigachiAhmed4iet4.Service.EntityLookup;
import tn.espritDigachiAhmed4iet4.Service.IAgenceService;

@Service
@RequiredArgsConstructor
@Transactional
public class AgenceServiceImpl implements IAgenceService {

    private final IAgenceRepo agenceRepo;

    @Override
    @Transactional(readOnly = true)
    public Agence getById(Long id) {
        return EntityLookup.getById(agenceRepo, id, "Agence");
    }

    @Override
    public Agence add(Agence agence) {
        EntityLookup.requireNew(agence.getIdAgence());
        return agenceRepo.save(agence);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Agence> findAll() {
        return agenceRepo.findAll();
    }

    @Override
    public Agence update(Agence agence) {
        Agence existing = getById(agence.getIdAgence());
        existing.setNom(agence.getNom());
        existing.setVille(agence.getVille());
        existing.setAdresse(agence.getAdresse());
        existing.setTelephone(agence.getTelephone());
        return agenceRepo.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        agenceRepo.delete(getById(id));
    }
}
