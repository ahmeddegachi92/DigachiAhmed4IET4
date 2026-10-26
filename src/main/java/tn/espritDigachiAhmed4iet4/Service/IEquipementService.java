package tn.espritDigachiAhmed4iet4.Service;

import java.util.List;
import tn.espritDigachiAhmed4iet4.Domain.Equipement;

public interface IEquipementService {
    Equipement getById(Long id);
    Equipement add(Equipement equipement);
    List<Equipement> findAll();
    Equipement update(Equipement equipement);
    void deleteById(Long id);
}
