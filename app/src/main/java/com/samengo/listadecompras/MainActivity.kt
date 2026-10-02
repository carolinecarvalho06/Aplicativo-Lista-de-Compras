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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
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
// LISTA DE COMPRAS
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

        // -------------------------------------------------
        // TÍTULO
        // -------------------------------------------------

        Text("Lista de Compras")


        // -------------------------------------------------
        // NOME DO PRODUTO
        // -------------------------------------------------

        Text("Nome do Produto:")

        TextField(
            value = nome,
            onValueChange = {
                nome = it
            },
            modifier = Modifier.fillMaxWidth()
        )


        // -------------------------------------------------
        // PREÇO
        // -------------------------------------------------

        Text("Preço:")

        TextField(
            value = preco,
            onValueChange = {
                preco = it
            },
            modifier = Modifier.fillMaxWidth()
        )


        // -------------------------------------------------
        // QUANTIDADE
        // -------------------------------------------------

        Text("Quantidade:")

        TextField(
            value = quantidade,
            onValueChange = {
                quantidade = it
            },
            modifier = Modifier.fillMaxWidth()
        )


        // -------------------------------------------------
        // BOTÃO CADASTRAR
        // -------------------------------------------------

        Button(
            onClick = {

                // Impede o cadastro se algum campo estiver vazio
                if (
                    nome.isNotBlank() &&
                    preco.isNotBlank() &&
                    quantidade.isNotBlank()
                ) {

                    // Conversão do preço
                    val precoDouble = preco.toDoubleOrNull()

                    // Conversão da quantidade
                    val quantidadeInt = quantidade.toIntOrNull()

                    // Verifica se as conversões são válidas
                    if (
                        precoDouble != null &&
                        quantidadeInt != null
                    ) {

                        // Cria o produto
                        val produto = Produto(
                            nome = nome,
                            preco = precoDouble,
                            quantidade = quantidadeInt
                        )

                        // Adiciona o produto na lista
                        produtos.add(produto)

                        // Soma o total
                        total += produto.calcularTotal()

                        // Limpa os campos
                        nome = ""
                        preco = ""
                        quantidade = ""
                    }
                }
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {

            Text("Cadastrar")
        }


        // -------------------------------------------------
        // QUANTIDADE DE PRODUTOS
        // -------------------------------------------------

        Text(
            "Quantidade de produtos cadastrados: ${produtos.size}",
            modifier = Modifier.padding(top = 16.dp)
        )


        // -------------------------------------------------
        // LISTA DE PRODUTOS
        // -------------------------------------------------

        for (produto in produtos) {

            Text(
                "${produto.nome} - " +
                        "${produto.quantidade} x R$ ${produto.preco} = " +
                        "R$ ${produto.calcularTotal()}"
            )
        }


        // -------------------------------------------------
        // TOTAL DA COMPRA
        // -------------------------------------------------

        Text(
            "Total da compra: R$ $total",
            modifier = Modifier.padding(top = 16.dp)
        )


        // -------------------------------------------------
        // BOTÃO LIMPAR LISTA
        // -------------------------------------------------

        Button(
            onClick = {

                // Limpa todos os produtos
                produtos.clear()

                // Zera o total
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