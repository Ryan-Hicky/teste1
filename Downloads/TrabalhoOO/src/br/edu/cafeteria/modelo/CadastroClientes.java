package br.edu.cafeteria.modelo;

import java.util.ArrayList;
import java.util.List;

public class CadastroClientes {
    private List<Cliente> clientes;

    public CadastroClientes() {
        this.clientes = new ArrayList<>();
    }

    public void cadastrarCliente(Cliente c) { clientes.add(c); }

    public Cliente pesquisarCliente(String cpf) {
        for (Cliente c : clientes) {
            if (c.getCpf().equals(cpf)) return c;
        }
        return null;
    }

    public List<Cliente> listarClientes() { return new ArrayList<>(clientes); }

    public boolean atualizarCliente(String cpf, Cliente novoCliente) {
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getCpf().equals(cpf)) {
                clientes.set(i, novoCliente);
                return true;
            }
        }
        return false;
    }

    public boolean removerCliente(String cpf) {
        Cliente c = pesquisarCliente(cpf);
        if (c != null) {
            clientes.remove(c);
            return true;
        }
        return false;
    }

    public boolean promoverParaVip(String cpf) {
        Cliente clienteAtual = pesquisarCliente(cpf);

        if (clienteAtual == null || clienteAtual.isVip()) {
            return false;
        }

        String nome = clienteAtual.getNome(); 
        double saldoAtual = clienteAtual.getSaldoXP();

        clientes.remove(clienteAtual);

        ClienteVip novoCliente = new ClienteVip(nome, cpf);
        novoCliente.setSaldoXP(saldoAtual); 

        clientes.add(novoCliente);
        return true;
    }
}