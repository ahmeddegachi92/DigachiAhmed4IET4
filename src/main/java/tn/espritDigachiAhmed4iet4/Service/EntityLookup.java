package tn.espritDigachiAhmed4iet4.Service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public final class EntityLookup {

    private EntityLookup() {
    }

    public static <T> T getById(JpaRepository<T, Long> repository, Long id, String entityName) {
        if (id == null || id <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    entityName + " requires a positive ID");
        }
        return repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, entityName + " not found: " + id));
    }

    public static void requireNew(Long id) {
        if (id != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Do not supply an ID when creating an entity");
        }
    }
}
