package com.daw.daw.task.service.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daw.daw.task.persistence.repositories.TareaRepository;

@Service
public class TareaService {
	
	@Autowired
	private TareaRepository tareaRepository;

}
