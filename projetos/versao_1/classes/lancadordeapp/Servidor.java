package lancadordeapp;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.regex.Pattern;


import org.apache.http.HttpVersion;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.Consts;




public final class Servidor {
	

private final String URL_SEVIDOR = "http://localhost/servidor.php";

private final String URL_APPS = "http://www.mscsolucoes.com.br/util/apps/";

private final String chave = "j5hp0wj59k4e3v9y0vn4y8n9b5p1z2mk";







	public Mensagem checaAtualizacoes(){
	
	System.out.println("Checando por atualizacoes...");
					
	String retorno = "";
	
		try {
				
		Form form = Form.form();	
		form.add("chave", this.chave);
		form.add("codigo", Credenciais.CODIGO);
		
		retorno = new String(Request.Post(this.URL_SEVIDOR)
		.useExpectContinue()
		.version(HttpVersion.HTTP_1_1)
		.bodyForm(form.build(), Consts.UTF_8).execute().returnContent().asString());
		
		
		if(retorno ==null || retorno.length()==0)
		return new Mensagem();
			
		String dados_retorno[] = retorno.split(Pattern.quote("#_#")); 
		
		if(dados_retorno.length<1)
		return new Mensagem();
		
		Mensagem mensagem_retorno = new Mensagem();
		
		mensagem_retorno.setStatus(dados_retorno[0]);
		
		if(dados_retorno.length>=2)
		mensagem_retorno.setCompilacao(dados_retorno[1]);
		
		if(dados_retorno.length>=3)
		mensagem_retorno.setNome(dados_retorno[2]);
		
		if(dados_retorno.length>=4)
		mensagem_retorno.setNome_sistema(dados_retorno[3]);
		
		if(dados_retorno.length>=5)
		mensagem_retorno.setData_cadastro(dados_retorno[4]);
		
		if(dados_retorno.length>=6)
		mensagem_retorno.setVersao(dados_retorno[5]);
		
		if(dados_retorno.length>=7)
		mensagem_retorno.setTamanho(dados_retorno[6]);
		
		return mensagem_retorno;
		} 
		catch (IOException  e) {
	
		return new Mensagem();
		}	
	}
	
	
	

	
	
	public File baixaExecutavel(String nome, FormStatuDeDownload formstatus) {
		
		try {
		
		URL url = new URL(this.URL_APPS+nome);
		
		String path_local  =Credenciais.getLocal();
		
		File diretorio = new File(path_local+"\\");
		if(!diretorio.exists())
		diretorio.mkdir();
		
		String nomeArq = path_local+"\\"+nome;
		
		InputStream is = url.openStream();
		
		BufferedInputStream bis = new BufferedInputStream(is);
		
		FileOutputStream fos = new FileOutputStream(nomeArq);
		int umByte = 0;
		int cont = 0;
			
			while ((umByte = bis.read()) != -1){
			
				fos.write(umByte);
				cont++;
				
				if(cont ==formstatus.taxa){
				cont = 0;	
				formstatus.atualizar();	
				}
			}
		
		
		bis.close();
		is.close();
		fos.close();
		
		return new File(nomeArq);
		} 
		catch (Exception e) {
		
		e.printStackTrace();
		return null;
		}
	}
	
	
	
	
}
