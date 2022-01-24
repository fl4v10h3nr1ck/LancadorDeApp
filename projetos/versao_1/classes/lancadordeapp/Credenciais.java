package lancadordeapp;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.JOptionPane;




public class Credenciais {


	
	
private static final String REG_PATH = "com/mscsolucoes/apps/";
	

public static String 	VERSAO;
public static String 	NOME;	
public static String 	CODIGO;

	
	
	
	public static String getLocal(){
		
	return System.getProperty("user.home")+"\\mscsolucoes";  	
	}




	
	
	public static void carregaCredenciais(){
	
	System.out.println("Carregando credenciais...");
		
	Preferences preferences	= null;
		
		try {
					
		preferences = Preferences.userRoot().node(REG_PATH+CODIGO);
					
		if(!preferences.nodeExists(""))
		return;
				
		VERSAO  = preferences.get("versao", "");
		NOME  = preferences.get("nome", "");
		}
		catch (BackingStoreException e) {
		
		JOptionPane.showMessageDialog(null, "Impossível acessar os registros do sistema operacional.");	
			
		VERSAO  = "";
		NOME  = "";
		}
	}
		
	
	
	

	protected static void setCredenciais(String nome, String compilacao){
		
	Preferences preferences	= null;
		
		try {
				
		preferences = Preferences.userRoot().node(REG_PATH+CODIGO);
			
		if(!preferences.nodeExists(""))
		return;
			
		preferences.put("versao", compilacao);
		preferences.put("nome", nome);
		}
		catch (BackingStoreException e) {
			
		JOptionPane.showMessageDialog(null, "Impossível acessar os registros do sistema operacional.");		
		return;
		}
	}
	

	
	
}
