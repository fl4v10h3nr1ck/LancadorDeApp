package lancadordeapp;

import java.io.IOException;

import javax.swing.JOptionPane;




public class Gerente {

	

public Mensagem mensagem;	
	


public boolean primeiroUso;


	



	public Gerente(String cod){
		
	Credenciais.CODIGO = cod;
		
	Credenciais.carregaCredenciais();
	
	this.primeiroUso = Credenciais.NOME==null || Credenciais.NOME.length()==0;
	}
	
	
	
	
	
	public void checaAtualizacoes(){
		
	Servidor servidor  = new Servidor();
	
	this.mensagem = servidor.checaAtualizacoes();
	
		if(this.primeiroUso && this.mensagem==null){
		
		JOptionPane.showMessageDialog(null, "A primeira versão deste sistema ainda não está disponível.");	
		return;
		}
	
		if(mensagem.getStatus()!=null && 
				mensagem.getStatus().compareTo("SUCESSO")==0){
			
			if(mensagem.getCompilacao()!=null && 
					mensagem.getCompilacao().length()>0 && 
						mensagem.getCompilacao().compareTo(Credenciais.VERSAO)!=0){	
			
			
			Gerente gerente = this;	
				
				new Thread(){
					
					@Override
					public void run() {
					System.out.println("Baixando atualizacoes...");	
					
					FormStatuDeDownload form = 
							new FormStatuDeDownload(servidor, gerente);
				
					form.mostrar();
					}
				}.start();
			}
			else{
				
			if(this.primeiroUso)
			JOptionPane.showMessageDialog(null, "A primeira versão deste sistema ainda não está disponível.");	

			}	
		}
		else{
			
		if(this.primeiroUso)		
		JOptionPane.showMessageDialog(null, "A primeira versão deste sistema ainda não está disponível.");	

		}
	}
	

	
	
	
	public void executa(){
		
		if(Credenciais.NOME!=null && Credenciais.NOME.length()>0){
			
		System.out.println("Carregando sistema...");
					
			try{
			        
			String base = Credenciais.getLocal();  
			
			String param = "";
					  
			if(Credenciais.NOME.endsWith(".jar"))
			param = "java -jar ";
					  
			if(Runtime.getRuntime().exec(param+base+"\\"+Credenciais.NOME)==null)
			JOptionPane.showMessageDialog(null, "Não foi possível localizar o arquivo executável do sistema. O arquivo pode ter sido renomeado ou apagado. Entre em contato com suporte.", "ERRO", JOptionPane.ERROR_MESSAGE);		 
			        
			}
			catch(IOException iOException){
				         
			iOException.printStackTrace();
			
			JOptionPane.showMessageDialog(null, "Não foi possível localizar o arquivo executável do sistema. O arquivo pode ter sido renomeado ou apagado. Entre em contato com suporte.", "ERRO", JOptionPane.ERROR_MESSAGE);		
			}	
		}
	
	System.out.println("Carregamento concluido...");
	}
	
	
	
	
	
}
