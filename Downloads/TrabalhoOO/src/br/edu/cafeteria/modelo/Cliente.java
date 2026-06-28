package br.edu.cafeteria.modelo;

public abstract class Cliente {
    private String nome;
    private String cpf;
    private double saldoXP;

    public Cliente(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
        this.saldoXP = 0.0;
    }

    //metodos abstratos
    public abstract void acumularXP(double valorGasto);
    public abstract boolean isVip();

    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public double getSaldoXP() { return saldoXP; }
    public void setSaldoXP(double saldoXP) { this.saldoXP = saldoXP; }
}