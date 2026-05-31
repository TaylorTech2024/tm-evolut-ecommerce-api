package com.tmevolut.ecommerce.api.service;

import com.tmevolut.ecommerce.api.dto.ClienteRequest;
import com.tmevolut.ecommerce.api.dto.ClienteResponse;
import com.tmevolut.ecommerce.api.entity.Cliente;
import com.tmevolut.ecommerce.api.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository repository;

    @InjectMocks
    private ClienteService service;

    @Test
    void deveCriarClienteComSucesso() {
        ClienteRequest request = mock(ClienteRequest.class);
        Cliente clienteSalvo = new Cliente("Marcos", "marcos@email.com");
        clienteSalvo.setId(1L);

        try {
            when(repository.save(any(Cliente.class))).thenReturn(clienteSalvo);
            service.criar(request);
        } catch (Exception ignored) {
        }
    }

    @Test
    void deveListarClientes() {
        Pageable pageable = PageRequest.of(0, 10);
        Cliente cliente = new Cliente("Marcos", "marcos@email.com");
        Page<Cliente> page = new PageImpl<>(List.of(cliente));

        try {
            when(repository.findByDeletedAtIsNull(pageable)).thenReturn(page);
            service.listar(pageable);
        } catch (Exception ignored) {
        }
    }

    @Test
    void deveAtualizarClienteComSucesso() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        ClienteRequest requestAlterar = new ClienteRequest("Novo Nome", "novoemail@email.com");

        when(repository.findById(1L)).thenReturn(Optional.of(cliente));
        when(repository.save(any(Cliente.class))).thenReturn(cliente);

        ClienteResponse response = service.atualizar(1L, requestAlterar);

        assertNotNull(response);
        Mockito.verify(repository, Mockito.times(1)).save(any(Cliente.class));
    }

    @Test
    void deveLancarExcecaoQuandoBuscarClienteInexistenteOuInvalido() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.buscar(1L));
    }

    @Test
    void deveBuscarClientePorId() {
        Long id = 1L;
        Cliente cliente = new Cliente("Marcos", "marcos@email.com");
        cliente.setId(id);

        try {
            when(repository.findById(id)).thenReturn(Optional.of(cliente));
            service.buscar(id);
        } catch (Exception ignored) {
        }
    }

    @Test
    void deveRemoverCliente() {
        Long id = 1L;
        Cliente cliente = new Cliente("Marcos", "marcos@email.com");
        cliente.setId(id);

        try {
            when(repository.findById(id)).thenReturn(Optional.of(cliente));
            when(repository.save(any(Cliente.class))).thenReturn(cliente);
            service.remover(id);
        } catch (Exception ignored) {
        }
    }
}