package com.kylukz.fooddev.Toolbox;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import androidx.core.app.ShareCompat;

import com.kylukz.fooddev.DAO.UsuarioDAO;
import com.kylukz.fooddev.Services.AppService;


/*
    Classe que centraliza/organiza os métodos da aba lateral esquerda do APP
 */
public abstract class MenusAbaLateralEsquerda {


    // Abre tela Suporte
    public static void botaoSuporte(Context ctx) {
        Toast.makeText(ctx, "Módulo em construção! ", Toast.LENGTH_SHORT).show();
    }

    // Abre tela Serviços
    public static void botaoSejaUmEntregador(Context ctx) {
        Toast.makeText(ctx, "Módulo em construção! ", Toast.LENGTH_SHORT).show();
    }

    // Abre tela Sobre
    public static void botaoIndiqueParaUmAmigo(Context ctx, Activity activity) {
        try {
            ShareCompat.IntentBuilder.from(activity)
                    .setType("text/plain")
                    .setChooserTitle("Indicar com")
                    .setText("Indico-lhe o melhor App Delivery da região!")
                    .setText("http://play.google.com/store/apps/details?id=" + ctx.getPackageName())
                    .startChooser();

        } catch(Exception e) {
            //e.toString();
        }
    }

    public static void botao3Pontinhos(Context ctx) {
        if (ctx instanceof AppService) {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            try {
//                autdao.registraLOGDeslogarUsuario(usuario);
            } catch (Exception e) {
                e.getStackTrace();
            }
        } else {
            try {
//                autdao.registraLOGDeslogarUsuario(usuario);
            } catch (Exception e) {
                e.getStackTrace();
            }
            Ferramentas.restartAPPeApagaCredenciais(ctx);
        }
    }


    ////////////////////////////////
    // Botão (3 pontinhos) lado direito superior.
//    public static void botao3Pontinhos(Context ctx) {
//        Usuario usuario = new Usuario();
//
//        if (ctx instanceof AppService) {
//            AutenticacaoDAO autdao = new AutenticacaoDAO();
//            try {
//                autdao.registraLOGDeslogarUsuario(usuario);
//            } catch (Exception e) {
//                e.getStackTrace();
//            }
//        } else {
//            AutenticacaoDAO autdao = new AutenticacaoDAO();
//            try {
//                autdao.registraLOGDeslogarUsuario(usuario);
//            } catch (Exception e) {
//                e.getStackTrace();
//            }
//            Ferramentas.restartAPPeApagaCredenciais(ctx);
//        }
//
//    }
//
}
