public class PessoaJuridica extends Pessoa {
    private String cnpj;

    public PessoaJuridica(String nome, String email, String cnpj) {
        super(nome, email);
        this.cnpj = cnpj;
    }

    public String getNome() {
        return super.getNome();
    }

    @Override
    public String getIdentificadorDocumento() {
        return cnpj;
    }

    public String getCnpj() {
        return cnpj;
    }


    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}
