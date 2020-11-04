package com.kylukz.fooddev.DAO;


import android.net.Uri;

import com.kylukz.fooddev.JavaBeans.EntidadeCadastroUsuario;
import com.kylukz.fooddev.Toolbox.Json;
import com.kylukz.fooddev.JavaBeans.EntidadeUsuario;
import com.kylukz.fooddev.Routes.Rotas;
import com.kylukz.fooddev.Toolbox.VariaveisGlobais;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Classe responsável por gerenciar
 * a autenticação e os dados do usuário
 *
 * @author Igor Maximo <igormaximo_1989@hotmail.com>
 * @date 09/05/2020
 */
public class UsuarioDAO {

    public volatile static EntidadeUsuario ENTIDADE_USUARIO = new EntidadeUsuario();
    Json json = new Json();

    /**
     * Carrega a entidade principal da classe
     * para economizar dados móveis
     *
     * @author Igor Maximo <igormaximo_1989@hotmail.com>
     * @date 09/05/2020
     */
    public EntidadeUsuario getUsuario(String pin, String senha) {
        EntidadeUsuario entidadeUsuario = new EntidadeUsuario();
        try {
            JSONArray jsonArray = json.setEnviaPostRetornaJsonObjectServidor(Rotas.SELECT_USUARIO, new Uri.Builder()
                    .appendQueryParameter("pin", pin)
                    .appendQueryParameter("senha", senha)
                    .appendQueryParameter("fkVersionamento", VariaveisGlobais.FK_VERSIONAMENTO+""));
            JSONObject jsonObject = jsonArray.getJSONObject(0);


            entidadeUsuario.setMsgAuth(jsonObject.getString("msg"));
            entidadeUsuario.setSeAuth(jsonObject.getBoolean("status"));
            if (jsonObject.getBoolean("status")) {
                entidadeUsuario.setId(jsonObject.getInt("id"));
                entidadeUsuario.setPin(jsonObject.getString("pin"));
                entidadeUsuario.setSenha(jsonObject.getString("senha"));
                entidadeUsuario.setCpfCnpj(jsonObject.getString("cpf"));
                entidadeUsuario.setSeCompletouCadastro(jsonObject.getBoolean("seCompletouCadastro"));
                entidadeUsuario.setFkAppVersionamento(jsonObject.getInt("fkAppVersionamento"));
            }

        } catch (Exception e) {
            System.out.println(e);
//            LogDAO.setErro("UsuarioDAO", "getUsuario", e.getMessage(), Integer.parseInt(String.valueOf(e.getStackTrace()[0])), 0, new EntidadeUsuario().getId(), new EntidadeUsuario().getFkVersionamento());
        }
        return entidadeUsuario;
    }

    /**
     * Carrega usuário por autenticação
     * via gmail
     *
     * @author Igor Maximo <igormaximo_1989@hotmail.com>
     * @date 09/05/2020
     */
    private void getAuthGmail() {
        try {

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Carrega usuário por auutenticação
     * via facebook
     *
     * @author Igor Maximo <igormaximo_1989@hotmail.com>
     * @date 09/05/2020
     */
    private void getAuthFacebook() {
        try {

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Set cadastrar novo usuário
     * e coletar o PIN retornado
     *
     * @author Igor Maximo <igormaximo_1989@hotmail.com>
     * @date 09/05/2020
     */
    public Object[] setCadastrarUsuario(EntidadeCadastroUsuario entidadeCadastroUsuario) {
        Object[] retorno = new Object[2];
        try {
            JSONArray jsonArray = json.setEnviaPostRetornaJsonObjectServidor(Rotas.INSERT_CADASTRA_NOVO_USUARIO, new Uri.Builder()
                    .appendQueryParameter("nome", entidadeCadastroUsuario.getNome())
                    .appendQueryParameter("sobrenome", entidadeCadastroUsuario.getSobreNome())
                    .appendQueryParameter("cpf", entidadeCadastroUsuario.getCpf())
                    .appendQueryParameter("dataNascimento", entidadeCadastroUsuario.getDataNascimento())
                    .appendQueryParameter("celular", entidadeCadastroUsuario.getCelular())
                    .appendQueryParameter("email", entidadeCadastroUsuario.getEmail())
                    .appendQueryParameter("senha", entidadeCadastroUsuario.getSenha())
                    .appendQueryParameter("fkVersionamento", VariaveisGlobais.FK_VERSIONAMENTO+"")
            );
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            // Coleta retorno
            retorno[0] = jsonObject.getBoolean("status");
            retorno[1] = jsonObject.getString("msg");

            System.out.println("0 ========> " + retorno[0]);
            System.out.println("1 ========> " + retorno[1]);
        } catch (Exception e) {
//            LogDAO.setErro("UsuarioDAO", "getUsuario", e.getMessage(), Integer.parseInt(String.valueOf(e.getStackTrace()[0])), 0, new EntidadeUsuario().getId(), new EntidadeUsuario().getFkVersionamento());
        }
        return retorno;
    }

    /**
     * Set completar
     * cadastro do usuário
     *
     * @author Igor Maximo <igormaximo_1989@hotmail.com>
     * @date 09/05/2020
     */
    public Object[] setCompletarCadastroUsuario(EntidadeCadastroUsuario entidadeCadastroUsuario) {
        Object[] retorno = new Object[2];
        try {
            JSONArray jsonArray = json.setEnviaPostRetornaJsonObjectServidor(Rotas.INSERT_COMPLETA_CADASTRO_USUARIO, new Uri.Builder()
                    .appendQueryParameter("cep", entidadeCadastroUsuario.getCep())
                    .appendQueryParameter("endereco", entidadeCadastroUsuario.getEndereco())
                    .appendQueryParameter("numero", entidadeCadastroUsuario.getNumero())
                    .appendQueryParameter("bairro", entidadeCadastroUsuario.getBairro())
                    .appendQueryParameter("cidade", entidadeCadastroUsuario.getCidade())
                    .appendQueryParameter("uf", entidadeCadastroUsuario.getUf())
                    .appendQueryParameter("referencia", entidadeCadastroUsuario.getReferencia())
                    .appendQueryParameter("latitude", entidadeCadastroUsuario.getLatitude())
                    .appendQueryParameter("longitude", entidadeCadastroUsuario.getLongitude())
                    .appendQueryParameter("fkVersionamento", VariaveisGlobais.FK_VERSIONAMENTO+"")
            );
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            // Coleta retorno
            retorno[0] = jsonObject.getBoolean("status");
            retorno[1] = jsonObject.getString("msg");
        } catch (Exception e) {
            LogDAO.setErro("UsuarioDAO", "getUsuario", e.getMessage(), Integer.parseInt(String.valueOf(e.getStackTrace()[0])), 0, new EntidadeUsuario().getId(), new EntidadeUsuario().getFkVersionamento());
        }
        return retorno;
    }

    /**
     * Grava o token gerado
     *
     * @author Igor Maximo <igormaximo_1989@hotmail.com>
     * @date 26/06/2020
     */
    public void setTokenFirebasePushUsuario(String token) {
        try {
            json.setEnviaPostRetornaJsonObjectServidor(Rotas.INSERT_FIREBASE_FCM_TOKEN_USUARIO, new Uri.Builder()
                    .appendQueryParameter("cpfCnpj", ENTIDADE_USUARIO.getCpfCnpj())
                    .appendQueryParameter("token", token)
                    .appendQueryParameter("fkVersionamento", VariaveisGlobais.FK_VERSIONAMENTO+"")
            );
        } catch (Exception e) {
//            LogDAO.setErro("UsuarioDAO", "getUsuario", e.getMessage(), Integer.parseInt(String.valueOf(e.getStackTrace()[0])), 0, new EntidadeUsuario().getId(), new EntidadeUsuario().getFkVersionamento());
        }
    }


    /**
     * Set enviar PIN esqueci senha
     * para o e-mail do cadastro do CPF
     *
     * @author Igor Maximo <igormaximo_1989@hotmail.com>
     * @date 19/06/2020
     */
    public Object[] setEnviarPINUsuarioPorEmail(String cpf) {
        Object[] retorno = new Object[2];
        try {
            JSONArray jsonArray = json.setEnviaPostRetornaJsonObjectServidor(Rotas.SELECT_ESQUECI_SENHA_USUARIO, new Uri.Builder()
                    .appendQueryParameter("cpf", cpf)
                    .appendQueryParameter("fkVersionamento", VariaveisGlobais.FK_VERSIONAMENTO+"")
            );
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            // Coleta retorno
            retorno[0] = jsonObject.getBoolean("status");
            retorno[1] = jsonObject.getString("msg");
        } catch (Exception e) {
//            LogDAO.setErro("UsuarioDAO", "getUsuario", e.getMessage(), Integer.parseInt(String.valueOf(e.getStackTrace()[0])), 0, new EntidadeUsuario().getId(), new EntidadeUsuario().getFkVersionamento());
        }
        return retorno;
    }
}

