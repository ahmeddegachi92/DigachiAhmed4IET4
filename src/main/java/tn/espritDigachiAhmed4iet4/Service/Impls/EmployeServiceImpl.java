package tn.espritDigachiAhmed4iet4.Service.Impls;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.espritDigachiAhmed4iet4.Domain.Employe;
import tn.espritDigachiAhmed4iet4.Repository.IEmployeRepo;
import tn.espritDigachiAhmed4iet4.Service.EntityLookup;
import tn.espritDigachiAhmed4iet4.Service.IAgenceService;
import tn.espritDigachiAhmed4iet4.Service.IEmployeService;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeServiceImpl implements IEmployeService {

    private final IEmployeRepo employeRepo;
    private final IAgenceService agenceService;

    @Override
    @Transactional(readOnly = true)
    public Employe getById(Long id) {
        return EntityLookup.getById(employeRepo, id, "Employe");
    }

    @Override
    public Employe add(Employe employe) {
        EntityLookup.requireNew(employe.getIdEmploye());
        resolveAgence(employe);
        return employeRepo.save(employe);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employe> findAll() {
        return employeRepo.findAll();
    }

    @Override
    public Employe update(Employe employe) {
        Employe existing = getById(employe.getIdEmploye());
        resolveAgence(employe);
        existing.setNom(employe.getNom());
        existing.setPrenom(employe.getPrenom());
        existing.setRole(employe.getRole());
        existing.setAgence(employe.getAgence());
        return employeRepo.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        employeRepo.delete(getById(id));
    }

    private void resolveAgence(Employe employe) {
        if (employe.getAgence() != null) {
            employe.setAgence(agenceService.getById(employe.getAgence().getIdAgence()));
        }
    }
}
