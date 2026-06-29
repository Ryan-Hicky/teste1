package br.edu.cafeteria.modelo;

import java.util.ArrayList;
import java.util.List;
import br.edu.cafeteria.servico.Promocional;
import br.edu.cafeteria.excecao.EstoqueInsuficienteException;

public class Pedido {
    
    // Atributo estático exigido pelo checklist para gerar o número sequencial automaticamente
    private static int geradorNumeroSequencial = 1; 

    private int numeroPedido;
    private String atendente;
    private Cliente cliente;
    private List<ItemPedido> itens;

    public Pedido(String atendente, Cliente cliente) {
        // Atribui o número atual e já incrementa para o próximo pedido
        this.numeroPedido = geradorNumeroSequencial++; 
        this.atendente = atendente;
        this.cliente = cliente;
        this.itens = new ArrayList<>();
    }

    // ==========================================
    // POLIMORFISMO DE SOBRECARGA (Exigência do Checklist)
    // ==========================================
    
    // Sobrecarga 1: Adiciona 1 unidade por padrão
    public void adicionarItem(Produto p) throws EstoqueInsuficienteException {
        adicionarItem(p, 1);
    }

    // Sobrecarga 2: Adiciona a quantidade especificada e reduz o estoque
    public void adicionarItem(Produto p, int quantidade) throws EstoqueInsuficienteException {
        p.reduzirEstoque(quantidade); // Pode lançar a exceção checked aqui
        itens.add(new ItemPedido(p, quantidade));
    }

    // ==========================================
    // CÁLCULO DE VENDAS E POLIMORFISMO DE INTERFACE
    // ==========================================

    public double calcularTotal(boolean isEventoGeek) {
        double total = 0;
        for (ItemPedido item : itens) {
            double precoItem = item.getProduto().getPrecoBase();
            
            // Verifica se é dia de evento E se o produto assina a interface Promocional
            if (isEventoGeek && item.getProduto() instanceof Promocional) {
                precoItem = ((Promocional) item.getProduto()).aplicarDesconto(precoItem);
            }
            
            total += (precoItem * item.getQuantidade());
        }
        return total;
    }

    // ==========================================
    // GETTERS
    // ==========================================

    public int getNumeroPedido() { 
        return numeroPedido; 
    }

    public String getAtendente() { 
        return atendente; 
    }

    public Cliente getCliente() { 
        return cliente; 
    }

    public List<ItemPedido> getItens() { 
        return new ArrayList<>(itens); // Retorna uma cópia para proteger o encapsulamento da lista original
    }
    public void setAtendente(String atendente) { this.atendente = atendente; }
}