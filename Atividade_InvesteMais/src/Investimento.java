public abstract class Investimento {
    private Pessoa pessoa;
    private double saldo;

    public Investimento(Pessoa pessoa, double saldoInicial) {
        this.pessoa = pessoa;
        this.saldo = saldoInicial;
    }

    public void aplicar(double valor) {
        if (valor > 0) saldo += valor;
    }

    public void resgatar(double valor) {
        if (valor > 0 && valor <= saldo) saldo -= valor;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = Math.max(saldo, 0);
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public abstract double calcularSaldoProjetado(int numeroMeses);
    public abstract void simularPassagemDeMes();
}
