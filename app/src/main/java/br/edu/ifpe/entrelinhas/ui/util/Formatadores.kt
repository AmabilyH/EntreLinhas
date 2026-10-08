package br.edu.ifpe.entrelinhas.ui.util

import java.text.NumberFormat
import java.util.Locale

// Dinheiro é guardado em centavos: 1800 vira "R$ 18,00"
fun formatarReais(centavos: Long): String =
    NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR")).format(centavos / 100.0)

// Texto digitado pelo usuário ("8,50") vira centavos (850). Devolve null se não for número.
fun reaisParaCentavos(texto: String): Long? =
    texto.trim().replace(",", ".").toBigDecimalOrNull()?.movePointRight(2)?.toLong()

// 20.0 vira "20" e 2.5 vira "2,50"
fun formatarQuantidade(valor: Double): String =
    if (valor % 1.0 == 0.0) {
        valor.toLong().toString()
    } else {
        String.format(Locale.forLanguageTag("pt-BR"), "%.2f", valor)
    }