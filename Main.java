import java.util.ArrayList;
import java.util.Scanner;

class Cliente {
    String nome;
    String cpf;
    int idade;

    public Cliente(String nome, String cpf, int idade) {
        this.nome = nome;
        this.cpf = cpf;
        this.idade = idade;
    }
}

class Produto {
    String nome;
    double preco;

    public Produto(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }
}

class Venda {
    Cliente cliente;
    ArrayList<Produto> produtos = new ArrayList<>();
    double total = 0.0;

    public Venda(Cliente cliente) {
        this.cliente = cliente;
    }

    public void adicionarProduto(Produto produto, int quantidade) {
        for (int i = 0; i < quantidade; i++) {
            produtos.add(produto);
        }
        total += produto.preco * quantidade;
    }

    public double calcularTotalComDesconto() {
        Desconto desconto;
        if (cliente.idade <= 7) {
            desconto = new DescontoCrianca();
        } else if (cliente.idade >= 60) {
            desconto = new DescontoIdoso();
        } else {
            desconto = new SemDesconto();
        }
        return desconto.calcularDesconto(total);
    }

    public void exibirResumo() {
        System.out.println("Cliente: " + cliente.nome + " | Total da venda: R$ " + calcularTotalComDesconto());
    }
}

abstract class Desconto {
    abstract double calcularDesconto(double total);
}

class DescontoCrianca extends Desconto {
    @Override
    double calcularDesconto(double total) {
        return total - (total* 0.15);
    }
}

class DescontoIdoso extends Desconto {
    @Override
    double calcularDesconto(double total) {
        return total - (total* 0.20);
    }
}

class SemDesconto extends Desconto {
    @Override
    double calcularDesconto(double total) {
        return total;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ArrayList<Cliente> clientes = new ArrayList<>();
        ArrayList<Produto> produtos = new ArrayList<>();
        ArrayList<Venda> vendas = new ArrayList<>();

        int opcao;
        System.out.println("Bem-vindo ao sistema de vendas!");
        do {
            System.out.println("\n1. Cadastrar produto\n2. Cadastrar cliente\n3. Realizar venda\n4. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = input.nextInt();
            input.nextLine();

            switch (opcao) {
                case 1:
                    System.out.println("Digite quantos produtos você deseja cadastrar: ");
                    int quantidadeCadastro = input.nextInt();
                    input.nextLine();

                    for (int i = 0; i < quantidadeCadastro; i++) {
                        System.out.println("Digite o nome do " + (i + 1) + "º produto: ");
                        String nome = input.nextLine();
                        System.out.println("Digite o preço do " + (i + 1) + "º produto: ");
                        double preco = input.nextDouble();
                        input.nextLine();
                        produtos.add(new Produto(nome, preco));
                        System.out.println("\n");
                    }
                    System.out.println("Produto(s) cadastrado(s) com sucesso!");
                    break;

                case 2:
                    System.out.println("Digite quantos clientes deseja cadastrar");
                    quantidadeCadastro = input.nextInt();
                    input.nextLine();

                    for (int i = 0; i < quantidadeCadastro; i++) {
                        System.out.println("Digite o nome do " + (i + 1) + "º cliente: ");
                        String nome = input.nextLine();
                        System.out.println("Digite o CPF do " + (i + 1) + "º cliente: ");
                        String cpf = input.nextLine();
                        System.out.println("Digite a idade do " + (i + 1) + "º cliente: ");
                        int idade = input.nextInt();
                        input.nextLine();
                        clientes.add(new Cliente(nome, cpf, idade));
                        System.out.println("\n");
                    }
                    System.out.println("Cliente(s) cadastrado(s) com sucesso!");
                    break;

                case 3:
                    if (clientes.isEmpty() || produtos.isEmpty()) {
                        System.out.println("Cadastre clientes e produtos antes de vender!");
                        break;
                    }
                    System.out.println("Clientes disponíveis:");
                    for (int i = 0; i < clientes.size(); i++) {
                        System.out.println((i + 1) + ". " + clientes.get(i).nome);
                    }
                    System.out.print("Escolha o cliente: ");
                    Cliente cliente = clientes.get(input.nextInt() - 1);
                    Venda venda = new Venda(cliente);

                    int escolha;
                    do {
                        System.out.println("Produtos disponíveis:");
                        for (int i = 0; i < produtos.size(); i++) {
                            System.out.println((i + 1) + ". " + produtos.get(i).nome + " - R$ " + produtos.get(i).preco);
                        }
                        System.out.print("Escolha um produto que irá comprar pelo NÚMERO: ");
                        Produto produto = produtos.get(input.nextInt() - 1);
                        System.out.print("Quantidade que irá ser comprada: ");
                        venda.adicionarProduto(produto, input.nextInt());
                        System.out.print("Adicionar mais produtos? Digite 1.Sim, 0.Não: ");
                        escolha = input.nextInt();
                    } while (escolha != 0);
                    vendas.add(venda);
                    System.out.println("\nVenda realizada!");
                    System.out.println("Total da compra: R$" + venda.calcularTotalComDesconto());
                    break;

                case 4:
                    double totalDia = 0.0;
                    System.out.println("\nResumo das vendas:");
                    System.out.println("---------------------------------------------");
                    for (Venda v : vendas) {
                        v.exibirResumo();
                    }
                    for (int i=0; i< vendas.size();i++) {
                        totalDia += vendas.get(i).calcularTotalComDesconto();
                    }
                    System.out.println("Total vendido: R$" + totalDia);
                    System.out.println("---------------------------------------------");
                    System.out.println("Obrigado por usar nosso sistema!");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 4);
        input.close();
    }
}
