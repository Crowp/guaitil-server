package com.guaitilsoft.services.concrete;


import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.Local;
import com.guaitilsoft.repositories.LocalRepository;
import com.guaitilsoft.services.LocalService;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Date;
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

        if(localRepository.existMemberPersonLocal(entity.personId(),entity.getLocalType())){
            throw new ApiRequestException("el local esta ocupado por el miembro, con cedula: " + entity.personId());
        }

        localRepository.save(entity);
    }

    @Override
    public void update(Long id, Local entity) {
        assert id != null;
        assert entity != null;
        Instant nowGmt = Instant.now();
        DateTimeZone americaCostaRica = DateTimeZone.forID("America/Costa_Rica");
        DateTime nowCostaRica = nowGmt.toDateTime(americaCostaRica);
        Date today = nowCostaRica.toDate();

        Local local = this.get(id);
        local.setName(entity.getName());
        local.setDescription(entity.getDescription());
        local.setTelephone(entity.getTelephone());
        local.setAddress(entity.getAddress());
        local.setUpdatedAt(today);
        local.setLocalType(entity.getLocalType());
        local.setMultimedia(entity.getMultimedia());
        entity = local;
        localRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        assert id != null;

        Local local = this.get(id);
        localRepository.delete(local);
    }
}
