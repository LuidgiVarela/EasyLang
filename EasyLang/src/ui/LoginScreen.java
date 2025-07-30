package ui;

import database.UsuarioDAO;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.InputStream;

public class LoginScreen extends Application {

    @Override
    public void start(Stage stage) {
        // Carrega a fonte personalizada
        Font customFont = carregarFonte("/assets/LuckiestGuy-Regular.ttf", 28);

        // Título
        Label titulo = new Label("LOGIN");
        titulo.setTextFill(Color.WHITE);
        if (customFont != null) {
            titulo.setFont(customFont);
        }

        // Campo de usuário (email)
        TextField campoUsuario = new TextField();
        campoUsuario.setPromptText("Usuário (e-mail)");
        campoUsuario.setMaxWidth(250);

        // Campo de senha
        PasswordField campoSenha = new PasswordField();
        campoSenha.setPromptText("Senha");
        campoSenha.setMaxWidth(250);

        // Botão Entrar
        Button botaoEntrar = new Button("Entrar");
        botaoEntrar.setStyle("-fx-background-color: #a55eea; -fx-text-fill: white; -fx-font-weight: bold;");
        botaoEntrar.setPrefWidth(250);

        botaoEntrar.setOnAction(e -> {
            String email = campoUsuario.getText();
            String senha = campoSenha.getText();

            if (email.isEmpty() || senha.isEmpty()) {
                mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Preencha todos os campos.");
                return;
            }

            boolean loginValido = UsuarioDAO.validarLogin(email, senha);

            if (loginValido) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Login realizado", "Bem-vindo ao EasyLang!");

                // Fecha a tela atual
                Stage telaAtual = (Stage) botaoEntrar.getScene().getWindow();
                telaAtual.close();

                // Abre a tela de tradução
                TraduzScreen traduzScreen = new TraduzScreen();
                try {
                    traduzScreen.start(new Stage());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao carregar a tela de tradução.");
                }

            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Usuário ou senha inválidos.");
            }
        });

        // Botão Cadastrar
        Button botaoCadastrar = new Button("Cadastrar");
        botaoCadastrar.setStyle(
                "-fx-background-color: #a55eea;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 10;" +
                        "-fx-cursor: hand;"
        );
        botaoCadastrar.setPrefWidth(250);

        botaoCadastrar.setOnAction(e -> {
            CadastroScreen cadastro = new CadastroScreen();
            try {
                cadastro.start(new Stage());
                ((Stage) botaoCadastrar.getScene().getWindow()).close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Layout
        VBox root = new VBox(15, titulo, campoUsuario, campoSenha, botaoEntrar, botaoCadastrar);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #480082;");

        Scene scene = new Scene(root, 800, 500);
        stage.setScene(scene);
        stage.setTitle("Tela de Login");
        stage.show();
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

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensagem) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
