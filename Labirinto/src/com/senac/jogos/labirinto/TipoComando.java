package com.senac.jogos.labirinto;


public enum TipoComando { 	
	MOVER("mover"), 
	OLHAR("olhar"), 
	ATACAR("atacar"), 
	PEGAR("pegar"), 
	LARGAR_CHAVE("largar chave"), 
	LARGAR_ARMA("largar arma"), 
	LARGAR_ARMADURA("largar armadura"), 
	SAIR("Sair"); 

	private String nome; 

	private TipoComando(String nome){ 
		this.nome = nome; 
	}

	public String getNome(){ 
		return nome;
	}
}