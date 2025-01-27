package br.com.pan.changeaddress.application.services;

import br.com.pan.changeaddress.application.ports.out.ClientRepositoryPort;
import br.com.pan.changeaddress.domain.AddressDomain;
import br.com.pan.changeaddress.domain.ClientDomain;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepositoryPort clientRepositoryPort;

    @InjectMocks
    private ClientService clientService;

    @Test
    void findClientBySocialIdShouldReturnClientDomainWhenRepositoryHasClient() {
        var cpf = "12345678901";
        var clientDomain = new ClientDomain(cpf, "John Doe", null);
        when(clientRepositoryPort.findClientByCpf(eq(cpf)))
                .thenReturn(clientDomain);

        var result = clientService.findClientBySocialId(cpf);

        assertNotNull(result);
        assertEquals(cpf, result.socialId());
        assertEquals("John Doe", result.name());
        verify(clientRepositoryPort).findClientByCpf(cpf);
    }

    @Test
    void findClientBySocialIdShouldReturnNullWhenRepositoryHasNoClient() {
        var cpf = "00000000000";
        when(clientRepositoryPort.findClientByCpf(eq(cpf)))
                .thenReturn(null);

        var result = clientService.findClientBySocialId(cpf);

        assertNull(result);
        verify(clientRepositoryPort).findClientByCpf(cpf);
    }

    @Test
    void updateClientBySocialIdSshouldUpdateClientWhenClientExists() {
        var cpf = "12345678901";
        var oldAddress = new AddressDomain("01001000", "Old Street", "1", "", "OldBairro", "OldCity", "OldState");
        var clientDomain = new ClientDomain(cpf, "John Doe", oldAddress);

        var newAddress = new AddressDomain("02002000", "New Street", "99", null, "NewBairro", "NewCity", "NS");

        when(clientRepositoryPort.findClientByCpf(eq(cpf)))
                .thenReturn(clientDomain);

        var updatedClient = new ClientDomain(cpf, "John Doe", newAddress);
        when(clientRepositoryPort.updateClientByCpf(eq(cpf), any(ClientDomain.class)))
                .thenReturn(updatedClient);

        var result = clientService.updateClientBySocialId(cpf, newAddress);

        assertNotNull(result);
        assertEquals(cpf, result.socialId());
        assertEquals("John Doe", result.name());
        assertEquals("New Street", result.address().street());

        verify(clientRepositoryPort).findClientByCpf(cpf);
        verify(clientRepositoryPort).updateClientByCpf(eq(cpf), any(ClientDomain.class));
    }

    @Test
    void updateClientBySocialIdShouldReturnNullWhenClientDoesNotExist() {
        var cpf = "99999999999";
        var newAddress = new AddressDomain("03003000", "Another Street", "100", "Apt 1", "BairroXYZ", "SomeCity", "SC");

        when(clientRepositoryPort.findClientByCpf(eq(cpf)))
                .thenReturn(null);

        var result = clientService.updateClientBySocialId(cpf, newAddress);

        assertNull(result);
        verify(clientRepositoryPort).findClientByCpf(cpf);
        verify(clientRepositoryPort, never()).updateClientByCpf(eq(cpf), any(ClientDomain.class));
    }
}
