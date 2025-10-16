package com.daw.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daw.persistence.entities.Tarea;
import com.daw.services.TareaService;
import com.daw.services.exceptions.TareaException;
import com.daw.services.exceptions.TareaNotFoundException;

@RestController
@RequestMapping("/tareas")
public class TareaController {

	@Autowired
	private TareaService tareaService;

	@GetMapping
	public ResponseEntity<List<Tarea>> list() {
		return ResponseEntity.ok(this.tareaService.findAll());
	}

	@GetMapping("/{idTarea}")
	public ResponseEntity<?> findById(@PathVariable int idTarea) {
		try {
			return ResponseEntity.ok(this.tareaService.findById(idTarea));
		} catch (TareaNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@PostMapping
	public ResponseEntity<?> create(@RequestBody Tarea tarea) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(this.tareaService.create(tarea));
		} catch (TareaException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}

	@PutMapping("/{idTarea}")
	public ResponseEntity<?> update(@PathVariable int idTarea, @RequestBody Tarea tarea) {
		try {
			return ResponseEntity.ok(this.tareaService.update(tarea, idTarea));
		} catch (TareaNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		} catch (TareaException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}

	@DeleteMapping("/{idTarea}")
	public ResponseEntity<?> delete(@PathVariable int idTarea) {
		try {
			this.tareaService.delete(idTarea);
			return ResponseEntity.ok().build();
		} catch (TareaNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}

	@PutMapping("/{idTarea}/iniciar")
	public ResponseEntity<?> iniciarTarea(@PathVariable int idTarea) {
		try {
			return ResponseEntity.ok(this.tareaService.marcarEnProgreso(idTarea));
		} catch (TareaNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		} catch (TareaException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}

	@GetMapping("/pendientes")
	public ResponseEntity<?> pendientes() {
		return ResponseEntity.ok(this.tareaService.pendientes());
	}

	@GetMapping("/enProgreso")
	public ResponseEntity<?> enProgreso() {
		return ResponseEntity.ok(this.tareaService.enProgreso());
	}

	@GetMapping("/completadas")
	public ResponseEntity<?> completadas() {
		return ResponseEntity.ok(this.tareaService.completadas());
	}

	@GetMapping("/vencidas")
	public ResponseEntity<?> vencidas() {
		return ResponseEntity.ok(this.tareaService.vencidas());
	}
	
	@GetMapping("/noVencidas")
	public ResponseEntity<?> noVencidas() {
	    return ResponseEntity.ok(this.tareaService.noVencidas());
	}
	
	@GetMapping("/buscarTitulo/{titulo}")
	public ResponseEntity<?> buscarPorTitulo(@PathVariable String titulo) {
	    try {
	        List<Tarea> tareas = this.tareaService.buscarPorTitulo(titulo);
	        return ResponseEntity.ok(tareas);
	    } catch (Exception ex) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	    }
	}



}
