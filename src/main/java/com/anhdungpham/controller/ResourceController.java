package com.anhdungpham.controller;

import com.anhdungpham.dto.ResourceDTO;
import com.anhdungpham.entities.ResourceEntity;
import com.anhdungpham.repositories.ResourceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/resources")
public class ResourceController {
    private final ResourceRepository resourceRepository;

    public ResourceController(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    @GetMapping(value = "/all")
    @PreAuthorize("hasAnyAuthority('USER:READ','ADMIN:READ')")
    public List<ResourceEntity> getAll() {
        return resourceRepository.findAll();
    }

    @GetMapping(value = "/all/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN:READ','USER:READ')")
    public ResponseEntity<ResourceEntity> getById(@PathVariable Integer id) {
        ResourceEntity resourceEntity = resourceRepository.findById(id).orElseThrow(
                () -> new IllegalStateException(String.format("NOT FOUND NEWS with ID: %s", id))
        );
        return ResponseEntity.ok(resourceEntity);
    }

    @PostMapping(value = "/post")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResourceEntity> postNews(@RequestBody ResourceDTO resourceDTO) {
        ModelMapper modelMapper = new ModelMapper();
        ResourceEntity resourceEntity = this.resourceRepository.save(
                modelMapper.map(resourceDTO, ResourceEntity.class)
        );
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(resourceEntity.getId()).toUri();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uri);
        return ResponseEntity.created(uri).body(resourceEntity);
    }

    @PutMapping(value = "/put/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResourceEntity updateNews(@RequestBody ResourceDTO resourceDTO,
                                     @PathVariable Integer id) {
        Optional<ResourceEntity> oldNew = this.resourceRepository.findById(id);
        if (oldNew.isEmpty()) {
            throw new IllegalStateException(String.format("NOT FOUND NEWS with ID: %s", id));
        }
        ModelMapper modelMapper = new ModelMapper();
        ResourceEntity resourceEntity = modelMapper.map(resourceDTO, ResourceEntity.class);
        resourceEntity.setId(id);
        return resourceRepository.save(resourceEntity);
    }

    @DeleteMapping(value = "/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.RESET_CONTENT)
    public void deleteNews(@PathVariable Integer id) {
        Optional<ResourceEntity> deleteNew = this.resourceRepository.findById(id);
        if (deleteNew.isEmpty()) {
            throw new IllegalStateException(String.format("NOT FOUND NEWS WITH ID: %s", id));
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        this.resourceRepository.deleteById(id);
    }
}
