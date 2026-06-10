package com.algaworks.erp.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;

@Entity
@Table(name="empresa")
public class Empresa implements Serializable {
	//unique version identifier for Serializable classes
	private static final long serialVersionUID=lL;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="nome_fantasia")
	private String nomeFantasia;
}
