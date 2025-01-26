package br.com.pan.changeadress.domain;

public record ClientDomain(
        String socialId,
        String name,
        AddressDomain address
) {}
