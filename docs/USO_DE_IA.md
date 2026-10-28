# Uso de IA no EntreLinhas

Este documento explica como o grupo usa a IA no projeto. A ferramenta é o **Gemini no Android Studio**. Quem dirige é o grupo; a IA digita. As regras que a IA segue estão no [`AGENTS.md`](../AGENTS.md), e o que o app deve fazer está no [`PRD.md`](../PRD.md).

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

## 3. Como trabalhamos com o Gemini

1. Um prompt pede uma coisa só (uma entidade, um DAO ou uma tela).
2. Antes de commitar, rodamos o app e lemos o código gerado.
3. Fazemos um commit por entidade, DAO ou tela, de preferência abaixo de 200 linhas.
4. Se o Gemini mexer em arquivos que não pedimos, desfazemos e pedimos de novo.
5. Outro integrante revisa o PR antes do merge.

## 4. O que a IA gerou

| Parte | O que foi gerado | Recurso | Quem pediu |
|---|---|---|---|
| Boilerplate | Projeto base, tema e navegação com 3 abas | Agent Mode | _[preencher]_ |
| Entidades de pedido | `Cliente`, `Pedido`, `ItemPedido` e `Produto`, com DAOs | Agent Mode | _[preencher]_ |
| Telas | Pedidos, Estoque e Produtos ligadas ao Room | Agent Mode | _[preencher]_ |
| Entidades do estoque | `Material`, `TipoMaterial` e `CompraMaterial`, com DAOs | _[preencher]_ | _[preencher]_ |
| `Categoria` | Entidade e DAO | _[preencher]_ | _[preencher]_ |

## 4.1 Registro de prompts

Para cada tarefa importante, anotamos o prompt usado, para que qualquer integrante possa explicar como o código foi gerado.

| Data | Quem | Tarefa | Prompt usado | O que mudamos depois |
|---|---|---|---|---|
| _[preencher]_ | _[preencher]_ | _[preencher]_ | _[preencher]_ | _[preencher]_ |

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
