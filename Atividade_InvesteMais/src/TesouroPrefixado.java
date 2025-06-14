public class TesouroPrefixado extends Investimento {
    private String nomeTitulo;
    private double taxaJurosAnual;
    private final double percentualImpostoRenda = 0.15;

    public TesouroPrefixado(Pessoa pessoa, double saldoInicial, String nomeTitulo, double taxaJurosAnual) {
        super(pessoa, saldoInicial);
        if (!(pessoa instanceof PessoaFisica)) {
            throw new IllegalArgumentException("Tesouro Prefixado só pode ser adquirido por Pessoa Física.");
        }
        this.nomeTitulo = nomeTitulo;
        this.taxaJurosAnual = taxaJurosAnual;
    }

    @Override
    public double calcularSaldoProjetado(int numeroMeses) {
        double taxaMensal = taxaJurosAnual / 12.0;
        double saldoBruto = getSaldo() * Math.pow(1 + taxaMensal, numeroMeses);
        double rendimento = saldoBruto - getSaldo();
        double imposto = rendimento * percentualImpostoRenda;
        return getSaldo() + rendimento - imposto;
    }

    @Override
    public void simularPassagemDeMes() {
        double rendimento = getSaldo() * (taxaJurosAnual / 12.0);
        setSaldo(getSaldo() + rendimento);
    }
}
