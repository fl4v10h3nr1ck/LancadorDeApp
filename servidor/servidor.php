<?php 
header('Content-Type: text/html; charset=utf-8');

$_POST["chave"]="j5hp0wj59k4e3v9y0vn4y8n9b5p1z2mk";
$_POST["codigo"]="3412";

	if(!array_key_exists("chave", $_POST) || strcmp($_POST["chave"], "j5hp0wj59k4e3v9y0vn4y8n9b5p1z2mk") !=0){
	echo "ERRO#_#chave de acesso inválida.";
	return;
	}
	
include_once 'BD/Opcoes_BD_Gerais.class.php';

$BD= new Opcoes_BD_Gerais;

$reg = $BD->get(
"select ver.compilacao, ver.nome_arquivo, sol.nome, ver.data_cadastro, ver.versao, ver.tamanho_em_bytes 	  
from versoes as ver  
inner join solucoes as sol on sol.id_solucao = ver.fk_sistema 
where sol.codigo='".$_POST['codigo']."' and sol.status='ATIVO' order by id_versao DESC");
		
if(count($reg)>0)	
echo "SUCESSO#_#".
	$reg[0]["compilacao"]."#_#".
	$reg[0]["nome_arquivo"]."#_#".
	$reg[0]["nome"]."#_#".
	date("d/m/Y", strtotime($reg[0]["data_cadastro"]))."#_#".
	$reg[0]["versao"]."#_#".
	$reg[0]["tamanho_em_bytes"];
else		
echo "SUCESSO";	

?>
