package br.com.unisul.analisador.motor;

import br.com.unisul.analisador.constants.Funcoes;
import br.com.unisul.analisador.dto.Token;
import br.com.unisul.analisador.exception.SintaticoException;

import java.util.List;
import java.util.Objects;
import java.util.Stack;

public class AnalisadorSintatico implements Funcoes {

	private Stack<Integer> fila;
	private List<Token> tokens;
	private Token tokenAtual;
	private Token tokenAnterior;

	public static void executa(List<Token> tokens) throws SintaticoException {
		new AnalisadorSintatico(tokens).processaTokens();
	}

	private AnalisadorSintatico(List<Token> tokens) {
		this.tokens = tokens;
		this.fila = new Stack<>();
	}

	/**
	 * Método responsável por executar a análise sintática
	 * @throws SintaticoException
	 */
	private void processaTokens() throws SintaticoException {

		/**
		 * Adiciona tokens iniciais na fila
		 */
		addFila(DOLLAR);
		addFila(START_SYMBOL);

		/**
		 * Inicia o processo buscando o primeiro token da lista.
		 */
		proximoToken();

		/**
		 * Enquanto houver token na lista continuará processando
		 */
		while (!tokens.isEmpty()) {
			verificaAdicionaTokenFinalPrograma();

			Integer topo = topoFila();

			/**
			 * Caso for o EPSILON pula para o próximo token da fila
			 */
			if (topo.compareTo(EPSILON) == 0) {
				continue;

			} else if (isTerminal(topo)) {
				processaTokenTerminal(topo);
				continue;

			} else if (nonTerminal(topo)) {
				processaTokenNaoTerminal(topo);
				continue;

			}
		};
	}

	private void processaTokenTerminal(Integer topo) throws SintaticoException {

		/**
		 * Se o token atual for o mesmo do topo da fila realiza o processo, caso contrário dispara exceção
		 */
		if (topo.compareTo(tokenAtual.getId()) == 0) {

			/**
			 * Se a fila estiver vazia significa que já está no ultimo token, não realiza nada.
			 */
			if (filaVazia()) {
				return;
			}

			/**
			 * Pula para o próximo token da lista
			 */
			proximoToken();

		} else {

			throw new SintaticoException(PARSER_ERROR[topo], tokenAtual.getPosition());
		}
	}

	/**
	 * Método responsável por fazer a válidação do token não terminal
	 * @param topo
	 */
	private void processaTokenNaoTerminal(Integer topo) throws SintaticoException {
		Integer parse = buscaTokenTabelaParse(topo);

		/**
		 * Se encontrar na tabela de parse empilha as produções em ordem inversa na fila
		 */
		if (parse.compareTo(0) >= 0) {
			int[] productions = PRODUCTIONS[parse];
			int size = productions.length;

			for (int i = size - 1; i >= 0; i--) {
				addFila(productions[i]);
			}

		} else {
			throw new SintaticoException(PARSER_ERROR[topo], tokenAtual.getPosition());
		}
	}

	/**
	 * Realiza a busca na tabela de parse
	 * @param topo
	 * @return
	 */
	private Integer buscaTokenTabelaParse(Integer topo) {
		return PARSER_TABLE[topo - FIRST_NON_TERMINAL][tokenAtual.getId() - 1];
	}

	/**
	 * Se o token atual for null define que o proximo será o final do programa.txt
	 */
	private void verificaAdicionaTokenFinalPrograma() {
		if (Objects.isNull(tokenAtual)) {
			int pos = 0;

			/**
			 * Se possui token anterior o atual é inserido na próxima posição após o anterior
			 */
			if (Objects.nonNull(tokenAnterior)) {
				pos = tokenAnterior.getPosition() + tokenAnterior.getToken().length();
			}

			tokenAtual = new Token(DOLLAR, "$", pos, "FINAL");
		}
	}

	/**
	 * Adiciona um token à fila
	 * @param token
	 */
	private void addFila(Integer token) {
		this.fila.push(token);
	}

	/**
	 * Busca o topo da fila
	 * @return
	 */
	private Integer topoFila() {
		return this.fila.pop();
	}

	/**
	 * Retorna true quando a fila está vazia
	 * @return
	 */
	private boolean filaVazia() {
		return this.fila.isEmpty();
	}

	/**
	 * Busca o próximo token da lista
	 */
	private void proximoToken() {
		if (Objects.nonNull(tokenAtual)) {
			this.tokenAnterior = tokenAtual;
		}

		this.tokenAtual = this.tokens.get(0);
		this.tokens.remove(0);
	}
}
