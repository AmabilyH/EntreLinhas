# EntreLinhas

App Android para ajudar a Suely, do Ateliê da Suuh, a organizar as encomendas, o estoque e o faturamento.

> Projeto final do Módulo 03 · 3º ano A (Ensino Médio) · Entrega: 10/12/2026

## O problema

A Suely vende lingerie, bottoms, chaveiros, ímãs, papelaria e crochê por encomenda. Hoje ela anota tudo em vários cadernos e agendas, e as informações acabam se perdendo. Ela também não tem como saber o que ainda tem de material, e as contas de pagamento e faturamento são feitas de cabeça, o que aumenta o risco de erro.

## Nossa solução (MVP)

Um app de uso pessoal, sem login e sem internet, direto no celular. Ele terá poucas telas:

- **Pedidos:** a Suely cadastra o pedido escolhendo o produto ou serviço em uma lista, sem precisar escrever o que o cliente pediu. O valor é calculado pela quantidade, e ela marca se está pago e se está para fazer ou entregue.
- **Produtos e serviços:** ela cadastra o que vende, com o preço, e pode adicionar itens novos sem mexer no código.
- **Estoque:** ela cadastra os materiais que compra, separados por tipo. Cada pedido desconta do estoque automaticamente, e o app avisa quando o material estiver acabando.
- **Faturamento:** na tela inicial, ela vê quanto gastou e quanto ganhou no mês.

## Quem somos

| Integrante | Responsabilidades |
|---|---|
| Amabily | |
| Analy | |
| Fábio Matheus | |

## Como trabalhamos

Cada integrante desenvolve em uma branch própria e envia com push. O código só entra na `main` (merge) depois de ser revisado e aprovado por outro membro da equipe.

## Tecnologias

Kotlin e Room (banco de dados local no celular).
