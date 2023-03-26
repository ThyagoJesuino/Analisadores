package analisadorSintatico;

import java.util.LinkedList;
import analisadorLexico.Token;

public class AnalisadorSintatico {
	int i;
	boolean testa = false;
	boolean operador = false;
	int aux;
	private LinkedList<Token> tokens;

	public void result() {
		if (testa) {
			System.out.println("Analise sintatica correta");
		} else {
			System.out.println("Analise sintatica incorreta");
		}
	}

	public Token getToken(int i) {
		for (i = aux; i < tokens.size(); i++) {
			return tokens.get(i);
		}
		return null;
	}

	public AnalisadorSintatico(LinkedList<Token> tokens) {
		super();
		this.i = 0;
		this.tokens = tokens;
		this.testa = false;
		this.aux = i;
	}
	public void empty(){
		return;
	}
	public void prog() {
		if (tokens.get(i).getValor().equals("program")) {
			//System.out.println("passou1");
			getToken(i++);
			id();
			if (tokens.get(i).getValor().equals(";")) {
				//System.out.println("passou2");
				getToken(i++);
				corpo();
			}
		}
	}
	public void corpo(){
		if (tokens.get(i).getValor().equals("var")){
			//System.out.println("passoucorpo*");
			getToken(i++);
			dec_var();
			rotina();
			if(tokens.get(i).getValor().equals("begin")){
				//System.out.println("passoubegin*");
				getToken(i++);
				sentencas();
			}
			if(tokens.get(i).getValor().equals("end")){
				testa = true;
			}

		}
	}
	public void rotina(){
		if(tokens.get(i).getValor().equals("procedure")){
			//System.out.println("passourotin*");
			getToken(i++);
			procedimento();
		}else if(tokens.get(i).getValor().equals("function")){
			getToken(i++);
			funcao();
		}else{
			empty();
		}
	}
	public void procedimento(){
		//System.out.println("passouproced*");
		id();
		parametros();
		if(tokens.get(i).getValor().equals(";")){
			getToken(i++);
			corpo();
			if(tokens.get(i).getValor().equals(";")){
				getToken(i++);
					rotina();
				}
			}
		}
	public void parametros(){
		if(tokens.get(i).getValor().equals("(")){
			//System.out.println("passouparam*");
			getToken(i++);
			listParam();
		}
		if(tokens.get(i).getValor().equals(")")){
			//System.out.println("passouparam2*");
			getToken(i++);
		}else if(!tokens.get(i).getValor().equals(")")){
			System.out.println(") esperado! 1");
		}else{
			empty();
		}
	}
	public void listParam(){
			listId();
			if(tokens.get(i).getValor().equals(":")){
				//System.out.println("passoulistparam*");
				getToken(i++);
				tipo();
				if(tokens.get(i).getValor().equals(";")){
					getToken(i++);
					listParam();
				}else{
					empty();
				}
			}else{
			empty();
		}
	}
	public void listId(){
		id();
		if(tokens.get(i).getValor().equals(",")){
			getToken(i++);
			listId();
		}else{
			empty();
		}

	}
	public void funcao(){
		id();
		parametros();
		if(tokens.get(i).getValor().equals(":")){
			getToken(i++);
			tipo();
			if(tokens.get(i).getValor().equals(";")){
				getToken(i++);
				corpo();
				if(tokens.get(i).getValor().equals(";")){
					getToken(i++);
					rotina();
				}
			}
		}

	}
	public void sentencas(){
		cmd();
		mSentencas();
	}
	public void mSentencas(){
		if(tokens.get(i).getValor().equals(";")){
			getToken(i++);
			sentencas();
		}else{
			empty();
		}
	}
	public void varRead(){
		id();
		mvarRead();
	}
	public void mvarRead(){
		if(tokens.get(i).getValor().equals(",")){
			getToken(i++);
			varRead();
		}else{
			empty();
		}
	}
	public void expressao(){
		expreNum();
		expreConj();
	}
	public void expreNum(){
		termo();
		id();
		argumentos();
	}
	public void termo(){
		if (tokens.get(i).getValor().equals("*") || tokens.get(i).getValor().equals("+")
				|| tokens.get(i).getValor().equals("-") || tokens.get(i).getValor().equals("/")
				|| tokens.get(i).getValor().equals("//")){
			getToken(i++);
			if(tokens.get(i).getValor().equals("(")){
				getToken(i++);
				termo();
			}if(tokens.get(i).getValor().equals(")")){
				getToken(i++);
			}else if(!tokens.get(i).getValor().equals(")")){
				System.out.println(") esperado! ");
			}
			operador = true;
		}else if(tokens.get(i).getTipo().equals(Token.IDENTIFIER_TOKEN)){
			getToken(i++);
		}else if(tokens.get(i).getTipo().equals(Token.INTEGER_TOKEN)){
			getToken(i++);
		}//else if(tokens.get(i).getTipo().equals(Token.IDENTIFIER_TOKEN)){
			//getToken(i++);
		//}	
	}
	public void argumentos(){
		if(tokens.get(i).getValor().equals("(")){
			getToken(i++);
			listArgumentos();
			if(tokens.get(i).getValor().equals(")")){
				getToken(i++);
			}else if(!tokens.get(i).getValor().equals(")")){
				System.out.println(") esperado! ");
			}
		}else{
			empty();
		}
	}
	public void listArgumentos(){
		expressao();
		contArgumentos();
	}
	public void contArgumentos(){
		if(tokens.get(i).getValor().equals(",")){
			getToken(i++);
			listArgumentos();
		}else{
			empty();
		}
	}
	public void expreConj(){
		if(tokens.get(i).getTipo().equals(Token.OPERATOR_TOKEN_CONJ)){
			getToken(i++);
			if(tokens.get(i).getValor().equals("(")){
				getToken(i++);
				conteudo();
				if(tokens.get(i).getValor().equals(",")){
					getToken(i++);
					conteudo();
				}
			}if(tokens.get(i).getValor().equals(")")){
				getToken(i++);
			}else if(!tokens.get(i).getValor().equals(")")){
				System.out.println(") esperado! ");
			}
		}else if(tokens.get(i).getValor().equals("pos")){
			if(tokens.get(i).getValor().equals("(")){
				getToken(i++);
				intergerNum();
			}if(tokens.get(i).getValor().equals(")")){
				getToken(i++);
			}else if(!tokens.get(i).getValor().equals(")")){
				System.out.println(") esperado! 5");
			}
		}
	}
	public void conteudo(){
		if(tokens.get(i).getValor().equals("{}")){
			getToken(i++);
		}else if(tokens.get(i).getValor().equals("{")){
			getToken(i++);
			intergerNum();
			integerCont();
			if(tokens.get(i).getValor().equals("}")){
				getToken(i++);
			}else if(!tokens.get(i).getValor().equals("}")){
				System.out.println("} esperado! ");
			}			
		}
		else if(tokens.get(i).getValor().equals("{")){
			getToken(i++);
			realNum();
			realNumCont();
			if(tokens.get(i).getValor().equals("}")){
				getToken(i++);
			}else if(!tokens.get(i).getValor().equals("}")){
				System.out.println("} esperado! ");
			}
		}
	}
	public void integerCont(){
		if(tokens.get(i).getValor().equals(",")){
			getToken(i++);
			intergerNum();
		}else{
			empty();
		}
	}
	public void intergerNum(){
		if(tokens.get(i).getValor().equals("+") || tokens.get(i).getValor().equals("-")){
			getToken(i++);
			if(tokens.get(i).getTipo().equals(Token.INTEGER_TOKEN)){
				getToken(i++);
			}
		}else if(tokens.get(i).getValor().equals("0")){
			getToken(i++);
		}else if(tokens.get(i).getTipo().equals(Token.INTEGER_TOKEN)){
			getToken(i++);
		}
	}
	public void realNum(){
		if(tokens.get(i).getValor().equals("+") || tokens.get(i).getValor().equals("-")){
			getToken(i++);
			if(tokens.get(i).getTipo().equals(Token.INTEGER_TOKEN)){
				getToken(i++);
				if(tokens.get(i).getValor().equals(".")){
					getToken(i++);
					if(tokens.get(i).getTipo().equals(Token.INTEGER_TOKEN)){
						getToken(i++);
					}
				}
			}
		}
	}
	public void realNumCont(){
		if(tokens.get(i).getValor().equals(",")){
			getToken(i++);
			realNum();
		}else{
			empty();
		}
	}
	public void condicao(){
		relacao();
		if (tokens.get(i).getValor().equals("(")) {
			getToken(i++);
			expreNum();
			expreConj();
			if(tokens.get(i).getValor().equals(",")){
				getToken(i++);
				expreNum();
				expreConj();
			}
			if(tokens.get(i).getValor().equals(")")){
				getToken(i++);
			}else if(!tokens.get(i).getValor().equals(")")){
				System.out.println(") esperado! 7");
			}
		}
	}
	public void pFalsa(){
		if(tokens.get(i).getValor().equals("else begin")){
			getToken(i++);
			sentencas();
			if(tokens.get(i).getValor().equals("end")){
				getToken(i++);
			}else if(!tokens.get(i).getValor().equals("end")){
				System.out.println("end esperado! ");
			}
		}else{
			empty();
		}
	}
	public void id() {
		idCont();
	}	boolean a =false;
	public void idCont() {
		if (tokens.get(i).getTipo().equals(Token.INTEGER_TOKEN)
				|| tokens.get(i).getTipo().equals(Token.IDENTIFIER_TOKEN)) {
			//System.out.println("passouidcont");
			getToken(i++);
			a=true;
			if (operador) {
				// System.out.println("Necessario uma variavel dps do operador!
				// ");
				testa = false;
			} else {
				idCont();
			}
		}else{
			empty();
		}
	}

	public void relacao() {
		if (tokens.get(i).getValor().equals("=") || tokens.get(i).getValor().equals("<>")
				|| tokens.get(i).getValor().equals(">") || tokens.get(i).getValor().equals("<")
				|| tokens.get(i).getValor().equals("<=") || tokens.get(i).getValor().equals(">=")) {
			getToken(i++);
		}
		operador = true;
	}

	public void cmd() {
		if(tokens.get(i).getValor().equals("read")){
			getToken(i++);
			if(tokens.get(i).getValor().equals("(")){
				getToken(i++);
				varRead();
			}if(tokens.get(i).getValor().equals(")")){
				getToken(i++);
			}else if(!tokens.get(i).getValor().equals(")")){
				System.out.println(") esperado! ");
			}
		}
		else if (tokens.get(i).getValor().equals("write")) {
			getToken(i++);
			if (tokens.get(i).getValor().equals("(")) {
				getToken(i++);
				varRead();
			}if(tokens.get(i).getValor().equals(")")){
				getToken(i++);
			}else if(!tokens.get(i).getValor().equals(")")){
				System.out.println(") esperado! ");
			}
		}
		else if(tokens.get(i).getValor().equals("for")){
			getToken(i++);
			id();
			if(tokens.get(i).getTipo().equals(Token.ATRIBUITION)){
				getToken(i++);
				expressao();
				if(tokens.get(i).getValor().equals("to")){
					getToken(i++);
					expressao();
					if(tokens.get(i).getValor().equals("do begin")){
						getToken(i++);
						sentencas();
						if(tokens.get(i).getValor().equals("end")){
							getToken(i++);
						}else if(!tokens.get(i).getValor().equals("end")){
							System.out.println("end esperado! ");
						}
					}
				}
			}
		}
		else if (tokens.get(i).getValor().equals("repeat")) {
			getToken(i++);
			sentencas();
			if (tokens.get(i).getValor().equals("until")) {
				getToken(i++);
				if (tokens.get(i).getValor().equals("(")) {
					getToken(i++);
					condicao();
				}if(tokens.get(i).getValor().equals(")")){
					getToken(i++);
				}else if(!tokens.get(i).getValor().equals(")")){
					System.out.println(") esperado! 11");
				}
			}
		} else if(tokens.get(i).getValor().equals("while")){
			getToken(i++);
			if (tokens.get(i).getValor().equals("(")) {
				getToken(i++);
				condicao();
			}if(tokens.get(i).getValor().equals(")")){
				getToken(i++);
			}else if(!tokens.get(i).getValor().equals(")")){
				System.out.println(") esperado! ");
			}if(tokens.get(i).getValor().equals("do begin")){
				getToken(i++);
				sentencas();
				if(tokens.get(i).getValor().equals("end")){
					getToken(i++);
				}else if(!tokens.get(i).getValor().equals("end")){
					System.out.println("end esperado! ");
				}
			}
		}else if(tokens.get(i).getValor().equals("if")){
			getToken(i++);
			if (tokens.get(i).getValor().equals("(")) {
				getToken(i++);
				condicao();
			}if(tokens.get(i).getValor().equals(")")){
				getToken(i++);
			}else if(!tokens.get(i).getValor().equals(")")){
				System.out.println(") esperado! 11");
			}if(tokens.get(i).getValor().equals("then begin")){
				getToken(i++);
				sentencas();
				if(tokens.get(i).getValor().equals("end")){
					getToken(i++);
				}else if(!tokens.get(i).getValor().equals("end")){
					System.out.println("end esperado! ");
				}
			}
		}
		else if(tokens.get(i).getTipo().equals(Token.IDENTIFIER_TOKEN)){
			getToken(i++);
			if(tokens.get(i).getTipo().equals(Token.ATRIBUITION)){
				getToken(i++);
				expressao();
			}
		}else{
			if(tokens.get(i).getTipo().equals(Token.IDENTIFIER_TOKEN)){
				getToken(i++);
				id();
				argumentos();
			}
		}
	}
	public void tipo() {
		// <tipo> ::= integer| real| {integer} |{real}
		if (tokens.get(i).getValor().equals("integer") || tokens.get(i).getValor().equals("{integer}")) {
			//System.out.println("passoutipo");
			getToken(i++);
		}else if(tokens.get(i).getValor().equals("real") || tokens.get(i).getValor().equals("{real}")){
			//System.out.println("passoutipo");
			getToken(i++);
		}
	}

	public void dec_var() {
		if (tokens.get(i).getTipo().equals(Token.INTEGER_TOKEN) || tokens.get(i).getTipo().equals(Token.IDENTIFIER_TOKEN)) {
			//System.out.println("passoudcvar");
			id();
			if (tokens.get(i).getValor().equals(":")) {
				//System.out.println("passoudcvar");
				getToken(i++);
				tipo();
				if (tokens.get(i).getValor().equals(";")) {
					//System.out.println("passoudcvarF");
					getToken(i++);
					dec_var();
				} else {
					empty();
				}
			} else {
				System.out.println(": esperado! ");
			}
		}
		else{
			//System.out.println("passouempty");
			empty();
		}
	}

}