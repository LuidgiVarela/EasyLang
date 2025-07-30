package controller;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class GoogleTradutorSelenium {

    public static String traduzirComGoogle(String texto, String de, String para) {
        String resultado = "";

        // Opções para rodar o Chrome em modo invisível (headless)
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Executa em segundo plano
        options.addArguments("--disable-gpu"); // Recomendado para headless
        options.addArguments("--no-sandbox");  // Evita problemas em ambientes Linux restritos
        options.addArguments("--disable-dev-shm-usage"); // Melhora desempenho

        WebDriver driver = new ChromeDriver(options);

        try {
            String url = String.format(
                    "https://translate.google.com/?sl=%s&tl=%s&text=%s&op=translate",
                    de, para, texto.replace(" ", "%20")
            );

            driver.get(url);

            // Espera até a tradução carregar (de forma mais robusta seria com WebDriverWait, mas usamos sleep simples aqui)
            Thread.sleep(4000);

            WebElement campoResultado = driver.findElement(By.xpath("//span[@jsname='W297wb']"));
            resultado = campoResultado.getText();

        } catch (Exception e) {
            resultado = "Erro ao traduzir: " + e.getMessage();
            e.printStackTrace();
        } finally {
            driver.quit();
        }

        return resultado;
    }
}
