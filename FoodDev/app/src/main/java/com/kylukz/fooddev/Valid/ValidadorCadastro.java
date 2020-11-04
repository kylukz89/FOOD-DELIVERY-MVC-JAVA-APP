package com.kylukz.fooddev.Valid;

import com.kylukz.fooddev.JavaBeans.EntidadeCadastroUsuario;


/**
 *
 */
public class ValidadorCadastro {

    /**
     * Valida o formulário de cadastro PIN por CPF
     * @author      Igor Maximo <igormaximo_1989@hotmail.com>
     * @date        16/06/2020
     */
    public Object[] setValidaFormularioCadastroPinPorCPF(EntidadeCadastroUsuario entidadeCadastroUsario) {
        Object[] valida = new Object[3];
        valida[0] = true;
        valida[1] = "";
        valida[2] = 0;
        // Nome
        if (entidadeCadastroUsario.getNome().length() < 3) {
            valida[0] = false;
            valida[1] = "Nome curto demais!";
            valida[2] = 0;
            return valida;
        }
        if (entidadeCadastroUsario.getNome().length() > 30) {
            valida[0] = false;
            valida[1] = "Nome longo demais! Máximo 30 caracteres.";
            valida[2] = 0;
            return valida;
        }
        // Sobrenome
        if (entidadeCadastroUsario.getSobreNome().length() < 3) {
            valida[0] = false;
            valida[1] = "Sobrenome curto demais!";
            valida[2] = 1;
            return valida;
        }
        if (entidadeCadastroUsario.getSobreNome().length() > 40) {
            valida[0] = false;
            valida[1] = "Sobrenome longo demais! Máximo 40 caracteres.";
            valida[2] = 1;
            return valida;
        }
        // CPF
        if (!ValidaCPF.valida(entidadeCadastroUsario.getCpf().replaceAll("[.]", "").replaceAll("[-]", "").replaceAll("[/]", "").replaceAll("[(]", "").replaceAll("[ ]", "").replaceAll("[:]", "").replaceAll("[)]", ""))) {
            valida[0] = false;
            valida[1] = "CPF inválido!";
            valida[2] = 2;
            return valida;
        }
        // Data Nascimento
        if (entidadeCadastroUsario.getDataNascimento().length() < 10 || !entidadeCadastroUsario.getDataNascimento().contains("/")) {
            valida[0] = false;
            valida[1] = "Data de nascimento inválida!";
            valida[2] = 3;
            return valida;
        }
        // Celular
        if (entidadeCadastroUsario.getCelular().length() != 15) {
            valida[0] = false;
            valida[1] = "Celular inválido!";
            valida[2] = 4;
            return valida;
        }
        // E-mail inválido
        if (entidadeCadastroUsario.getEmail().length() < 10 || !entidadeCadastroUsario.getEmail().contains("@")) {
            valida[0] = false;
            valida[1] = "E-mail inválido!";
            valida[2] = 5;
            return valida;
        }
        // Senha
        if (entidadeCadastroUsario.getSenha().length() < 5) {
            valida[0] = false;
            valida[1] = "Senha curta demais!";
            valida[2] = 6;
            return valida;
        }
        // Senha
        if (entidadeCadastroUsario.getSenha().length() > 50) {
            valida[0] = false;
            valida[1] = "Senha longa demais!";
            valida[2] = 6;
            return valida;
        }
        return valida;
    }

    /**
     * Valida o formulário de completar cadastro
     * @author      Igor Maximo <igormaximo_1989@hotmail.com>
     * @date        20/06/2020
     */
    public Object[] setValidaFormularioCompletarCadastro(EntidadeCadastroUsuario entidadeCadastroUsario) {
        Object[] valida = new Object[3];
        valida[0] = true;
        valida[1] = "";
        valida[2] = 0;
        // Nome
        if (entidadeCadastroUsario.getCep().length() != 8 || !entidadeCadastroUsario.getCep().replace("-", "").matches("-?\\d+(\\.\\d+)?")) {
            valida[0] = false;
            valida[1] = "CEP inválido!";
            valida[2] = 0;
            return valida;
        }
        // Endereço
        if (entidadeCadastroUsario.getEndereco().length() < 8) {
            valida[0] = false;
            valida[1] = "Endereço curto demais!";
            valida[2] = 1;
            return valida;
        }
        if (entidadeCadastroUsario.getEndereco().length() > 100) {
            valida[0] = false;
            valida[1] = "Endereço longo demais!";
            valida[2] = 1;
            return valida;
        }
        if (entidadeCadastroUsario.getEndereco().contains("  ")) {
            valida[0] = false;
            valida[1] = "Endereço com muitos espaços!";
            valida[2] = 1;
            return valida;
        }
        // nº
        if (entidadeCadastroUsario.getNumero().length() < 1) {
            valida[0] = false;
            valida[1] = "nº curto demais inválido";
            valida[2] = 2;
            return valida;
        }
        if (entidadeCadastroUsario.getNumero().length() > 6) {
            valida[0] = false;
            valida[1] = "nº longo demais inválido";
            valida[2] = 2;
            return valida;
        }
        // Bairro
        if (entidadeCadastroUsario.getBairro().length() < 5) {
            valida[0] = false;
            valida[1] = "Bairro curto demais!";
            valida[2] = 3;
            return valida;
        }
        if (entidadeCadastroUsario.getBairro().length() > 80) {
            valida[0] = false;
            valida[1] = "Bairro longo demais!";
            valida[2] = 3;
            return valida;
        }
        if (entidadeCadastroUsario.getBairro().contains("  ")) {
            valida[0] = false;
            valida[1] = "Bairro com muitos espaços!";
            valida[2] = 3;
            return valida;
        }
        // Cidade
        if (entidadeCadastroUsario.getCidade().length() < 5) {
            valida[0] = false;
            valida[1] = "Cidade curta demais!";
            valida[2] = 4;
            return valida;
        }
        if (entidadeCadastroUsario.getCidade().length() > 80) {
            valida[0] = false;
            valida[1] = "Cidade longa demais!";
            valida[2] = 4;
            return valida;
        }
        if (entidadeCadastroUsario.getCidade().contains("  ")) {
            valida[0] = false;
            valida[1] = "Cidade com muitos espaços!";
            valida[2] = 4;
            return valida;
        }
        return valida;
    }

}
