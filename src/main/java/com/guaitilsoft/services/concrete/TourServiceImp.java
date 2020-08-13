package com.guaitilsoft.services.concrete;

import com.guaitilsoft.models.Tour;

import com.guaitilsoft.repositories.TourRepository;
import com.guaitilsoft.services.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TourServiceImp implements TourService {

    private TourRepository tourRepository;

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
        Tour tour = tourRepository.findById(id).orElse(null);
        if(tour == null){
            throw new EntityNotFoundException();
        }
        return tour;
    }

    @Override
    public void save(Tour entity)  {
        tourRepository.save(entity);
    }

    @Override
    public void update(Long id, Tour entity) {
        Tour tour = this.get(id);
        tour.setAmountPerson(entity.getAmountPerson());
        tour.setActivity(entity.getActivity());
        tourRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        Tour tour = this.get(id);
        if(tour != null){
            tourRepository.delete(tour);
        }
    }
}
