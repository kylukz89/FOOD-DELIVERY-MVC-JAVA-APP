<?php

namespace App;

require_once __DIR__ . '/vendor/autoload.php';

use App\Model\Entidade\EntidadeEmpresa;
use App\Model\Entidade\EntidadeUsuario;
use App\Model\Entidade\EntidadeEnderecoUsuario;
use App\Model\DAO\EmpresaDAO;
use App\Model\DAO\GrupoDAO;
use App\Model\DAO\ProdutoDAO;
use App\Model\DAO\UsuarioDAO;
use App\Model\DAO\CidadeDAO;
use App\Model\DAO\LogDAO;
use App\Model\DAO\VersionamentoDAO;
use App\Model\DAO\EnderecoUsuarioDAO;
use App\Lib\Email;

require_once '/home2/versa357/public_html/app/desenvolvimento/App/Lib/Email.php';
require_once '/home2/versa357/public_html/app/desenvolvimento/App/Model/Entidade/EntidadeEmpresa.php';
require_once '/home2/versa357/public_html/app/desenvolvimento/App/Model/Entidade/EntidadeUsuario.php';
require_once '/home2/versa357/public_html/app/desenvolvimento/App/Model/Entidade/EntidadeEnderecoUsuario.php';
require_once '/home2/versa357/public_html/app/desenvolvimento/App/Model/DAO/EmpresaDAO.php';
require_once '/home2/versa357/public_html/app/desenvolvimento/App/Model/DAO/GrupoDAO.php';
require_once '/home2/versa357/public_html/app/desenvolvimento/App/Model/DAO/CidadeDAO.php';
require_once '/home2/versa357/public_html/app/desenvolvimento/App/Model/DAO/VersionamentoDAO.php';
require_once '/home2/versa357/public_html/app/desenvolvimento/App/Model/DAO/ProdutoDAO.php';
require_once '/home2/versa357/public_html/app/desenvolvimento/App/Model/DAO/LogDAO.php';
require_once '/home2/versa357/public_html/app/desenvolvimento/App/Model/DAO/EnderecoUsuarioDAO.php';
require_once '/home2/versa357/public_html/app/desenvolvimento/App/Model/DAO/UsuarioDAO.php';


/**
 * <b>SERVER-SIDE</b>
 * API Rest, vulgarmene arquivo de Rotas
 * Todas as chamadas de métodos de manipulação 
 * dos dados do App, são feitas aqui
 * 
 * @author          Igor Maximo
 * @date            13/04/2020 
 */
//if ($_POST['tokenX'] == 'm3fq1traNGgtMBM3gcuBKSPhY4S2JYNwRtdJeKjrRGbfHoYoGJqRd6z21ItAh8x0rP9Lbj' && $_GET['tokenY'] == 'Jzqw1K3dxfRpIE8kNXiCqX7jAwK9dqsngaZYkS2YyJD7OBidZGgFedpfO1esiNWFnwdOFU') {
// Casos de todos os métodos dos Apps
switch ($_GET['func']) {

    // Consulta usuário por autenticação simples (meramente cadastral)
    case "getUsuario":
        $entidadeUsuario = new EntidadeUsuario();
        $usuarioDAO = new UsuarioDAO();
        $versionamentoDAO = new VersionamentoDAO();
        $logDAO = new LogDAO();
        // POST
        $pin = @$_POST['pin'];
        $senha = @$_POST['senha'];
        $fkVersionamento = @$_POST['fkVersionamento']; // versão
        // Retorno do usuário por pin
        $auth = $usuarioDAO->getUsuarioPorPin($pin);
        // Pequena validação
        if (strlen($pin) == 8) {
            if ($auth['pin_gerado'] == $pin && $auth['senha'] == $senha) {
                $seCompletouCadastro = "false";
                if ($auth['seCompletouCadastro'] == 1) {
                    $seCompletouCadastro = "true";
                }
                // Atualiza versão do usuário no banco caso seja diferente da última vez que logou
                $idVersaoUsuario = intval($versionamentoDAO->getFkVersionamentoUltimaVezUsuarioLogou($auth['id']));
                if ($fkVersionamento != intval($idVersaoUsuario)) {
                    // Cria novo versionamento para o usuário caso a versão atual dele seja diferente desde a última vez que logou
                    $idVersaoUsuario = $versionamentoDAO->setInserirVersionamentoUsuario($fkVersionamento, $auth['id']);
                }
                // Set ação LOG
                $logDAO->setAcao("Autenticou-se com sucesso!", $auth['id'], $idVersaoUsuario);
                // Retorno para o app
                print_r('[{"status":true,"msg":"Autenticado com sucesso!","pin":"' . $auth['pin_gerado'] . '","senha":"' . $auth['senha'] . '","cpf":"' . $auth['cpf'] . '","seCompletouCadastro":' . $seCompletouCadastro . ',"id":' . $auth['id'] . ',"fkAppVersionamento":' . $idVersaoUsuario . '}]');
                error_log('[{"status":true,"msg":"Autenticado com sucesso!","pin":"' . $auth['pin_gerado'] . '","senha":"' . $auth['senha'] . '","cpf":"' . $auth['cpf'] . '","seCompletouCadastro":' . $seCompletouCadastro . ',"id":' . $auth['id'] . ',"fkAppVersionamento":' . $idVersaoUsuario . '}]');
            } else {
                // Exibe o erro
                if ($auth['pin_gerado'] != $pin) {
                    print_r('[{"status":false,"msg":"PIN inválido","pin":"","senha":""}]');
                    if ($auth['senha'] != $senha) {
                        print_r('[{"status":false,"msg":"Senha incorreta!","pin":"","senha":""}]');
                    } else {
                        print_r('[{"status":false,"msg":"PIN incorreto!","pin":"","senha":""}]');
                    }
                } else {
                    if ($auth['senha'] != $senha) {
                        print_r('[{"status":false,"msg":"Senha incorreta!","pin":"","senha":""}]');
                    } else {
                        print_r('[{"status":false,"msg":"Erro interno!","pin":"","senha":""}]');
                    }
                        
                }
            }
        } else {
            print_r('[{"status":false,"msg":"Formato PIN inválido!","pin":"","senha":""}]');
        }
        break;
    // Envia PIN e senha por e-mail esqueci senha
    case "getEsqueciSenha":
        $entidadeUsuario = new EntidadeUsuario();
        $usuarioDAO = new UsuarioDAO();
        $dispararEmail = new Email();
        // POST
        $cpf = @$_POST['cpf'];
        // Retorno do usuário por pin

        $retorno = $usuarioDAO->getUsuarioPorCPF(str_replace("-", "", str_replace(".", "", $cpf)));

        // Pequena validação
        error_log("boolval ========>>> " . boolval($retorno['status']));
        error_log("status ========>>> " . $retorno['status']);
        error_log("msg ========>>> " . $retorno['msg']);

        if (strlen(str_replace("-", "", str_replace(".", "", $cpf))) == 11) {
            // Disparar e-mail com o PIN
            if (($retorno['status']) == 'true') {
                $entidadeUsuario->setNome($retorno['nome']);
                $entidadeUsuario->setSobrenome($retorno['sobrenome']);
                $entidadeUsuario->setEmail($retorno['email']);
                $entidadeUsuario->setSenha($retorno['senha']);
                $dispararEmail->setDispararEmailNovoCadastroPIN($entidadeUsuario, $retorno['pin_gerado']);
                print_r('[{"status":true,"msg":"Credenciais enviadas para o e-mail!"}]');
            } else {
                print_r('[{"status":false,"msg":"CPF inexistente!"}]');
                error_log('[{"status":false,"msg":"CPF inexistente!"}]');
            }
        } else {
            print_r('[{"status":false,"msg":"CPF inválido!"}]');
            error_log('[{"status":false,"msg":"CPF inválido!"}]');
        }
        break;
    // Envia PIN e senha por e-mail esqueci senha
    case "getViaCep":
        $cep = @$_POST['cep'];

        break;
    // Cadastra usuário
    case "cadastraUsuario":

        $entidadeUsuario = new EntidadeUsuario();
        $usuarioDAO = new UsuarioDAO();
        $dispararEmail = new Email();

        $nome = @$_POST['nome'];
        $sobrenome = @$_POST['sobrenome'];
        $cpf = @$_POST['cpf'];
        $dataNascimento = @$_POST['dataNascimento'];
        $celular = @$_POST['celular'];
        $email = @$_POST['email'];
        $senha = @$_POST['senha'];
        $fkVersionamento = @$_POST['fkVersionamento']; // versão
        // Carrega entidade
        $entidadeUsuario->setNome($nome);
        $entidadeUsuario->setSobrenome($sobrenome);
        $entidadeUsuario->setCpf(str_replace("-", "", str_replace(".", "", $cpf)));
        $entidadeUsuario->setDataNascimento(implode('-', array_reverse(explode('/', $dataNascimento))));
        $entidadeUsuario->setCelular($celular);
        $entidadeUsuario->setEmail($email);
        $entidadeUsuario->setSenha($senha);
        $entidadeUsuario->setFkVersionamento($fkVersionamento);
        // Validação
//        if ($validador->valida()) {
//           print_r("Existem campos obrigátorios!");
//        } 
        // Retorno do cadastramento
        $retorno = $usuarioDAO->setCadastrarUsuario($entidadeUsuario);
        // Disparar e-mail com o PIN
        if (boolval($retorno['status'])) {
            $dispararEmail->setDispararEmailNovoCadastroPIN($entidadeUsuario, $retorno['pin']);
            
        }
        // Retorno para o App
        print_r('[{"status":' . strval($retorno['status']) . ',"msg":"' . $retorno['msg'] . '"}]');
        break;
    // Cadastra usuário
    case "setTokenFirebasePushUsuario":
        $entidadeUsuario = new EntidadeUsuario();
        $usuarioDAO = new UsuarioDAO();
        $cpf = @$_POST['cpfCnpj'];
        $token = @$_POST['token'];
        // Grava e não retorna nada
        $usuarioDAO->setFirebaseFcmTokenUsuario($cpf, $token);
        break;
    // COmpleta cadastro usuário
    case "setCompletarCadastroUsuario":
        // DAO
        $entidadeEnderecoUsuario = new EntidadeEnderecoUsuario();
        $usuarioDAO = new UsuarioDAO();
        $usuarioEnderecoDAO = new EnderecoUsuarioDAO();
        $cidadeDAO = new CidadeDAO();

        $id = 1; //@$_POST['id']; // Id usuário
        // Carrega Entidade 
        $idCidade = $cidadeDAO->getIdCidadePorNome(@$_POST['cidade']); // Id da cidade por nome
        $entidadeEnderecoUsuario->setFkCidade($idCidade);
        $entidadeEnderecoUsuario->setFkUsuario($id);
        $entidadeEnderecoUsuario->setFkUf($cidadeDAO->getIdUfPorIdCidade($idCidade)); // Id UF por Id da cidade
        $entidadeEnderecoUsuario->setNomeCidade(@$_POST['cidade']);
        $entidadeEnderecoUsuario->setNumero(@$_POST['cidade']);
        $entidadeEnderecoUsuario->setBairro(@$_POST['bairro']);
        $entidadeEnderecoUsuario->setCep(@$_POST['cep']);
        $entidadeEnderecoUsuario->setPontoRef(@$_POST['pontoRef']);
        $entidadeEnderecoUsuario->setLatitude(@$_POST['latitude']);
        $entidadeEnderecoUsuario->setLongitude(@$_POST['longitude']);



        // Verifica se o usuário tem pelo menos 1 endereço cadastrado
        if ($usuarioEnderecoDAO->getSeUsuarioCompletouCadastro($id) == 0) {
            $seCadastroCompleto = $usuarioDAO->setUpdateSeUsuarioCompletouCadastro($id);
            // Verifica se deu certo atualizar o campo de "seCadastroCompleto"
            $seCompletou = $usuarioEnderecoDAO->setCompletarCadastrUsuario($entidadeEnderecoUsuario);
            if ($seCompletou) {
                // Retorno
                print_r('[{"status":true,"msg":"Dados atualizados com sucesso!"}]');
            } else {
                print_r('[{"status":false,"msg":"Dados informados anteriormente!"}]');
            }
        } else {
            print_r('[{"status":false,"msg":"Dados informados anteriormente!"}]');
        }


        break;
    // Edita usuário
    case "editaUsuario":
        // DAO
        break;
    // Inativa usuário
    case "inativaUsuario":
        // DAO
        break;

    //////////////////////////////////////////
    //         CONSULTAS (SELECTS)          //
    //////////////////////////////////////////
    // Consulta todas as empresas
    case "selectEmpresas":
        $empresa = new EntidadeEmpresa();
        $empresaDAO = new EmpresaDAO();
        $nomeEmpresa = @$_POST['nomeEmpresa']; // Usado para campo de pesquisa do SearchView
        error_log($nomeEmpresa);
        print_r(json_encode($empresaDAO->getListaEmpresa($nomeEmpresa)));
        break;
    // Consulta todos os grupos da empresa escolhida    
    case "selectGruposEmpresa":
        $grupoDAO = new GrupoDAO();
        $idEmpresa = @$_POST['idEmpresa'];
        print_r(json_encode($grupoDAO->getListaGruposEmpresa($idEmpresa)));
        error_log(json_encode($grupoDAO->getListaGruposEmpresa($idEmpresa)));
        break;
    // Consulta todos os produtos do grupo escolhido
    case "selectProdutosGrupoEmpresa":
        $grupoDAO = new ProdutoDAO();
        $idEmpresa = @$_POST['idEmpresa'];
        $idGrupo = @$_POST['idGrupo'];
        print_r(json_encode($grupoDAO->getProdutosGrupoEmpresa($idEmpresa, $idGrupo)));
//            print_r(json_encode($grupoDAO->getProdutosGrupoEmpresa(33, 39)));
        break;
    case $value:
        break;
}

    // if Web || Android || iOS
    /*
} else {
    // Se foram 3 ataques do mesmo IP Bloqueia o IP do atacante 
    echo '[{"erro":"404"}]';
}
    */