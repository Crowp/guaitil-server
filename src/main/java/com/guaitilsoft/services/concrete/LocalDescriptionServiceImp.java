package com.guaitilsoft.services.concrete;

import com.guaitilsoft.models.LocalDescription;
import com.guaitilsoft.repositories.LocalDescriptionRepository;
import com.guaitilsoft.services.LocalDescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class LocalDescriptionServiceImp implements LocalDescriptionService {

    private final LocalDescriptionRepository localDescriptionRepository;

    @Autowired
    public LocalDescriptionServiceImp(LocalDescriptionRepository localDescriptionRepository) {
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
}
