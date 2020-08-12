package com.guaitilsoft.services.concrete;


import com.guaitilsoft.models.Associated;
import com.guaitilsoft.repositories.AssociatedRepository;
import com.guaitilsoft.services.AssociatedService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class AssociatedServiceImp implements AssociatedService {

    @Autowired
    private AssociatedRepository associatedRepository;

    @Override
    public List<Associated> list() {
        Iterable<Associated>iterable =associatedRepository.findAll();
        List<Associated>associateds =new ArrayList<>();
        iterable.forEach(associateds::add);
        return associateds;
    }

    @Override
    public Associated get(Long id) {
        Associated associated = associatedRepository.findById(id).orElse(null);
        if(associated == null){
            throw new EntityNotFoundException();
        }
        return associated;
    }

    @Override
    public void save(Associated entity) {
        associatedRepository.save(entity);
    }

    @Override
    public void update(Long id, Associated entity) {
        Associated associated = this.get(id);
        associated.setOccupation(entity.getOccupation());
        associated.setMembershipDate(entity.getMembershipDate());
        associated.setPerson(entity.getPerson());
        associatedRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        Associated associated = this.get(id);
        if(associated != null){
            associatedRepository.delete(associated);
        }
    }
}
