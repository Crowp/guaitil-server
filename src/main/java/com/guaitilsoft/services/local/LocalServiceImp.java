package com.guaitilsoft.services.local;

import com.guaitilsoft.models.Local;
import com.guaitilsoft.models.Multimedia;
import com.guaitilsoft.models.constant.LocalType;
import com.guaitilsoft.utils.Utils;
import com.guaitilsoft.web.models.local.LocalRequest;
import com.guaitilsoft.web.models.local.LocalResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocalServiceImp implements LocalService {

    private final LocalRepositoryService localRepositoryService;
    private final ModelMapper modelMapper;
    private final Utils utils;

    @Autowired
    public LocalServiceImp(@Qualifier("LocalRepositoryServiceValidation") LocalRepositoryService localRepositoryService,
                           ModelMapper modelMapper,
                           Utils utils) {
        this.localRepositoryService = localRepositoryService;
        this.modelMapper = modelMapper;
        this.utils = utils;
    }

    @Override
    public List<LocalResponse> list() {

        return this.parseToLocalResponseList(localRepositoryService.list());
    }

    @Override
    public LocalResponse get(Long id) {
        return this.parseToLocalResponse(localRepositoryService.get(id));
    }

    @Override
    public LocalResponse save(LocalRequest entity) {
        Local local = this.parseToLocal(entity);
        Long memberId = entity.getMember().getId();
        local.setMember(this.utils.loadFullMember(memberId));
        this.utils.loadMultimedia(local.getMultimedia());
        return onSaveLocal(local);
    }

    private LocalResponse onSaveLocal(Local localToStore){
        Local local = localRepositoryService.save(localToStore);
        LocalResponse localResponse = this.parseToLocalResponse(local);
        this.utils.addUrlToMultimedia(localResponse.getMultimedia());
        return localResponse;
    }

    @Override
    public LocalResponse update(Long id, LocalRequest entity) {
        return this.parseToLocalResponse(localRepositoryService.update(id, this.parseToLocal(entity)));
    }

    @Override
    public void delete(Long id) {
        localRepositoryService.delete(id);
    }

    @Override
    public List<LocalResponse> getAllLocalByIdMember(Long id) {
        return this.parseToLocalResponseList(localRepositoryService.getAllLocalByIdMember(id));
    }

    @Override
    public LocalResponse deleteMultimediaById(Long id, Long idMultimedia) {
        Local local = localRepositoryService.get(id);

        Optional<Multimedia> multimediaToDelete = local.getMultimedia()
                .stream()
                .filter(m -> m.getId().equals(idMultimedia))
                .findFirst();

        multimediaToDelete.ifPresent(local::removeMultimediaById);

        localRepositoryService.save(local);
        return this.parseToLocalResponse(local);
    }

    @Override
    public List<LocalResponse> getLocalByLocalType(LocalType localType) {
        return this.list()
                .stream()
                .filter(local -> local.getLocalDescription().getLocalType().equals(localType) && local.getState())
                .collect(Collectors.toList());
    }

    @Override
    public List<Local> localList() {
        return localRepositoryService.list();
    }

    private Local parseToLocal(LocalRequest localRequest){
        return modelMapper.map(localRequest, Local.class);
    }

    private List<LocalResponse> parseToLocalResponseList(List<Local> locals){
        Type lisType = new TypeToken<List<LocalResponse>>(){}.getType();
        return modelMapper.map(locals, lisType);
    }

    private LocalResponse parseToLocalResponse(Local local){
        return modelMapper.map(local, LocalResponse.class);
    }
}
