package br.com.unisul.analisador.application;

import br.com.unisul.analisador.motor.AnalisadorLexico;
import br.com.unisul.analisador.motor.AnalisadorSemantico;
import br.com.unisul.analisador.motor.AnalisadorSintatico;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.StringReader;
import java.util.Objects;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		AnchorPane root = FXMLLoader.load(getClass().getResource("../views/main/Principal.fxml"));
		Scene scene = new Scene(root);

		primaryStage.setScene(scene);
		primaryStage.setTitle("Analizador L�xico");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);

//        try {
//            var lexico = new AnalisadorLexico(new StringReader("teste if 3 a tes ewq asd 123"));
//            var token = lexico.proximoToken();
//
//            while (Objects.nonNull(token)) {
//                System.out.println(token);
//                token = lexico.proximoToken();
//            }
//
//            System.out.println("Finalizou");
//
//            var semantico = new AnalisadorSemantico();
//            new AnalisadorSintatico().parse(lexico, semantico);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
	}

}
