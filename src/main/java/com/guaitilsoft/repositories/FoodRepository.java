package com.guaitilsoft.repositories;

import com.guaitilsoft.models.Food;
import org.springframework.data.repository.CrudRepository;

public interface FoodRepository extends CrudRepository<Food, Long> {

}
