package br.edu.cafeteria.modelo;

import br.edu.cafeteria.excecao.PontosInsuficientesException;

public class ClienteVip extends Cliente {

    public static final double TAXA_CONVERSAO_XP = 10.0;

    public ClienteVip(String nome, String cpf) {
        super(nome, cpf);
    }

    public void acumularXP(double valorGasto) {
        double novoSaldo = getSaldoXP() + (valorGasto * 2); 
        setSaldoXP(novoSaldo);
    }

    public void pagarComXP(double valorTotal) throws PontosInsuficientesException {
        double xpNecessario = valorTotal * TAXA_CONVERSAO_XP;

        if (getSaldoXP() < xpNecessario) {
            throw new PontosInsuficientesException("Saldo de XP insuficiente para pagar este pedido."); 
        }
        
        double novoSaldo = getSaldoXP() - xpNecessario;
        setSaldoXP(novoSaldo);
    }

    public boolean isVip() {
        return true;
    }
}