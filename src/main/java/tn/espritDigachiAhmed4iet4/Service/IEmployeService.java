package tn.espritDigachiAhmed4iet4.Service;

import java.util.List;
import tn.espritDigachiAhmed4iet4.Domain.Employe;

public interface IEmployeService {
    Employe getById(Long id);
    Employe add(Employe employe);
    List<Employe> findAll();
    Employe update(Employe employe);
    void deleteById(Long id);
}
