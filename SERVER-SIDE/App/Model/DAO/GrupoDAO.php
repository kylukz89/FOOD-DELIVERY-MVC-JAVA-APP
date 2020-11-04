<?php



namespace App\Model\DAO;

use App\Model\DAO\ConexaoDAO;
//use App\Model\DAO\ErroDAO;

require_once '/home2/versa357/public_html/app/desenvolvimento/App/Model/DAO/ErroDAO.php';

/**
 * 
 * Entidade responsa´vel por gerenciar
 * os dados dos grupos de itens por empresa
 * 
 * @author      Igor Maximo
 * @date        31/05/2020
 */
class GrupoDAO /*extends ErroDAO */{
    
    /**
     * Retorna lista grupos de uma empresas
     */
    public function getListaGruposEmpresa($idEmpresa) {
        $lista = [];
        try {
            $conexao = new ConexaoDAO();
            $conn = $conexao->conexao();
 
            mysqli_query($conn, "SET NAMES 'utf8'");

            $sql = "SELECT * FROM grupo inner join foto on grupo.fk_foto = foto.id where fk_empresa = " . $idEmpresa . " and Ativo = 1";
            
            error_log($sql);
            
            if ($result = mysqli_query($conn, $sql)) {
                while ($row = mysqli_fetch_assoc($result)) {
                    $registro = [];
                    $registro['foto'] = base64_encode($row['foto']);
                    $registro['Cod_GRUPO'] = $row["Cod_GRUPO"];
                    $registro['fk_empresa'] = $row["fk_empresa"];
                    $registro['Nome'] = $row["Nome"];
                    if (intval($row["PIZZA"]) == 1) {
                        $registro['sePizza'] = "true";
                    } else {
                        $registro['sePizza'] = "false";
                    }
                    
                    array_push($lista, $registro);
                }
            }
        } catch (Exception $e1) {
            echo $e1;
        }
        return $lista;
    }
}