package com.guaitilsoft.services.local;

import com.guaitilsoft.models.Local;
import com.guaitilsoft.models.Multimedia;
import com.guaitilsoft.models.constant.LocalType;
import com.guaitilsoft.services.multimedia.MultimediaService;
import com.guaitilsoft.utils.Utils;
import com.guaitilsoft.web.models.local.LocalRequest;
import com.guaitilsoft.web.models.local.LocalResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocalServiceImp implements LocalService {

    private final LocalRepositoryService localRepositoryService;
    private final MultimediaService multimediaService;
    private final ModelMapper modelMapper;

    @Autowired
    public LocalServiceImp(LocalRepositoryService localRepositoryService,
                           MultimediaService multimediaService,
                           ModelMapper modelMapper) {
        this.localRepositoryService = localRepositoryService;
        this.multimediaService = multimediaService;
        this.modelMapper = modelMapper;
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
        loadMultimedia(local.getMultimedia());
        return onSaveLocal(local);
    }

    private LocalResponse onSaveLocal(Local localToStore){
        Local local = localRepositoryService.save(localToStore);
        return this.parseToLocalResponse(local);
    }

    @Override
    public LocalResponse update(Long id, LocalRequest entity) {
        return this.parseToLocalResponse(localRepositoryService.update(id, this.parseToLocal(entity)));
    }

    @Override
    public LocalResponse updateShowLocal(Long id) {
        Local local = localRepositoryService.get(id);
        local.setShowLocal(!local.getShowLocal());
        return this.update(id, this.parseToLocalRequest(local));
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
                .filter(local -> local.getLocalDescription().getLocalType().equals(localType) && local.getShowLocal())
                .collect(Collectors.toList());
    }

    @Override
    public List<Local> localList() {
        return localRepositoryService.list();
    }

    private Local parseToLocal(LocalRequest localRequest){
        return modelMapper.map(localRequest, Local.class);
    }

    private LocalRequest parseToLocalRequest(Local local){ return modelMapper.map(local, LocalRequest.class); }

    private List<LocalResponse> parseToLocalResponseList(List<Local> list){
        Type lisType = new TypeToken<List<LocalResponse>>(){}.getType();
        List<LocalResponse> locals = modelMapper.map(list, lisType);
        locals.forEach(l -> Utils.addUrlToMultimedia(l.getMultimedia()));
        return locals;
    }

    private LocalResponse parseToLocalResponse(Local local){
        LocalResponse localResponse =  modelMapper.map(local, LocalResponse.class);
        Utils.addUrlToMultimedia(localResponse.getMultimedia());
        return localResponse;
    }

    private void loadMultimedia(List<Multimedia> multimediaList) {
        List<Multimedia> multimediaLoaded = new ArrayList<>();
        multimediaList.forEach(media -> multimediaLoaded.add(multimediaService.get(media.getId())));
        multimediaList.clear();
        multimediaList.addAll(multimediaLoaded);
    }
}
