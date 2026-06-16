package atividades_estagio.erp.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class Pet implements Serializable {
	//It ensures that during deserialization, the object being read matches 
	//the exact same class version that was originally serialized
	private static final long serialVersionUID=1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable=false, length=50)
	private String nome;
	
	@ManyToOne //pet <n---1> dono
	@JoinColumn(name="user_id", nullable=false)
	private Usuario dono;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private String especie;
	
	@Temporal(TemporalType.DATE)
	@Column(name="data_nascimento", nullable=true)
	private Date dataNascimento;
	
	
}
