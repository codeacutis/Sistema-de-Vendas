/* Nesse código foi criado um aplicativo de vendas.
 * O mesmo verifica se existe o cadstro do cliente e do produto antes da venda ser feita8
 * E também existe o desconto por idade do cliente.
 */


import java.util.ArrayList;
import java.util.Scanner;

// Objeto cliente e seus atributos
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
// Objeto produto e seus atributos
class Produto {
    String nome;
    double preco;

    public Produto(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }
}
// Aqui em vendas já colocamos para vendas verificar a classe clientes
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
/*
    * Parte de desconto que foi colocada como citado no começo do código;
    * Se a idade do cliente for menor ou igual a 7 ele tem desconto na compra;
    * Se a idade do cliente dor maior ou igual que 60 também existe esse desconto;
    * As demais idades não tem desconto na compra.
 */
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
    // Resumo da Venda efetuada
    public void exibirResumo() {
        System.out.println("Cliente: " + cliente.nome + " | Total da venda: R$ " + calcularTotalComDesconto());
    }
}
// Aqui vamos calcular o desconto conforme os parâmetros citados anteriormente
abstract class Desconto {
    abstract double calcularDesconto(double total);
}
// Desconto aplicado as crianças de 15%
class DescontoCrianca extends Desconto {
    @Override
    double calcularDesconto(double total) {
        return total - (total* 0.15);
    }
}
// Desconto apicado aos idosos de 20%
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
        /*
            * No começo do nosso código já colocamos as 4 opções a serem escolhidas;
            * Lembrando que se tentar realizar a venda sem o cadastro de clientes e produtos primeiro ,
            será pedido para realizar este antes de prosseguir;
            * Na venda , nosso sitema verifica as idades e ve se há necessidade de desconto ou não;
            * se houver desconto como mostrado atrás já está automaticamente para ser calculado;
            * após a venda se não for ncessária uma nova venda o usuário deve sair;
            * ao sai o sistema apresenta um resumo geral das vendas realizadas.
         */
        int opcao;
        System.out.println("Bem-vindo ao sistema de vendas!");
        // Aqui colocamos um DO-WHILE para o sistema verificar pelo menos uma vez nossa solicitação do que será feito.
        do {
            System.out.println("\n1. Cadastrar produto\n2. Cadastrar cliente\n3. Realizar venda\n4. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = input.nextInt();
            input.nextLine();
            /*
                * Nossas 4 opções do sistema sendo elas :
                * 1. Cadastrar o Produto , onde se cadastra o nome e o preço;
                * 2. Cadastrar o cliente, colocando seu CPF, NOME  e IDADE;
                * 3. Realizar a Venda, o Sistema te mostra os cliente e produtos dispónoves para vendas,
                se não houver cadastro de um ou outro o mesmo retorna para ser efetuado. Após a confirmação
                da venda , se encerrar e não tiver mais vendas o sistema retorno o resumo da venda.
                * 4. Opção de sair do sistema.
                * As demais escolhas se estiver fora do nosso escopo será apresentadas a mensagem de
                escolha inválida e retornára para realizar a escolhas das opções válidas novamente.
             */
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
                    int quantidadeClientes = input.nextInt();
                    input.nextLine();

                    for (int i = 0; i < quantidadeClientes; i++) {
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
