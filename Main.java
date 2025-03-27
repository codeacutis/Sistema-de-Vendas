/* Nesse código foi criado um aplicativo de vendas.
 * O mesmo verifica se existe o cadstro do cliente e do produto antes da venda ser feita.
 * O sistema, ainda, aplica o desconto por idade do cliente.
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
    double total = 0.0;

    public Venda(Cliente cliente) {
        this.cliente = cliente;
    }

    public void adicionarProduto(Produto produto, int quantidade) {
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
// Aqui, cria-se os métodos que serão utilizados para calcular o desconto. Nota-se que o mesmo método toma diferentes formas a depender de sua aplicação no código.
//Classe abstrata criada e que será sobrescrita pelos métodos das subclasses
abstract class Desconto {
    abstract double calcularDesconto(double total);
}
// Subclasse da classe Desconto, aplicando às crianças desconto de 15%
class DescontoCrianca extends Desconto {
    @Override
    double calcularDesconto(double total) {
        return total - (total* 0.15);
    }
}
// Subclasse da classe Desconto, aplicando aos idosos desconto de 20%
class DescontoIdoso extends Desconto {
    @Override
    double calcularDesconto(double total) {
        return total - (total* 0.20);
    }
}
// Subclasse da classe Desconto, não aplica nenhum desconto e retorna o valor natural da compra
class SemDesconto extends Desconto {
    @Override
    double calcularDesconto(double total) {
        return total;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ArrayList<Cliente> clientes = new ArrayList<>(); //Criação de um ArrayList do tipo da Classe "Cliente"
        ArrayList<Produto> produtos = new ArrayList<>(); //Criação de um ArrayList do tipo da Classe "Produto"
        ArrayList<Venda> vendas = new ArrayList<>(); //Criação de um ArrayList do tipo da Classe "Venda"
        /*

         */
        int opcao;
        System.out.println("Bem-vindo ao sistema de vendas!");
        // Aqui colocamos um DO-WHILE para o sistema executar pelo menos uma vez o menu inicial e manter sua repetição até o usuário requerer a saída do sistema.
        do {
            System.out.println("\n1. Cadastrar produto\n2. Cadastrar cliente\n3. Realizar venda\n4. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = input.nextInt();
            input.nextLine();
            /*
                * No começo do nosso código, colocamos um menu interativo com 4 opções a serem escolhidas;
                * Lembrando que, se tentar realizar a venda sem o cadastro de clientes e produtos primeiro ,
                será pedido para realizar este antes de prosseguir;
                * As 4 opções das atividades do sistema são:
                * 1. Cadastrar o Produto , onde se cadastra o nome e o preço;
                * 2. Cadastrar o cliente, colocando seu CPF, NOME  e IDADE;
                * 3. Realizar a Venda, o Sistema mostra os clientes e produtos disponíveis para vendas,
                se não houver cadastro de um ou outro o mesmo retorna para ser efetuado. Após a confirmação
                da venda , se encerrar e não tiver mais vendas, o sistema retorna o resumo da venda.
                * 4. Opção de sair do sistema. O sistema ainda retorna as vendas realizadas no período de utilização do sistema.
                * As demais escolhas, se estiver fora das opções válidas, será apresentadas a mensagem de
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
