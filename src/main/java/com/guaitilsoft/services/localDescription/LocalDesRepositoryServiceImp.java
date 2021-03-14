package com.guaitilsoft.services.localDescription;

import com.guaitilsoft.models.LocalDescription;
import com.guaitilsoft.repositories.LocalDescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class LocalDesRepositoryServiceImp implements LocalDesRepositoryService {

    private final LocalDescriptionRepository localDescriptionRepository;

    @Autowired
    public LocalDesRepositoryServiceImp(LocalDescriptionRepository localDescriptionRepository) {
        this.localDescriptionRepository = localDescriptionRepository;
    }


    @Override
    public List<LocalDescription> list() {
        Iterable<LocalDescription> iterable = localDescriptionRepository.findAll();
        List<LocalDescription> localDescriptions = new ArrayList<>();
        iterable.forEach(localDescriptions::add);
        return localDescriptions;
    }

    @Override
    public LocalDescription get(Long id) {
        assert id != null;
        LocalDescription localDescription = localDescriptionRepository.findById(id).orElse(null);
        if (localDescription != null){
            return localDescription;
        }
        throw new EntityNotFoundException("No se encontró el local description con el id: " + id);
    }

    @Override
    public List<LocalDescription> getLocalsDescriptionNoRelationships() {
        return localDescriptionRepository.getLocalsDescriptionNoRelationships();
    }

    @Override
    public void delete(Long id) {
        assert id != null;
        LocalDescription localDescription = this.get(id);
        localDescriptionRepository.delete(localDescription);
    }
}
