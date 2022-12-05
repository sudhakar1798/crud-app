package io.sudhakar.student.repository;

import io.sudhakar.student.entity.UserDetailsEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserDetailsRepository extends CrudRepository<UserDetailsEntity, Integer> {

    Optional<UserDetailsEntity> findByUsername(String username);

}




