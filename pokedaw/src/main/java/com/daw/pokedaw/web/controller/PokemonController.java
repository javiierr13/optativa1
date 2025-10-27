package com.daw.pokedaw.web.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.daw.pokedaw.persistence.entities.Pokemon;
import com.daw.pokedaw.persistence.entities.enums.Tipo;
import com.daw.pokedaw.services.PokemonService;
import com.daw.pokedaw.services.exceptions.PokemonException;
import com.daw.pokedaw.services.exceptions.PokemonNotFoundException;

@RestController
@RequestMapping("/pokemon")
public class PokemonController {

	@Autowired
	private PokemonService pokemonService;

	@GetMapping
	public ResponseEntity<List<Pokemon>> list() {
		return ResponseEntity.ok(pokemonService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable int id) {
		try {
			return ResponseEntity.ok(pokemonService.findById(id));
		} catch (PokemonNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@PostMapping
	public ResponseEntity<?> create(@RequestBody Pokemon pokemon) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(pokemonService.create(pokemon));
		} catch (PokemonException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable int id, @RequestBody Pokemon pokemon) {
		try {
			return ResponseEntity.ok(pokemonService.update(pokemon, id));
		} catch (PokemonNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		} catch (PokemonException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable int id) {
		try {
			pokemonService.delete(id);
			return ResponseEntity.ok().build();
		} catch (PokemonNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}

	@GetMapping("/numero/{numero}")
	public ResponseEntity<List<Pokemon>> buscarPorNumero(@PathVariable int numero) {
		return ResponseEntity.ok(pokemonService.buscarPorNumero(numero));
	}

	@GetMapping("/capturados")
	public ResponseEntity<?> capturadosEntre(@RequestParam String inicio, @RequestParam String fin) {
		try {
			LocalDate fechaInicio = LocalDate.parse(inicio);
			LocalDate fechaFin = LocalDate.parse(fin);

			List<Pokemon> lista = pokemonService.capturadosEntre(fechaInicio, fechaFin);
			return ResponseEntity.ok(lista);
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Formato de fecha inválido");
		}
	}

	@GetMapping("/tipo/{tipo}")
	public ResponseEntity<List<Pokemon>> buscarPorTipo(@PathVariable Tipo tipo) {
		return ResponseEntity.ok(pokemonService.buscarPorTipo(tipo));
	}

	@PutMapping("/{id}/tipo")
	public ResponseEntity<?> cambiarTipo(@PathVariable int id, @RequestParam Tipo tipo1,
			@RequestParam(required = false) Tipo tipo2) {
		try {
			return ResponseEntity.ok(pokemonService.cambiarTipo(id, tipo1, tipo2));
		} catch (PokemonNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		} catch (PokemonException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}
}