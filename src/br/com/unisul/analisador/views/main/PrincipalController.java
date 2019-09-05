package br.com.unisul.analisador.views.main;

import br.com.unisul.analisador.dto.Token;
import br.com.unisul.analisador.exception.LexicoException;
import br.com.unisul.analisador.exception.SemanticoExcepion;
import br.com.unisul.analisador.exception.SintaticoException;
import br.com.unisul.analisador.motor.AnalisadorLexico;
import br.com.unisul.analisador.motor.AnalisadorSemantico;
import br.com.unisul.analisador.motor.AnalisadorSintatico;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
	private TextArea txtOutSintatico;

	public void analizar() {
		try {
			
			//lexico
			List<Token> tokens = new ArrayList<>();
			AnalisadorLexico lexico = new AnalisadorLexico(new StringReader(txtIn.getText()));
			Token token = lexico.proximoToken();

			while (Objects.nonNull(token)) {
				tokens.add(token);

				token = lexico.proximoToken();
			}

			tbl.setItems(listaDeTokens(tokens));
			
			lexico.setPosicaoAtual(0);
			sintatico(lexico);
			
		} catch (LexicoException e) {
			e.printStackTrace();
			txtOut.setText(e.toString());
		} catch (SintaticoException | SemanticoExcepion ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
			txtOutSintatico.setText(ex.toString());
		}

	}

	private void sintatico(AnalisadorLexico lexico) throws SintaticoException, LexicoException, SemanticoExcepion {
		
		new AnalisadorSintatico(lexico).analisa();
		txtOutSintatico.setText("Analise sintatica concluida");
		
		
		
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

	}

	private ObservableList<Token> listaDeTokens(List<Token> tokens) {
		return FXCollections.observableArrayList(tokens);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		codigo.setCellValueFactory(new PropertyValueFactory<>("id"));
		token.setCellValueFactory(new PropertyValueFactory<>("token"));
		descricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));

		try {
			BufferedReader reader = new BufferedReader(new FileReader("C:\\workspace\\analisador\\resources\\programa"));
			String texto = null;

			while ((texto = reader.readLine()) != null) {
				txtIn.appendText(texto + "\r\n");
			}

			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		analizar();
	}

}
