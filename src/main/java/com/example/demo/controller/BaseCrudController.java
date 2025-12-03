package com.example.demo.controller;

import com.example.demo.service.BaseCrudService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class BaseCrudController<R, E, ID, C, U> {

    public abstract BaseCrudService<R, E, ID, C, U> getCrudService();

    @PostMapping
    ResponseEntity<R> create(@RequestBody C createDto) {
        return ResponseEntity.ok(getCrudService().create(createDto));
    }

    @GetMapping
    ResponseEntity<R> read(@RequestParam(name = "id") ID id) {
        return ResponseEntity.ok(getCrudService().read(id));
    }

    @PutMapping
    ResponseEntity<R> update(@RequestBody U updateDto) {
        return ResponseEntity.ok(getCrudService().update(updateDto));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable(name = "id") ID id){
        getCrudService().delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    ResponseEntity<List<R>> getAll() {
        return ResponseEntity.ok(getCrudService().readAll());
    }
}
