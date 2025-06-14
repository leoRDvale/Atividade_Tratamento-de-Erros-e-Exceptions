public class Debenture extends Investimento {
    private String nomeEmpresaEmissora;
    private double taxaJurosAnualDebenture;
    private double percentualTributacaoPJ;

    public Debenture(Pessoa pessoa, double saldoInicial, String nomeEmpresaEmissora, double taxaJurosAnualDebenture, double percentualTributacaoPJ) {
        super(pessoa, saldoInicial);
        if (!(pessoa instanceof PessoaJuridica)) {
            throw new IllegalArgumentException("Somente Pessoa Jurídica pode adquirir Debêntures.");
        }
        this.nomeEmpresaEmissora = nomeEmpresaEmissora;
        this.taxaJurosAnualDebenture = taxaJurosAnualDebenture;
        this.percentualTributacaoPJ = percentualTributacaoPJ;
    }

    @Override
    public double calcularSaldoProjetado(int numeroMeses) {
        double taxaMensal = taxaJurosAnualDebenture / 12.0;
        double saldoBruto = getSaldo() * Math.pow(1 + taxaMensal, numeroMeses);
        double rendimento = saldoBruto - getSaldo();
        double imposto = rendimento * percentualTributacaoPJ;
        return getSaldo() + rendimento - imposto;
    }

    @Override
    public void simularPassagemDeMes() {
        double rendimento = getSaldo() * (taxaJurosAnualDebenture / 12.0);
        setSaldo(getSaldo() + rendimento);
    }
}
