package br.com.unisul.analisador.views.main;

import br.com.unisul.analisador.dto.Instruction;
import br.com.unisul.analisador.dto.Token;
import br.com.unisul.analisador.exception.LexicoException;
import br.com.unisul.analisador.exception.SintaticoException;
import br.com.unisul.analisador.motor.AnalisadorLexico;
import br.com.unisul.analisador.motor.AnalisadorSemantico;
import br.com.unisul.analisador.motor.AnalisadorSintatico;
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
import java.util.Collections;
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
    private TableView<Instruction> tblIntermediaria;
    
    @FXML
    private TableColumn<Instruction, Integer> index;

    @FXML
    private TableColumn<Instruction, String> nome;

    @FXML
    private TableColumn<Instruction, String> operador1;
    
    @FXML
    private TableColumn<Instruction, String> operador2;


    @FXML
    private TextArea txtOut;

    @FXML
    private TextArea txtOutSintatico;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        codigo.setCellValueFactory(new PropertyValueFactory<>("id"));
        token.setCellValueFactory(new PropertyValueFactory<>("token"));
        descricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        index.setCellValueFactory(new PropertyValueFactory<>("index"));
        nome.setCellValueFactory(new PropertyValueFactory<>("name"));
        operador1.setCellValueFactory(new PropertyValueFactory<>("operator1"));
        operador2.setCellValueFactory(new PropertyValueFactory<>("operator2"));

        
        txtIn.setText("Program ProgramaTrabalho3;\n" +
                "\tConst maxNums = 5;\n" +
                "\tVar x,res,cont,soma: Integer;\n" +
                "\n" +
                "\tProcedure calcula(y: integer);\n" +
                "\t\tvar divisor : integer;\n" +
                "\n" +
                "\t\tProcedure divide(a,b: integer);\n" +
                "\t\tbegin\n" +
                "\t\t\tres := a / b;\n" +
                "\t\tend;\n" +
                "\n" +
                "\tbegin\n" +
                "\t\twriteln(\"Informe o valor do divisor:\" );\n" +
                "\t\tReadln(divisor);\n" +
                "\t\tcall divide(y,divisor);\n" +
                "\tend;\n" +
                "\n" +
                "Begin\n" +
                "\tWriteln(\"Iniciando programa...\");\n" +
                "\tWriteln(\"Informe o valor do dividendo: \");\n" +
                "\tReadln(x);\n" +
                "\tsoma := 0;\n" +
                "\n" +
                "\tfor cont := 1 to maxNums do begin\n" +
                "\t\tcont := cont + 1;\n" +
                "\t\tcall calcula(x);\n" +
                "\t\tWriteln(\"Resultado do calculo: \", res);\n" +
                "\t\tsoma := soma + res;\n" +
                "\tend;\n" +
                "\tWriteln(\"Resultado da soma dos calculos: \", soma);\n" +
                "End.");
    }
    
    @FXML
    public void interpretar() {
    	 AnalisadorSemantico.interpretar();
    }

    @FXML
    public void analizar() {
        txtOut.setText("");
        txtOutSintatico.setText("");
        preencheTabelaTokens(Collections.emptyList());

        try {

            List<Token> tokens = AnalisadorLexico.executa(txtIn.getText());
            preencheTabelaTokens(tokens);
            txtOut.setText("Analisador Léxico executado com sucesso!");

            AnalisadorSintatico.executa(tokens);
            txtOutSintatico.setText("Analisador Sintático executado com sucesso!");
            
            preencheTabelaIntermediaria();
            
        } catch (LexicoException e) {
            txtOut.setText(e.toString());
        } catch (SintaticoException ex) {
            txtOutSintatico.setText(ex.toString());
        }

        /**
         * Executa com o analisador padrão do gals, somente para via de comparação
         */
        try {
//			new AnalisadorSintaticoDefault().parse(new AnalisadorLexico(txtIn.getText()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void lerArquivo() {
        FileChooser fileChooser = new FileChooser();
        File file = new File("/");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivo de Texto (.txt)", "*.txt"));

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
    
    private void preencheTabelaIntermediaria() {
        tblIntermediaria.setItems(FXCollections.observableArrayList(AnalisadorSemantico.getIntermediateCodel()));
    }

}
