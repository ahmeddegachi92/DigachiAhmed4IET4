package tn.espritDigachiAhmed4iet4.Service;

import java.util.List;
import tn.espritDigachiAhmed4iet4.Domain.Client;

public interface IClientService {
    Client getById(Long id);
    Client add(Client client);
    List<Client> findAll();
    Client update(Client client);
    void deleteById(Long id);
}
