package com.learning.repository;

import com.learning.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, String> {
    @Query("SELECT r FROM Resource r")
    Optional<Resource> find();
}
