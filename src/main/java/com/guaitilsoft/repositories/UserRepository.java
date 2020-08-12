package com.guaitilsoft.repositories;

import com.guaitilsoft.models.authoriry.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

}
