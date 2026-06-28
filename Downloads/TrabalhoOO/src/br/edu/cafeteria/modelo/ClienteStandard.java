package br.edu.cafeteria.modelo;

public class ClienteStandard extends Cliente {

    public ClienteStandard(String nome, String cpf) {
        super(nome, cpf);
    }

    public void acumularXP(double valorGasto) {
        int pontosGanhos = (int) valorGasto; 
        double novoSaldo = getSaldoXP() + pontosGanhos; 
        setSaldoXP(novoSaldo);
    }

    public boolean isVip() {
        return false;
    }
}