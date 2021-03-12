package com.guaitilsoft.services.multimedia;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.Multimedia;
import com.guaitilsoft.repositories.MultimediaRepository;
import com.guaitilsoft.web.models.multimedia.MultimediaRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class MultimediaServiceImp implements MultimediaService {

    private final Path root;
    private final MultimediaRepository multimediaRepository;

    @Autowired
    public MultimediaServiceImp(MultimediaRepository multimediaRepository) {
        root = Multimedia.getMultimediaRoot();
        this.multimediaRepository = multimediaRepository;
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(this.root.normalize());
        } catch (IOException e) {
            throw new ApiRequestException("Could not initialize folder for upload!");
        }
    }

    @Override
    public List<Multimedia> list() {
        Iterable<Multimedia> iterable = multimediaRepository.findAll();
        List<Multimedia> multimedia = new ArrayList<>();
        iterable.forEach(multimedia::add);
        return multimedia;
    }

    @Override
    public Multimedia get(Long id) {
        assert id != null;

        Multimedia multimedia = multimediaRepository.findById(id).orElse(null);
        if (multimedia != null) {
            return multimedia;
        }
        throw new EntityNotFoundException("No se encontro un recurso con el id: " + id);
    }

    @Override
    public void delete(Long id) {
        Multimedia multimedia = this.get(id);
        multimediaRepository.delete(multimedia);
    }

    @Override
    public Multimedia store(MultimediaRequest multimediaRequest) {
        assert multimediaRequest != null;
        String fileName = createFileName(multimediaRequest);
        Multimedia multimedia = new Multimedia();
        multimedia.setFileName(fileName);
        multimedia.setSize(multimediaRequest.getFile().getSize());
        multimedia.setFormat(multimediaRequest.getContentType());
        multimedia.setType(multimediaRequest.getType());

        storeFile(multimediaRequest, fileName);
        return multimediaRepository.save(multimedia);
    }

    @Override
    public Resource load(String filename) throws ApiRequestException {
        assert filename != null;
        assert !filename.equals("");

        try {
            Path file = getFilePath(filename);
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

    private Path getFilePath(String filename) {
        return root.resolve(filename);
    }

    private String createFileName(MultimediaRequest multimediaRequest) {
        String originalFileName = StringUtils.cleanPath(multimediaRequest.getFileName());
        if (originalFileName.contains("..")) {
            throw new ApiRequestException("Sorry! Filename contains invalid path sequence " + originalFileName);
        }
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String randomKey = RandomStringUtils.randomAlphanumeric(8);
        return (multimediaRequest.getPrefix() + randomKey + multimediaRequest.getSuffix()).trim()
                + fileExtension.toLowerCase();
    }

    private void storeFile(MultimediaRequest multimedia, String fileName) {
        try {
            Path targetLocation = getFilePath(fileName);
            Files.copy(multimedia.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new ApiRequestException("Could not store the file. Error: " + e.getMessage());
        }

    }
}

