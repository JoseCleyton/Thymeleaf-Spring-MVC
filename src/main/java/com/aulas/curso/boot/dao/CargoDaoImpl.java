package com.aulas.curso.boot.dao;

import org.springframework.stereotype.Repository;

import com.aulas.curso.boot.domain.Cargo;

@Repository
public class CargoDaoImpl extends AbstractDao<Cargo, Long> implements CargoDao {

}
