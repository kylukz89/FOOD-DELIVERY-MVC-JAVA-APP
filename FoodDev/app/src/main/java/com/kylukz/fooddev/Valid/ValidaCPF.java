package com.kylukz.fooddev.Valid;

import java.util.InputMismatchException;

/**
 * Valida um CPF de acordo com padrão da receita
 * RETORNO:
 * FALSE                   - CPF inválido
 * TRUE                   - CPF válido
 *
 * @author Igor Maximo <igormaximo_1989@hotmail.com>
 * @criado 19/02/2019
 * @editado 02/03/2019
 */
public final class ValidaCPF {

    public static boolean valida(String CPF) {
        // considera-se erro CPF's formados por uma sequencia de numeros iguais
        if (CPF.equals("00000000000") || CPF.equals("11111111111") || CPF.equals("22222222222") || CPF.equals("33333333333") || CPF.equals("44444444444") || CPF.equals("55555555555") || CPF.equals("66666666666") || CPF.equals("77777777777") || CPF.equals("88888888888") || CPF.equals("99999999999") || (CPF.length() != 11))
            return (false);

        char dig10, dig11;
        int sm, i, r, num, peso;

        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
            // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                // converte o i-esimo caractere do CPF em um numero:
                // por exemplo, transforma o caractere '0' no inteiro 0
                // (48 eh a posicao de '0' na tabela ASCII)
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else
                dig10 = (char) (r + 48); // converte no respectivo caractere numerico

            // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else
                dig11 = (char) (r + 48);

            // Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                return (true);
            else
                return (false);
        } catch (InputMismatchException erro) {
            return (false);
        }
    }

/*
        String[] crescente = new String[cpf.length()-2]; // CPF
        String[] decrescente = new String[]{"10","9","8","7","6","5","4","3","2"}; // Pesos padrão
        String[] decrescente2 = new String[]{"11","10","9","8","7","6","5","4","3","2"}; // Pesos padrão
        String[] crescente2;
        String[] crescSegDig = new String[cpf.length()-2];

        // 123.456.789-10
        for (int i = 0, j = cpf.length(); i < cpf.length()-2; i++, j--) {
            crescente[i] = cpf.substring(i, i+1);
        }

        int[] multiplicaValoresColuna = new int[crescente.length];
        int[] multiplicaValoresSegCol = new int[crescSegDig.length+1];

        int soma = 0, resto = 0;

        for (int i = 0; i < crescente.length; i++) {
            multiplicaValoresColuna[i] = Integer.parseInt(crescente[i]) * Integer.parseInt(decrescente[i]);
            soma += multiplicaValoresColuna[i];
        }

        resto = soma % 11; // padrão 11 para cálculo
        resto = 11 - resto;

        String digitoVerificador1 = "";

        if (resto > 9) {
            digitoVerificador1 = "0";
        } else {
            digitoVerificador1 = String.valueOf(resto);
        }
        crescente2 = new String[crescente.length+2];

        int k = 0;
        for (k = 0; k < crescente.length; k++) {
            crescente2[k] = crescente[k];
        }

        crescente2[crescente2.length-2] = digitoVerificador1;

        soma = 0;
        for (int i = 0; i < crescente2.length-1; i++) {
            multiplicaValoresSegCol[i] = Integer.parseInt(crescente2[i]) * Integer.parseInt(decrescente2[i]);
            soma += multiplicaValoresSegCol[i];
        }
        resto = soma % 11; // padrão 11 para cálculo

        soma = (resto -  11) * -1;

        crescente2[crescente2.length-1] = soma+"";
        String montaCPF = "";

        for (int i = 0; i < crescente2.length; i++) {
            montaCPF += crescente2[i];
        }

        // Se o CPF de entrada é igual a saída dos cálculos acima.
        if (montaCPF.equals((cpf))) { // comparara se
            return true;
        } else {
            return false;
        }
*/

    private static int randomiza(int n) {
        int ranNum = (int) (Math.random() * n);
        return ranNum;
    }

    private static int mod(int dividendo, int divisor) {
        return (int) Math.round(dividendo - (Math.floor(dividendo / divisor) * divisor));
    }


}
