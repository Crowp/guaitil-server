package com.guaitilsoft.services.concrete;


import com.guaitilsoft.models.Associated;
import com.guaitilsoft.repositories.AssociatedRepository;
import com.guaitilsoft.services.AssociatedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AssociatedServiceImp implements AssociatedService {

    private AssociatedRepository associatedRepository;

    @Autowired
    public AssociatedServiceImp(AssociatedRepository associatedRepository) {
        this.associatedRepository = associatedRepository;
    }

    @Override
    public List<Associated> list() {
        Iterable<Associated> iterable = associatedRepository.findAll();
        List<Associated> associates = new ArrayList<>();
        iterable.forEach(associates::add);
        return associates;
    }

    @Override
    public Associated get(Long id) {
        assert id != null;

        Associated associated = associatedRepository.findById(id).orElse(null);
        if(associated != null){
            return associated;
        }
        throw new EntityNotFoundException("No se encontro un asociado con el id: " + id);
    }

    @Override
    public void save(Associated entity) {
        assert entity != null;
        associatedRepository.save(entity);
    }

    @Override
    public void update(Long id, Associated entity) {
        assert id != null;
        assert entity != null;

        Associated associated = this.get(id);
        associated.setOccupation(entity.getOccupation());
        associated.setMembershipDate(entity.getMembershipDate());
        associatedRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        assert id != null;

        Associated associated = this.get(id);
        if(associated != null){
            associatedRepository.delete(associated);
        }
    }
}
