package com.guaitilsoft.services.concrete;


import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.Local;
import com.guaitilsoft.models.Multimedia;
import com.guaitilsoft.repositories.LocalRepository;
import com.guaitilsoft.services.LocalService;
import com.guaitilsoft.services.MultimediaService;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocalServiceImp implements LocalService {

    private LocalRepository localRepository;
    private MultimediaService multimediaService;

    @Autowired
    public LocalServiceImp(LocalRepository localRepository, MultimediaService multimediaService) {
        this.localRepository = localRepository;
        this.multimediaService = multimediaService;
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
        local.setMember(entity.getMember());
        local.setLocalType(entity.getLocalType());
        local.setProducts(entity.getProducts());
        local.setMultimedia(entity.getMultimedia());
        local.setUpdatedAt(today);
        entity = local;
        localRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        assert id != null;

        Local local = this.get(id);
        List<Multimedia> multimediaList = new ArrayList<>(local.getMultimedia());
        local.setMultimedia(null);
        localRepository.save(local);
        if(multimediaList.size() > 0){
            multimediaList.forEach(media -> {
                multimediaService.delete(media.getId());
            });
        }
        localRepository.delete(local);
    }

    @Override
    public void deleteMultimediaById(Long id, Long idMultimedia) {
        Local local = this.get(id);
        List<Multimedia> multimedia = local.getMultimedia()
                .stream()
                .filter(media -> !media.getId().equals(idMultimedia))
                .collect(Collectors.toList());
        local.setMultimedia(multimedia);
        localRepository.save(local);
        multimediaService.delete(idMultimedia);
    }
}
