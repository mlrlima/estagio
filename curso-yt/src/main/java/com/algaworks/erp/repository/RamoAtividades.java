package com.algaworks.erp.repository;

import com.algaworks.erp.model.RamoAtividade;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public class RamoAtividades implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private EntityManager manager;
	
	public RamoAtividades() {}
	
	public RamoAtividades(EntityManager manager) {
		this.manager=manager;
	}
	
	/* no video ~1:54
	public List<RamoAtividade> pesquisar(String descricao){
		CriteriaBuilder criteriaBuilder=manager.getCriteriaBuilder();
		
		CriteriaQuery<RamoAtividade> criteriaQuery=criteriaBuilder.createQuery(RamoAtividade.class);
	}
	*/
}
