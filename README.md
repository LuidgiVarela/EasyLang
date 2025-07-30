# 🗣️ EasyLang

**EasyLang** é um aplicativo de tradução multilíngue com interface gráfica moderna e leitura por voz, desenvolvido em **Java** com **JavaFX**. Ele permite traduzir textos entre vários idiomas e escutar a tradução com uma voz natural adaptada ao idioma escolhido. O projeto é ideal para estudos, prática de línguas ou acessibilidade.

## 🚀 Funcionalidades

- ✍️ Interface de tradução com seleção de idiomas (origem e destino)
- 🔁 Tradução automática usando Google Tradutor via Selenium
- 🔊 Leitura por voz com vozes naturais (ex: "Marisol" para espanhol)
- 💡 Animação de carregamento durante a tradução
- 📦 Interface gráfica feita com JavaFX
- 🌐 Projeto totalmente local — **não depende de APIs externas**

## 🛠️ Tecnologias Utilizadas

- **Java 17+**
- **JavaFX**
- **Selenium WebDriver**
- **Google Chrome for Testing** (automatizado com ChromeDriver)
- `say` (comando nativo do macOS para leitura de voz)

## 🧱 Estrutura do Projeto

EasyLang/
├── src/ # Códigos-fonte do Java
├── resources/assets/ # Imagens e ícones
├── libs/ # Bibliotecas (JavaFX, Selenium, etc.)
├── .gitignore
└── README.md # (Você está aqui)


## ⚙️ Requisitos

- Java instalado (Java 17 ou superior)
- ChromeDriver compatível instalado no sistema
- macOS (uso do comando `say` para leitura de voz)
- Maven ou Gradle (opcional, se quiser automatizar o build)

## 🔧 Como rodar

1. Clone o repositório:
   ```bash
   git clone https://github.com/LuidgiVarela/EasyLang.git
   cd EasyLang

2. Compile e execute o projeto:
   - Via terminal ou usando uma IDE como IntelliJ IDEA ou VSCode

3. Use a interface para:
   - Digitar o texto
   - Escolher os idiomas
   - Traduzir
   - Ouvir a tradução com voz natural

## 🎯 Objetivo

Este projeto foi criado com fins educativos e práticos, com foco em:

- Aprender sobre JavaFX e automação com Selenium
- Explorar leitura de voz no macOS
- Criar uma experiência fluida e bonita para tradução


## 📄 Licença

Este projeto está sob a licença MIT. Sinta-se à vontade para usar, modificar e contribuir.

---

Feito por [Luidgi Varela](https://github.com/LuidgiVarela)



