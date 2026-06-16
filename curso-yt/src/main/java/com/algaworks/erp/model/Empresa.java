package com.algaworks.erp.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;

@Entity
@Table(name="empresa")

//Serialization is the conversion of the state of an object into a byte stream;
//which we can then save to a database or transfer over a network.
public class Empresa implements Serializable {
	//unique version identifier for Serializable classes
	private static final long serialVersionUID=1L;
	
	@Id //PK
	@GeneratedValue(strategy=GenerationType.IDENTITY) //crescente
	private Long id;
	
	@Column(name="nome_fantasia", nullable=false, length=80)
	private String nomeFantasia;
	
	@Column(name="razao_social", nullable=false, length=120)
	private String razaoSocial;
	
	@Column(nullable=false, length=18)
	private String cnpj;
	
	@Temporal(TemporalType.DATE)
	@Column(name="data_fundacao")
	private Date dataFundacao;
	
	@ManyToOne //empresa <n---1> ramoAtividade
	@JoinColumn(name="ramo_atividade_id", nullable=true)
	private RamoAtividade ramoAtividade;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false, length=30)
	private TipoEmpresa tipo;
	
	
	
	//getters e setters e outros
	
	public TipoEmpresa getTipo() {
		return tipo;
	}
	public void setTipo(TipoEmpresa tipo) {
		this.tipo=tipo;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}
	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}
	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public Date getDataFundacao() {
		return dataFundacao;
	}
	public void setDataFundacao(Date dataFundacao) {
		this.dataFundacao = dataFundacao;
	}

	public RamoAtividade getRamoAtividade() {
		return ramoAtividade;
	}
	public void setRamoAtividade(RamoAtividade ramoAtividade) {
		this.ramoAtividade = ramoAtividade;
	}
	
	
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
		
	}
	
	//The default implementation of equals() compares the identity of the object
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Empresa other = (Empresa) obj;
		return Objects.equals(id, other.id);
	}
	@Override
	public String toString() {
		return "Empresa [id=" + id + "]";
	}
}
