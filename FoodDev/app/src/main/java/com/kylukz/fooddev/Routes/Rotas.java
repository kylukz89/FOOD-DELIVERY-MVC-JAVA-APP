package com.kylukz.fooddev.Routes;

import com.kylukz.fooddev.Toolbox.VariaveisGlobais;

/**
 * Arquivo que contém as rotas de todas as chamadas
 * de função que estão no servidor
 *
 * @author         Igor Maximo <igormaximo_1989@hotmail.com>
 * @date           13/04/2020
 */
public class Rotas {

    ///////////////////////////////////// ROTA/// ////////////////////////////////
    public static String ROTA_PADRAO = VariaveisGlobais.IP + "Rotas.php?";                                          // Arquivo de todas as rotas.
    ///////////////////////////////////// SELECTS ////////////////////////////////
    public static String SELECT_USUARIO = "func=getUsuario" + VariaveisGlobais.AUT_GET;                             // Traz as credênciais do usuário quando ele escolhe autenticação simples
    public static String SELECT_ESQUECI_SENHA_USUARIO = "func=getEsqueciSenha" + VariaveisGlobais.AUT_GET;          // Traz as credênciais do usuário quando ele escolhe autenticação simples
    public static String SELECT_EMPRESAS = "func=selectEmpresas" + VariaveisGlobais.AUT_GET;                        // Traz todas as empresas (Lista geral com param de consulta)
    public static String SELECT_GRUPOS_EMPRESAS = "func=selectGruposEmpresa" + VariaveisGlobais.AUT_GET;            // Traz todos os grupos de uma determinada empresa (Lista geral)
    public static String SELECT_LISTA_PRODUTOS_GRUPOS_EMPRESA = "func=selectProdutosGrupoEmpresa" + VariaveisGlobais.AUT_GET;
    public static String SELECT_VERSIONAMENTO = "func=selectVersionamentoEStatus" + VariaveisGlobais.AUT_GET;       // Traz o versionameno do app (mais recente para confrontar)
    ///////////////////////////////////// INSERT ///////////////////////////////////
    public static String INSERT_CADASTRA_NOVO_USUARIO = "func=cadastraUsuario" + VariaveisGlobais.AUT_GET;          // Realiza o cadastro do novo cliente por PIN
    ///////////////////////////////////// UPDATE ///////////////////////////////////
    public static String INSERT_COMPLETA_CADASTRO_USUARIO = "func=setCompletarCadastroUsuario" + VariaveisGlobais.AUT_GET;          // Realiza o cadastro do novo cliente por PIN
    public static String INSERT_FIREBASE_FCM_TOKEN_USUARIO = "func=setTokenFirebasePushUsuario" + VariaveisGlobais.AUT_GET;          // Token Firebase FCM
}



