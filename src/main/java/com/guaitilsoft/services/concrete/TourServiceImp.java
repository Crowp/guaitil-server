package com.guaitilsoft.services.concrete;

import com.guaitilsoft.models.Tour;
import com.guaitilsoft.repositories.TourRepository;
import com.guaitilsoft.services.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TourServiceImp implements TourService {

    private final TourRepository tourRepository;

    @Autowired
    public TourServiceImp(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    @Override
    public List<Tour> list() {
        Iterable<Tour> iterable = tourRepository.findAll();
        List<Tour> tours = new ArrayList<>();
        iterable.forEach(tours::add);
        return tours;
    }

    @Override
    public Tour get(Long id) {
        assert id != null;

        Tour tour = tourRepository.findById(id).orElse(null);
        if(tour == null){
            throw new EntityNotFoundException("No se encontro un tour con el id: " + id);
        }
        return tour;
    }

    @Override
    public void save(Tour entity)  {
        assert entity != null;
        entity.setUpdatedAt(new Date());
        entity.setCreatedAt(new Date());
        tourRepository.save(entity);
    }

    @Override
    public void update(Long id, Tour entity) {
        assert id != null;
        assert entity != null;

        Tour tour = this.get(id);
        tour.setAmountPerson(entity.getAmountPerson());
        tour.setActivity(entity.getActivity());
        tour.setUpdatedAt(new Date());
        tourRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        assert id != null;

        Tour tour = this.get(id);

        tourRepository.delete(tour);
    }

    @Override
    public void deleteTourByActivityId(Long activityId) {
        Optional<Tour> tourOptional = tourRepository.selectTourByActivityId(activityId);
        tourOptional.ifPresent(tour -> this.delete(tour.getId()));
    }
}
