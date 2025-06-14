import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        //Dados de exemplo ao final do código, descomente para testar. Linha 163
        

        List<Pessoa> pessoas = new ArrayList<>();
        List<CarteiraInvestimento> carteiras = new ArrayList<>();

        while (true) {
            int op = Integer.parseInt(JOptionPane.showInputDialog("      --- Investe Mais ---\n--- Sistema de Investimentos ---\n\n"
                    +"Escolha uma opção:\n\n" +
                    "1. Cadastrar Pessoa Física\n" +
                    "2. Cadastrar Pessoa Jurídica\n" +
                    "3. Criar Carteira de Investimento\n" +
                    "4. Adicionar Investimento à Carteira\n" +
                    "5. Simular Passagem de Meses\n" +
                    "6. Exibir Carteiras e Investimentos\n" +
                    "0. Sair"));

            if (op == 0) break;

            switch (op) {
                case 1:
                    String nomePF = JOptionPane.showInputDialog("Nome da Pessoa Física:");
                    String emailPF = JOptionPane.showInputDialog("Email da Pessoa Física:");
                    String cpf = JOptionPane.showInputDialog("CPF da Pessoa Física:");
                    pessoas.add(new PessoaFisica(nomePF, emailPF, cpf));
                    JOptionPane.showMessageDialog(null, "Pessoa Física cadastrada com sucesso!", "Cadastro", JOptionPane.INFORMATION_MESSAGE);
                    break;

                case 2:
                    String nomePJ = JOptionPane.showInputDialog("Nome da Pessoa Jurídica:");
                    String emailPJ = JOptionPane.showInputDialog("Email da Pessoa Jurídica:");
                    String cnpj = JOptionPane.showInputDialog("CNPJ da Pessoa Jurídica:");
                    pessoas.add(new PessoaJuridica(nomePJ, emailPJ, cnpj));
                    JOptionPane.showMessageDialog(null, "Pessoa Jurídica cadastrada com sucesso!", "Cadastro", JOptionPane.INFORMATION_MESSAGE);
                    break;

                case 3:
                    if (pessoas.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Nenhum cadastro encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                        break;
                    }

                    StringBuilder listaPessoas = new StringBuilder("Escolha a pessoa:\n");
                    for (int i = 0; i < pessoas.size(); i++) {
                        listaPessoas.append(i).append(" - ").append(pessoas.get(i).getNome()).append("\n");
                    }
                    String input = JOptionPane.showInputDialog(null, listaPessoas.toString(), "Selecionar Pessoa", JOptionPane.QUESTION_MESSAGE);

                    if (input != null) {
                        try {
                            int idxPessoa = Integer.parseInt(input.trim());
                            carteiras.add(new CarteiraInvestimento(pessoas.get(idxPessoa)));
                            JOptionPane.showMessageDialog(null, "Carteira criada!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "Entrada inválida.", "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    break;

                case 4:
                    if (carteiras.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Nenhuma carteira encontrada.", "Erro", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                    StringBuilder listaCarteiras = new StringBuilder("Escolha a carteira:\n");
                    for (int i = 0; i < carteiras.size(); i++) {
                        listaCarteiras.append(i)
                                .append(" - ")
                                .append(carteiras.get(i).getInvestimentos().size())
                                .append(" investimentos - ")
                                .append(carteiras.get(i).getPessoa().getNome())
                                .append("\n");
                    }
                    String inputCart = JOptionPane.showInputDialog(null, listaCarteiras.toString(), "Selecionar Carteira", JOptionPane.QUESTION_MESSAGE);
                    if (inputCart == null) break;
                    try {
                        int idxCart = Integer.parseInt(inputCart.trim());
                        CarteiraInvestimento carteira = carteiras.get(idxCart);
                        Pessoa pessoa = carteira.getPessoa();

                        String tipoStr = JOptionPane.showInputDialog(null, "Tipos:\n1-Tesouro\n2-Ação\n3-Fundo\n4-Debenture\n\nDigite o número do tipo:", "Tipo de Investimento", JOptionPane.QUESTION_MESSAGE);
                        if (tipoStr == null) break;
                        int tipo = Integer.parseInt(tipoStr.trim());

                        switch (tipo) {
                            case 1: {
                                double saldoT = Double.parseDouble(JOptionPane.showInputDialog("Saldo inicial:"));
                                String nomeT = JOptionPane.showInputDialog("Nome título:");
                                double taxaT = Double.parseDouble(JOptionPane.showInputDialog("Taxa anual (ex: 0.05 para 5%):"));
                                carteira.adicionarInvestimento(new TesouroPrefixado(pessoa, saldoT, nomeT, taxaT));
                                break;
                            }
                            case 2: {
                                double saldoA = Double.parseDouble(JOptionPane.showInputDialog("Saldo inicial:"));
                                String codA = JOptionPane.showInputDialog("Código ação:");
                                String nomeA = JOptionPane.showInputDialog("Nome empresa:");
                                double precoA = Double.parseDouble(JOptionPane.showInputDialog("Preço unitário:"));
                                carteira.adicionarInvestimento(new AcaoBolsa(pessoa, saldoA, codA, nomeA, precoA));
                                break;
                            }
                            case 3: {
                                double saldoF = Double.parseDouble(JOptionPane.showInputDialog("Saldo inicial:"));
                                String nomeF = JOptionPane.showInputDialog("Nome fundo:");
                                String cnpjF = JOptionPane.showInputDialog("CNPJ gestora:");
                                double taxaF = Double.parseDouble(JOptionPane.showInputDialog("Taxa adm anual (ex: 0.01 para 1%):"));
                                carteira.adicionarInvestimento(new FundoInvestimento(pessoa, saldoF, nomeF, cnpjF, taxaF));
                                break;
                            }
                            case 4: {
                                double saldoD = Double.parseDouble(JOptionPane.showInputDialog("Saldo inicial:"));
                                String nomeD = JOptionPane.showInputDialog("Nome empresa emissora:");
                                double taxaD = Double.parseDouble(JOptionPane.showInputDialog("Taxa juros anual (ex: 0.09 para 9%):"));
                                double tribD = Double.parseDouble(JOptionPane.showInputDialog("Percentual tributação PJ (ex: 0.13 para 13%):"));
                                carteira.adicionarInvestimento(new Debenture(pessoa, saldoD, nomeD, taxaD, tribD));
                                break;
                            }
                            default:
                                JOptionPane.showMessageDialog(null, "Tipo inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
                                break;
                        }
                        JOptionPane.showMessageDialog(null, "Investimento adicionado!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                    break;

                case 5: {
                    String mesesStr = JOptionPane.showInputDialog(null, "Quantos meses simular?", "Simulação", JOptionPane.QUESTION_MESSAGE);
                    if (mesesStr == null) break;
                    try {
                        int meses = Integer.parseInt(mesesStr.trim());
                        for (CarteiraInvestimento c : carteiras)
                            for (int m = 0; m < meses; m++)
                                c.simularMes();
                        JOptionPane.showMessageDialog(null, "Simulação concluída.\nResultado disponivel no menu 6 - Exibir Carteiras", "Simulação", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Entrada inválida.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                }
                case 6: {
                    if (carteiras.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Nenhuma carteira encontrada.", "Erro", JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                    StringBuilder sb = new StringBuilder();
                    for (CarteiraInvestimento c : carteiras) {
                        sb.append("\nCarteira de: ").append(c.getPessoa().getNome()).append("\n");
                        for (Investimento i : c.getInvestimentos())
                            sb.append(String.format("%s: R$ %.2f\n", i.getClass().getSimpleName(), i.getSaldo()));
                        sb.append(String.format("Total: R$ %.2f\n", c.calcularValorTotalInvestido()));
                    }
                    JOptionPane.showMessageDialog(null, sb.toString(), "Carteiras e Investimentos", JOptionPane.INFORMATION_MESSAGE);
                    break;
                }
                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida.", "Erro", JOptionPane.ERROR_MESSAGE);

        JOptionPane.showMessageDialog(null, "Programa finalizado.", "Saída", JOptionPane.INFORMATION_MESSAGE);
                    return;
            }
        }
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