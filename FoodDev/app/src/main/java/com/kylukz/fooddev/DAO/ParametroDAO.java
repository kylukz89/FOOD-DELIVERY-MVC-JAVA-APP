package com.kylukz.fooddev.DAO;

public class ParametroDAO {

    public boolean getSeAppLiberadoPagamento(int modoPagamento) {
        switch (modoPagamento) {
            case 0: // Crédito
                break;
            case 1: // Débito
                break;
            case 2: // Dinheiro no ato da entrega
                break;

        }
        return false;
    }

}
