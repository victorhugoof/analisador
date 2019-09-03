package br.com.unisul.analisador.views.main;

import br.com.unisul.analisador.dto.Token;
import br.com.unisul.analisador.motor.AnalisadorLexico;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class PrincipalController implements Initializable {

	@FXML
	private TextArea txtIn;

	@FXML
	private TableView<Token> tbl;

	@FXML
	private TableColumn<Token, Integer> codigo;

	@FXML
	private TableColumn<Token, String> token;

	@FXML
	private TableColumn<Token, String> descricao;

	@FXML
	private TextArea txtOut;

	@FXML
	private TextArea txtOutSemantico;

//	public boolean pertenceAoConjunto(char ch, char[] conjunto) {
//		for (char c : conjunto)
//			if (ch == c) {
//				return true;
//			}
//		return false;
//	}
//
	public void analizar() {
		try {
			List<Token> tokens = new ArrayList<>();
			AnalisadorLexico lexico = new AnalisadorLexico(new StringReader(txtIn.getText()));
			Token token = lexico.proximoToken();

			while (Objects.nonNull(token)) {
				System.out.println(token);
				tokens.add(token);

				token = lexico.proximoToken();
			}

			tbl.setItems(listaDeTokens(tokens));
		} catch (Exception e) {
			e.printStackTrace();
			txtOut.setText(e.getMessage());
		}
//
//		lexico = new AnalisadorLexico();
//
//		String texto = txtIn.getText() + " ";
//		texto = texto.toLowerCase();
//		StringBuffer palavra = new StringBuffer();
//
//		String chave = "padrao";
//
//		char caracter;
//		txtOut.setText("");
//
//		for (int i = 0; i < texto.length(); i++) {
//			caracter = texto.charAt(i);
//
//			switch (chave) {
//
//			case "padrao":
//				if (pertenceAoConjunto(caracter, lexico.getLetras())) {
//					palavra.append(caracter);
//					chave = "litOuReserv";
//				} else if (pertenceAoConjunto(caracter, lexico.getNumeros())) {
//					palavra.append(caracter);
//					chave = "inteiro";
//				} else if (caracter == '"') {
//					lexico.setLiteral(true);
//					chave = "literal";
//				} else if (caracter == '+') {
//					lexico.getTokens().add(new Token(caracter + "", 2, "Operador de Adição"));
//					// break;
//				} else if (caracter == '-') {
//					lexico.getTokens().add(new Token(caracter + "", 3, "Operador de Subtração"));
//					break;
//				} else if (caracter == '*') {
//					lexico.getTokens().add(new Token(caracter + "", 4, "Operador de Multiplicação"));
//					break;
//				} else if (caracter == '/') {
//					lexico.getTokens().add(new Token(caracter + "", 5, "Operador de Divisão"));
//					break;
//				} else if (caracter == '<') {
//					palavra.append(caracter);
//					chave = "comparacao";
//				} else if (caracter == '>') {
//					palavra.append(caracter);
//					chave = "comparacaoMaior";
//				} else if (caracter == '=') {
//					lexico.getTokens().add(new Token(caracter + "", 6, "Igual"));
//					break;
//				} else if (caracter == ':') {
//					palavra.append(caracter);
//					chave = "doisPontos";
//				} else if (caracter == '.') {
//					lexico.getTokens().add(new Token(caracter + "", 16, "Ponto"));
//					break;
//				} else if (caracter == '(') {
//					palavra.append(caracter);
//					chave = "abreComent";
//				} else if (caracter == ')') {
//					lexico.getTokens().add(new Token(caracter + "", 18, "Fecha parenteses"));
//					break;
//				} else if (caracter == ',') {
//					lexico.getTokens().add(new Token(caracter + "", 15, "Vírgula"));
//					break;
//				} else if (caracter == ';') {
//					lexico.getTokens().add(new Token(caracter + "", 14, "Ponto e vírgula"));
//					break;
//				} else if (caracter == ' ' || caracter == '\n' || caracter == '\t' || caracter == '\b'
//						|| caracter == '\f' || caracter == '\r') {
//				} else if (caracter == '$' && i == texto.length() - 1) {
//					System.out.println("entrou aqui");
//				} else if (caracter == '$') {
//					lexico.getTokens().add(new Token(caracter + "", 1, "Dollar - Fim"));
//					return;
//				}else {
//					txtOut.setText("caracter inválido: " + caracter);
//					return;
//
//				}
//				break;
//
//			case "litOuReserv":
//				if (pertenceAoConjunto(caracter, lexico.getLetras()) || pertenceAoConjunto(caracter, lexico.getNumeros())) {
//					palavra.append(caracter);
//				} else {
//					if(palavra.length()>30) {
//						txtOut.setText("Palavra muito grande :(");
//						return;
//					}
//					chave = "padrao";
//					i--;
//					Token token = new Token(palavra.toString(), 10, "Identificador");
//					palavra = new StringBuffer();
//					for (Token t : lexico.getReservados()) {
//						if (t.getToken().equals(token.getToken())) {
//							token = t;
//							break;
//						}
//					}
//					lexico.getTokens().add(token);
//				}
//				break;
//
//			case "inteiro":
//				if (pertenceAoConjunto(caracter, lexico.getNumeros())) {
//					palavra.append(caracter);
//				} else {
//
//					chave = "padrao";
//					i--;
//					int numero = 0;
//					try {
//						numero = Integer.parseInt(palavra.toString());
//					} catch (Exception e) {
//						txtOut.setText("Erro relativo ao numero");
//					}
//					if (numero > 32767 || numero < -32767) {
//						txtOut.setText("Número fora da margem dos calculáveis :(");
//						return;
//					}
//					lexico.getTokens().add(new Token(palavra.toString(), 20, "Inteiro"));
//					palavra = new StringBuffer();
//					break;
//
//				}
//				break;
//
//			case "literal":
//				if (caracter != '"') {
//					palavra.append(caracter);
//				} else {
//					chave = "padrao";
//					lexico.setLiteral(false);
//					if (palavra.length() > 255) {
//						txtOut.setText("Palavra muito grande :(");
//						return;
//					}
//					lexico.getTokens().add(new Token(palavra.toString(), 21, "Literal"));
//					palavra = new StringBuffer();
//
//				}
//				break;
//
//			case "comparacao":
//				chave = "padrao";
//				if (caracter == '>') {
//					palavra.append(caracter);
//					lexico.getTokens().add(new Token(palavra.toString(), 11, "Diferente"));
//					palavra = new StringBuffer();
//
//					break;
//				} else if (caracter == '=') {
//					palavra.append(caracter);
//					lexico.getTokens().add(new Token(palavra.toString(), 10, "Menor ou igual"));
//					palavra = new StringBuffer();
//
//					break;
//				}
//				lexico.getTokens().add(new Token(palavra.toString(), 9, "Menor"));
//				palavra = new StringBuffer();
//				i--;
//				break;
//
//			case "comparacaoMaior":
//				chave = "padrao";
//				if (caracter == '=') {
//					palavra.append(caracter);
//					lexico.getTokens().add(new Token(palavra.toString(), 8, "Maior ou Igual"));
//					palavra = new StringBuffer();
//					break;
//				} else {
//					lexico.getTokens().add(new Token(palavra.toString(), 7, "Maior"));
//					palavra = new StringBuffer();
//					i--;
//					break;
//				}
//
//			case "doisPontos":
//				chave = "padrao";
//				if (caracter == '=') {
//					palavra.append(caracter);
//					lexico.getTokens().add(new Token(palavra.toString(), 12, "Atribuição"));
//					palavra = new StringBuffer();
//					break;
//				} else {
//					lexico.getTokens().add(new Token(palavra.toString(), 13, "Dois Pontos"));
//					i--;
//					palavra = new StringBuffer();
//					break;
//				}
//			case "abreComent":
//				if (caracter == '*') {
//					lexico.setComentario(true);
//					palavra = new StringBuffer();
//					chave = "fechaComent";
//				} else {
//					chave = "padrao";
//					lexico.getTokens().add(new Token(caracter + "", 17, "Abre parenteses"));
//					palavra = new StringBuffer();
//					i--;
//					break;
//				}
//			case "fechaComent":
//				if (caracter == '*' && texto.charAt(i + 1) == ')') {
//					lexico.setComentario(false);
//					chave = "padrao";
//					i += 1;
//				}
//				break;
//			}
//			tbl.setItems(listaDeTokens());
//		}
//
//		if (lexico.isComentario())
//			txtOut.setText("Comentário ainda aberto");
//		if (lexico.isLiteral())
//			txtOut.setText("Literal ainda aberto");
//		if (txtOut.getText() == "" || txtOut.getText().isEmpty()) {
//			txtOut.setText("Análise concluída. Tokens encontrados: " + lexico.getTokens().size());
//			tbl.setItems(listaDeTokens());
//
//			sintatico();
//		}
//
	}
//
	private void sintatico() {
//		txtOutSemantico.setText("Análise Semantica ");
//
//		ArrayList<Integer> pilha = new ArrayList<Integer>();
//		ArrayList<Integer> fila = new ArrayList<Integer>();
//
//		for(Token token : lexico.getTokens()){
//			fila.add(token.getCodigo());
//		}
//
//		pilha.add(Constants.DOLLAR); //
//		pilha.add(ParserConstants.FIRST_NON_TERMINAL); //topo da pilha
//
//		while (pilha.size() - 1 != Constants.DOLLAR) {
//			if(pilha.size() - 1 < ParserConstants.FIRST_NON_TERMINAL) {
//				if(pilha.size() - 1 == fila.get(0)) {
//					pilha.remove(pilha.size() -1);
//					fila.remove(fila.get(0));
//				}else {
//					txtOutSemantico.setText("Erro ");
//				}
//			}
//		}
//
	}

	private ObservableList<Token> listaDeTokens(List<Token> tokens) {
		return FXCollections.observableArrayList(tokens);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		codigo.setCellValueFactory(new PropertyValueFactory<>("id"));
		token.setCellValueFactory(new PropertyValueFactory<>("token"));
		descricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));

		txtIn.setText("if (0 > 1)");
	}

}
