public class FundoInvestimento extends Investimento{

    private String nomeFundo;
    private String cnpjGestora;
    private double taxaAdministracaoAnual;

    public FundoInvestimento(Pessoa pessoa, double saldoInicial, String nomeFundo, String cnpjGestora, double taxaAdministracaoAnual) {
        super(pessoa, saldoInicial);
        this.nomeFundo = nomeFundo;
        this.cnpjGestora = cnpjGestora;
        this.taxaAdministracaoAnual = taxaAdministracaoAnual;
    }

    @Override
    public double calcularSaldoProjetado(int numeroMeses) {
        double saldoAtual = getSaldo();
        double taxaAdmMensal = taxaAdministracaoAnual / 12.0;
        for (int i = 0; i < numeroMeses; i++) {
            double rendimento = saldoAtual * 0.01;
            double taxaAdm = saldoAtual * taxaAdmMensal;
            saldoAtual += rendimento - taxaAdm;
        }
        return Math.max(saldoAtual, 0);
    }

    @Override
    public void simularPassagemDeMes() {
        double rendimento = getSaldo() * 0.01;
        double taxaAdm = getSaldo() * (taxaAdministracaoAnual / 12.0);
        setSaldo(getSaldo() + rendimento - taxaAdm);
        if (getSaldo() < 0) setSaldo(0);
    }
}
