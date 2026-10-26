package tn.espritDigachiAhmed4iet4.Service;

import java.util.List;
import tn.espritDigachiAhmed4iet4.Domain.Reservation;

public interface IReservationService {
    Reservation getById(Long id);
    Reservation add(Reservation reservation);
    List<Reservation> findAll();
    Reservation update(Reservation reservation);
    void deleteById(Long id);
}
