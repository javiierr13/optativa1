package com.daw.pokedaw.services.exceptions;

public class PokemonException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PokemonException(String msg) {
		super(msg);
	}
}