package br.edu.cafeteria.modelo;

import java.util.ArrayList;
import java.util.List;
import br.edu.cafeteria.servico.Promocional;
import br.edu.cafeteria.excecao.EstoqueInsuficienteException;

public class Pedido {
    private String atendente;
    private Cliente cliente;
    private List<ItemPedido> itens;

    public Pedido(String atendente, Cliente cliente) {
        this.atendente = atendente;
        this.cliente = cliente;
        this.itens = new ArrayList<>();
    }

    //Sobrecarga
    public void adicionarItem(Produto p) throws EstoqueInsuficienteException {
        adicionarItem(p, 1);
    }

    //Sobrecarga
    public void adicionarItem(Produto p, int quantidade) throws EstoqueInsuficienteException {
        p.reduzirEstoque(quantidade);
        itens.add(new ItemPedido(p, quantidade));
    }

    public double calcularTotal(boolean isEventoGeek) {
        double total = 0;
        for (ItemPedido item : itens) {
            double precoItem = item.getProduto().getPrecoBase();
            
            //Polimorfismo da Interface
            if (isEventoGeek && item.getProduto() instanceof Promocional) {
                precoItem = ((Promocional) item.getProduto()).aplicarDesconto(precoItem);
            }
            
            total += (precoItem * item.getQuantidade());
        }
        return total;
    }
}