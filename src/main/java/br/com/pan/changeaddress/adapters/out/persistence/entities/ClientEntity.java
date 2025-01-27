package br.com.pan.changeaddress.adapters.out.persistence.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "cliente")
public class ClientEntity {

    @Id
    private String cpf;
    private String nome;

    @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private AddressEntity address;

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

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }
}
