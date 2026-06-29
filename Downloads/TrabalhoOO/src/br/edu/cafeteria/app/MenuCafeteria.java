package br.edu.cafeteria.app;

import br.edu.cafeteria.modelo.*;
import br.edu.cafeteria.excecao.EstoqueInsuficienteException;
import java.util.List;
import java.util.Scanner;

public class MenuCafeteria {
    private Scanner scanner;
    private CadastroClientes cadastroClientes;
    private CadastroProdutos cardapio;
    private CadastroVendas cadastroVendas; // <-- NOVO GERENCIADOR AQUI

    public MenuCafeteria() {
        this.scanner = new Scanner(System.in);
        this.cadastroClientes = new CadastroClientes();
        this.cardapio = new CadastroProdutos();
        this.cadastroVendas = new CadastroVendas();

        // Dados iniciais para facilitar os testes
        cadastroClientes.cadastrarCliente(new ClienteStandard("Aragorn", "111"));
        cadastroClientes.cadastrarCliente(new ClienteVip("Gandalf", "222"));
    }

    public void iniciar() {
        System.out.println("==========================================");
        System.out.println("   BEM-VINDO AO SISTEMA BYTE & BREW       ");
        System.out.println("==========================================");

        int opcao = -1;
        while (opcao != 0) {
            exibirOpcoesMenu();
            if (scanner.hasNextInt()) {
                opcao = scanner.nextInt();
                scanner.nextLine();
                processarOpcao(opcao);
            } else {
                System.out.println("Entrada inválida. Digite um número.");
                scanner.nextLine();
            }
        }
        scanner.close();
    }

    private void exibirOpcoesMenu() {
        System.out.println("\n--- MENU PRINCIPAL ---");
        System.out.println("PRODUTOS:");
        System.out.println("  1. Ver Cardápio | 2. Adicionar | 3. Editar | 4. Excluir");
        System.out.println("CLIENTES:");
        System.out.println("  5. Ver Clientes | 6. Cadastrar | 7. Editar | 8. Excluir");
        System.out.println("VENDAS:");
        System.out.println("  9. Abrir Novo Pedido");
        System.out.println(" 10. Histórico de Vendas");
        System.out.println(" 11. Pesquisar Venda");
        System.out.println(" 12. Atualizar Atendente da Venda");
        System.out.println(" 13. Excluir Venda");
        System.out.println("  0. Sair do Sistema");
        System.out.print("Escolha uma opção: ");
    }

    private void processarOpcao(int opcao) {
        switch (opcao) {
            case 1: exibirCardapio(); break;
            case 2: adicionarProduto(); break;
            case 3: editarProduto(); break;
            case 4: excluirProduto(); break;
            case 5: listarClientes(); break;
            case 6: cadastrarCliente(); break;
            case 7: editarCliente(); break;
            case 8: excluirCliente(); break;
            case 9: abrirPedido(); break;
            case 10: listarVendas(); break;
            case 11: pesquisarVenda(); break;
            case 12: atualizarVenda(); break;
            case 13: excluirVenda(); break;
            case 0: System.out.println("Encerrando o sistema Byte & Brew. Até logo!"); break;
            default: System.out.println("Opção inválida. Tente novamente.");
        }
    }

    // ==========================================
    // CRUD DE PRODUTOS
    // ==========================================

    private void exibirCardapio() {
        System.out.println("\n--- CARDÁPIO BYTE & BREW ---");
        List<Produto> produtos = cardapio.listarProdutos();
        if (produtos.isEmpty()) {
            System.out.println("O cardápio está vazio.");
            return;
        }
        for (Produto p : produtos) {
            System.out.println("[" + p.getCodigo() + "] " + p.getNome() + 
                               " | R$ " + p.getPrecoBase() + " | Estoque: " + p.getQuantidadeEstoque());
        }
    }

    private void adicionarProduto() {
        System.out.println("\n--- ADICIONAR PRODUTO ---");
        System.out.print("Tipo (1 - Bebida, 2 - Comida): ");
        int tipo = scanner.nextInt(); scanner.nextLine();
        System.out.print("Código: "); String cod = scanner.nextLine();
        System.out.print("Nome: "); String nome = scanner.nextLine();
        System.out.print("Preço: R$ "); double preco = scanner.nextDouble();
        System.out.print("Estoque Inicial: "); int est = scanner.nextInt(); scanner.nextLine();

        if (tipo == 1) {
            System.out.print("Tamanho (P/M/G): "); String tam = scanner.nextLine();
            System.out.print("Cafeína (mg): "); int caf = scanner.nextInt(); scanner.nextLine();
            cardapio.cadastrarProduto(new Bebida(cod, nome, preco, est, tam, caf));
            System.out.println("Bebida adicionada!");
        } else if (tipo == 2) {
            System.out.print("Tempo de preparo (min): "); int tempo = scanner.nextInt();
            System.out.print("É vegano/sem glúten? (true/false): "); boolean veg = scanner.nextBoolean(); scanner.nextLine();
            cardapio.cadastrarProduto(new Comida(cod, nome, preco, est, tempo, veg));
            System.out.println("Comida adicionada!");
        }
    }

    private void editarProduto() {
        System.out.println("\n--- EDITAR PRODUTO ---");
        System.out.print("Código do produto: ");
        String cod = scanner.nextLine();
        Produto p = cardapio.pesquisarProduto(cod);
        if (p != null) {
            System.out.print("Novo Nome: "); String nNome = scanner.nextLine();
            System.out.print("Novo Preço: R$ "); double nPreco = scanner.nextDouble();
            System.out.print("Novo Estoque: "); int nEst = scanner.nextInt(); scanner.nextLine();
            
            if (p instanceof Bebida) {
                Bebida b = (Bebida) p;
                cardapio.atualizarProduto(cod, new Bebida(cod, nNome, nPreco, nEst, b.getTamanho(), b.getCafeinaMg()));
            } else if (p instanceof Comida) {
                Comida c = (Comida) p;
                cardapio.atualizarProduto(cod, new Comida(cod, nNome, nPreco, nEst, c.getTempoPreparoMinutos(), c.isVeganoSemGluten()));
            }
            System.out.println("Produto atualizado!");
        } else {
            System.out.println("Produto não encontrado.");
        }
    }

    private void excluirProduto() {
        System.out.println("\n--- EXCLUIR PRODUTO ---");
        System.out.print("Código do produto a ser excluído: ");
        String cod = scanner.nextLine();
        if (cardapio.removerProduto(cod)) System.out.println("Produto removido com sucesso!");
        else System.out.println("Erro: Produto não encontrado.");
    }

    // ==========================================
    // CRUD DE CLIENTES
    // ==========================================

    private void listarClientes() {
        System.out.println("\n--- CLIENTES CADASTRADOS ---");
        List<Cliente> clientes = cadastroClientes.listarClientes();
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }
        for (Cliente c : clientes) {
            String tipo = c.isVip() ? "VIP" : "Standard";
            System.out.println("CPF: " + c.getCpf() + " | Nome: " + c.getNome() + 
                               " | Tipo: " + tipo + " | XP: " + c.getSaldoXP());
        }
    }

    private void cadastrarCliente() {
        System.out.println("\n--- CADASTRAR CLIENTE ---");
        System.out.print("Nome: "); String nome = scanner.nextLine();
        System.out.print("CPF: "); String cpf = scanner.nextLine();
        System.out.print("Tipo (1 - Standard, 2 - VIP): ");
        int tipo = scanner.nextInt(); scanner.nextLine();

        if (tipo == 1) {
            cadastroClientes.cadastrarCliente(new ClienteStandard(nome, cpf));
            System.out.println("Cliente Standard cadastrado!");
        } else if (tipo == 2) {
            cadastroClientes.cadastrarCliente(new ClienteVip(nome, cpf));
            System.out.println("Cliente VIP cadastrado!");
        }
    }

    private void editarCliente() {
        System.out.println("\n--- EDITAR CLIENTE ---");
        System.out.print("CPF do cliente: ");
        String cpf = scanner.nextLine();
        Cliente c = cadastroClientes.pesquisarCliente(cpf);
        if (c != null) {
            System.out.print("Novo Nome: "); String novoNome = scanner.nextLine();
            System.out.print("Deseja alterar para VIP? (S/N): ");
            boolean mudarVip = scanner.nextLine().equalsIgnoreCase("S");

            double xpSalvo = c.getSaldoXP();
            Cliente novoCli = (mudarVip || c.isVip()) ? new ClienteVip(novoNome, cpf) : new ClienteStandard(novoNome, cpf);
            novoCli.setSaldoXP(xpSalvo);
            
            cadastroClientes.atualizarCliente(cpf, novoCli);
            System.out.println("Cadastro atualizado!");
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    private void excluirCliente() {
        System.out.println("\n--- EXCLUIR CLIENTE ---");
        System.out.print("CPF a ser excluído: ");
        String cpf = scanner.nextLine();
        if (cadastroClientes.removerCliente(cpf)) System.out.println("Cliente removido com sucesso!");
        else System.out.println("Erro: Cliente não encontrado.");
    }

    // ==========================================
    // CRUD E FLUXO DE VENDAS
    // ==========================================

    private void abrirPedido() {
        System.out.println("\n--- ABRIR PEDIDO ---");
        System.out.print("Digite o CPF do cliente (ou ENTER para Cliente Casual): ");
        String cpf = scanner.nextLine();
        
        Cliente cliente = null; 
        if (!cpf.trim().isEmpty()) {
            cliente = cadastroClientes.pesquisarCliente(cpf);
            if (cliente == null) {
                System.out.println("Cliente não encontrado! Cadastre-o primeiro.");
                return;
            }
            System.out.println("Cliente identificado: " + cliente.getNome());
        } else {
            System.out.println("Iniciando venda para Cliente Casual.");
        }

        System.out.print("Nome do Atendente: ");
        String atendente = scanner.nextLine();
        Pedido pedido = new Pedido(atendente, cliente);
        
        boolean adicionando = true;
        while (adicionando) {
            System.out.print("CÓDIGO do produto (ou 'fim' para finalizar): ");
            String cod = scanner.nextLine();
            if (cod.equalsIgnoreCase("fim")) break;

            Produto p = cardapio.pesquisarProduto(cod);
            if (p != null) {
                System.out.print("Quantidade: ");
                int qtd = scanner.nextInt(); scanner.nextLine();
                try {
                    pedido.adicionarItem(p, qtd);
                    System.out.println("Item adicionado ao carrinho!");
                } catch (EstoqueInsuficienteException e) {
                    System.out.println("ERRO: " + e.getMessage());
                }
            } else {
                System.out.println("Produto não encontrado.");
            }
        }

        System.out.print("Hoje é Dia de Evento Geek? (S/N): ");
        boolean isGeek = scanner.nextLine().equalsIgnoreCase("S");
        double total = pedido.calcularTotal(isGeek);
        
        if (total > 0) {
            System.out.println("\n*** RESUMO DA VENDA (Pedido #" + pedido.getNumeroPedido() + ") ***");
            System.out.println("Valor Total a Pagar: R$ " + total);
            
            boolean pagoComXP = false;

            if (cliente instanceof ClienteVip) {
                ClienteVip vip = (ClienteVip) cliente;
                double xpNecessario = total * ClienteVip.TAXA_CONVERSAO_XP;
                System.out.println("Saldo de XP atual: " + vip.getSaldoXP());
                System.out.print("Deseja pagar com XP? Custa " + xpNecessario + " XP (S/N): ");
                if (scanner.nextLine().equalsIgnoreCase("S")) {
                    try {
                        vip.pagarComXP(total);
                        System.out.println("Pagamento realizado com sucesso usando XP!");
                        pagoComXP = true;
                    } catch (br.edu.cafeteria.excecao.PontosInsuficientesException e) {
                        System.out.println("ERRO: " + e.getMessage() + " O pagamento será feito em dinheiro.");
                    }
                }
            }

            if (cliente != null && !pagoComXP) {
                cliente.acumularXP(total);
                System.out.println("Venda finalizada! Novo saldo de XP: " + cliente.getSaldoXP());

                // Gatilho Automático: Promoção de Standard para VIP ao atingir 1000 XP
                if (cliente instanceof ClienteStandard && cliente.getSaldoXP() >= 1000.0) {
                    System.out.println("\n🎉 UAU! O cliente " + cliente.getNome() + " atingiu 1000 XP!");
                    System.out.println("Elevando o status para Mestre da Guilda (VIP)...");
                    ClienteVip vip = new ClienteVip(cliente.getNome(), cliente.getCpf());
                    vip.setSaldoXP(cliente.getSaldoXP());
                    cadastroClientes.atualizarCliente(cliente.getCpf(), vip);
                }
            } else if (cliente == null) {
                System.out.println("Venda finalizada! Cliente casual não acumula XP.");
            }
            
            // Registra no banco de Vendas (O pilar que faltava!)
            cadastroVendas.cadastrarVenda(pedido);
            
        } else {
            System.out.println("Pedido cancelado (vazio).");
        }
    }

    private void listarVendas() {
        System.out.println("\n--- HISTÓRICO DE VENDAS ---");
        List<Pedido> vendas = cadastroVendas.listarVendas();
        if (vendas.isEmpty()) {
            System.out.println("Nenhuma venda registrada.");
            return;
        }
        for (Pedido p : vendas) {
            String nomeCli = (p.getCliente() != null) ? p.getCliente().getNome() : "Casual";
            System.out.println("Pedido #" + p.getNumeroPedido() + " | Atendente: " + p.getAtendente() + 
                               " | Cliente: " + nomeCli + " | Itens: " + p.getItens().size());
        }
    }

    private void pesquisarVenda() {
        System.out.println("\n--- PESQUISAR VENDA ---");
        System.out.print("Número do Pedido: ");
        int num = scanner.nextInt(); scanner.nextLine();
        Pedido p = cadastroVendas.pesquisarVenda(num);
        if (p != null) {
            String nomeCli = (p.getCliente() != null) ? p.getCliente().getNome() : "Casual";
            System.out.println("Encontrado -> Pedido #" + p.getNumeroPedido() + " | Cliente: " + nomeCli);
        } else {
            System.out.println("Venda não encontrada.");
        }
    }

    private void atualizarVenda() {
        System.out.println("\n--- ATUALIZAR VENDA ---");
        System.out.print("Número do Pedido: ");
        int num = scanner.nextInt(); scanner.nextLine();
        Pedido p = cadastroVendas.pesquisarVenda(num);
        if (p != null) {
            System.out.println("Venda encontrada. Atendente atual: " + p.getAtendente());
            System.out.print("Novo Atendente: ");
            String novoAtendente = scanner.nextLine();
            cadastroVendas.atualizarVenda(num, novoAtendente);
            System.out.println("Atendente atualizado com sucesso!");
        } else {
            System.out.println("Venda não encontrada.");
        }
    }

    private void excluirVenda() {
        System.out.println("\n--- EXCLUIR VENDA ---");
        System.out.print("Número do Pedido a ser excluído: ");
        int num = scanner.nextInt(); scanner.nextLine();
        if (cadastroVendas.removerVenda(num)) System.out.println("Venda removida do histórico!");
        else System.out.println("Erro: Venda não encontrada.");
    }
}