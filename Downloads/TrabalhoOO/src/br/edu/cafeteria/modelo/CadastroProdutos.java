package br.edu.cafeteria.modelo;

import java.util.ArrayList;
import java.util.List;

public class CadastroProdutos {
    private List<Produto> produtos;

    public CadastroProdutos() {
        this.produtos = new ArrayList<>();
        // produtos iniciais
        produtos.add(new Comida("C001", "Pão de Queijo", 5.00, 50, 10, false));
        produtos.add(new Comida("C002", "Bolo de Chocolate", 8.50, 30, 20, true));
        produtos.add(new Bebida("B001", "Café Expresso", 4.00, 100, "P", 80));
        produtos.add(new Bebida("B002", "Suco de Laranja", 7.00, 60, "G", 0));
    }

    public void cadastrarProduto(Produto p) { produtos.add(p); }

    public Produto pesquisarProduto(String codigo) {
        for (Produto p : produtos) {
            if (p.getCodigo().equals(codigo)) return p;
        }
        return null;
    }

    public List<Produto> listarProdutos() { return new ArrayList<>(produtos); }

    public boolean atualizarProduto(String codigo, Produto novoProduto) {
        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i).getCodigo().equals(codigo)) {
                produtos.set(i, novoProduto);
                return true;
            }
        }
        return false;
    }

    public boolean removerProduto(String codigo) {
        Produto p = pesquisarProduto(codigo);
        if (p != null) {
            produtos.remove(p);
            return true;
        }
        return false;
    }
}