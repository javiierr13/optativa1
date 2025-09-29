package com.daw.daw.task.persistence.repositories;

import org.springframework.data.repository.ListCrudRepository;

import com.daw.daw.task.persistence.entities.Tarea;

public interface TareaRepository extends ListCrudRepository<Tarea, Integer> {

}
