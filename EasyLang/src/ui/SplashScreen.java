package ui;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import java.io.InputStream;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import database.UsuarioDAO;

public class SplashScreen extends Application {

    @Override
    public void start(Stage splashStage) {
        UsuarioDAO.criarTabela();

        // Texto de carregamento
        Label label = new Label("EasyLang carregando...");

        // Fonte personalizada (certifique-se de que o .ttf está em resources/assets/)
        Font customFont = Font.loadFont(
                getClass().getResourceAsStream("/assets/LuckiestGuy-Regular.ttf"), 28
        );
        label.setFont(customFont);
        label.setTextFill(Color.WHITE);

        // Barra de progresso
        ProgressBar progressBar = new ProgressBar();
        progressBar.setStyle("-fx-accent: #a55eea;"); // cor roxa
        progressBar.setPrefWidth(300);

        // Layout centralizado
        VBox root = new VBox(20, label, progressBar);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #480082;"); // roxo escuro

        // Cena da splash screen
        Scene scene = new Scene(root, 800, 500);

        splashStage.initStyle(StageStyle.UNDECORATED); // Remove barra de título
        splashStage.setScene(scene);
        splashStage.show();

        // Aguarda 3 segundos e abre a tela principal
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(event -> {
            splashStage.close();
            abrirTelaLogin();
        });
        delay.play();
    }

    private void abrirTelaLogin() {
        LoginScreen login = new LoginScreen();
        Stage stage = new Stage();
        try {
            login.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Font carregarFonte(String caminho, double tamanho) {
        try {
            InputStream is = getClass().getResourceAsStream(caminho);
            return Font.loadFont(is, tamanho);
        } catch (Exception e) {
            System.out.println("Erro ao carregar fonte: " + caminho);
            return null;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
