# 🎯 Canvas do Projeto Final — App Android

> **Como usar:** este é o primeiro documento do projeto. Preencha em grupo, em uma única aula, **antes de escrever qualquer linha de código**. Cada bloco tem no máximo 5 linhas — se não couber, o projeto está grande demais.
> Depois de preenchido e validado pelo professor, ele vira a base do [`PRD.md`](PRD.md).

| | |
|---|---|
| **Grupo nº** | |
| **Integrantes (3 a 4)** | Amabily, Analy, Fábio Matheus |
| **Turma** | 3º ano — Ensino Médio |
| **Repositório** | `https://github.com/AmabilyH/entrelinhas` |
| **Data de preenchimento** | 06/10/2026 |
| **Entrega final** | **10/12/2026** |

---

## 🧩 Bloco 1 — Nome e pitch do app

**Nome do app:** EntreLinhas

**Pitch em uma frase:**
> "O **EntreLinhas** ajuda **a Suely, que vende produtos personalizados e crochê por encomenda,** a **organizar pedidos, estoque e faturamento** sem precisar de **vários cadernos e de fazer as contas de cabeça**."

---

## 😖 Bloco 2 — Problema

Qual dor real vocês estão resolvendo? Descrevam uma situação concreta que alguém vive hoje.

- A Suely vende broches, ímãs, chaveiros, canecas, papelaria e crochê por encomenda e anota tudo em cadernos e agendas diferentes, então informações se perdem.
- Ela não sabe quanto material ainda tem, só percebe que acabou na hora de produzir, e faz as contas de gasto e lucro de cabeça, na correria.

**Como esse problema é resolvido hoje (sem o app)?**

- Cadernos e agendas de papel, memória e contas feitas de cabeça, sem calculadora por perto.

---

## 👥 Bloco 3 — Público-alvo

Para quem é o app? Sejam específicos (idade, contexto, com que frequência usariam).

- **Perfil principal:** Suely, dona do ateliê, uso pessoal (não precisa de login).
- **Quando/onde usam:** todo dia, no celular, ao receber um pedido, comprar material ou entregar uma encomenda.
- **Uma pessoa real que testaria o app:** Suely, mãe da Amabily.

---

## 💡 Bloco 4 — Solução em uma tela

Descreva o que a **tela principal** mostra e o que o usuário consegue fazer nela.

- **A tela principal lista:** os pedidos (cliente, itens, valor, pago/não pago, para fazer/entregue, data), com o resumo do faturamento do mês no topo e uma busca por nome ou data.
- **A ação principal do usuário é:** cadastrar um novo pedido escolhendo categoria e produto em uma lista, sem digitar o que o cliente pediu.
- **Depois de agir, o usuário vê:** o pedido na lista, o estoque descontado e o faturamento atualizado, com uma mensagem de confirmação.

---

## ✅ Bloco 5 — Funcionalidades do MVP

Máximo de **4 funcionalidades**. Se tiver mais, corte. Lembre: *qualidade acima de complexidade*.

| # | Funcionalidade | Essencial? | Quem faz |
|---|---|---|---|
| F1 | **Pedidos:** cadastro com itens escolhidos da lista, preço combinado para crochê, marcação de pago/não pago e para fazer/entregue, busca por nome ou data, clientes salvos | Sim | |
| F2 | **Produtos e serviços:** categorias e produtos com preço fixo; cada produto pode usar **um ou mais materiais** do estoque, com a quantidade usada por unidade | Sim | |
| F3 | **Estoque:** materiais por tipo, nova compra, link de onde comprar, desconto automático a cada pedido e aviso de estoque baixo dentro do app | Sim | |
| F4 | **Faturamento:** resumo do mês na tela inicial e uma tela de detalhamento com gráficos mensal, anual e de todo o tempo de uso (ganho, gasto, lucro e % de lucro) | Sim | |

---

## 🚫 Bloco 6 — Fora do escopo

O que o app **não** vai fazer nesta entrega. Escrever isso aqui protege vocês de perder o prazo.

- ❌ Login, cadastro de usuário e senha (o app é de uso pessoal).
- ❌ Sincronização com a nuvem e notificações push (o aviso de estoque baixo aparece só dentro do app).
- ❌ Pagamento real (o app só marca se o pedido foi pago) e custo de mão de obra.

---

## ⚙️ Bloco 7 — Caminho técnico

Marque **uma** opção (as três valem a mesma nota):

- [x] **Opção A — Room:** dados salvos no próprio celular (lista de compras, agenda, diário de treino, controle financeiro)
- [ ] **Opção B — Retrofit:** dados vindos de uma API pública (notícias, filmes, feed, clima)
- [ ] **Opção C — Desafio:** API + salvar favoritos localmente

**Se escolheu B ou C — qual API?** Não se aplica.

**Bibliotecas que o grupo vai usar:** Jetpack Compose com Material 3, Navigation Compose, Room (com KSP) e Coroutines/Flow. Os gráficos do faturamento serão desenhados com o `Canvas` do próprio Compose, sem biblioteca extra (a confirmar).

**Onde entra o `try/catch`?**

- Pode falhar: salvar pedido com campo em branco ou item sem produto, material sem estoque suficiente, leitura ou gravação no banco.
- O usuário vê a mensagem: avisos claros, como "Escreva o nome de quem pediu" e "Escolha um produto da lista".

---

## 🎨 Bloco 8 — Identidade visual

| Item | Definição do grupo |
|---|---|
| Nome exibido (`strings.xml`) | EntreLinhas |
| Cor principal (hex, em `Color.kt`) | `#8362a6` |
| Ideia do ícone (512×512) | Tesoura e agulha com linha, em traço branco, sobre fundo em degradê de roxo para rosa. Lembra o trabalho manual do ateliê. |
| `applicationId` | `br.edu.ifpe.entrelinhas` |
| Versão inicial | `1.0` (versionCode `1`) |

---

## 👤 Bloco 9 — Equipe, papéis e riscos

| Integrante | Papel principal | Responsável por |
|---|---|---|
| Amabily | | |
| Analy | | |
| Fábio Matheus | | |

> Todos programam. O "papel" define quem **responde** por aquela parte, não quem trabalha sozinho.
> A divisão entre F1 a F4 e os papéis ainda será combinada em grupo, de forma justa.

**Riscos — o que pode dar errado e o plano B:**

| Risco | Plano B |
|---|---|
| Os gráficos do faturamento ficarem complicados demais | Mostrar barras simples por mês e por ano, e só depois o gráfico de todo o período |
| O desconto automático do estoque errar quando um produto usa vários materiais | Testar com exemplos reais da Suely e, se preciso, começar com 1 material por produto |
| Conflitos de código entre as branches | Branches pequenas, `git pull` antes de começar e revisão de outro membro antes do merge |

---

## 🤖 Bloco 10 — Acordo de trabalho com IA

A implementação pode ser feita com o **Gemini no Android Studio**. Vocês orientam, ele digita — e cada integrante precisa saber explicar o que entrou no projeto. Regras completas em [`docs/USO_DE_IA.md`](docs/USO_DE_IA.md).

**Três regras que vamos escrever no nosso `AGENTS.md`** _(o arquivo que diz à IA como trabalhar no nosso projeto)_:

1. Usar sempre Kotlin, Jetpack Compose e Room, e manter a estrutura de pacotes do projeto.
2. Fazer uma funcionalidade por vez, em passos pequenos, explicando o que mudou em cada arquivo.
3. Não criar telas, dependências ou funcionalidades que não estejam no Canvas e no PRD.

**Combinados do grupo:**

- [ ] Ninguém clica *Accept* no Agent Mode sem ler a mudança inteira.
- [ ] Quem aceitou o código escreve o comentário de fronteira do arquivo.
- [ ] Antes de cada marco, revisamos juntos: alguém aqui não entende alguma parte?
- [ ] Nenhuma chave de API ou senha vai para o prompt.
- Outro combinado nosso: cada um trabalha na sua branch e o merge na `main` só acontece depois de um pull request aprovado por outro membro.

**Como vamos garantir que todos entendem tudo** _(ex.: quem implementa apresenta o arquivo aos outros; revezar as partes; revisar o pull request do colega)_:

- Todo pull request é revisado por outro integrante, que precisa entender a mudança antes de aprovar.

---

## 🗓️ Bloco 11 — Marcos até 10/12

| Marco | Prazo | Como se comprova no GitHub |
|---|---|---|
| M1 — Canvas preenchido + repositório criado | 16/09 | `CANVAS.md` no `main` |
| M2 — PRD aprovado + telas rascunhadas | 30/09 | `PRD.md` + imagens em `docs/` |
| M3 — Funcionalidade base rodando | 21/10 | tela principal lista dados + 1 ação + `try/catch` |
| M4 — Dados completos (Room/Retrofit) e erros tratados | 11/11 | commits da camada de dados |
| M5 — Identidade visual + `.apk` de release testado | 25/11 | ícone, cores, `.apk` testado por 2 pessoas de fora |
| M6 — `.aab` + material de loja + `README.md` | 02/12 | pasta `loja/` + `README.md` completo |
| **Entrega e apresentação** | **10/12** | tag `v1.0` no repositório |

---

## 🏁 Bloco 12 — Definição de pronto

O grupo só considera o app pronto quando **todas** estas frases forem verdadeiras:

- [ ] O app abre e não fecha sozinho depois de 5 minutos de uso.
- [ ] A tela principal mostra dados reais (não texto de exemplo fixo no código).
- [ ] A ação principal funciona e o resultado aparece na tela.
- [ ] Quando algo falha, aparece uma mensagem clara — o app não quebra.
- [ ] O app tem nome, ícone e cor próprios (nada de ícone padrão do Android).
- [ ] Duas pessoas de fora do grupo instalaram o `.apk` e conseguiram usar sem explicação.
- [ ] O `README.md` explica o que o app faz, com o que foi feito e como gerar o build.
- [ ] O `docs/USO_DE_IA.md` e o `AGENTS.md` estão preenchidos.
- [ ] **Cada integrante consegue abrir o projeto e fazer uma mudança pequena sozinho** — trocar um texto, acrescentar um campo, mudar a ordem da lista.
- [ ] Todo arquivo nosso tem o comentário de fronteira escrito por nós.

---

## ✍️ Validação do professor

| | |
|---|---|
| Data | |
| Situação | ( ) Aprovado ( ) Aprovado com ajustes ( ) Refazer |
| Observações | |
