package com.guaitilsoft.services.concrete;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.Local;
import com.guaitilsoft.models.Multimedia;
import com.guaitilsoft.models.constant.LocalType;
import com.guaitilsoft.repositories.LocalRepository;
import com.guaitilsoft.services.ActivityService;
import com.guaitilsoft.services.LocalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocalServiceImp implements LocalService {

    private final LocalRepository localRepository;
    private final ActivityService activityService;

    @Autowired
    public LocalServiceImp(LocalRepository localRepository, ActivityService activityService) {
        this.localRepository = localRepository;
        this.activityService = activityService;
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
        if (local != null) {
            return local;
        }
        throw new EntityNotFoundException("No se encontró un local con el id: " + id);
    }

    @Override
    public void save(Local entity) {
        assert entity != null;

        if (localRepository.existMemberPersonLocal(entity.getPersonId(), entity.getLocalType())) {
            throw new ApiRequestException("El local esta ocupado por el miembro con cédula: " + entity.getPersonId());
        }
        localRepository.save(entity);
    }

    @Override
    public void update(Long id, Local entity) {
        assert id != null;
        assert entity != null;

        Local local = this.get(id);
        local.setLocalDescription(entity.getLocalDescription());
        local.setProducts(entity.getProducts());
        local.setMultimedia(entity.getMultimedia());
        local.setState(entity.getState());
        if (localRepository.memberHaveLocalWithType(entity.getMemberId(), entity.getLocalType())) {
            throw new ApiRequestException("El miembro con la cédula " + entity.getPersonId() + " posee un local del mismo tipo");
        }
        local.setMember(entity.getMember());
        localRepository.save(local);
    }

    @Override
    public void delete(Long id) {
        assert id != null;

        Local local = this.get(id);
        activityService.removeLocalFromActivity(id);
        localRepository.delete(local);
    }

    @Override
    public Local deleteMultimediaById(Long id, Long idMultimedia) {
        Local local = this.get(id);

        Optional<Multimedia> multimediaToDelete = local.getMultimedia()
                .stream()
                .filter(m -> m.getId().equals(idMultimedia))
                .findFirst();

        multimediaToDelete.ifPresent(local::removeMultimediaById);

        localRepository.save(local);
        return local;
    }

    @Override
    public List<Local> getAllLocalByIdMember(Long id) {
        assert id != null;
        Iterable<Local> iterable = localRepository.getAllLocalByIdMember(id);
        List<Local> locals = new ArrayList<>();
        iterable.forEach(locals::add);
        return locals;
    }

    @Override
    public List<Local> getLocalByLocalType(LocalType localType) {
        return this.list()
                .stream()
                .filter(local -> local.getLocalType().equals(localType) && local.getState())
                .collect(Collectors.toList());
    }

}
