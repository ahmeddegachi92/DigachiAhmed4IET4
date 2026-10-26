package tn.espritDigachiAhmed4iet4.Service;

import java.util.List;
import tn.espritDigachiAhmed4iet4.Domain.Paiement;

public interface IPaiementService {
    Paiement getById(Long id);
    Paiement add(Paiement paiement);
    List<Paiement> findAll();
    Paiement update(Paiement paiement);
    void deleteById(Long id);
}
