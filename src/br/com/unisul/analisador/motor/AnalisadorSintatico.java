package br.com.unisul.analisador.motor;

import br.com.unisul.analisador.constants.Constants;
import br.com.unisul.analisador.dto.Token;
import br.com.unisul.analisador.exception.LexicoException;
import br.com.unisul.analisador.exception.SemanticoExcepion;
import br.com.unisul.analisador.exception.SintaticoException;

import java.util.Stack;

public class AnalisadorSintatico implements Constants {

	private Stack<Integer> fila = new Stack<>();
	private Token currentToken;
	private Token previousToken;
	private AnalisadorLexico lexico;
	private AnalisadorSemantico semantico;

//	private static final boolean isTerminal(int x) {
//		return x < FIRST_NON_TERMINAL;
//	}
//
//	private static final boolean isNonTerminal(int x) {
//		return x >= FIRST_NON_TERMINAL && x < FIRST_SEMANTIC_ACTION;
//	}
//
//	private static final boolean isSemanticAction(int x) {
//		return x >= FIRST_SEMANTIC_ACTION;
//	}
	
	public AnalisadorSintatico(AnalisadorLexico lexico) {
		this.lexico = lexico;
		this.semantico = new AnalisadorSemantico();
	}

	public void analisa() throws LexicoException, SintaticoException, SemanticoExcepion {

		// Limpa a fila e adiciona
		fila.clear();
		
		// adiciona os tokens iniciais
		fila.push(DOLLAR);
		fila.push(START_SYMBOL);

		// busca o primeiro token o chama o metodo de percorrer
		currentToken = lexico.proximoToken();
		while (percorreLexico());
	}

	/**
	 * 
	 * @return false quando finaliza; true quando sucesso
	 * @throws LexicoException
	 * @throws SintaticoException
	 * @throws SemanticoExcepion
	 */
	private boolean percorreLexico() throws LexicoException, SintaticoException, SemanticoExcepion {
		
		verificaAdicionaTokenFinalPrograma();

		int topoFila = fila.pop().intValue();
		int atual = currentToken.getId();

		if (topoFila == EPSILON) {
			return true;
			
		} else if (isTerminal(topoFila)) {
			return trataTerminal(topoFila, atual);
			
		} else if (isNonTerminal(topoFila)) {
			return trataNaoTerminal(topoFila, atual);
			
		} else {
			return trataSemantico(topoFila);
		}
	}

	/**
	 * Se o token atual for null define que o proximo será o final do programa
	 */
	private void verificaAdicionaTokenFinalPrograma() {
		if (currentToken == null) {
			int pos = 0;
			if (previousToken != null) {
				pos = previousToken.getPosition() + previousToken.getToken().length();
			}

			currentToken = new Token(DOLLAR, "$", pos, "FINAL");
		}
	}

	/**
	 * Analise semantica
	 * @param topoFila
	 * @return
	 * @throws SemanticoExcepion
	 */
	private boolean trataSemantico(int topoFila) throws SemanticoExcepion {
		semantico.executeAction(topoFila - FIRST_SEMANTIC_ACTION, previousToken);
		return true;
	}

	/**
	 * Processa nao terminal, verifica se possui na tabela de parse
	 * @param topoFila
	 * @param atual
	 * @return
	 * @throws SintaticoException
	 */
	private boolean trataNaoTerminal(int topoFila, int atual) throws SintaticoException {
		if (consultaTabelaDeParse(topoFila, atual)) {
			return true; //se esta na tabela de parse pula para a proxima iteracao
			
		} else {
			// senao dispara a excecao
			throw new SintaticoException(PARSER_ERROR[topoFila], currentToken.getPosition());
		}
	}

	/**
	 * Processa terminais
	 * @param topoFila
	 * @param atual
	 * @return
	 * @throws LexicoException
	 * @throws SintaticoException
	 */
	private boolean trataTerminal(int topoFila, int atual) throws LexicoException, SintaticoException {
		if (topoFila == atual) {
			
			// se for o ultimo da fila PARA
			if (fila.empty()) {
				return false;
			}

			// senao pula para a proxima iteracao
			else {
				previousToken = currentToken;
				currentToken = lexico.proximoToken();
				return true;
			}
			
		} else { // se o atual nao for o mesmo do topo da fila dispara erro
			throw new SintaticoException(PARSER_ERROR[topoFila], currentToken.getPosition());
		}
	}

	private boolean consultaTabelaDeParse(int topo, int atual) {
		int parse = PARSER_TABLE[topo - FIRST_NON_TERMINAL][atual - 1];
		
		if (parse >= 0) {
			int[] productions = PRODUCTIONS[parse];
			int size = productions.length;
			//empilha a producao em ordem reversa
			for (int i = size -1; i >= 0; i--) {
				fila.push(productions[i]);
			}
			
			return true;
			
		} else {
			return false;
		}
	}

}
