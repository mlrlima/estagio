package atividades_estagio.id;

public class TesteService {

	private Teste teste;
	
	public TesteService() {}
	
	public TesteService(Teste teste) {
		this.teste=teste;
	}
	
	
	public String helloWorld() {
		return teste.retornar();
	}
	
	public void setTeste(Teste teste){
		this.teste=teste;
	}
}
