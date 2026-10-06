# 📄 PRD — Documento de Requisitos do Produto

| | |
|---|---|
| **App** | EntreLinhas |
| **Grupo** | |
| **Autores** | Amabily, Analy, Fábio Matheus |
| **Versão do documento** | 1.0 |
| **Última atualização** | 06/10/2026 |
| **Status** | (x) Rascunho ( ) Em revisão ( ) Aprovado |

---

## 1. Visão do produto

**Pitch:** O **EntreLinhas** ajuda a Suely, que vende produtos personalizados e crochê por encomenda, a organizar pedidos, estoque e faturamento sem precisar de vários cadernos e de fazer as contas de cabeça.

**Problema:** A Suely vende broches, ímãs, chaveiros, canecas, papelaria e crochê por encomenda. Hoje ela anota tudo em cadernos e agendas diferentes, então informações se perdem. Ela não sabe quanto material ainda tem e só percebe que acabou na hora de produzir. As contas de gasto e lucro são feitas de cabeça, na correria.

**Por que vale a pena fazer isso:** Com tudo em um só lugar, a Suely deixa de perder pedidos, sabe o que falta comprar antes de acabar e vê quanto ganhou, gastou e lucrou, sem precisar fazer conta.

---

## 2. Público e cenário de uso

**Usuário-alvo:** Suely, dona do ateliê. O app é de uso pessoal, no celular, todos os dias, sem login.

**História de uso:**
> "São 19h, a Suely acabou de receber, pelo WhatsApp, um pedido do Fernando: 5 broches de 32 mm e 6 ímãs de 5 cm. Ela abre o EntreLinhas, toca em *Novo pedido*, escolhe o cliente, a categoria e o produto de cada item e confirma. Em menos de 30 segundos, o pedido aparece na lista, o estoque de botões e de folha adesiva já foi descontado e o faturamento do mês está atualizado."

---

## 3. Objetivos e não-objetivos

**Objetivos desta versão (v1.0):**

1. Cadastrar pedidos escolhendo os produtos em uma lista, sem digitar o que o cliente pediu.
2. Controlar o estoque de materiais, com desconto automático a cada pedido e aviso quando estiver acabando.
3. Mostrar ganho, gasto e lucro, com gráficos mensal, anual e de todo o tempo de uso.

**Não-objetivos (fora do escopo):**

- ❌ Login, cadastro de usuário e senha (o app é de uso pessoal).
- ❌ Sincronização com a nuvem e notificações push (o aviso de estoque baixo aparece só dentro do app).
- ❌ Pagamento real (o app só marca se o pedido foi pago) e custo de mão de obra.

---

## 4. Requisitos funcionais

Prioridade: **Must** (sem isso não entrega), **Should** (importante), **Could** (se sobrar tempo).

| ID | História de usuário | Critério de aceite | Prioridade |
|---|---|---|---|
| RF01 | Como Suely, quero ver a lista de pedidos para saber o que falta fazer e entregar. | Ao abrir o app, aparecem os pedidos com cliente, itens, valor total, pago/não pago, para fazer/entregue e data. O topo mostra quantos pedidos estão abertos (não entregues). Sem pedidos, aparece "Nenhum pedido por aqui ainda. Toque em 'Novo pedido' para anotar o primeiro." | Must |
| RF02 | Como Suely, quero cadastrar um pedido escolhendo os produtos em uma lista para não precisar escrever o que o cliente pediu. | Em *Novo pedido*, ela informa o nome de quem pediu e a data (hoje por padrão) e, em cada item, escolhe categoria, produto e quantidade (padrão 1). O valor do item é o preço do produto × quantidade e o total é a soma dos itens. Ao salvar, o pedido aparece na lista. | Must |
| RF03 | Como Suely, quero informar o preço na hora nos itens de crochê, porque cada peça tem um valor diferente. | Em categorias marcadas como "preço combinado", o item pede uma descrição (ex.: "Tapete") e o preço digitado. O total considera esse valor. | Must |
| RF04 | Como Suely, quero marcar se o pedido foi pago e se está para fazer ou entregue para acompanhar cada encomenda. | Todo pedido novo começa como "não pago" e "para fazer". Ela alterna pago/não pago e para fazer/entregue em um toque, e o faturamento é atualizado na hora. | Must |
| RF05 | Como Suely, quero pesquisar pedidos por nome ou data para achar um pedido quando não lembro o nome. | A busca filtra a lista por parte do nome ou por data. Sem resultados, aparece "Nenhum pedido encontrado". | Must |
| RF06 | Como Suely, quero cadastrar categorias e produtos com preço fixo para escolher da lista sem mexer no código. | O app já traz as categorias que ela vende. Ela cria uma categoria (marcando se tem preço combinado) e adiciona produtos com nome e preço. Os novos itens aparecem na escolha do pedido. | Must |
| RF07 | Como Suely, quero dizer que um produto usa um ou mais materiais para o estoque ser descontado certo. | No cadastro do produto, ela adiciona um ou mais materiais do estoque e informa a quantidade usada por unidade de cada um. | Must |
| RF08 | Como Suely, quero cadastrar meus materiais por tipo para achar rápido o que tenho. | Ela cadastra nome, tipo (existente ou novo), unidade, quantidade, "avisar em" e custo por unidade. A tela de estoque agrupa por tipo e tem busca por material ou tipo. | Must |
| RF09 | Como Suely, quero registrar uma nova compra de material para aumentar o estoque e contar o gasto. | Em *Nova compra*, ela informa a quantidade e quanto custou. A quantidade soma ao estoque e o valor entra no gasto do mês da compra. | Must |
| RF10 | Como Suely, quero que o estoque diminua sozinho quando salvo um pedido. | Ao salvar, para cada item o app subtrai (quantidade do item × quantidade por unidade) de cada material do produto. Exemplo: 5 broches que usam 1 botão cada tiram 5 botões do estoque. | Must |
| RF11 | Como Suely, quero ser avisada quando um material estiver acabando para comprar mais a tempo. | Quando a quantidade fica igual ou abaixo de "avisar em", o material ganha a marca "Comprar mais" e a tela inicial mostra o aviso "Estoque baixo" com o nome do material. | Must |
| RF12 | Como Suely, quero ver na tela inicial o faturamento do mês para saber como estou indo. | O resumo mostra ganho, gasto, lucro e % de lucro do mês atual. Ganho é a soma dos pedidos pagos, gasto é a soma das compras de material e lucro é ganho menos gasto. A % de lucro é lucro ÷ ganho (0,0% se não houver ganho). | Must |
| RF13 | Como Suely, quero uma tela de detalhamento do faturamento para ver a evolução do ateliê. | Ao tocar no resumo, abre a tela com três visões: **Mensal** (gráfico dos meses do ano escolhido), **Anual** (um grupo por ano) e **Todo o período** (total desde o primeiro registro). Cada gráfico compara ganho e gasto, e os números de lucro e % de lucro aparecem abaixo. | Must |
| RF14 | Como Suely, quero salvar clientes para repetir pedidos sem digitar de novo. | Ao marcar "Salvar esta pessoa para próximos pedidos", o cliente (nome e telefone opcional) fica salvo e pode ser escolhido em novos pedidos. Dois clientes com o mesmo nome podem existir, e o telefone ajuda a diferenciar. | Should |
| RF15 | Como Suely, quero guardar o link de onde compro cada material para acessar rápido. | O material pode ter um link da loja. Ao tocar em "onde comprar", o link abre no navegador. | Should |
| RF16 | Como Suely, quero excluir um pedido registrado errado. | Ao excluir e confirmar, o pedido some da lista, os materiais voltam ao estoque e o faturamento é recalculado. | Should |
| RF17 | Como Suely, quero editar ou excluir produtos, categorias e materiais. | Cada item tem as opções editar e excluir, com confirmação. Pedidos antigos continuam mostrando o nome e o preço que tinham. | Should |
| RF18 | Como Suely, quero editar um pedido já salvo. | Ela altera itens, cliente e observação, e o estoque e o faturamento são ajustados. | Could |

---

## 5. Requisitos não funcionais

| ID | Requisito | Como será verificado |
|---|---|---|
| RNF01 | O app não pode fechar sozinho durante o uso normal | 5 minutos de uso contínuo sem crash, em 2 celulares diferentes |
| RNF02 | Toda operação que pode falhar está dentro de `try/catch` | Revisão do código: banco e entradas do usuário |
| RNF03 | Nenhuma falha mostra tela branca ou fecha o app — sempre há mensagem ao usuário | Testes de falha da seção 9 |
| RNF04 | O app roda a partir do Android 7.0 (minSdk 24) | Instalação em dispositivo real |
| RNF05 | Textos visíveis ficam em `strings.xml`, não escritos direto no código | Revisão do código |
| RNF06 | Todo arquivo do pacote do app tem comentário de fronteira escrito pelo grupo | Revisão do código |
| RNF07 | Qualquer integrante consegue localizar e alterar qualquer parte do app | Teste de mudança ao vivo (rubrica) |
| RNF08 | O app funciona 100% sem internet (só abrir o link "onde comprar" exige conexão) | Teste em modo avião: todas as funções funcionam |
| RNF09 | A lista de pedidos abre em até 2 segundos com 100 pedidos cadastrados | Teste com 100 pedidos de exemplo |
| RNF10 | Os valores em dinheiro são mostrados em reais, com duas casas decimais (ex.: R$ 18,00) | Revisão das telas |
| RNF11 | Os dados continuam salvos depois de fechar e abrir o app | Teste T8 da seção 11 |

---

## 6. Telas e navegação

**Mapa de navegação:** barra inferior com três abas: **Pedidos**, **Estoque** e **Produtos**.

```
[Pedidos — tela principal]
      │
      ├── toca em "Novo pedido"          → [Novo pedido]
      ├── toca no resumo do faturamento  → [Faturamento — detalhamento]
      ├── digita na busca                → lista filtrada por nome ou data
      └── (lista vazia)                  → "Nenhum pedido por aqui ainda..."

[Estoque]
      ├── toca em "+ Material"           → [Novo material]
      ├── toca em "+ nova compra"        → [Nova compra]
      └── toca em "onde comprar"         → abre o link no navegador

[Produtos]
      ├── toca em "Nova categoria"       → cria categoria
      └── toca em "+ produto"            → [Novo produto, com materiais]
```

| Tela | O que mostra | Ações disponíveis |
|---|---|---|
| Pedidos (principal) | Resumo do faturamento do mês, aviso de estoque baixo, busca e a lista de pedidos | Novo pedido, buscar, marcar pago/não pago, marcar para fazer/entregue, excluir, abrir o faturamento |
| Novo pedido | Cliente, telefone, data, itens (categoria, produto, quantidade, preço), observação e total | Adicionar item, remover item, salvar cliente, salvar pedido |
| Faturamento | Gráficos de ganho e gasto nas visões Mensal, Anual e Todo o período, com lucro e % de lucro | Trocar de visão, escolher o ano, voltar |
| Estoque | Materiais agrupados por tipo, com quantidade, "avisar em", custo e marca "Comprar mais" | Buscar, novo material, nova compra, onde comprar |
| Novo material / Nova compra | Nome, tipo, unidade, quantidade, avisar em, custo, link / quantidade e valor pago | Salvar |
| Produtos | Categorias com seus produtos e preços | Nova categoria, novo produto, editar, excluir |
| Novo produto | Nome, preço e lista de materiais usados com a quantidade por unidade | Adicionar material, remover material, salvar |

**Rascunhos das telas:** as imagens ficam em `docs/telas/`.

- `docs/telas/01-pedidos.png`
- `docs/telas/02-novo-pedido.png`
- `docs/telas/03-produtos.png`
- `docs/telas/04-novo-produto.png`
- `docs/telas/05-estoque.png`
- `docs/telas/06-novo-material.png`
- `docs/telas/07-faturamento.png` _(ainda a desenhar)_

---

## 7. Dados

### Opção A (Room)

**Entidade principal:** `Pedido`

Valores em dinheiro são guardados em centavos (`Long`) para evitar erros de arredondamento.

**`Pedido`**

| Campo | Tipo | Obrigatório | Observação |
|---|---|---|---|
| `id` | Long | sim | chave primária, autogerada |
| `clienteId` | Long | não | liga ao cliente salvo, se houver |
| `nomeCliente` | String | sim | nome de quem pediu |
| `data` | Long | sim | data do pedido |
| `observacao` | String | não | detalhes do pedido |
| `pago` | Boolean | sim | começa como `false` |
| `entregue` | Boolean | sim | começa como `false` (para fazer) |
| `total` | Long | sim | soma dos itens, em centavos |

**`ItemPedido`**

| Campo | Tipo | Obrigatório | Observação |
|---|---|---|---|
| `id` | Long | sim | chave primária, autogerada |
| `pedidoId` | Long | sim | liga ao pedido |
| `produtoId` | Long | não | vazio nos itens de preço combinado |
| `descricao` | String | sim | nome do produto ou texto digitado (ex.: "Tapete") |
| `quantidade` | Int | sim | padrão 1 |
| `precoUnitario` | Long | sim | em centavos |

**`Cliente`:** `id`, `nome` (obrigatório), `telefone` (opcional).

**`Categoria`:** `id`, `nome`, `precoCombinado` (Boolean: o preço é digitado na hora).

**`Produto`:** `id`, `categoriaId`, `nome`, `preco` (centavos; vazio quando a categoria é de preço combinado).

**`Material`:** `id`, `nome`, `tipoId`, `unidade`, `quantidade`, `avisarEm`, `custoUnidade` (centavos), `linkLoja` (opcional).

**`TipoMaterial`:** `id`, `nome`.

**`ProdutoMaterial`** (um produto pode usar vários materiais): `produtoId`, `materialId`, `quantidadePorUnidade`.

**`CompraMaterial`:** `id`, `materialId`, `quantidade`, `valorPago` (centavos), `data`.

**Regras dos dados:**

- O cadastro de um material não conta como gasto. Só a *nova compra* entra no gasto, porque a Suely também cadastra materiais que já tinha.
- O ganho de um mês é a soma dos pedidos pagos com data naquele mês. O gasto é a soma das compras com data naquele mês.

**Operações necessárias:** (x) inserir (x) listar (x) atualizar (x) excluir

---

## 8. Arquitetura e tecnologias

| Item | Escolha |
|---|---|
| Linguagem | Kotlin |
| Interface | (x) Jetpack Compose ( ) XML/Views |
| Persistência | (x) Room ( ) — |
| Rede | ( ) Retrofit (x) — (o app não usa internet) |
| Outras bibliotecas | Material 3, Navigation Compose, Coroutines/Flow, KSP. Gráficos desenhados com o `Canvas` do Compose (a confirmar) |
| `minSdk` / `targetSdk` | 24 / 35 |

**Organização de pastas do projeto:**

```
app/src/main/java/br/edu/ifpe/entrelinhas/
├── ui/
│   ├── theme/         # cores, tipografia e tema
│   ├── navigation/    # rotas e NavGraph
│   └── features/      # telas (pedidos, estoque, produtos, faturamento)
├── data/
│   ├── local/         # Room (entidades, DAOs, database)
│   └── repository/    # acesso aos dados
├── model/             # modelos usados pelas telas
└── MainActivity.kt
```

---

## 9. Tratamento de erros

| Situação de falha | O que o app faz | Mensagem para o usuário |
|---|---|---|
| Lista de pedidos vazia | Mostra a tela com o aviso no lugar da lista | "Nenhum pedido por aqui ainda. Toque em 'Novo pedido' para anotar o primeiro." |
| Busca sem resultado | Mostra a lista vazia com o aviso | "Nenhum pedido encontrado." |
| Nome de quem pediu em branco | Não salva e mantém o formulário preenchido | "Escreva o nome de quem pediu." |
| Pedido sem nenhum item, ou item sem produto escolhido | Não salva | "Escolha um produto da lista." |
| Categoria, produto ou material com nome em branco | Não salva | "Dê um nome à categoria." / "Dê um nome ao produto." / "Dê um nome ao material." |
| Preço ou quantidade inválidos (vazios, zero ou negativos) | Não salva | "Informe um valor válido." |
| Estoque insuficiente para o pedido | Pede confirmação; se ela confirmar, salva e o estoque do material fica em 0 | "Estoque insuficiente de [material]. Salvar mesmo assim?" |
| Erro ao salvar no banco | Cancela a operação inteira, sem salvar pela metade, e mantém a tela | "Não foi possível salvar. Tente novamente." |
| Erro ao ler os dados do banco | Mostra a tela vazia com opção de tentar de novo | "Não foi possível carregar os dados. Tente novamente." |
| Link de "onde comprar" inválido ou sem navegador | Não abre nada e mantém a tela | "Não foi possível abrir o link." |
| Excluir item usado em pedidos antigos | Pede confirmação e preserva o histórico dos pedidos | "Tem certeza? Os pedidos antigos continuam salvos." |

---

## 10. Identidade visual e publicação

| Item | Definição | Onde fica |
|---|---|---|
| Nome do app | EntreLinhas | `strings.xml` |
| Cor principal | `#8362a6` | `Color.kt` |
| Cor secundária | _a definir pelo grupo_ | `Color.kt` |
| Ícone 512×512 | Tesoura e agulha com linha, em traço branco, sobre degradê de roxo para rosa | `loja/icone-512.png` |
| `applicationId` | `br.edu.ifpe.entrelinhas` | `build.gradle.kts` |
| `versionName` / `versionCode` | `1.0` / `1` | `build.gradle.kts` |

**Material da loja** (Etapa 4 do projeto):

| Artefato | Limite | Conteúdo |
|---|---|---|
| Título | 30 caracteres | EntreLinhas |
| Descrição curta | 80 caracteres | Pedidos, estoque e faturamento do seu ateliê, sem internet e sem login. |
| Descrição completa | — | escrever em `loja/descricao.md` |
| Imagem de destaque | 1024×500 | `loja/destaque-1024x500.png` |
| Screenshots | mín. 2 | `loja/screenshots/` |
| Esboço de privacidade | — | `loja/privacidade.md` — os dados (pedidos, nomes e telefones de clientes) ficam só no celular e não saem dele. O app não tem login nem envia nada para a internet. |
| Arquivo `.aab` | — | `loja/app-release.aab` |

---

## 11. Plano de testes

| # | O que testar | Passos | Resultado esperado | OK? |
|---|---|---|---|---|
| T1 | Abrir o app pela primeira vez | Instalar e abrir | Tela de pedidos aparece com a mensagem de lista vazia | |
| T2 | Ação principal: cadastrar pedido | Novo pedido, nome, 2 itens da lista, salvar | Pedido aparece na lista com o total correto | |
| T3 | Preço combinado | Adicionar um item de crochê com descrição e preço digitados | O total soma o valor digitado | |
| T4 | Desconto do estoque | Cadastrar produto com 1 material, estoque de 100, pedir 5 | Estoque vai para 95 | |
| T5 | Produto com 2 materiais | Cadastrar produto com 2 materiais e pedir 3 unidades | Os dois materiais são descontados na quantidade certa | |
| T6 | Estoque baixo | Baixar o estoque até o valor de "avisar em" | Aparece "Comprar mais" no material e o aviso na tela de pedidos | |
| T7 | Faturamento | Marcar um pedido como pago e registrar uma compra | Ganho, gasto, lucro e % mudam no resumo e nos gráficos | |
| T8 | Reabrir o app | Fechar e abrir de novo | Pedidos, estoque e produtos continuam lá | |
| T9 | Falha ao salvar | Tentar salvar pedido sem nome e sem item | Mensagem clara, app não fecha | |
| T10 | Uso sem internet | Ativar modo avião e repetir T2 | Tudo funciona normalmente | |
| T11 | Teste com usuário externo | Pessoa de fora usa sem explicação | Consegue cadastrar um pedido sozinha | |

**Testado em:** _(modelo do celular e versão do Android — pelo menos 2 aparelhos)_

---

## 12. Cronograma

| Marco | Prazo | Responsável | Status |
|---|---|---|---|
| M1 — Canvas + repositório | 16/09 | grupo | ✅ |
| M2 — PRD aprovado + telas | 30/09 | grupo | ⏳ |
| M3 — Funcionalidade base | 21/10 | | ⏳ |
| M4 — Dados e erros tratados | 11/11 | | ⏳ |
| M5 — Identidade + `.apk` testado | 25/11 | | ⏳ |
| M6 — `.aab` + loja + README | 02/12 | | ⏳ |
| **Entrega e apresentação** | **10/12** | grupo | ⏳ |

---

## 13. Riscos

| Risco | Impacto | Plano B |
|---|---|---|
| Os gráficos do faturamento ficarem complicados demais | Médio | Mostrar barras simples por mês e por ano, e só depois o gráfico de todo o período |
| O desconto do estoque errar quando um produto usa vários materiais | Alto | Testar com exemplos reais da Suely (T4 e T5) e, se preciso, começar com 1 material por produto |
| Conflitos de código entre as branches | Médio | Branches pequenas, `git pull` antes de começar e revisão de outro membro antes do merge |
| Integrante fica sem computador | Médio | Outro integrante assume a tarefa a partir da branch já enviada com `push` |
| Prazo apertado para a quantidade de telas | Alto | Entregar primeiro as funcionalidades Must e deixar as Should e Could para o fim |

---

## 14. Como vamos orientar a implementação com IA

A implementação usa o **Gemini no Android Studio**. Este PRD é o documento que diz à IA o que construir — quanto mais preciso ele estiver, menos a IA inventa. Regras completas em [`docs/USO_DE_IA.md`](docs/USO_DE_IA.md).

**Recursos que vamos usar:** ( ) Chat ( ) Agent Mode ( ) Explain Code ( ) Ask Gemini no Logcat ( ) Generate Unit Tests ( ) Transform UI

**Regras que colocamos no `AGENTS.md`** _(resumo — o arquivo fica na raiz do repositório)_:

- Usar sempre Kotlin, Jetpack Compose e Room, e manter a estrutura de pacotes do projeto.
- Fazer uma funcionalidade por vez, em passos pequenos, explicando o que mudou em cada arquivo.
- Não criar telas, dependências ou funcionalidades que não estejam no Canvas e no PRD.

**Divisão do perímetro explicável** — quem responde por explicar o quê na apresentação:

| Parte do código | Responsável |
|---|---|
| Telas (`ui/`) | |
| Dados (`data/`) | |
| Identidade visual e recursos | |
| Build e artefatos de loja | |

**Decisões que o grupo tomou contra a sugestão da IA** _(preencher ao longo do projeto — isso conta a favor na avaliação)_:

-

---

## 15. Histórico de versões deste documento

| Versão | Data | Autor | O que mudou |
|---|---|---|---|
| 1.0 | 06/10/2026 | Amabily, Analy, Fábio Matheus | Versão inicial |
| | | | |
