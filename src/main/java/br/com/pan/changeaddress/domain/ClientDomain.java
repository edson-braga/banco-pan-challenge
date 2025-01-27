package br.com.pan.changeaddress.domain;

public record ClientDomain(
        String socialId,
        String name,
        AddressDomain address
) {}
