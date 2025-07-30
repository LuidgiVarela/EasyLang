package controller;

public class VozSistema {
    public static void falar(String texto, String idioma) {
        String voz;

        switch (idioma) {
            case "en":
                voz = "Samantha"; break;
            case "es":
                voz = "Marisol"; break;
            case "fr":
                voz = "Thomas"; break;
            case "de":
                voz = "Anna"; break;
            case "it":
                voz = "Alice"; break;
            case "pt":
            default:
                voz = "Luciana"; break;
        }

        try {
            String comando = String.format("say -v %s \"%s\"", voz, texto);
            Runtime.getRuntime().exec(comando);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
