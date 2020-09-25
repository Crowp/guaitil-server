package com.guaitilsoft.services;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.Multimedia;
import com.guaitilsoft.web.models.multimedia.MultimediaRequest;
import org.springframework.core.io.Resource;

import java.util.List;

public interface MultimediaService {
     void init();

     List<Multimedia> list();

     Multimedia get(Long id);

     void delete(Long id);

     void deleteOnlyFile(String filename);

     Multimedia update(Long id, MultimediaRequest multimediaRequest);

     Multimedia store(MultimediaRequest multimedia);

     Resource load(String filename) throws ApiRequestException;

     void deleteAll();
}
