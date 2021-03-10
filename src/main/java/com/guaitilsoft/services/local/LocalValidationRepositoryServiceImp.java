package com.guaitilsoft.services.local;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Primary
@Service("LocalRepositoryServiceValidation")
public class LocalValidationRepositoryServiceImp implements LocalRepositoryService {

    private final LocalRepositoryService localRepositoryService;

    @Autowired
    public LocalValidationRepositoryServiceImp(@Qualifier("LocalRepositoryServiceBasic") LocalRepositoryService localRepositoryService) {
        this.localRepositoryService = localRepositoryService;
    }

    @Override
    public List<Local> list() {
        return localRepositoryService.list();
    }

    @Override
    public Local get(Long id) {
        Local local = localRepositoryService.get(id);
        if (local != null) {
            return local;
        }
        throw new EntityNotFoundException("No se encontró un local con el id: " + id);
    }

    @Override
    public Local save(Local entity) {
        return localRepositoryService.save(entity);
    }

    @Override
    public Local update(Long id, Local entity) {
        if (!id.equals(entity.getId())) {
            throw new ApiRequestException("El id del local: " + entity.getId() + " es diferente al id del parametro: " + id);
        }
        return localRepositoryService.update(id, entity);
    }

    @Override
    public void delete(Long id) {
        localRepositoryService.delete(id);
    }

    @Override
    public List<Local> getAllLocalByIdMember(Long id) {
        return localRepositoryService.getAllLocalByIdMember(id);
    }
}
