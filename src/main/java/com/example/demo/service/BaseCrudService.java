package com.example.demo.service;

import com.example.demo.core.exception.EntityNotExistException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class BaseCrudService<R, E, ID, C, U> {

    protected abstract JpaRepository<E, ID> getCrudRepository();

    protected abstract R convertEntityToResponse(E entity);

    protected abstract E convertCreateDtoToEntity(C createDto);

    protected abstract ID getId(U updateDto);

    protected abstract E updateEntity(U updateDto, E entity);

    public R create(C createDto) {
        E entity = convertCreateDtoToEntity(createDto);
        entity = getCrudRepository().save(entity);
        return convertEntityToResponse(entity);
    }

    public R read(ID id) {
        E entity = getCrudRepository().findById(id)
                .orElseThrow(() -> new EntityNotExistException(id));
        return convertEntityToResponse(entity);
    }

    public List<R> readAll() {
        return getCrudRepository().findAll().stream()
                .map(this::convertEntityToResponse)
                .toList();
    }

    public R update(U updateDto) {
        ID id = getId(updateDto);
        E entity = getCrudRepository().findById(id)
                .orElseThrow(() -> new EntityNotExistException(id));
        entity = updateEntity(updateDto, entity);
        return convertEntityToResponse(getCrudRepository().save(entity));
    }

    public void delete(ID id) {
        getCrudRepository().deleteById(id);
    }
}
