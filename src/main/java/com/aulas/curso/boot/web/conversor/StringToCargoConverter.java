package com.aulas.curso.boot.web.conversor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.aulas.curso.boot.domain.Cargo;
import com.aulas.curso.boot.service.CargoService;

@Component
public class StringToCargoConverter implements Converter<String, Cargo> {

	@Autowired
	private CargoService cargoService;

	@Override
	public Cargo convert(String text) {
		if (text.isEmpty()) {
			return null;
		} else {
			Long id = Long.valueOf(text);
			return this.cargoService.buscarPorId(id);
		}
	}

}
