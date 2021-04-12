package com.guaitilsoft.services.local;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.Local;
import com.guaitilsoft.repositories.LocalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("LocalRepositoryServiceBasic")
public class LocalRepositoryServiceImp implements LocalRepositoryService {

    private final LocalRepository localRepository;

    @Autowired
    public LocalRepositoryServiceImp(LocalRepository localRepository) {
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
        return this.localRepository.findById(id).orElse(null);
    }

    @Override
    public Local getByLocalDescriptionId(Long localDescriptionId) {
        Optional<Local> local = this.localRepository.getByLocalDescriptionId(localDescriptionId);
        if (local.isPresent()) {
            return local.get();
        }
        throw new EntityNotFoundException("No se encontró el local");
    }

    @Override
    public Local save(Local entity) {
        assert entity != null;
        if (localRepository.existMemberPersonLocal(this.getPersonId(entity), entity.getLocalType())) {
            throw new ApiRequestException("Esta persona ya cuenta con un local del mismo tipo");
        }
        return this.localRepository.save(entity);
    }

    @Override
    public Local update(Long id, Local entity) {
        assert id != null;
        assert entity != null;

        Local local = this.get(id);
        local.setLocalDescription(entity.getLocalDescription());
        local.setProducts(entity.getProducts());
        local.setMultimedia(entity.getMultimedia());
        local.setShowLocal(entity.getShowLocal());
        if (localRepository.memberHaveLocalWithType(entity.getMember().getId(), entity.getId() , entity.getLocalType())) {
            throw new ApiRequestException("El miembro con la cédula " + this.getPersonId(entity) + " posee un local del mismo tipo");
        }
        local.setMember(entity.getMember());
        return this.localRepository.save(local);
    }

    @Override
    public void delete(Long id) {
        Local local = this.get(id);
        localRepository.delete(local);
    }

    @Override
    public List<Local> getAllLocalByIdMember(Long id) {
        Iterable<Local> iterable = localRepository.getAllLocalByIdMember(id);
        List<Local> locals = new ArrayList<>();
        iterable.forEach(locals::add);
        return locals;
    }

    private String getPersonId(Local local){
        return local.getMember().getPerson().getId();
    }
}
