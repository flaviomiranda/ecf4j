/**
 * Ecf4J - framework Java para intera��o com equipamentos de Automa��o Comercial 
 * 
 * Direitos Autorais Reservados (c) 2009-2010 ecf4j.org
 *
 * Autores: Agner Ger�nimo Munhoz, 
 *          Pablo Henrique Fassina, 
 *          Rafael Pasqualini de Freitas,
 *          Wellington Carvalho
 *
 * Voc� pode obter a �ltima vers�o desse arquivo na pagina do Ecf4J.org
 * dispon�vel em: <http://www.ecf4j.org> 21/09/2009.
 *
 * Este arquivo � parte da framework Ecf4J
 *
 * Ecf4J � um framework livre; voc� pode redistribui-lo e/ou 
 * modifica-lo dentro dos termos da Licen�a P�blica Geral Menor GNU como 
 * publicada pela Funda��o do Software Livre (FSF); na vers�o 2.1 da 
 * Licen�a.
 *
 * Este framework � distribuido na esperan�a que possa ser  util, 
 * mas SEM NENHUMA GARANTIA; sem uma garantia implicita de ADEQUA��O a qualquer
 * MERCADO ou APLICA��O EM PARTICULAR. Veja a
 * Licen�a P�blica Geral GNU para maiores detalhes.
 *
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral Menor GNU
 * junto com este framework, se n�o, escreva para a Funda��o do Software
 * Livre(FSF) Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
package org.ecf4j;

import java.util.ArrayList;
import java.util.List;

import org.ecf4j.scanner.ScannerAbstract;
import org.ecf4j.scanner.ScannerListener;
import org.ecf4j.scanner.exceptions.ScannerInativoException;
import org.ecf4j.utils.Ecf4jFactory;
import org.ecf4j.utils.bytes.ByteUtils;
import org.ecf4j.utils.comm.exceptions.CommException;

/**
 * Classe controladora de Scanner serial
 * @author Pablo Fassina e Agner Munhoz
 * @version 1.0.0
 */
public class Scanner {
	
	//propriedades
	private List<ScannerListener> scannerListeners = new ArrayList<ScannerListener>();
	private Thread thread = null;
	private ScannerAbstract scanner = null;
	private boolean scannerVazio = true;
	private byte[] buffer = null;
	private boolean terminated = false;
	
	//metodos
	/**
	 * M�todo construtor da classe
	 */
	public Scanner(){
		scanner = null;
		criarThread();
	}
	
	/**
	 * M�todo cria a thread que faz a leitura da porta do scanner
	 */
	private void criarThread(){
		thread = new Thread(){
			
			@Override
			public void run() {
				byte[] b = null;
				
				while(!terminated){
					
					try {
						b = scanner.lerBytes();
						if (b.length > 0){
							scannerVazio = false;
							for(int i = 0; i < b.length; i++){
								if(b[i] != scanner.getDelimitador()){
									buffer = ByteUtils.encondeByteArray(buffer, ByteUtils.newByteArray(b[i]));
								}
								else{
									if ((buffer != null)&&(buffer.length > 0)){
										notifyScannerListeners(ByteUtils.byteArrayToEan(buffer));//(new String(buffer).trim());
										buffer = null;
									}
									/*
									if(b[i+1] != 0){
										buffer = ByteUtils.encondeByteArray(buffer, ByteUtils.newByteArray(b[i+1]));
									}
									*/
								}
							}	
						} else{
							scannerVazio = true;	
						}
					} catch (Exception e) {}
				}
			}
		};
		thread.setPriority(Thread.MIN_PRIORITY);
	}
	
	/**
	 * M�todo captura o conte�do da porta do Scanner
	 * @return C�digo capturado pelo Scanner
	 * @throws ScannerInativoException 
	 * @throws CommException 
	 */
	public String lerScanner() throws ScannerInativoException, CommException{
		if (scanner != null){
			return scanner.lerScanner();
		}else{
			throw new ScannerInativoException();
		}
	}
	
	/**
	 * M�todo inicializa comunica��o com o Scanner
	 * @param codigo <i>String</i>
	 * @param porta <i>String</i>
	 * @param velocidade <i>Integer</i>
	 * @param bitsDados <i>Integer</i>
	 * @param paridade <i>Integer</i>
	 * @param bitsParada <i>Integer</i>
	 * @throws CommException Exce��o de comunica��o
	 */
	public void inicializar (String codigo, String porta, Integer velocidade, Integer bitsDados,
			Integer paridade, Integer bitsParada) throws CommException{
		scanner = Ecf4jFactory.createScanner(codigo);
		scanner.inicializar(porta, velocidade, bitsDados, paridade, bitsParada);
		
		if(!scannerListeners.isEmpty()){
			thread.start();
		}
		
	}
	
	/**
	 * M�todo finaliza comunica��o com Scanner
	 * @throws CommException Exce��o de comunica��o
	 */
	public void finalizar() throws CommException{
		terminated = true;
		scanner.finalizar();
	}
	
	/**
	 * M�todo adiciona um ScannerListener a lista
	 * @param s ScannerListener
	 */
	public void addScannerListener(ScannerListener s){
		scannerListeners.add(s);
	}
	
	/**
	 * M�todo remove um ScannerListener da lista
	 * @param s ScannerListener
	 */
	public void removeScannerListener(ScannerListener s){
		scannerListeners.remove(s);
	}
	
	/**
	 * M�todo passa o c�digo capturado pelo scanner para o ScannerListener
	 * @param codigo <i>String</i>
	 */
	private synchronized void notifyScannerListeners(String codigo){
		if (!terminated){
			for(ScannerListener s : scannerListeners){
				s.onRead(codigo);
			}	
		}
	}
	
	/**
	 * M�todo limpa o buffer do scanner
	 * @throws CommException Exce��o de comunica��o
	 */
	public void limparScanner() throws CommException{
		byte[] b = scanner.lerBytes();
		buffer = null;		
	}
	
	/**
	 * M�todo verifica se o buffer do scanner est� vazio
	 * @return boolena
	 * <li> True - Caso o buffer esteja vazio
	 * <li> False - Caso o buffer n�o estaja vazio
	 */
	public boolean isScannerVazio(){
		return scannerVazio;
	}
}
