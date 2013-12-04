package com.senac.jogos.labirinto;

import static java.lang.System.*;

import java.io.FileInputStream;
import java.util.Scanner;

public class Labirinto {

	private static final Scanner teclado = new Scanner(System.in);


	private Sala[] salas;
	private int countSalas;
	private int salaAtual;
	private Jogador j;

	private void run()
	{
		inicializaLabirinto();
	
		salaAtual = randomicRoom();
		do {
			
			if(salaAtual==0){
				System.out.println("Voce venceu");
				System.exit(0);
			}
			
			System.out.println("Voce esta na sala: " + getMyRoomValue());
			System.out.println(getMyRoom().toString());
			System.out.println("Digite o comando: ");			
			String comando = teclado.next().toUpperCase();

			try {
				commandType(TipoComando.valueOf(comando));

			} catch (Exception e) {
				System.out.println("Comando Invalido");
			}

		} while (true);

	}

	private void inicializaLabirinto()
	{
		salas = new Sala[50];
		salas[0] = new Sala();
		countSalas = 1;
		try {
			leLabirinto( new Scanner( new FileInputStream("labirinto.txt") ) );
		} catch (Exception e) {
			err.println(e.getMessage());
			exit(1);
		}
	}

	private void leLabirinto( Scanner arquivo ) throws Exception
	{
		String cmd = arquivo.next().toLowerCase();
		while (cmd.equals("room")) {
			int salaId = arquivo.nextInt();
			salas[salaId] = new Sala();
			Sala sala = salas[salaId];
			countSalas++;

			String direcao = arquivo.next();

			do {
				if (arquivo.hasNextInt()) {
					salaId = arquivo.nextInt();
				} else if (arquivo.next().equalsIgnoreCase("EXIT")) {
					salaId = 0;
				} else break;

				sala.addConexao(direcao, salaId);

				if (!arquivo.hasNext())
					return;
				cmd = arquivo.next().toLowerCase();	
				if (cmd.equals("trap")) {
					sala.setArmadilha(direcao);
					if (!arquivo.hasNext())
						return;
					cmd = arquivo.next();
				}
				direcao = cmd;
			} while (!cmd.equals("room"));
		}
		throw new Exception("Arquivo de descricao do labirinto invalido.");
	}

	public Sala getRoom(String direcao)
	{
		Sala sala = null;
		try {
			sala = salas[Sala.getDirecaoIndex(direcao)];
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sala;
	}

	public Sala getMyRoom(){
		return salas[salaAtual];
	}

	public void setNextRoom(String direcao)
	{
		this.salaAtual =  Integer.parseInt(direcao);
	}

	public void mover(){

		System.out.println("Digite a direcao desejada: ");
		String direcao = teclado.next();

		Sala myRoom = getMyRoom();


		if(myRoom.getConexao(direcao) == null){
			System.out.println("Direcao invalida");
		}
		else{
			Conexao c = myRoom.getConexao(direcao);
			int roomConexao = c.getSala();
			setMyRoom(String.valueOf(roomConexao));
		}
	}

	public int randomicRoom(){
		return (int) (countSalas * (Math.random()));
	}

	public void commandType(TipoComando c){

		switch (c) {
		case MOVER:			
			mover();
			break;

		default:
			break;
		}
	}
	
	public void setMyRoom(String d){
		this.salaAtual = Integer.parseInt(d);
	}

	public int getMyRoomValue()
	{
		return salaAtual;
	}

	public static void main(String[] args)
	{
		(new Labirinto()).run();
	}
}