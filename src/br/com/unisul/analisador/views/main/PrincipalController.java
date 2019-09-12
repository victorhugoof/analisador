package br.com.unisul.analisador.views.main;

import br.com.unisul.analisador.dto.Token;
import br.com.unisul.analisador.exception.LexicoException;
import br.com.unisul.analisador.exception.SintaticoException;
import br.com.unisul.analisador.motor.AnalisadorLexico;
import br.com.unisul.analisador.motor.AnalisadorSintatico;
import br.com.unisul.analisador.motor.AnalisadorSintaticoDefault;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
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

	@Override
	@FXML
	public void initialize(URL location, ResourceBundle resources) {
		codigo.setCellValueFactory(new PropertyValueFactory<>("id"));
		token.setCellValueFactory(new PropertyValueFactory<>("token"));
		descricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
	}

	@FXML
	public void analizar() {
		try {
			
			List<Token> tokens = AnalisadorLexico.executa(txtIn.getText());
			preencheTabelaTokens(tokens);
			txtOut.setText("Analisador Léxico executado com sucesso!");

			AnalisadorSintatico.executa(tokens);
			txtOutSintatico.setText("Analisador Sintático executado com sucesso!");
			
		} catch (LexicoException e) {
			txtOut.setText(e.toString());
		} catch (SintaticoException ex) {
			txtOutSintatico.setText(ex.toString());
		}

		/**
		 * Executa com o analisador padr�o do gals, somente para via de compara��o
		 */
		try {
			new AnalisadorSintaticoDefault().parse(new AnalisadorLexico(txtIn.getText()));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@FXML
	public void lerArquivo() {
		FileChooser fileChooser = new FileChooser();
		File file = new File("/");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivo de Texto (.txt)","*.txt"));

		fileChooser.setInitialDirectory(file);
		File selecionado = fileChooser.showOpenDialog(null);

		if (Objects.nonNull(selecionado)) {
			lerArquivo(selecionado);
		}
	}

	private void lerArquivo(File file) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String texto = null;

			while ((texto = reader.readLine()) != null) {
				txtIn.appendText(texto + "\r\n");
			}

			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void preencheTabelaTokens(List<Token> tokens) {
		tbl.setItems(FXCollections.observableArrayList(tokens));
	}

}
