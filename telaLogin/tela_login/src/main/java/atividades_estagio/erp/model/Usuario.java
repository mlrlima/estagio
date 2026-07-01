package atividades_estagio.erp.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name="usuario")
public class Usuario implements Serializable{
	//transforma a informacao em streams de bytes
	private static final long serialVersionUID=1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) //gera um valor auto-incremental
	private Long id;
	
	@NotNull
	@Email
	@Column(nullable=false, length=50, unique=true)
	private String email;
	
	@NotNull
	@Size(min=4, message="A senha deve ter no minimo 4 caracteres")
	@Column(nullable=false, length=50)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //the field can be accepted
	//during deserialization (when converting JSON into an object), but it will be omitted
	//during serialization (when converting an object back into JSON)
	private String senha;
	
	@NotEmpty //not null && min 1 caractere
	@Column(nullable=false, length=100)
	private String nome;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private Role role;

	
	
	
	//getters e setters e outros utils
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role=role;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome){
		this.nome = nome;
	}
	
	
	@Override //pra substituir por essa funcao a q ja existe
	public String toString() {
		return "Usuario [id=" + id + "]";
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(id, other.id);
	}
}
