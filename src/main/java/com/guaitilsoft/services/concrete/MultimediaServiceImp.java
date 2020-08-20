package com.guaitilsoft.services.concrete;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.Multimedia;
import com.guaitilsoft.repositories.MultimediaRepository;
import com.guaitilsoft.services.MultimediaService;
import com.guaitilsoft.web.models.multimedia.MultimediaRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
public class MultimediaServiceImp implements MultimediaService {

    private final Path root = Paths.get("uploads");
    private final MultimediaRepository multimediaRepository;

    @Autowired
    public MultimediaServiceImp(MultimediaRepository multimediaRepository) {
        this.multimediaRepository = multimediaRepository;
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(root.normalize());
        } catch (IOException e) {
            throw new ApiRequestException("Could not initialize folder for upload!");
        }
    }

    @Override
    public Multimedia store(MultimediaRequest multimediaRequest) {
        String originalFileName = StringUtils.cleanPath(multimediaRequest.getFileName());
        try {
            if(originalFileName.contains("..")) {
                throw new ApiRequestException("Sorry! Filename contains invalid path sequence " + originalFileName);
            }
            String fileExtension = "";
            try {
                fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
            } catch(Exception e) {
                fileExtension = "";
            }
            String fileName = multimediaRequest.getPrefix() + RandomStringUtils.randomAlphanumeric(8) + multimediaRequest.getSuffix();
            fileName = (fileName.trim() + fileExtension).toLowerCase();
            storeFile(multimediaRequest, fileName);
            Multimedia multimedia = new Multimedia();
            multimedia.setFileName(fileName);
            multimedia.setFormat(multimediaRequest.getContentType());
            multimedia.setType(multimediaRequest.getType());

            return multimediaRepository.save(multimedia);
        } catch (IOException e) {
            throw new ApiRequestException("Could not store the file. Error: " + e.getMessage());
        }
    }

    @Override
    public Resource load(String filename) {
        assert filename != null;
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new ApiRequestException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new ApiRequestException("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new ApiRequestException("Could not load the files!");
        }
    }

    private void storeFile(MultimediaRequest multimedia, String fileName) throws IOException {
        Path targetLocation =  this.root.resolve(fileName);
        Files.copy(multimedia.getInputStream(),targetLocation, StandardCopyOption.REPLACE_EXISTING);
    }
}

