package com.guaitilsoft.services.local;

import com.guaitilsoft.models.Local;
import com.guaitilsoft.models.Multimedia;
import com.guaitilsoft.models.constant.LocalType;
import com.guaitilsoft.services.multimedia.MultimediaService;
import com.guaitilsoft.services.user.UserService;
import com.guaitilsoft.utils.Utils;
import com.guaitilsoft.web.models.local.LocalRequest;
import com.guaitilsoft.web.models.local.LocalResponse;
import com.guaitilsoft.web.models.user.UserResponse;
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

    private final LocalServiceLoad localServiceLoad;
    private final MultimediaService multimediaService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public LocalServiceImp(LocalServiceLoad localServiceLoad,
                           MultimediaService multimediaService,
                           ModelMapper modelMapper,
                           UserService userService) {
        this.localServiceLoad = localServiceLoad;
        this.multimediaService = multimediaService;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @Override
    public List<LocalResponse> list() {
        List<LocalResponse> localResponseList = this.parseToLocalResponseList(localServiceLoad.list());
        localResponseList.forEach(this::loadUserState);
        return  localResponseList;
    }

    private void loadUserState(LocalResponse localResponse) {
        UserResponse userResponse = this.userService.getByMemberID(localResponse.getMember().getId());
        localResponse.setFirstLogin(userResponse.getFirstLogin());
        localResponse.setResetPassword(userResponse.getResetPassword());
    }

    @Override
    public LocalResponse get(Long id) {
        LocalResponse localResponse = this.parseToLocalResponse(localServiceLoad.get(id));
        this.loadUserState(localResponse);
        return localResponse;
    }

    @Override
    public LocalResponse save(LocalRequest entity) {
        Local local = this.parseToLocal(entity);
        loadMultimedia(local.getMultimedia());
        return onSaveLocal(local);
    }

    private LocalResponse onSaveLocal(Local localToStore){
        Local local = localServiceLoad.save(localToStore);
        return this.parseToLocalResponse(local);
    }

    @Override
    public LocalResponse update(Long id, LocalRequest entity) {
        return this.parseToLocalResponse(localServiceLoad.update(id, this.parseToLocal(entity)));
    }

    @Override
    public LocalResponse updateShowLocal(Long id) {
        Local local = localServiceLoad.get(id);
        local.setShowLocal(!local.getShowLocal());
        return this.update(id, this.parseToLocalRequest(local));
    }

    @Override
    public List<LocalResponse> resetPassword(Long id) {
        List<LocalResponse> localResponseList = this.parseToLocalResponseList(localServiceLoad.resetPasswordByLocalId(id));
        localResponseList.forEach(this::loadUserState);
        return localResponseList;
    }

    @Override
    public void delete(Long id) {
        localServiceLoad.delete(id);
    }

    @Override
    public List<LocalResponse> getAllLocalByIdMember(Long id) {
        return this.parseToLocalResponseList(localServiceLoad.getAllLocalByIdMember(id));
    }

    @Override
    public LocalResponse deleteMultimediaById(Long id, Long idMultimedia) {
        Local local = localServiceLoad.get(id);

        Optional<Multimedia> multimediaToDelete = local.getMultimedia()
                .stream()
                .filter(m -> m.getId().equals(idMultimedia))
                .findFirst();

        multimediaToDelete.ifPresent(local::removeMultimediaById);

        localServiceLoad.save(local);
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
        return localServiceLoad.list();
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
