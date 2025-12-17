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


## 🔧 Nota técnica — Binding id-based para `Encontro`

Implementação recente (aplicada apenas aos artefatos relacionados a `Encontro`): o formulário de edição/salvamento de encontros foi refatorado do antigo "binding indexado" (ex.: `participacoes[0].personagem.id`) para um esquema id-based mais robusto.

Como funciona agora:
- A view envia duas listas de ids para participação: `personagensSelecionados` e `ameacasSelecionadas` (cada checkbox envia o id da entidade quando marcado).
- Campos auxiliares por entidade são enviados com nomes específicos por id, por exemplo:
	- `morte_personagem_{id}` — checkbox indicando morte do personagem
	- `ultimoGolpe_personagem_{id}` — checkbox indicando se aplicou o último golpe
	- `anotacoes_personagem_{id}` — campo de texto com anotações
	- equivalentes com `ameaca` no nome para ameaças
- Quando uma participação já existe no banco, o template envia também `participacaoId_personagem_{id}` ou `participacaoId_ameaca_{id}` para permitir atualização (em vez de criar um novo registro).

No servidor (`EncontroController` → `EncontroService`) o fluxo é:
1. Receber as listas de ids (`personagensSelecionados`, `ameacasSelecionadas`) e os campos por-entity via parâmetros do request.
2. Construir a lista de `ParticipacaoEncontro` a partir desses ids (preservando ids existentes quando fornecidos).
3. Resolver `Personagem` e `Ameaca` por id (via repositórios) e persistir o `Encontro` com as participações filtradas/atualizadas.

Vantagens dessa abordagem:
- Não depende da ordem/índices da lista no HTML, evitando problemas quando o DOM é reordenado ou quando o usuário adiciona/remova linhas client-side.
- Permite atualização explícita de participações existentes (preservando seus ids) e criação de novas participações quando necessário.

Como testar rapidamente:
1. Abra a edição de um encontro: `GET /encontros/editar/{id}` — o formulário deverá vir com checkboxes pré-marcados para participações existentes.
2. Marque/desmarque participantes (personagens/ameaças) e ajuste campos Morte/Último Golpe/Anotações.
3. Submeta o formulário — o servidor irá reconstruir as participações por id e persistir as alterações.

Observação: esta refatoração foi feita apenas nos arquivos relativos a `Encontro` (controller + template).

## 🔬 Dados de teste adicionados

O carregador de dados (`DataLoader`) foi estendido localmente para incluir exemplos adicionais: uma sessão extra, novos encontros, mais personagens e mais ameaças — úteis para testar fluxos de edição, listagem e pesquisa.
---

## 📜 Licença

Este projeto poderá ser distribuído sob uma licença livre (a definir).

---

📣 Caso queira contribuir com ideias ou organização estrutural, sinta-se à vontade!
