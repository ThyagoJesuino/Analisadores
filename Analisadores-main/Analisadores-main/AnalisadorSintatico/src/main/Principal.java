package main;

import java.util.LinkedList;
import analisadorLexico.AnalisadorLexico;
import analisadorLexico.Token;
import analisadorSintatico.AnalisadorSintatico;
import exceptions.AnaliseException;

public class Principal {

	public static void main(String[] args) {
		LinkedList<Token> tokens = null;
		try {

			tokens = AnalisadorLexico.parceLexico("C:/Users/braba/OneDrive/Área de Trabalho/Ufl/Analisadores-main/Analisadores-main/AnalisadorSintatico/src/textfile.txt");

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("An�lise Lexica: ");
		 for (Token token : tokens) {
		 System.out.println(token);
		 }

		try {
			System.out.println();
			System.out.printf("Analise Sintatica: ");
			AnalisadorSintatico as = new AnalisadorSintatico(tokens);
			as.prog();
			System.out.println();
			as.result();
		} catch (Exception e) {
			AnaliseException exception = new AnaliseException();
		}

	}

}
