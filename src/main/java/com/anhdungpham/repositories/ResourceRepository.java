package com.anhdungpham.repositories;

import com.anhdungpham.entities.ResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResourceRepository extends JpaRepository<ResourceEntity, Integer> {
    List<ResourceEntity> findAllByTitle(String title);
}
