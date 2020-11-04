package com.kylukz.fooddev.Valid;


/**
 *
 */
public class ValidadorLogin {

    /**
     * Valida o formulário de cadastro PIN por CPF
     * @author      Igor Maximo <igormaximo_1989@hotmail.com>
     * @date        16/06/2020
     */
    public Object[] setValidaFormularioLoginPorPin(String pin, String senha) {
        Object[] valida = new Object[3];
        valida[0] = true;
        valida[1] = "";
        valida[2] = 0;
        // PIN
        if (pin.length() != 8) {
            valida[0] = false;
            valida[1] = "Formato de PIN inválido!";
            valida[2] = 0;
            return valida;
        }
        // Senha
        if (senha.length() < 4) {
            valida[0] = false;
            valida[1] = "Senha curta demais!";
            valida[2] = 1;
            return valida;
        }
        return valida;
    }

}
