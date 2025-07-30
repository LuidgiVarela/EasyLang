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

public class CadastroScreen extends Application {

    @Override
    public void start(Stage stage) {
        // Título
        Label titulo = new Label("Cadastro");

        Font customFont = Font.loadFont(getClass().getResourceAsStream("/assets/LuckiestGuy-Regular.ttf"), 28);
        titulo.setFont(customFont);
        titulo.setTextFill(Color.WHITE);

        // Campos
        TextField campoNome = new TextField();
        campoNome.setPromptText("Nome completo");
        campoNome.setMaxWidth(250);

        TextField campoEmail = new TextField();
        campoEmail.setPromptText("E-mail");
        campoEmail.setMaxWidth(250);

        TextField campoTelefone = new TextField();
        campoTelefone.setPromptText("Número de telefone");
        campoTelefone.setMaxWidth(250);

        TextField campoCpf = new TextField();
        campoCpf.setPromptText("CPF");
        campoCpf.setMaxWidth(250);

        PasswordField campoSenha = new PasswordField();
        campoSenha.setPromptText("Senha");
        campoSenha.setMaxWidth(250);

        PasswordField campoConfirmarSenha = new PasswordField();
        campoConfirmarSenha.setPromptText("Confirmar senha");
        campoConfirmarSenha.setMaxWidth(250);

        // Botão de Cadastrar
        Button botaoCadastrar = new Button("Cadastrar");
        botaoCadastrar.setStyle("-fx-background-color: #a55eea; -fx-text-fill: white; -fx-font-weight: bold;");
        botaoCadastrar.setMaxWidth(250);

        botaoCadastrar.setOnAction(e -> {
            String nome = campoNome.getText();
            String email = campoEmail.getText();
            String telefone = campoTelefone.getText();
            String cpf = campoCpf.getText();
            String senha = campoSenha.getText();
            String confirmarSenha = campoConfirmarSenha.getText();

            if (nome.isEmpty() || email.isEmpty() || telefone.isEmpty() || cpf.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty()) {
                mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Por favor, preencha todos os campos.");
                return;
            }

            if (!senha.equals(confirmarSenha)) {
                mostrarAlerta(Alert.AlertType.ERROR, "Erro", "As senhas não coincidem.");
                return;
            }

            boolean sucesso = UsuarioDAO.cadastrarUsuario(nome, email, telefone, cpf, senha);
            if (sucesso) {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Sucesso", "Usuário cadastrado com sucesso!");

                // Fecha a tela de cadastro
                stage.close();

                // Abre a tela de login
                try {
                    new LoginScreen().start(new Stage());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Erro", "Erro ao cadastrar o usuário.");
            }
        });

        // Layout
        VBox root = new VBox(10, titulo, campoNome, campoEmail, campoTelefone, campoCpf, campoSenha, campoConfirmarSenha, botaoCadastrar);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #480082;");

        Scene scene = new Scene(root, 800, 500);
        stage.setTitle("Tela de Cadastro");
        stage.setScene(scene);
        stage.show();
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
