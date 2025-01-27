package br.com.pan.changeaddress.adapters.out.persistence;

import br.com.pan.changeaddress.adapters.exceptions.ClientNotFoundException;
import br.com.pan.changeaddress.adapters.out.persistence.entities.ClientEntity;
import br.com.pan.changeaddress.domain.ClientDomain;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ClientRepositoryAdapterTest {

    @Mock
    private ClientRepository jpaClientRepository;

    @InjectMocks
    private ClientRepositoryAdapter adapter;

    @Test
    void findClientByCpfWhenClientExistsShouldReturnClientDomain() {
        var cpf = "12345678901";

        var entity = EntityMocks.mockClientEntity(cpf, "John Doe");
        when(jpaClientRepository.findById(eq(cpf)))
                .thenReturn(Optional.of(entity));

        var result = adapter.findClientByCpf(cpf);

        assertNotNull(result);
        assertEquals(cpf, result.socialId());
        assertEquals("John Doe", result.name());
        verify(jpaClientRepository).findById(cpf);
    }

    @Test
    void findClientByCpfWhenClientNotFoundShouldThrowClientNotFoundException() {
        var cpf = "99999999999";

        when(jpaClientRepository.findById(eq(cpf)))
                .thenReturn(Optional.empty());

        var ex = assertThrows(ClientNotFoundException.class,
                () -> adapter.findClientByCpf(cpf));
        assertEquals("CLIENT_NOT_FOUND", ex.getErrorCode());
    }

    @Test
    void updateClientByCpfWhenClientExistsShouldUpdateAndReturnDomain() {
        var cpf = "12345678901";
        var domain = new ClientDomain(cpf, "John Updated", null);

        when(jpaClientRepository.findById(any()))
                .thenReturn(Optional.of(EntityMocks.mockClientEntity(cpf, "John Old")));

        var savedEntity = EntityMocks.mockClientEntity(cpf, "John Updated");
        when(jpaClientRepository.save(any(ClientEntity.class)))
                .thenReturn(savedEntity);

        var result = adapter.updateClientByCpf(cpf, domain);

        assertNotNull(result);
        assertEquals(cpf, result.socialId());
        assertEquals("John Updated", result.name());

        verify(jpaClientRepository).findById(cpf);
        verify(jpaClientRepository).save(any());
    }

    @Test
    void updateClientByCpfWhenClientNotFoundShouldThrowClientNotFoundException() {
        var cpf = "77788899900";
        var clientDomain = new ClientDomain(cpf, "NoSuchClient", null);

        when(jpaClientRepository.findById(eq(cpf)))
                .thenReturn(Optional.empty());

        var ex = assertThrows(ClientNotFoundException.class,
                () -> adapter.updateClientByCpf(cpf, clientDomain));
        assertEquals("CLIENT_NOT_FOUND", ex.getErrorCode());
        verify(jpaClientRepository, never()).save(any());
    }

    static class EntityMocks {
        static ClientEntity mockClientEntity(String cpf, String nome) {
            var e = new ClientEntity();
            e.setCpf(cpf);
            e.setNome(nome);
            return e;
        }
    }
}
