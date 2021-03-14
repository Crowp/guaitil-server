package com.guaitilsoft.models;

import com.guaitilsoft.exceptions.ApiRequestException;
import com.guaitilsoft.models.constant.MultimediaType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Multimedia {

    private static final Path multimediaRoot = Paths.get("uploads");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String fileName;

    private Long size;

    @Enumerated(EnumType.STRING)
    private MultimediaType type;

    private String format;

    public static Path getMultimediaRoot(){
        return multimediaRoot;
    }


    @PreRemove
    private void preRemove(){
        deleteMultimedia();
    }

    @PreUpdate
    private void preUpdate(){
        deleteMultimedia();
    }

    private void deleteMultimedia() {
        Path targetLocation = Multimedia.multimediaRoot.resolve(this.fileName);
        try {
            Files.delete(targetLocation);
        } catch (IOException ex) {
            throw new ApiRequestException("Could not store file. Please try again!", ex);
        }
    }
}
