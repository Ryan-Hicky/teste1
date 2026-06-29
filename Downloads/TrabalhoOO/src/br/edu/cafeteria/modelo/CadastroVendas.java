package br.edu.cafeteria.modelo;

import java.util.ArrayList;
import java.util.List;

public class CadastroVendas {
    private List<Pedido> vendas;

    public CadastroVendas() {
        this.vendas = new ArrayList<>();
    }

    // CREATE (Registrar)
    public void cadastrarVenda(Pedido p) {
        vendas.add(p);
    }

    // READ (Pesquisar por número do pedido)
    public Pedido pesquisarVenda(int numero) {
        for (Pedido p : vendas) {
            if (p.getNumeroPedido() == numero) {
                return p;
            }
        }
        return null;
    }

    // READ (Listar todas)
    public List<Pedido> listarVendas() {
        return new ArrayList<>(vendas);
    }

    // UPDATE (Atualizar Atendente)
    // Feito dessa forma para não ter que criar um Pedido novo e estragar o número sequencial!
    public boolean atualizarVenda(int numero, String novoAtendente) {
        Pedido p = pesquisarVenda(numero);
        if (p != null) {
            p.setAtendente(novoAtendente);
            return true;
        }
        return false;
    }

    // DELETE (Remover)
    public boolean removerVenda(int numero) {
        Pedido p = pesquisarVenda(numero);
        if (p != null) {
            vendas.remove(p);
            return true;
        }
        return false;
    }
}