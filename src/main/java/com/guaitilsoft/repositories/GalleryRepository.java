package com.guaitilsoft.repositories;

import com.guaitilsoft.models.Gallery;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GalleryRepository extends CrudRepository<Gallery, Long> {

}
