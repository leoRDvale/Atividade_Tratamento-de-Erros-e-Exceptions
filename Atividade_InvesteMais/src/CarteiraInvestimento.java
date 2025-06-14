import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class CarteiraInvestimento {
    private Pessoa pessoa;
    private List<Investimento> investimentos;

    public CarteiraInvestimento(Pessoa pessoa) {
        this.pessoa = pessoa;
        this.investimentos = new ArrayList<>();
    }

    public List<Investimento> getInvestimentos() {
        return investimentos;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void adicionarInvestimento(Investimento investimento) {
        if (investimento.getPessoa().equals(this.pessoa)) {
            investimentos.add(investimento);
        } else {
            throw new IllegalArgumentException("O investimento não pertence à uma pessoa da carteira.");
        }
    }

    public double calcularValorTotalInvestido() {
        double total = 0;
        for (Investimento i : investimentos) {
            total += i.getSaldo();
        }
        return total;
    }

    public void simularMes() {
        for (Investimento i : investimentos) {
            i.simularPassagemDeMes();
        }
    }

    public void exibirInvestimentos() {
        for (Investimento i : investimentos) {
            JOptionPane.showMessageDialog(null, String.format("Saldo atual do investimento (%s): R$ %.2f", i.getClass().getSimpleName(), i.getSaldo()), "Investimentos", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
