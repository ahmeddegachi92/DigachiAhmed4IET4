package tn.espritDigachiAhmed4iet4.Service.Impls;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.espritDigachiAhmed4iet4.Domain.Reservation;
import tn.espritDigachiAhmed4iet4.Repository.IReservationRepo;
import tn.espritDigachiAhmed4iet4.Service.EntityLookup;
import tn.espritDigachiAhmed4iet4.Service.IClientService;
import tn.espritDigachiAhmed4iet4.Service.IReservationService;
import tn.espritDigachiAhmed4iet4.Service.IVehiculeService;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationServiceImpl implements IReservationService {

    private final IReservationRepo reservationRepo;
    private final IClientService clientService;
    private final IVehiculeService vehiculeService;

    @Override
    @Transactional(readOnly = true)
    public Reservation getById(Long id) {
        return EntityLookup.getById(reservationRepo, id, "Reservation");
    }

    @Override
    public Reservation add(Reservation reservation) {
        EntityLookup.requireNew(reservation.getIdReservation());
        resolveAssociations(reservation);
        return reservationRepo.save(reservation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reservation> findAll() {
        return reservationRepo.findAll();
    }

    @Override
    public Reservation update(Reservation reservation) {
        Reservation existing = getById(reservation.getIdReservation());
        resolveAssociations(reservation);
        existing.setDateDebut(reservation.getDateDebut());
        existing.setDateFin(reservation.getDateFin());
        existing.setStatut(reservation.getStatut());
        existing.setClient(reservation.getClient());
        existing.setVehicule(reservation.getVehicule());
        return reservationRepo.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        reservationRepo.delete(getById(id));
    }

    private void resolveAssociations(Reservation reservation) {
        if (reservation.getClient() != null) {
            reservation.setClient(clientService.getById(reservation.getClient().getIdClient()));
        }
        if (reservation.getVehicule() != null) {
            reservation.setVehicule(vehiculeService.getById(reservation.getVehicule().getIdVehicule()));
        }
    }
}
