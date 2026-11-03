# Uso de IA no EntreLinhas

Este documento explica como o grupo usa a IA no projeto. A ferramenta principal é o **Gemini no Android Studio**. Em tarefas pontuais usamos também o **Claude (chat)**, e cada uso aparece na tabela da seção 4 e no registro da seção 4.1, com a ferramenta real. Quem dirige é o grupo; a IA digita. As regras que a IA segue estão no [`AGENTS.md`](../AGENTS.md), e o que o app deve fazer está no [`PRD.md`](../PRD.md).

## 1. Recursos que usamos

| Recurso | Usamos? | Para quê |
|---|---|---|
| Chat | Sim | Tirar dúvidas e pedir trechos pequenos de código |
| Agent Mode | Sim | Gerar boilerplate, entidades, DAOs e telas, uma tarefa por vez |
| Explain Code | Não | — |
| Ask Gemini no Logcat | Não | — |
| Generate Unit Tests | Não | — |
| Transform UI | Não | — |

## 2. Regras que seguimos

Estas regras estão no `AGENTS.md` e valem para todo o grupo:

- Usar sempre Kotlin, Jetpack Compose e Room, mantendo a estrutura de pacotes do projeto.
- Fazer uma funcionalidade por vez, em passos pequenos, explicando o que mudou em cada arquivo.
- Não criar telas, dependências ou funcionalidades que não estejam no Canvas e no PRD.
- Valores em dinheiro em centavos (`Long`) e textos visíveis em `strings.xml`.
- Nunca fazer commit direto na `main`: cada tarefa em uma branch, com merge só depois de PR aprovado por outro integrante.
- A IA **não escreve** o comentário de fronteira dos arquivos. Esse comentário é escrito pelo grupo.

## 3. Como trabalhamos com a IA

1. Um prompt pede uma coisa só (uma entidade, um DAO ou uma tela).
2. Antes de commitar, rodamos o app e lemos o código gerado.
3. Fazemos um commit por entidade, DAO ou tela, de preferência abaixo de 200 linhas.
4. Se a IA mexer em arquivos que não pedimos, desfazemos e pedimos de novo.
5. Outro integrante revisa o PR antes do merge.

## 4. O que a IA gerou

| Parte | O que foi gerado | Ferramenta / recurso | Quem pediu |
|---|---|---|---|
| Boilerplate | Projeto base, tema e navegação com 3 abas | Gemini, Agent Mode | _[preencher]_ |
| Entidades de pedido | `Cliente`, `Pedido`, `ItemPedido` e `Produto`, com DAOs | Gemini, Agent Mode | _[preencher]_ |
| Telas | Pedidos, Estoque e Produtos ligadas ao Room | Gemini, Agent Mode | _[preencher]_ |
| Entidades do estoque | `Material`, `TipoMaterial` e `CompraMaterial`, com DAOs | _[preencher]_ | _[preencher]_ |
| `Categoria` | Entidade e DAO | _[preencher]_ | Analy |
| Tela de categorias | `CategoriasViewModel`, `CategoriaDialog` e `TelaCategorias` (listar, adicionar, editar e excluir) | Claude (chat) | Analy |

## 4.1 Registro de prompts

Para cada tarefa importante, anotamos o prompt usado, para que qualquer integrante possa explicar como o código foi gerado.

| Data | Quem | Tarefa | Prompt usado | O que mudamos depois |
|---|---|---|---|---|
| 03/11/2026 | Analy | Tela de categorias (Claude, chat) | Criar uma tela simples de categorias usando o CategoriaDao: listar, adicionar e, se der tempo, editar e excluir. Antes, olhar a tela de Produtos da Amabily, porque o PRD prevê "Nova categoria" lá. Se já existir algo parecido, integrar a tela ao que existe e não duplicar. Fazer commits pequenos, por exemplo um para a tela e outro para a navegação. Seguir o AGENTS.md: Kotlin, Compose e Room, textos em strings.xml, operações do banco em try/catch e comentário FRONTEIRA deixado para o grupo. |Até agora não houve necessidade de reajustes no codigo. |

## 5. Decisões que tomamos contra a sugestão da IA

| O que a IA sugeriu | O que o grupo decidiu | Por quê |
|---|---|---|
| Usar `Double` para os preços | Guardar os preços em centavos, em `Long` | Evitar erros de arredondamento |

_Novas decisões entram nesta tabela ao longo do projeto._

## 6. Quem explica cada parte do código

| Parte do código | Responsável |
|---|---|
| Telas (`ui/`) | Amabily |
| Dados (`data/`) | Fábio Matheus |
| Identidade visual e recursos | Analy |
| Build e artefatos de loja | Analy e Fábio Matheus |

Todos devem conseguir localizar e alterar qualquer parte do app (RNF07 do PRD). A divisão acima define apenas quem explica cada parte na apresentação.
