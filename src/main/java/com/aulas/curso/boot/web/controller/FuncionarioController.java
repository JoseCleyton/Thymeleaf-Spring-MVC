package com.aulas.curso.boot.web.controller;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aulas.curso.boot.domain.Cargo;
import com.aulas.curso.boot.domain.Funcionario;
import com.aulas.curso.boot.domain.UF;
import com.aulas.curso.boot.service.CargoService;
import com.aulas.curso.boot.service.FuncionarioService;
import com.aulas.curso.boot.validator.FuncionarioValidator;

@Controller
@RequestMapping("/funcionarios")
public class FuncionarioController {

	@Autowired
	private FuncionarioService funcionarioService;

	@Autowired
	private CargoService cargoService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(new FuncionarioValidator());
	}

	@GetMapping("/cadastrar")
	public String cadastrar(Funcionario funcionario) {
		return "funcionario/cadastro";
	}

	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("funcionarios", this.funcionarioService.buscarTodos());
		return "funcionario/lista";
	}

	@PostMapping("/salvar")
	public String salvar(@Valid Funcionario funcionario, BindingResult result, RedirectAttributes attr) {

		if (result.hasErrors()) {
			return "funcionario/cadastro";
		}

		this.funcionarioService.salvar(funcionario);
		attr.addFlashAttribute("success", "Funcionario inserido com sucesso");
		return "redirect:/funcionarios/cadastrar";
	}

	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("funcionario", this.funcionarioService.buscarPorId(id));
		return "funcionario/cadastro";
	}

	@PostMapping("/editar")
	public String editar(@Valid Funcionario funcionario, BindingResult result, RedirectAttributes attr) {

		if (result.hasErrors()) {
			return "funcionario/cadastro";
		}

		this.funcionarioService.editar(funcionario);
		attr.addFlashAttribute("success", "Funcionário editado com sucesso.");
		return "redirect:/funcionarios/cadastrar";
	}

	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
		this.funcionarioService.excluir(id);
		attr.addFlashAttribute("success", "Funcionário removido com sucesso");
		return "redirect:/funcionarios/listar";
	}

	@GetMapping("/buscar/nome")
	public String getPorNome(@RequestParam("nome") String nome, ModelMap model) {
		List<Funcionario> listaDeFuncionarios = this.funcionarioService.buscarPorNome(nome);
		if (!listaDeFuncionarios.isEmpty()) {

			model.addAttribute("funcionarios", listaDeFuncionarios);
		} else {

			model.addAttribute("fail", "Nenhum registro encontrado");
			model.addAttribute("funcionarios", this.funcionarioService.buscarTodos());
		}
		return "funcionario/lista";
	}

	@GetMapping("/buscar/cargo")
	public String getPorCargo(@RequestParam("id") Long id, ModelMap model) {
		List<Funcionario> listaDeFuncionarios = this.funcionarioService.buscarPorCargo(id);
		if (!listaDeFuncionarios.isEmpty()) {

			model.addAttribute("funcionarios", listaDeFuncionarios);
		} else {

			model.addAttribute("fail", "Nenhum registro encontrado");
			model.addAttribute("funcionarios", this.funcionarioService.buscarTodos());
		}
		return "funcionario/lista";
	}

	@GetMapping("/buscar/data")
	public String getPorData(@RequestParam("entrada") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate entrada,
			@RequestParam("saida") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate saida, ModelMap model) {
		List<Funcionario> listaDeFuncionarios = this.funcionarioService.buscarPorData(entrada, saida);
		if (!listaDeFuncionarios.isEmpty()) {

			model.addAttribute("funcionarios", listaDeFuncionarios);
		} else {

			model.addAttribute("fail", "Nenhum registro encontrado");
			model.addAttribute("funcionarios", this.funcionarioService.buscarTodos());
		}
		return "funcionario/lista";
	}

	@ModelAttribute("cargos")
	public List<Cargo> getCargos() {
		return this.cargoService.buscarTodos();
	}

	@ModelAttribute("ufs")
	public UF[] getUfs() {
		return UF.values();
	}

}
