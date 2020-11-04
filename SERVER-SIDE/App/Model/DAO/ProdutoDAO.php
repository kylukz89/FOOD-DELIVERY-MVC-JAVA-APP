<?php

namespace App\Model\DAO;

use App\Model\DAO\ConexaoDAO;
use App\Model\DAO\ErroDAO;

require_once '/home2/versa357/public_html/app/desenvolvimento/App/Model/DAO/ErroDAO.php';

/**
 * 
 * Entidade responsa´vel por gerenciar
 * os dados dos grupos de itens por empresa
 * 
 * @author      Igor Maximo
 * @date        31/05/2020
 */
class ProdutoDAO /* extends ErroDAO */ {

    /**
     * Retorna lista grupos de uma empresas
     */
    public function getProdutosGrupoEmpresa($idEmpresa, $idGrupo) {
        $lista = [];
        try {
            $conexao = new ConexaoDAO();
            $conn = $conexao->conexao();

            mysqli_query($conn, "SET NAMES 'utf8'");

            $sql = "SELECT grupo.PIZZA as sePizza, produto.*, foto.foto "
                    . "FROM produto "
                    . "inner join foto on produto.fk_foto = foto.id "
                    . "inner join grupo on produto.fk_grupo = grupo.Cod_GRUPO "
                    . "where produto.fk_empresa = ".$idEmpresa." and fk_grupo = ".$idGrupo." and produto.Ativo = 1";

            error_log($sql);
            
            if ($result = mysqli_query($conn, $sql)) {
                while ($row = mysqli_fetch_assoc($result)) {
                    $registro = [];
                    if ($row["sePizza"] == 1) {
                        $registro['sePizza'] = "true";    
                    } else {
                        $registro['sePizza'] = "false";
                    }
                    $registro['id'] = $row["id"];
                    $registro['ativo'] = $row["ativo"];
                    $registro['foto'] = base64_encode($row['foto']);
                    $registro['fk_empresa'] = $row["fk_empresa"];
                    $registro['fk_grupo'] = $row["fk_grupo"];
                    $registro['nome'] = $row["nome"];
                    $registro['descricao'] = $row["descricao"];
                    $registro['cod_barra_produto'] = $row["cod_barra_produto"];
                    $registro['unidade'] = $row["unidade"];
                    $registro['preco_custo'] = $row["preco_custo"];
                    $registro['preco_avista'] = $row["preco_avista"];
                    $registro['preco_promocao'] = $row["preco_promocao"];
//                    $registro['itens_adicionais'] = array(
//                        1 => 'Ovo',
//                        2 => 'Salsicha',
//                        3 => 'Bacon',
//                        4 => 'Tomate',
//                        5 => 'Mussarella'
//                    );
                    array_push($lista, $registro);
                }
            }
        } catch (Exception $e1) {
            echo $e1;
        }
        return $lista;
    }

    /**
     * Retorna se o grupo de um determinado
     * produto é pizza
     */
    private function getSeGrupoDoProdutoEPizza($idProduto) {
        try {
            $conexao = new ConexaoDAO();
            $conn = $conexao->conexao();

            mysqli_query($conn, "SET NAMES 'utf8'");

            $sql = "select grupo.PIZZA as se_pizza from grupo "
                    . "inner join produto on grupo.Cod_GRUPO = produto.fk_grupo "
                    . "where produto.id = " . $idProduto;

            error_log($sql);
            
            
            if ($result = mysqli_query($conn, $sql)) {
                if ($row["se_pizza"] == 1) {
                    return 'true';
                } else {
                    return 'false';
                }
            } else {
                return 'false';
            }
        } catch (Exception $e1) {
            echo $e1;
        }
    }

}
