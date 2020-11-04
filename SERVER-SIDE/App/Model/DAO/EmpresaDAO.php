<?php

namespace App\Model\DAO;

use App\Model\DAO\ConexaoDAO;
require_once '/home2/versa357/public_html/app/desenvolvimento/App/Model/DAO/EmpresaDAO.php';
require_once '/home2/versa357/public_html/app/desenvolvimento/App/Model/DAO/ConexaoDAO.php';

/**
 * Classe responsável por manipular 
 * a classe de Empresa
 */
class EmpresaDAO {

    /**
     * Retorna lista de empresas
     */
    public function getListaEmpresa($nomeEmpresa) {
        try {
            $conexao = new ConexaoDAO();
            $conn = $conexao->conexao();

            mysqli_query($conn, "SET NAMES 'utf8'");
            
            // Se nome empresa vem vazio, significa que deve retornar toda consulta 
            if ($nomeEmpresa == "") {
                $sql = "SELECT * FROM empresa inner join foto on empresa.fk_foto = foto.id where empresa.ativo = 1";
            } else {
                $sql = "SELECT * FROM empresa inner join foto on empresa.fk_foto = foto.id where upper(empresa.Fantasia) like '%" . strtoupper($nomeEmpresa) . "%' and empresa.ativo = 1";
            }
            
            $lista = [];
            if ($result = mysqli_query($conn, $sql)) {
                while ($row = mysqli_fetch_assoc($result)) {
                    $registro = [];
                    $registro['Cod_empresa'] = $row["Cod_empresa"];
                    $registro['Fantasia'] = $row["Fantasia"];
                    $registro['Endereco'] = $row["Endereco"] .", ". $row["Numero_endereco"] . " - " . $row["Bairro"];
                    $registro['foto'] = base64_encode($row['foto']);
                    array_push($lista, $registro);
                }
            }

            return $lista;
            
        } catch (Exception $e1) {
            echo $e1;
        }

        return null;
    }

}
