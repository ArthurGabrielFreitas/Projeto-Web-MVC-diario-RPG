# 🏰 Registro de Sessões de RPG – Tormenta20

Aplicação web em desenvolvimento utilizando **Spring Boot**, **Spring MVC**, **Thymeleaf**, **Bootstrap** e **H2 Database** para registrar e organizar informações de campanhas de RPG no sistema **Tormenta20**. A aplicação tem como objetivo armazenar personagens, ameaças, encontros e sessões, criando um histórico visual e persistido da campanha.

> 🔥 O projeto segue como referência de estrutura, padrões e práticas o repositório: **Torresmo** (por @angoti).

---

## 🚀 Tecnologias Utilizadas

| Tecnologia      | Uso                                          |
| --------------- | -------------------------------------------- |
| Spring Boot     | Base da aplicação e gestão das dependências  |
| Spring MVC      | Estruturação do padrão Model-View-Controller |
| Thymeleaf       | Templates HTML integrados com o backend      |
| Bootstrap       | Estilização responsiva da interface          |
| H2 Database     | Banco em memória para desenvolvimento        |
| JPA / Hibernate | Mapeamento e persistência das entidades      |

---

## 🛠️ Funcionalidades Planejadas

✔ Registro de personagens e ameaças

✔ Registro de encontros e sessões

✔ Histórico de cenas e eventos dentro da sessão

🔧 Relacionamentos entre personagens e cenas (ex.: presença, ação, condição)

🔧 Interface visual no estilo wiki modular

🔧 Filtragens, buscas e consultas por campanha

---

## 📁 Estrutura Geral do Projeto (prevista)

```
📦 src
 ┣ 📂 main
 ┃ ┣ 📂 java
 ┃ ┃ ┗ 📂 br.com.seuprojeto
 ┃ ┃ ┃ ┣ 📂 controller
 ┃ ┃ ┃ ┣ 📂 model
 ┃ ┃ ┃ ┣ 📂 repository
 ┃ ┃ ┃ ┣ 📂 service
 ┃ ┃ ┃ ┗ Application.java
 ┃ ┣ 📂 resources
 ┃ ┃ ┣ application.properties
 ┃ ┃ ┣ 📂 static
 ┃ ┃ ┣ 📂 templates
 ┃ ┃ ┃ ┗ index.html
 ┗ 📂 test
```

---

## ▶️ Como Executar o Projeto

1. **Clone o repositório:**

```bash
git clone https://github.com/SEU-USUARIO/NOME-DO-PROJETO.git
```

2. **Acesse o diretório:**

```bash
cd NOME-DO-PROJETO
```

3. **Execute o projeto com Maven ou pela sua IDE:**

```bash
./mvnw spring-boot:run
```

4. **Abra no navegador:**

```
http://localhost:8080
```

---

## 🧪 Banco de Dados H2

Após iniciar o projeto, acesse:

```
http://localhost:8080/h2-console
```

Certifique‑se de que a configuração corresponde ao `application.properties`.

---

## 📌 Status do Projeto

🔨 **Em Desenvolvimento**

## 📝 Observações recentes (edição de Encontros)

- Nota importante sobre a edição de `Encontro` via formulário Thymeleaf: o formulário atual usa binding indexado (ex.: `participacoes[0].participa`, `participacoes[1].personagem.id`). Isso funciona, mas é frágil — requer que a ordem e o índice das participações na view coincidam exatamente com a lista no objeto enviado ao servidor. Se a ordem mudar entre abrir e submeter o formulário, os dados podem não mapear corretamente.

Recomendação: para tornar o fluxo robusto recomendamos uma refatoração para binding por id (enviar parâmetros nomeados por id ou um payload JSON) em vez de depender de índices. Posso implementar essa refatoração se desejar (opção de maior trabalho, mas mais segura).

## 🔬 Dados de teste adicionados

O carregador de dados (`DataLoader`) foi estendido localmente para incluir exemplos adicionais: uma sessão extra, novos encontros, mais personagens e mais ameaças — úteis para testar fluxos de edição, listagem e pesquisa.
---

## 📜 Licença

Este projeto poderá ser distribuído sob uma licença livre (a definir).

---

📣 Caso queira contribuir com ideias ou organização estrutural, sinta-se à vontade!
