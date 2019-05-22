package com.cepalab.sistemaVendas.utils;

public class ValidaSenha {

	public static boolean senhaValida(String senha) {
		
		//Verifica se a senha possui espaços vazios 
		int index = senha.indexOf(" "); // returna -1 se o valor não for encontrado.
		
		
		if(senha.length() < 6 || index != -1 ) {
			return false;
		}
		
		
		try {
			int testeSenha = Integer.parseInt(senha);
		} catch (NumberFormatException e) {
			//Vai chegar aqui se tiver mais que seis caracteres e não tiver apenas número
				return true;
		}
		
		//Será executado se tiver mais de 6 caracteres, mas apenas números
		return false;
	}

}
