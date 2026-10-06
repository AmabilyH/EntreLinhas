# AGENTS.md — Regras para a IA no projeto EntreLinhas

Este arquivo diz à IA (Gemini no Android Studio) como trabalhar neste projeto. Quem dirige é o grupo; a IA digita. Antes de qualquer tarefa, leia o [`CANVAS.md`](CANVAS.md) e o [`PRD.md`](PRD.md).

## O projeto

EntreLinhas é um app Android de uso pessoal para a Suely organizar **pedidos, estoque e faturamento** do seu ateliê (broches, ímãs, chaveiros, canecas, papelaria e crochê). Funciona 100% no celular, sem internet e sem login.

## As três regras principais

1. Use sempre **Kotlin, Jetpack Compose e Room**, e mantenha a estrutura de pacotes abaixo.
2. Faça **uma funcionalidade por vez**, em passos pequenos, explicando o que mudou em cada arquivo.
3. **Não crie** telas, dependências ou funcionalidades que não estejam no Canvas e no PRD.

## Tecnologias

- Linguagem: Kotlin.
- Interface: Jetpack Compose com Material 3 e Navigation Compose.
- Dados: Room (com KSP), Coroutines e Flow.
- `minSdk` 24, `targetSdk` 35, `applicationId` `br.edu.ifpe.entrelinhas`.
- **Não use** Retrofit, Hilt, Koin, login, nuvem, notificações push nem bibliotecas novas sem o grupo pedir.

## Estrutura de pacotes (`br.edu.ifpe.entrelinhas`)

- `data/local/`: entidades, DAOs e o banco Room.
- `data/repository/`: acesso aos dados.
- `model/`: modelos usados pelas telas.
- `ui/theme/`: cores, tipografia e tema.
- `ui/navigation/`: `NavTarget` e `NavGraph`.
- `ui/features/`: uma pasta por área (pedidos, estoque, produtos, faturamento).

## Como trabalhar

- Antes de mudar código, diga o que pretende alterar e em quais arquivos.
- Mude o mínimo possível: não reescreva nem renomeie arquivos que não fazem parte da tarefa.
- Explique cada mudança em linguagem simples, para que todos do grupo consigam entender.
- Se o pedido estiver confuso ou conflitar com o PRD, **pergunte** em vez de adivinhar.
- Não aceite nem aplique mudanças grandes de uma vez: prefira blocos pequenos que dê para revisar inteiros.

## Regras do código

- Todo texto visível ao usuário fica em `strings.xml`, nunca escrito direto no código.
- Toda operação que pode falhar (banco, campos do formulário) fica em `try/catch` e mostra uma mensagem clara. O app nunca fecha nem mostra tela branca.
- Use as mensagens de erro da seção 9 do PRD.
- Valores em dinheiro são guardados em **centavos** (`Long`) e mostrados em reais, como `R$ 18,00`.
- Nomes de entidades e campos em português, como no PRD (`Pedido`, `ItemPedido`, `Material`, `ProdutoMaterial`, `CompraMaterial`).
- A cor principal é `#8362a6`, definida em `Color.kt`.
- Não coloque dados de exemplo fixos no código; as telas mostram os dados do banco.

## Regras de negócio que não podem ser quebradas

- Ao salvar um pedido, o estoque de cada material do produto diminui: quantidade do item × quantidade por unidade.
- Ganho = soma dos pedidos **pagos**. Gasto = soma das **compras** de material. Lucro = ganho − gasto. A % de lucro é lucro ÷ ganho (0,0% se não houver ganho).
- Cadastrar um material **não** conta como gasto; só a nova compra conta.
- Categorias de preço combinado (crochê) pedem descrição e preço digitados no item.

## Comentário de fronteira

Todo arquivo do pacote do app tem um comentário no topo, **escrito pelo grupo**, dizendo o que o arquivo faz e até onde vai a sua responsabilidade. A IA **não escreve esse comentário**. Se criar um arquivo novo, deixe só a linha `// FRONTEIRA: a ser escrito pelo grupo`.

## Segurança e arquivos

- Nunca coloque senha, chave ou dado pessoal no código nem em prompts.
- Não leia nem altere `local.properties`, arquivos de chave de assinatura, `build/` e `.gradle/`.
- Não mexa em `CANVAS.md`, `PRD.md`, `RUBRICA.md` e `docs/` sem o grupo pedir.

## Git

- Nunca faça commit direto na `main`: cada tarefa fica em uma branch própria (`feat/`, `fix/`, `docs/` ou `chore/`).
- Commits pequenos, com mensagem em português que diz o que foi feito, por exemplo `feat: salvar pedido no Room`.
- O merge na `main` só acontece depois de um pull request aprovado por outro integrante.
