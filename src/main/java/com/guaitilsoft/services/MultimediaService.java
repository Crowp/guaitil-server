package com.guaitilsoft.services;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface MultimediaService {
     void init();

     void save(MultipartFile file);

     Resource load(String filename);

     void deleteAll();

     Stream<Path> loadAll();
}
