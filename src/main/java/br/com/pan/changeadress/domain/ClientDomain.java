package br.com.pan.changeadress.domain;

public class ClientDomain {

    private String cpf;
    private String nome;
    private AddressDomain address;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setAddress(AddressDomain address) {
        this.address = address;
    }

    public AddressDomain getAddress() {
        return address;
    }
}
