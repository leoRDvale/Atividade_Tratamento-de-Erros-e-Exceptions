import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        //Dados de exemplo ao final do código, descomente para testar. Linha 163

        Scanner sc = new Scanner(System.in);
        sc.useLocale(Locale.US); //usar ponto como separador decimal
        List<Pessoa> pessoas = new ArrayList<>();
        List<CarteiraInvestimento> carteiras = new ArrayList<>();

        while (true) {
            System.out.println("\n      --- Investe Mais ---\n--- Sistema de Investimentos ---\n\n");
            System.out.println("1. Cadastrar Pessoa Física");
            System.out.println("2. Cadastrar Pessoa Jurídica");
            System.out.println("3. Criar Carteira de Investimento");
            System.out.println("4. Adicionar Investimento à Carteira");
            System.out.println("5. Simular Passagem de Meses");
            System.out.println("6. Exibir Carteiras e Investimentos");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            int op = sc.nextInt();
            sc.nextLine();

            if (op == 0) break;

            switch (op) {
                case 1:
                    System.out.print("Nome: ");
                    String nomePF = sc.nextLine();
                    System.out.print("Email: ");
                    String emailPF = sc.nextLine();
                    System.out.print("CPF: ");
                    String cpf = sc.nextLine();
                    pessoas.add(new PessoaFisica(nomePF, emailPF, cpf));
                    System.out.println("Pessoa Física cadastrada!");
                    break;
                case 2:
                    System.out.print("Nome: ");
                    String nomePJ = sc.nextLine();
                    System.out.print("Email: ");
                    String emailPJ = sc.nextLine();
                    System.out.print("CNPJ: ");
                    String cnpj = sc.nextLine();
                    pessoas.add(new PessoaJuridica(nomePJ, emailPJ, cnpj));
                    System.out.println("Pessoa Jurídica cadastrada!");
                    break;
                case 3:
                    if (pessoas.isEmpty()) {
                        System.out.println("Nenhum cadastro encontrado.");
                        break;
                    }
                    for (int i = 0; i < pessoas.size(); i++)
                        System.out.println(i + " - " + pessoas.get(i).getNome());
                    System.out.print("Insira o número referente a pessoa: ");
                    int idxPessoa = sc.nextInt();
                    sc.nextLine();
                    carteiras.add(new CarteiraInvestimento(pessoas.get(idxPessoa)));
                    System.out.println("Carteira criada!");
                    break;
                case 4:
                    if (carteiras.isEmpty()) {
                        System.out.println("Nenhuma carteira encontrada.");
                        break;
                    }
                    for (int i = 0; i < carteiras.size(); i++)
                        System.out.println(i + " - " + carteiras.get(i).getInvestimentos().size() + " investimentos - " + carteiras.get(i).getPessoa().getNome());
                    System.out.print("Insira o número referente a carteira: ");
                    int idxCart = sc.nextInt();
                    sc.nextLine();
                    CarteiraInvestimento carteira = carteiras.get(idxCart);
                    Pessoa pessoa = carteira.getPessoa();
                    System.out.println("Tipos: 1-Tesouro    2-Ação    3-Fundo    4-Debenture");
                    int tipo = sc.nextInt();
                    sc.nextLine();
                    try {
                        switch (tipo) {
                            case 1:
                                System.out.print("Saldo inicial: ");
                                double saldoT = sc.nextDouble();
                                sc.nextLine();
                                System.out.print("Nome título: ");
                                String nomeT = sc.nextLine();
                                System.out.print("Taxa anual: ");
                                double taxaT = sc.nextDouble();
                                sc.nextLine();
                                carteira.adicionarInvestimento(new TesouroPrefixado(pessoa, saldoT, nomeT, taxaT));
                                break;
                            case 2:
                                System.out.print("Saldo inicial: ");
                                double saldoA = sc.nextDouble();
                                sc.nextLine();
                                System.out.print("Código ação: ");
                                String codA = sc.nextLine();
                                System.out.print("Nome empresa: ");
                                String nomeA = sc.nextLine();
                                System.out.print("Preço unitário: ");
                                double precoA = sc.nextDouble();
                                sc.nextLine();
                                carteira.adicionarInvestimento(new AcaoBolsa(pessoa, saldoA, codA, nomeA, precoA));
                                break;
                            case 3:
                                System.out.print("Saldo inicial: ");
                                double saldoF = sc.nextDouble();
                                sc.nextLine();
                                System.out.print("Nome fundo: ");
                                String nomeF = sc.nextLine();
                                System.out.print("CNPJ gestora: ");
                                String cnpjF = sc.nextLine();
                                System.out.print("Taxa adm anual: ");
                                double taxaF = sc.nextDouble();
                                sc.nextLine();
                                carteira.adicionarInvestimento(new FundoInvestimento(pessoa, saldoF, nomeF, cnpjF, taxaF));
                                break;
                            case 4:
                                System.out.print("Saldo inicial: ");
                                double saldoD = sc.nextDouble();
                                sc.nextLine();
                                System.out.print("Nome empresa emissora: ");
                                String nomeD = sc.nextLine();
                                System.out.print("Taxa juros anual: ");
                                double taxaD = sc.nextDouble();
                                System.out.print("Percentual tributação PJ: ");
                                double tribD = sc.nextDouble();
                                sc.nextLine();
                                carteira.adicionarInvestimento(new Debenture(pessoa, saldoD, nomeD, taxaD, tribD));
                                break;
                        }
                        System.out.println("Investimento adicionado!");
                    } catch (Exception e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;
                case 5:
                    System.out.print("Quantos meses simular? ");
                    int meses = sc.nextInt();
                    sc.nextLine();
                    for (CarteiraInvestimento c : carteiras)
                        for (int m = 0; m < meses; m++)
                            c.simularMes();
                    System.out.println("Simulação concluída.");
                    break;
                case 6:
                    for (CarteiraInvestimento c : carteiras) {
                        System.out.println("\nCarteira de: " + c.getPessoa().getNome());
                        for (Investimento i : c.getInvestimentos())
                            System.out.printf("%s: R$ %.2f\n", i.getClass().getSimpleName(), i.getSaldo());
                        System.out.printf("Total: R$ %.2f\n", c.calcularValorTotalInvestido());
                    }
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
        sc.close();
        System.out.println("Programa encerrado.");
    }
}


//Dados prontos para teste, descomente para executar


//        PessoaFisica pf = new PessoaFisica("Leonard Vale", "leordvale@gmail.com", "123.456.789-00");
//        PessoaJuridica pj = new PessoaJuridica("Nubank", "nubank@contato.com", "11.222.333/4444-55");
//
//        CarteiraInvestimento carteiraPF = new CarteiraInvestimento(pf);
//        CarteiraInvestimento carteiraPJ = new CarteiraInvestimento(pj);
//
//        TesouroPrefixado tesouro = new TesouroPrefixado(pf, 8000, "Tesouro Prefixado 2035", 0.12);
//        AcaoBolsa acao = new AcaoBolsa(pf, 3000, "VALE3", "Vale ON", 65.50);
//        FundoInvestimento fundo = new FundoInvestimento(pf, 12000, "Fundo Multimercado", "98.765.432/0001-11", 0.015);
//        Debenture debenture = new Debenture(pj, 20000, "Capital Livre", 0.09, 0.13);
//
//        carteiraPF.adicionarInvestimento(tesouro);
//        carteiraPF.adicionarInvestimento(acao);
//        carteiraPF.adicionarInvestimento(fundo);
//        carteiraPJ.adicionarInvestimento(debenture);
//
//        try {
//            Debenture debPF = new Debenture(pf, 5000, "Capital Livre", 0.07, 0.15);
//            carteiraPF.adicionarInvestimento(debPF);
//        } catch (Exception e) {
//            System.out.println("Restrição OK: " + e.getMessage());
//        }
//
//        try {
//            TesouroPrefixado tesouroPJ = new TesouroPrefixado(pj, 8000, "Tesouro Prefixado 2035", 0.09);
//            carteiraPJ.adicionarInvestimento(tesouroPJ);
//        } catch (Exception e) {
//            System.out.println("Restrição Tesouro PJ: " + e.getMessage());
//        }
//
//        tesouro.setSaldo(tesouro.getSaldo() + 2000);
//        acao.setSaldo(acao.getSaldo() - 1000);
//
//        for (int mes = 1; mes <= 3; mes++) {
//            System.out.println("\n--- Mês " + mes + " ---");
//            carteiraPF.simularMes();
//            carteiraPJ.simularMes();
//
//            System.out.println("Investimentos PF:");
//            for (Investimento inv : new Investimento[]{tesouro, acao, fundo}) {
//                System.out.printf("%s: R$ %.2f\n", inv.getClass().getSimpleName(), inv.getSaldo());
//            }
//            System.out.printf("Total PF: R$ %.2f\n", carteiraPF.calcularValorTotalInvestido());
//
//            System.out.println("Investimentos PJ:");
//            for (Investimento inv : carteiraPJ.getInvestimentos()) {
//                System.out.printf("%s: R$ %.2f\n", inv.getClass().getSimpleName(), inv.getSaldo());
//            }
//            System.out.printf("Total PJ: R$ %.2f\n", carteiraPJ.calcularValorTotalInvestido());
//        }
//    }