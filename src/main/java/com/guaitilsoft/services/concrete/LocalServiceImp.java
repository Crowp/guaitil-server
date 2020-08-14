package com.guaitilsoft.services.concrete;


import com.guaitilsoft.models.Local;
import com.guaitilsoft.repositories.LocalRepository;
import com.guaitilsoft.services.LocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class LocalServiceImp implements LocalService {

    private LocalRepository localRepository;

    @Autowired
    public LocalServiceImp(LocalRepository localRepository) {
        this.localRepository = localRepository;
    }

    @Override
    public List<Local> list() {
        Iterable<Local> iterable = localRepository.findAll();
        List<Local> locals = new ArrayList<>();
        iterable.forEach(locals::add);
        return locals;
    }

    @Override
    public Local get(Long id) {
        assert id != null;

        Local local = localRepository.findById(id).orElse(null);
        if(local != null){
            return local;
        }
        throw new EntityNotFoundException("No se encontro un local con el id: " + id);
    }

    @Override
    public void save(Local entity) {
        assert entity != null;

        localRepository.save(entity);
    }

    @Override
    public void update(Long id, Local entity) {
        assert id != null;
        assert entity != null;

        Local local = this.get(id);
        local.setName(entity.getName());
        local.setDescription(entity.getDescription());
        local.setTelephone(entity.getTelephone());
        local.setAddress(entity.getAddress());
        local.setLocalType(entity.getLocalType());
        local.setPerson(entity.getPerson());
        local.setMultimedia(entity.getMultimedia());
        localRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        assert id != null;

        Local local = this.get(id);
        localRepository.delete(local);
    }
}
