package br.com.pan.changeadress.domain;

public record ClientDomain(
        String cpf,
        String nome,
        AddressDomain address
) {}
