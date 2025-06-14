public class AcaoBolsa extends Investimento{

    private String codigoAcao;
    private String nomeEmpresa;
    private double taxaCorretagemFixaMensal;

    public AcaoBolsa(Pessoa pessoa, double saldoInicial, String codigoAcao, String nomeEmpresa, double taxaCorretagemFixaMensal) {
        super(pessoa, saldoInicial);
        this.codigoAcao = codigoAcao;
        this.nomeEmpresa = nomeEmpresa;
        this.taxaCorretagemFixaMensal = taxaCorretagemFixaMensal;
    }

    @Override
    public double calcularSaldoProjetado(int numeroMeses) {
        double saldoProjetado = getSaldo() * Math.pow(1.008, numeroMeses);
        saldoProjetado -= taxaCorretagemFixaMensal * numeroMeses;
        return Math.max(saldoProjetado, 0);
    }

    @Override
    public void simularPassagemDeMes() {
        setSaldo(getSaldo() * 1.008);
        setSaldo(getSaldo() - taxaCorretagemFixaMensal);
        if (getSaldo() < 0) setSaldo(0);
    }


}
