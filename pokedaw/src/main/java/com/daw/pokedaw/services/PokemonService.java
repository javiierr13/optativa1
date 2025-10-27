package com.daw.pokedaw.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.pokedaw.persistence.entities.Pokemon;
import com.daw.pokedaw.persistence.entities.enums.Tipo;
import com.daw.pokedaw.persistence.entities.enums.TipoCaptura;
import com.daw.pokedaw.persistence.repositories.PokemonRepository;
import com.daw.pokedaw.services.exceptions.PokemonException;
import com.daw.pokedaw.services.exceptions.PokemonNotFoundException;

@Service

public class PokemonService {

	@Autowired
	private PokemonRepository pokemonRepository;

	// findAll
	public List<Pokemon> findAll() {
		return this.pokemonRepository.findAll();
	}

	// findById
	public Pokemon findById(int IdPokemon) {
		if (!this.pokemonRepository.existsById(IdPokemon)) {
			throw new PokemonNotFoundException("El id " + IdPokemon + " del pokemon no existe");

		}
		return this.pokemonRepository.findById(IdPokemon).get();
	}

	// create
	public Pokemon create(Pokemon pokemon) {
		if (pokemon.getTipo1() == null) {
			throw new PokemonException("El tipo1 es obligatorio");
		}

		if (pokemon.getTipo2() == null) {
			pokemon.setTipo2(Tipo.NINGUNO);
		}

		if (pokemon.getTipo1() == pokemon.getTipo2()) {
			throw new PokemonException("tipo1 y tipo2 no pueden ser iguales");
		}

		if (pokemon.getCapturadoCon() == null) {
			pokemon.setCapturadoCon(TipoCaptura.POKEBALL);
		}

		pokemon.setFechaCaptura(LocalDate.now());

		return pokemonRepository.save(pokemon);
	}

	// update
	public Pokemon update(Pokemon pokemon, int id) {
		Pokemon pokemonActual = findById(id);

		if (pokemon.getCapturadoCon() != null || pokemon.getFechaCaptura() != null) {
			throw new PokemonException("No se puede modificar capturadoCon ni fechaCaptura");
		}

		pokemonActual.setNombre(pokemon.getNombre());
		pokemonActual.setNumeroPokedex(pokemon.getNumeroPokedex());

		return pokemonRepository.save(pokemonActual);
	}

	// delete
	public boolean delete(int id) {
		Pokemon pokemonActual = findById(id);
		pokemonRepository.delete(pokemonActual);
		return !pokemonRepository.existsById(id);
	}

	// buscar por número de la pokédex
	public List<Pokemon> buscarPorNumero(int numero) {
		return pokemonRepository.findByNumeroPokedex(numero);
	}

	// buscar por rango de fechas de captura
	public List<Pokemon> capturadosEntre(LocalDate inicio, LocalDate fin) {
		return pokemonRepository.findByFechaCapturaBetween(inicio, fin);
	}

	// buscar por tipo
	public List<Pokemon> buscarPorTipo(Tipo tipo) {
		return pokemonRepository.findByTipo1OrTipo2(tipo, tipo);
	}

	// cambiar tipos
	public Pokemon cambiarTipo(int id, Tipo tipo1, Tipo tipo2) {
		Pokemon pokemonActual = findById(id);

		if (tipo2 == null)
			tipo2 = Tipo.NINGUNO;

		if (tipo1 == tipo2) {
			throw new PokemonException("tipo1 y tipo2 no pueden ser iguales");
		}

		pokemonActual.setTipo1(tipo1);
		pokemonActual.setTipo2(tipo2);

		return pokemonRepository.save(pokemonActual);
	}
}