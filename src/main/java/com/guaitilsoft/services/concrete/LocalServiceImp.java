package com.guaitilsoft.services.concrete;


import com.guaitilsoft.models.Local;
import com.guaitilsoft.repositories.LocalRepository;
import com.guaitilsoft.services.LocalService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class LocalServiceImp implements LocalService {
    @Autowired
    private LocalRepository localRepository;

    @Override
    public List<Local> list() {
        Iterable<Local>iterable =localRepository.findAll();
        List<Local>locals =new ArrayList<>();
        iterable.forEach(locals::add);
        return locals;
    }

    @Override
    public Local get(Long id) {
        Local local = localRepository.findById(id).orElse(null);
        if(local == null){
            throw new EntityNotFoundException();
        }
        return local;
    }

    @Override
    public void save(Local entity) {
        localRepository.save(entity);
    }

    @Override
    public void update(Long id, Local entity) {
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
        Local local = this.get(id);
        if(local != null){
            localRepository.delete(local);
        }
    }
}
