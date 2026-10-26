package tn.espritDigachiAhmed4iet4.Service.Impls;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.espritDigachiAhmed4iet4.Domain.Contrat;
import tn.espritDigachiAhmed4iet4.Repository.IContratRepo;
import tn.espritDigachiAhmed4iet4.Service.EntityLookup;
import tn.espritDigachiAhmed4iet4.Service.IContratService;
import tn.espritDigachiAhmed4iet4.Service.IReservationService;

@Service
@RequiredArgsConstructor
@Transactional
public class ContratServiceImpl implements IContratService {

    private final IContratRepo contratRepo;
    private final IReservationService reservationService;

    @Override
    @Transactional(readOnly = true)
    public Contrat getById(Long id) {
        return EntityLookup.getById(contratRepo, id, "Contrat");
    }

    @Override
    public Contrat add(Contrat contrat) {
        EntityLookup.requireNew(contrat.getIdContrat());
        resolveReservation(contrat);
        return contratRepo.save(contrat);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Contrat> findAll() {
        return contratRepo.findAll();
    }

    @Override
    public Contrat update(Contrat contrat) {
        Contrat existing = getById(contrat.getIdContrat());
        resolveReservation(contrat);
        existing.setDateSignature(contrat.getDateSignature());
        existing.setMontantTotal(contrat.getMontantTotal());
        existing.setValide(contrat.isValide());
        existing.setReservation(contrat.getReservation());
        return contratRepo.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        contratRepo.delete(getById(id));
    }

    private void resolveReservation(Contrat contrat) {
        if (contrat.getReservation() != null) {
            contrat.setReservation(reservationService.getById(contrat.getReservation().getIdReservation()));
        }
    }
}
