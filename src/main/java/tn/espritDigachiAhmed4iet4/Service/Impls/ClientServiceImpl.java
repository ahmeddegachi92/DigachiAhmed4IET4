package tn.espritDigachiAhmed4iet4.Service.Impls;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.espritDigachiAhmed4iet4.Domain.Client;
import tn.espritDigachiAhmed4iet4.Repository.IClientRepo;
import tn.espritDigachiAhmed4iet4.Service.EntityLookup;
import tn.espritDigachiAhmed4iet4.Service.IClientService;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientServiceImpl implements IClientService {

    private final IClientRepo clientRepo;

    @Override
    @Transactional(readOnly = true)
    public Client getById(Long id) {
        return EntityLookup.getById(clientRepo, id, "Client");
    }

    @Override
    public Client add(Client client) {
        EntityLookup.requireNew(client.getIdClient());
        return clientRepo.save(client);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Client> findAll() {
        return clientRepo.findAll();
    }

    @Override
    public Client update(Client client) {
        Client existing = getById(client.getIdClient());
        existing.setNom(client.getNom());
        existing.setPrenom(client.getPrenom());
        existing.setEmail(client.getEmail());
        existing.setTelephone(client.getTelephone());
        existing.setNumPermis(client.getNumPermis());
        existing.setDateInscription(client.getDateInscription());
        return clientRepo.save(existing);
    }

    @Override
    public void deleteById(Long id) {
        clientRepo.delete(getById(id));
    }
}
