package com.aulas.curso.boot.validator;

import java.time.LocalDate;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.aulas.curso.boot.domain.Funcionario;

public class FuncionarioValidator implements Validator {

	@Override
	public boolean supports(Class<?> c) {
		return Funcionario.class.equals(c);
	}

	@Override
	public void validate(Object object, Errors errors) {

		Funcionario f = (Funcionario) object;
		LocalDate dataEntrada = f.getDataEntrada();
		
		if(f.getDataSaida()!=null) {
			if(f.getDataSaida().isBefore(dataEntrada)) {
				errors.rejectValue("dataSaida", "PosteriorDataEntrada.funcionario.dataSaida");
			}
		}
	}

}
