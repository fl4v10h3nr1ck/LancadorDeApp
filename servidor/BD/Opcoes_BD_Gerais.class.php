<?php


// possui rotinas de manipulacao da base de dados gerais.


include_once 'BD.class.php';
	




final class Opcoes_BD_Gerais extends BD{








	function __construct() {
	parent::__construct();
	}








	
	public function aDD( $nome_tabela, $nome_campos, $campos,  &$referencia = null){
	

	if( strlen($nome_tabela) == 0 || count( $nome_campos) != count( $campos))
	return 0;
	
	if( $referencia == null)
	$referencia = $this->conecta();
	
	$aux = "";
	$cont = 0;
		
	$query = "insert into ".$nome_tabela." (";

	
		foreach( $nome_campos as $value){
		
		if($cont++ > 0 )
		$aux = $aux. ", ";
		
		$aux = $aux.$value;
		}
	
	$query = $query.$aux.") value (";
	$aux = "";
	$cont = 0;
		
		foreach( $campos as $value){
		
		if($cont++ > 0 )
		$aux = $aux. ", ";
		
		$aux = $aux. "'".$value."'";
		}
		
	$query = $query.$aux.")";
	
	if( mysql_query( $query, $referencia))
	return mysql_insert_id($referencia);
	
	return 0;
	}
	
	
	
	


	
	
	



	
	public function atualiza( $nome_tabela, $nome_campo_id, $id_valor, $nome_campos, $campos, &$referencia = null){
	
	
	
	if( strlen ($nome_tabela) == 0|| strlen ($nome_campo_id) == 0||  count( $campos) != count( $nome_campos) || $id_valor == 0)
	return false;
	
	if( $referencia == null)
	$referencia = $this->conecta();
	
	
	$aux = "";
	$cont = 0;
		foreach( $campos as $value){
		
		if($cont > 0 )
		$aux = $aux. ", ";
		
		$aux = $aux. $nome_campos[$cont++]." = '".$value."'";
		}
		
	if( mysql_query( "update ".$nome_tabela." set ".$aux." where ".$nome_campo_id." = ".$id_valor, $referencia))
	return true;
	
	return false;
	}
	
	
	
	





	

		
	
	public function get( $query, &$referencia = null){
	
	
	
	if( strlen($query) == 0)
	return null;
	
	if( $referencia == null)
	$referencia = $this->conecta();
	
	$registros = "";
	$reg = mysql_query( $query, $referencia);
	
		if( $reg){
			if( mysql_num_rows($reg) > 0){
	
			while($aux = mysql_fetch_assoc($reg))
			$registros[] = $aux;
	
			return $registros;
			}
		}

	
	return null;
	}
	
	
	


	
	
	
	
	
	
	
	public function deleta( $nome_tabela, $nome_campo_id, $id_valor, &$referencia = null){
	
	if( strlen ($nome_tabela) == 0|| strlen ($nome_campo_id) == 0||  $id_valor == 0 )
	return false;
	
	
	if( $referencia == null)
	$referencia = $this->conecta();
	
	
	return mysql_query( "delete from ".$nome_tabela." where ".$nome_campo_id." = ".$id_valor, $referencia);
	}
	


	
	
	
	
	
	
	
	
		
	public function deletaPerson( $nome_tabela, $subquery = "", &$referencia = null){
	
	if( strlen ($nome_tabela) == 0 )
	return false;
	
	
	if( $referencia == null)
	$referencia = $this->conecta();
	
	
	return mysql_query( "delete from ".$nome_tabela." ".(strripos($subquery, "where")===false?"WHERE ":"").$subquery);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public function deletaConjunto( $nome_tabela, $nome_campo_id, $array_ids, &$referencia = null){
	
	if( count($array_ids) == 0 || strlen ($nome_tabela) == 0|| strlen ($nome_campo_id) == 0 )
	return false;
	
	
	if( $referencia == null)
	$referencia = $this->conecta();
	
	
	foreach ( $array_ids as $value) 
	$this->deleta( $nome_tabela, $nome_campo_id, $value, $referencia);
	
	
	return true;
	}
	


	
	
	
	
	
	
	
	
	
	
	
	
	public function executaQuery( $query,  &$referencia = null){
	

	if( strlen($query) == 0)
	return 0;
	
	if( $referencia == null)
	$referencia = $this->conecta();
	
	return mysql_query( $query, $referencia);
	}
	
	
	
	
	
	
	
}
?>