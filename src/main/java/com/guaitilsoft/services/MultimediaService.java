package com.guaitilsoft.services;

import java.nio.file.Path;
import java.util.stream.Stream;

import com.guaitilsoft.models.Multimedia;
import com.guaitilsoft.web.models.multimedia.MultimediaRequest;
import org.springframework.core.io.Resource;

public interface MultimediaService {

     void init();

     Multimedia store(MultimediaRequest multimedia);

     Resource load(String filename);

     void deleteAll();

     Stream<Path> loadAll();
}
