package com.daw.persistence.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

import com.daw.persistence.entities.Estado;
import com.daw.persistence.entities.Tarea;

public interface TareaRepository extends ListCrudRepository<Tarea, Integer> {

	// select * from tarea where estado = ?
	List<Tarea> findByEstado(Estado estado);
	
	// select * from tarea where fechaVencimiento = ?
	List<Tarea> findByFechaVencimientoBefore(LocalDate fecha);
	List<Tarea> findByFechaVencimientoAfter(LocalDate fecha);

	// select * from tarea where titulo = ?
    List<Tarea> findByTituloContainingIgnoreCase(String titulo);

	

}
