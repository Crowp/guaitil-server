package com.guaitilsoft.services.local;

import com.guaitilsoft.models.Local;
import com.guaitilsoft.models.Member;
import com.guaitilsoft.models.Multimedia;
import com.guaitilsoft.models.constant.LocalType;
import com.guaitilsoft.services.MultimediaService;
import com.guaitilsoft.services.member.MemberRepositoryService;
import com.guaitilsoft.web.models.local.LocalRequest;
import com.guaitilsoft.web.models.local.LocalResponse;
import com.guaitilsoft.web.models.multimedia.MultimediaResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LocalServiceImp implements LocalService {

    private final LocalRepositoryService localRepositoryService;
    private final MemberRepositoryService memberRepositoryService;
    private final MultimediaService multimediaService;
    private final ModelMapper modelMapper;

    @Autowired
    public LocalServiceImp(LocalRepositoryService localRepositoryService,
                           MemberRepositoryService memberRepositoryService,
                           MultimediaService multimediaService,
                           ModelMapper modelMapper) {
        this.localRepositoryService = localRepositoryService;
        this.memberRepositoryService = memberRepositoryService;
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
        Long memberId = entity.getMember().getId();
        local.setMember(loadFullMember(memberId));
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

    private List<LocalResponse> parseToLocalResponseList(List<Local> list){
        Type lisType = new TypeToken<List<LocalResponse>>(){}.getType();
        List<LocalResponse> locals = modelMapper.map(list, lisType);
        locals.forEach(l -> addUrlToMultimedia(l.getMultimedia()));
        return locals;
    }

    private LocalResponse parseToLocalResponse(Local local){
        LocalResponse localResponse =  modelMapper.map(local, LocalResponse.class);
        addUrlToMultimedia(localResponse.getMultimedia());
        return localResponse;
    }

    public Member loadFullMember(Long id){
        return this.memberRepositoryService.get(id);
    }

    private void loadMultimedia(List<Multimedia> multimediaList) {
        List<Multimedia> multimediaLoaded = new ArrayList<>();
        multimediaList.forEach(media -> multimediaLoaded.add(multimediaService.get(media.getId())));
        multimediaList.clear();
        multimediaList.addAll(multimediaLoaded);
    }

    private void addUrlToMultimedia(List<MultimediaResponse> multimedia){
        multimedia.forEach(m -> m.setUrl(getUrlHost(m)));
    }

    private static String getUrlHost(MultimediaResponse multimediaResponse){
        String resourcePath = "/api/multimedia/load/";
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(resourcePath)
                .path(multimediaResponse.getFileName())
                .toUriString();
    }
}
