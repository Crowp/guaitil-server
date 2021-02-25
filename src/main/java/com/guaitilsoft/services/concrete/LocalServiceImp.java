package com.guaitilsoft.services.concrete;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.Local;
import com.guaitilsoft.models.Multimedia;
import com.guaitilsoft.models.Product;
import com.guaitilsoft.models.constant.LocalType;
import com.guaitilsoft.repositories.LocalRepository;
import com.guaitilsoft.services.ActivityService;
import com.guaitilsoft.services.LocalService;
import com.guaitilsoft.services.MultimediaService;
import com.guaitilsoft.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocalServiceImp implements LocalService {

    private final LocalRepository localRepository;
    private final ProductService productService;
    private final MultimediaService multimediaService;
    private final ActivityService activityService;

    @Autowired
    public LocalServiceImp(LocalRepository localRepository, ProductService productService, MultimediaService multimediaService, ActivityService activityService) {
        this.localRepository = localRepository;
        this.productService = productService;
        this.multimediaService = multimediaService;
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
        if(local != null){
            return local;
        }
        throw new EntityNotFoundException("No se encontro un local con el id: " + id);
    }

    @Override
    public void save(Local entity) {
        assert entity != null;

        if(localRepository.existMemberPersonLocal(entity.personId(),entity.getLocalType())){
            throw new ApiRequestException("El local esta ocupado por el miembro con cédula: " + entity.personId());
        }
        loadMultimedia(entity);
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
        local.setProducts(entity.getProducts());
        local.setMultimedia(entity.getMultimedia());
        if(!local.getMember().getId().equals(entity.getMember().getId())) {
            if (localRepository.memberHaveLocal(entity.getMember().getId(), entity.getLocalType())) {
                throw new ApiRequestException("El miembro con la cédula " + entity.personId() + " posee un local del mismo tipo");
            }
        }
        local.setMember(entity.getMember());

        entity = local;
        loadMultimedia(entity);
        localRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        assert id != null;

        Local local = this.get(id);
        List<Multimedia> multimediaList = new ArrayList<>(local.getMultimedia());
        List<Product> productList = new ArrayList<>(local.getProducts());
        local.setMultimedia(null);
        local.setProducts(null);
        localRepository.save(local);
        if(multimediaList.size() > 0){
            multimediaList.forEach(media -> multimediaService.delete(media.getId()));
        }
        if(productList.size() > 0){
            productList.forEach(product -> productService.delete(product.getId()));
        }
        activityService.removeLocalFromActivity(id);
        localRepository.delete(local);
    }

    @Override
    public Local deleteMultimediaById(Long id, Long idMultimedia) {
        Local local = this.get(id);
        List<Multimedia> multimedia = local.getMultimedia()
                .stream()
                .filter(media -> !media.getId().equals(idMultimedia))
                .collect(Collectors.toList());
        local.setMultimedia(multimedia);
        localRepository.save(local);
        multimediaService.delete(idMultimedia);
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
                .filter(local -> local.getLocalType().equals(localType))
                .collect(Collectors.toList());
    }

    public void loadMultimedia(Local entity){
        if (entity.getMultimedia().size() > 0){
            List<Multimedia> multimediaList = new ArrayList<>();
            entity.getMultimedia().forEach(media -> {
                Multimedia multimedia = multimediaService.get(media.getId());
                multimediaList.add(multimedia);
            });
            entity.setMultimedia(multimediaList);
        }
    }
}
