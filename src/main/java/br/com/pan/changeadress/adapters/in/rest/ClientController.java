package br.com.pan.changeadress.adapters.in.rest;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
public class ClientController {

    @GetMapping("/{cpf}")
    public String consultarCliente(@PathVariable String cpf) {
        // Lógica simulada para retornar dados do cliente
        return "Dados cadastrais do cliente com CPF: " + cpf;
    }

    @PutMapping("/{cpf}/endereco")
    public String alterarEndereco(@PathVariable String cpf, @RequestBody String novoEndereco) {
        // Lógica simulada para atualizar o endereço do cliente
        return "Endereço do cliente com CPF " + cpf + " atualizado para: " + novoEndereco;
    }
}
