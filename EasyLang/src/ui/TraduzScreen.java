package ui;

import controller.GoogleTradutorSelenium;
import controller.VozSistema;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class TraduzScreen extends Application {

    private Font carregarFonte(String caminho, double tamanho) {
        try {
            InputStream is = getClass().getResourceAsStream(caminho);
            return Font.loadFont(is, tamanho);
        } catch (Exception e) {
            System.out.println("Erro ao carregar fonte: " + caminho);
            return Font.font(tamanho);
        }
    }

    private final Map<String, String> mapaIdiomas = new HashMap<>() {{
        put("Português", "pt");
        put("Inglês", "en");
        put("Espanhol", "es");
        put("Francês", "fr");
        put("Alemão", "de");
        put("Italiano", "it");
    }};

    @Override
    public void start(Stage stage) {
        Font fonte = carregarFonte("/assets/LuckiestGuy-Regular.ttf", 26);

        Label titulo = new Label("EASYLANG TRADUTOR");
        titulo.setFont(fonte);
        titulo.setTextFill(Color.WHITE);

        ComboBox<String> idiomaOrigem = new ComboBox<>();
        idiomaOrigem.getItems().addAll(mapaIdiomas.keySet());
        idiomaOrigem.setPromptText("Idioma de origem");
        idiomaOrigem.setMaxWidth(250);

        ComboBox<String> idiomaDestino = new ComboBox<>();
        idiomaDestino.getItems().addAll(mapaIdiomas.keySet());
        idiomaDestino.setPromptText("Idioma de destino");
        idiomaDestino.setMaxWidth(250);

        TextArea campoTexto = new TextArea();
        campoTexto.setPromptText("Digite o texto aqui...");
        campoTexto.setWrapText(true);
        campoTexto.setPrefSize(500, 150);

        TextArea campoResultado = new TextArea();
        campoResultado.setEditable(false);
        campoResultado.setWrapText(true);
        campoResultado.setPromptText("Tradução aparecerá aqui...");
        campoResultado.setPrefSize(500, 150);

        // Spinner GIF
        ImageView spinner = new ImageView(new Image(getClass().getResourceAsStream("/assets/spinner.gif")));
        spinner.setFitWidth(50);
        spinner.setFitHeight(50);
        spinner.setVisible(false);

        // Botão traduzir
        Button botaoTraduzir = new Button("Traduzir");
        botaoTraduzir.setStyle("-fx-background-color: #a55eea; -fx-text-fill: white; -fx-font-weight: bold;");
        botaoTraduzir.setPrefWidth(200);

        botaoTraduzir.setOnAction(e -> {
            String texto = campoTexto.getText();
            String origem = idiomaOrigem.getValue();
            String destino = idiomaDestino.getValue();

            if (texto == null || texto.isEmpty()) {
                campoResultado.setText("Digite um texto para traduzir.");
                return;
            }
            if (origem == null || destino == null) {
                campoResultado.setText("Selecione os idiomas.");
                return;
            }

            String codigoOrigem = mapaIdiomas.get(origem);
            String codigoDestino = mapaIdiomas.get(destino);

            spinner.setVisible(true);
            campoResultado.clear();

            new Thread(() -> {
                try {
                    String resultado = GoogleTradutorSelenium.traduzirComGoogle(texto, codigoOrigem, codigoDestino);
                    Platform.runLater(() -> {
                        campoResultado.setText(resultado);
                        spinner.setVisible(false);
                    });
                } catch (Exception ex) {
                    Platform.runLater(() -> {
                        campoResultado.setText("Erro na tradução: " + ex.getMessage());
                        spinner.setVisible(false);
                    });
                    ex.printStackTrace();
                }
            }).start();
        });

        // Botão ouvir
        Button botaoOuvir = new Button("🔊 Ouvir Tradução");
        botaoOuvir.setStyle("-fx-background-color: #7d5fff; -fx-text-fill: white; -fx-font-weight: bold;");
        botaoOuvir.setPrefWidth(200);

        botaoOuvir.setOnAction(ev -> {
            String texto = campoResultado.getText();
            String destino = idiomaDestino.getValue();
            String codigoDestino = mapaIdiomas.get(destino); // Pega o código do idioma (ex: "en", "es")

            if (texto != null && !texto.isEmpty() && codigoDestino != null) {
                VozSistema.falar(texto, codigoDestino);
            }
        });

        // Botão Voltar
        Button botaoVoltar = new Button("← Voltar");
        botaoVoltar.setStyle(
                "-fx-background-color: #a55eea;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 15;"
        );
        botaoVoltar.setOnAction(e -> {
            LoginScreen login = new LoginScreen();
            try {
                login.start(new Stage());
                stage.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Botão Sair
        Button botaoSair = new Button("Sair ✖");
        botaoSair.setStyle(
                "-fx-background-color: #ff4d4d;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 15;"
        );
        botaoSair.setOnAction(e -> Platform.exit());

        // Layout dos botões superiores
        BorderPane botoesTopo = new BorderPane();
        botoesTopo.setPadding(new Insets(10, 20, 10, 20));
        botoesTopo.setLeft(botaoVoltar);
        botoesTopo.setRight(botaoSair);

        // Define tamanhos padrão para os botões
        botaoVoltar.setPrefWidth(100);
        botaoSair.setPrefWidth(100);

        // Layout principal
        VBox conteudo = new VBox(15);
        conteudo.setAlignment(Pos.CENTER);
        conteudo.getChildren().addAll(
                new Region() {{ setMinHeight(20); }}, // adiciona espaçamento entre os botões do topo e o título
                titulo, idiomaOrigem, idiomaDestino,
                campoTexto, botaoTraduzir, spinner, botaoOuvir, campoResultado
        );

        BorderPane root = new BorderPane();
        root.setTop(botoesTopo);
        root.setCenter(conteudo);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #480082;");

        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Tela Principal - EasyLang");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
