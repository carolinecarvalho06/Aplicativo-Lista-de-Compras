package com.samengo.listadecompras

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.samengo.listadecompras.ui.theme.ListadecomprasTheme

// ---------------------------------------------------------
// PRODUTO
// ---------------------------------------------------------

class Produto(
    val nome: String,
    val preco: Double,
    val quantidade: Int
) {
    fun calcularTotal(): Double {
        return preco * quantidade
    }
}

// ---------------------------------------------------------
// MAIN ACTIVITY
// ---------------------------------------------------------

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ListadecomprasTheme {
                ListaCompras()
            }
        }
    }
}

// ---------------------------------------------------------
// FORMULÁRIO (ENTRADA DE DADOS)
// ---------------------------------------------------------

@Composable
fun Formulario(
    nome: String,
    preco: String,
    quantidade: String,
    onNomeChange: (String) -> Unit,
    onPrecoChange: (String) -> Unit,
    onQuantidadeChange: (String) -> Unit,
    onCadastrar: () -> Unit
) {
    Text("Nome do Produto:")

    TextField(
        value = nome,
        onValueChange = onNomeChange,
        modifier = Modifier.fillMaxWidth()
    )

    Text("Preço:")

    TextField(
        value = preco,
        onValueChange = onPrecoChange,
        modifier = Modifier.fillMaxWidth()
    )

    Text("Quantidade:")

    TextField(
        value = quantidade,
        onValueChange = onQuantidadeChange,
        modifier = Modifier.fillMaxWidth()
    )

    Button(
        onClick = onCadastrar,
        modifier = Modifier.padding(top = 8.dp)
    ) {
        Text("Cadastrar")
    }
}

// ---------------------------------------------------------
// LISTA DE PRODUTOS (PRODUTOS CADASTRADOS)
// ---------------------------------------------------------

@Composable
fun ListaProdutos(
    produtos: List<Produto>
) {
    Text(
        "Quantidade de produtos cadastrados: ${produtos.size}",
        modifier = Modifier.padding(top = 16.dp)
    )

    for (produto in produtos) {
        Text(
            "${produto.nome} - " +
                    "${produto.quantidade} x R$ ${produto.preco} = " +
                    "R$ ${produto.calcularTotal()}"
        )
    }
}

// ---------------------------------------------------------
// RESUMO (TOTAL DA COMPRA)
// ---------------------------------------------------------

@Composable
fun Resumo(
    total: Double
) {
    Text(
        "Total da compra: R$ $total",
        modifier = Modifier.padding(top = 16.dp)
    )
}

// ---------------------------------------------------------
// LISTA DE COMPRAS (COMPOSABLE PRINCIPAL)
// ---------------------------------------------------------

@Composable
fun ListaCompras() {

    // Estado do nome
    var nome by remember {
        mutableStateOf("")
    }

    // Estado do preço
    var preco by remember {
        mutableStateOf("")
    }

    // Estado da quantidade
    var quantidade by remember {
        mutableStateOf("")
    }

    // Lista de produtos
    val produtos = remember {
        mutableStateListOf<Produto>()
    }

    // Total da compra
    var total by remember {
        mutableStateOf(0.0)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text("Lista de Compras")

        // 1. Formulário (Entrada de Dados)
        Formulario(
            nome = nome,
            preco = preco,
            quantidade = quantidade,
            onNomeChange = { nome = it },
            onPrecoChange = { preco = it },
            onQuantidadeChange = { quantidade = it },
            onCadastrar = {
                if (
                    nome.isNotBlank() &&
                    preco.isNotBlank() &&
                    quantidade.isNotBlank()
                ) {
                    val precoDouble = preco.toDoubleOrNull()
                    val quantidadeInt = quantidade.toIntOrNull()

                    if (precoDouble != null && quantidadeInt != null) {
                        val produto = Produto(
                            nome = nome,
                            preco = precoDouble,
                            quantidade = quantidadeInt
                        )

                        produtos.add(produto)
                        total += produto.calcularTotal()

                        nome = ""
                        preco = ""
                        quantidade = ""
                    }
                }
            }
        )

        // 2. Lista de Produtos Cadastrados
        ListaProdutos(produtos = produtos)

        // 3. Resumo da Compra
        Resumo(total = total)

        // Botão Limpar Lista
        Button(
            onClick = {
                produtos.clear()
                total = 0.0
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Limpar Lista")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewListaCompras() {
    ListaCompras()
}