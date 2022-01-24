package lancadordeapp;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.File;

import javax.swing.JFrame;



public class FormStatuDeDownload extends JFrame{

	
	
private static final long serialVersionUID = 1L;
	
	
private int status = 0;
	

public int taxa = 128 * 1024;  //128KB


private Servidor servidor;
	
private Mensagem mensagem;	


private Gerente gerente;

private String tamanho_total_em_kb;

private String tamanho_total_em_b;


	
	public FormStatuDeDownload(Servidor servidor, Gerente gerente){
		
		this.setSize( 320, 270);
		this.setResizable(false); 
		this.setLayout(null);
		this.setUndecorated(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		this.servidor = servidor;
		
		this.gerente  =gerente;
		
		this.mensagem  = gerente.mensagem;
		
		
		try { 
			
		double valor = (Double.parseDouble(this.mensagem.getTamanho()));
	   
		tamanho_total_em_kb = String.format("%(,.2f KB", valor/1024);
		tamanho_total_em_b = String.format("%(,.0f KB", valor);
		} 
		catch (NumberFormatException e) {	  
		tamanho_total_em_kb = "indefindo";
		tamanho_total_em_b = "indefindo";
		}
	}
		
		
		
		
		

		
	public final void paint(){
		
	Graphics g = getBufferStrategy().getDrawGraphics();
		
	int largura = getWidth() - getInsets().left;
	int altura = getHeight() - getInsets().bottom;
		
	Graphics2D g2 = (Graphics2D) g.create(getInsets().right,
									            getInsets().top,
									            largura,
									             altura);	
				
	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);	
		
	g2.setPaint( Color.GRAY);
	g2.fillRect(  0 , 0  , largura, altura);
		
	g2.setPaint( Color.WHITE);
	g2.fillRect(  1 , 1  , largura-2, altura-2);
		
	g2.setPaint( Color.GRAY);
		
	Font   f_t         = new Font( "Arial" ,Font.BOLD , 18 );
	Font   f_n         = new Font( "Arial" ,Font.PLAIN , 14 );
		
	int i = 0;
			
	g2.setFont( f_t);	
	g2.drawString( "Atualização disponível:", 10, i+=25);
		
	g2.drawLine(0, 40, largura, i+=15);
		
	g2.setFont( f_n);
		
	g2.drawString( "Sistema: "+mensagem.getNome_sistema(), 10, i+=20);
	g2.drawString( "Versão: "+mensagem.getVersao(), 10, i+=20);
	g2.drawString( "Compilação: "+mensagem.getCompilacao(), 10, i+=20);
	g2.drawString( "Data: "+mensagem.getData_cadastro(), 10, i+=20);
	g2.drawString( "Tamanho Total: "+tamanho_total_em_kb, 10, i+=20);
		
	if(this.status < 3*this.taxa)
	g2.drawString( "Status: iniciando download", 10, i+=20);
	else
	g2.drawString( "Status: baixando...", 10, i+=20);
	
	i+=20;
	g2.drawLine(0, i, largura, i);
		
	g2.drawString( "Baixados: "+this.status+" de "+tamanho_total_em_b+" Bytes.", 10, i+=20);
		
	i+=20;
	g2.drawLine(0, i, largura, i);
	
	g2.setFont( f_t);
	g2.drawString( "Aguarde...", 115, i+=30);
		
	if (!getBufferStrategy().contentsLost())
	getBufferStrategy().show();
	
	g.dispose(); 
	g2.dispose();
	}
		
		
		
	
	
	
		
	public void paint( Graphics g){
			
	super.paint(g);	
	paint();	
	}
		
		
			
	
		
		
	public void mostrar(){
			
	setVisible(true);	
	createBufferStrategy(2);
	paint();
		
	File arq = servidor.baixaExecutavel(mensagem.getNome(), this);
		
		if(arq!=null){
				
		Credenciais.setCredenciais(mensagem.getNome(), mensagem.getCompilacao());			
		
		System.out.println("Download terminado.");	
		
			if(this.gerente.primeiroUso){
			
			Credenciais.carregaCredenciais();
			
			this.gerente.executa();
			}
		}
		
	this.dispose();
	}
		
		
		
		
		
		
	
	
	public void atualizar(){
			
	this.status+=this.taxa;

	paint();
	}	
	
	
	
	
	
	
}
