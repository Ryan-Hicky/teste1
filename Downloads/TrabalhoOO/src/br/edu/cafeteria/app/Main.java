package br.edu.cafeteria.app;

import br.edu.cafeteria.modelo.*;
import br.edu.cafeteria.excecao.EstoqueInsuficienteException;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao = -1;

        CadastroClientes cadastroClientes = new CadastroClientes();
        CadastroProdutos cardapio = new CadastroProdutos();

        cadastroClientes.cadastrarCliente(new ClienteStandard("Aragorn", "111"));
        cadastroClientes.cadastrarCliente(new ClienteVip("Gandalf", "222"));

        System.out.println("==========================================");
        System.out.println("   BEM-VINDO AO SISTEMA BYTE & BREW       ");
        System.out.println("==========================================");

        while (opcao != 0) {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("GERENCIAR PRODUTOS:");
            System.out.println("  1. Ver Cardápio");
            System.out.println("  2. Adicionar Produto");
            System.out.println("  3. Editar Produto");
            System.out.println("  4. Excluir Produto");
            System.out.println("GERENCIAR CLIENTES:");
            System.out.println("  5. Ver Clientes");
            System.out.println("  6. Cadastrar Cliente");
            System.out.println("  7. Editar Cliente");
            System.out.println("  8. Excluir Cliente");
            System.out.println("VENDAS:");
            System.out.println("  9. Abrir Novo Pedido");
            System.out.println("  0. Sair do Sistema");
            System.out.print("Escolha uma opção: ");

            if (scanner.hasNextInt()) {
                opcao = scanner.nextInt();
                scanner.nextLine(); 
            } else {
                System.out.println("Entrada inválida. Digite um número.");
                scanner.nextLine(); 
                continue; 
            }

            switch (opcao) {
                // ==========================================
                // CRUD DE PRODUTOS
                // ==========================================
                case 1: // LER PRODUTOS
                    System.out.println("\n--- CARDÁPIO BYTE & BREW ---");
                    List<Produto> produtos = cardapio.listarProdutos();
                    if (produtos.isEmpty()) {
                        System.out.println("O cardápio está vazio.");
                    } else {
                        for (Produto p : produtos) {
                            System.out.println("[" + p.getCodigo() + "] " + p.getNome() + 
                                               " | R$ " + p.getPrecoBase() + 
                                               " | Estoque: " + p.getQuantidadeEstoque());
                        }
                    }
                    break;

                case 2: // CRIAR PRODUTO
                    System.out.println("\n--- ADICIONAR PRODUTO ---");
                    System.out.print("Tipo (1 - Bebida, 2 - Comida): ");
                    int tipoProd = scanner.nextInt(); scanner.nextLine();
                    
                    System.out.print("Código: "); String codProd = scanner.nextLine();
                    System.out.print("Nome: "); String nomeProd = scanner.nextLine();
                    System.out.print("Preço: R$ "); double precoProd = scanner.nextDouble();
                    System.out.print("Estoque Inicial: "); int estProd = scanner.nextInt(); scanner.nextLine();

                    if (tipoProd == 1) {
                        System.out.print("Tamanho (P/M/G): "); String tam = scanner.nextLine();
                        System.out.print("Cafeína (mg): "); int caf = scanner.nextInt(); scanner.nextLine();
                        cardapio.cadastrarProduto(new Bebida(codProd, nomeProd, precoProd, estProd, tam, caf));
                        System.out.println("Bebida adicionada com sucesso!");
                    } else if (tipoProd == 2) {
                        System.out.print("Tempo de preparo (min): "); int tempo = scanner.nextInt();
                        System.out.print("É vegano/sem glúten? (true/false): "); boolean veg = scanner.nextBoolean(); scanner.nextLine();
                        cardapio.cadastrarProduto(new Comida(codProd, nomeProd, precoProd, estProd, tempo, veg));
                        System.out.println("Comida adicionada com sucesso!");
                    }
                    break;

                case 3: // ATUALIZAR PRODUTO
                    System.out.println("\n--- EDITAR PRODUTO ---");
                    System.out.print("Digite o código do produto que deseja editar: ");
                    String codEdit = scanner.nextLine();
                    Produto prodAtual = cardapio.pesquisarProduto(codEdit);

                    if (prodAtual != null) {
                        System.out.println("Produto encontrado: " + prodAtual.getNome());
                        System.out.println("Para atualizar, informe os novos dados (ou repita os antigos):");
                        System.out.print("Novo Nome: "); String nNome = scanner.nextLine();
                        System.out.print("Novo Preço: R$ "); double nPreco = scanner.nextDouble();
                        System.out.print("Nova Quantidade em Estoque: "); int nEst = scanner.nextInt(); scanner.nextLine();

                        // Substitui pela nova versão mantendo a classe original
                        if (prodAtual instanceof Bebida) {
                            Bebida b = (Bebida) prodAtual;
                            cardapio.atualizarProduto(codEdit, new Bebida(codEdit, nNome, nPreco, nEst, b.getTamanho(), b.getCafeinaMg()));
                        } else if (prodAtual instanceof Comida) {
                            Comida c = (Comida) prodAtual;
                            cardapio.atualizarProduto(codEdit, new Comida(codEdit, nNome, nPreco, nEst, c.getTempoPreparoMinutos(), c.isVeganoSemGluten()));
                        }
                        System.out.println("Produto atualizado com sucesso!");
                    } else {
                        System.out.println("Produto não encontrado.");
                    }
                    break;

                case 4: // DELETAR PRODUTO
                    System.out.println("\n--- EXCLUIR PRODUTO ---");
                    System.out.print("Código do produto a ser excluído: ");
                    String codDel = scanner.nextLine();
                    if (cardapio.removerProduto(codDel)) {
                        System.out.println("Produto removido com sucesso!");
                    } else {
                        System.out.println("Erro: Produto não encontrado.");
                    }
                    break;

                // ==========================================
                // CRUD DE CLIENTES
                // ==========================================
                case 5: // LER CLIENTES
                    System.out.println("\n--- CLIENTES CADASTRADOS ---");
                    List<Cliente> clientes = cadastroClientes.listarClientes();
                    if (clientes.isEmpty()) {
                        System.out.println("Nenhum cliente cadastrado.");
                    } else {
                        for (Cliente c : clientes) {
                            String tipo = c.isVip() ? "VIP" : "Standard";
                            System.out.println("CPF: " + c.getCpf() + " | Nome: " + c.getNome() + 
                                               " | Tipo: " + tipo + " | XP: " + c.getSaldoXP());
                        }
                    }
                    break;

                case 6: // CRIAR CLIENTE
                    System.out.println("\n--- CADASTRAR CLIENTE ---");
                    System.out.print("Nome: "); String nomeC = scanner.nextLine();
                    System.out.print("CPF: "); String cpfC = scanner.nextLine();
                    System.out.print("Tipo (1 - Standard, 2 - VIP): ");
                    int tipoC = scanner.nextInt(); scanner.nextLine();

                    if (tipoC == 1) {
                        cadastroClientes.cadastrarCliente(new ClienteStandard(nomeC, cpfC));
                        System.out.println("Cliente Standard cadastrado com sucesso!");
                    } else if (tipoC == 2) {
                        cadastroClientes.cadastrarCliente(new ClienteVip(nomeC, cpfC));
                        System.out.println("Cliente VIP cadastrado com sucesso!");
                    }
                    break;

                case 7: // ATUALIZAR CLIENTE
                    System.out.println("\n--- EDITAR CLIENTE ---");
                    System.out.print("Digite o CPF do cliente que deseja editar: ");
                    String cpfEdit = scanner.nextLine();
                    Cliente cliAtual = cadastroClientes.pesquisarCliente(cpfEdit);

                    if (cliAtual != null) {
                        System.out.print("Novo Nome: "); String novoNomeCli = scanner.nextLine();
                        System.out.print("Deseja alterar para VIP? (S/N): ");
                        boolean mudarVip = scanner.nextLine().equalsIgnoreCase("S");

                        double xpSalvo = cliAtual.getSaldoXP();
                        Cliente novoClienteObj;

                        if (mudarVip || cliAtual.isVip()) {
                            novoClienteObj = new ClienteVip(novoNomeCli, cpfEdit);
                        } else {
                            novoClienteObj = new ClienteStandard(novoNomeCli, cpfEdit);
                        }
                        
                        novoClienteObj.setSaldoXP(xpSalvo); // Mantém o XP antigo
                        cadastroClientes.atualizarCliente(cpfEdit, novoClienteObj);
                        System.out.println("Cadastro de cliente atualizado com sucesso!");
                    } else {
                        System.out.println("Cliente não encontrado.");
                    }
                    break;

                case 8: // DELETAR CLIENTE
                    System.out.println("\n--- EXCLUIR CLIENTE ---");
                    System.out.print("CPF do cliente a ser excluído: ");
                    String cpfDel = scanner.nextLine();
                    if (cadastroClientes.removerCliente(cpfDel)) {
                        System.out.println("Cliente removido com sucesso!");
                    } else {
                        System.out.println("Erro: Cliente não encontrado.");
                    }
                    break;

                // ==========================================
                // VENDAS
                // ==========================================
                case 9:
                    System.out.println("\n--- ABRIR PEDIDO ---");
                    System.out.print("Digite o CPF do cliente: ");
                    String cpfBusca = scanner.nextLine();
                    
                    Cliente clienteEncontrado = cadastroClientes.pesquisarCliente(cpfBusca);

                    if (clienteEncontrado == null) {
                        System.out.println("Cliente não encontrado! Cadastre-o primeiro.");
                        break;
                    }

                    System.out.print("Nome do Atendente: ");
                    String atendente = scanner.nextLine();
                    
                    Pedido pedido = new Pedido(atendente, clienteEncontrado);
                    boolean adicionandoItens = true;

                    while (adicionandoItens) {
                        System.out.print("CÓDIGO do produto (ou 'fim' para finalizar os itens): ");
                        String codigoProduto = scanner.nextLine();

                        if (codigoProduto.equalsIgnoreCase("fim")) {
                            adicionandoItens = false;
                            break;
                        }

                        Produto produtoEncontrado = cardapio.pesquisarProduto(codigoProduto);

                        if (produtoEncontrado != null) {
                            System.out.print("Quantidade desejada: ");
                            int qtd = scanner.nextInt();
                            scanner.nextLine();

                            try {
                                pedido.adicionarItem(produtoEncontrado, qtd);
                                System.out.println("Item adicionado ao carrinho!");
                            } catch (EstoqueInsuficienteException e) {
                                System.out.println("ERRO: " + e.getMessage());
                            }
                        } else {
                            System.out.println("Produto não encontrado.");
                        }
                    }

                    System.out.print("Hoje é Dia de Evento Geek? (S/N): ");
                    boolean isDiaGeek = scanner.nextLine().equalsIgnoreCase("S");

                    double total = pedido.calcularTotal(isDiaGeek);
                    
                    if (total > 0) {
                        System.out.println("\n*** RESUMO DA VENDA ***");
                        System.out.println("Valor Total a Pagar: R$ " + total);
                        clienteEncontrado.acumularXP(total);
                        System.out.println("Venda finalizada! Novo saldo de XP do cliente: " + clienteEncontrado.getSaldoXP());
                    } else {
                        System.out.println("Pedido cancelado (vazio).");
                    }
                    break;

                case 0:
                    System.out.println("Encerrando o sistema Byte & Brew. Até logo!");
                    break;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
        scanner.close();
    }
}