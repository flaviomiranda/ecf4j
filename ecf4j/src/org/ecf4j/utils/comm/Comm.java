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
 * Esse arquivo usa a biblioteca RXTX Copyright 1997-2007 by Trent Jarvi.
 * dispon�vel em: <http://users.frii.com/jarvi/rxtx/index.html> 21/09/2009.
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
package org.ecf4j.utils.comm;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.ParallelPort;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.ecf4j.utils.comm.exceptions.CommException;
import org.ecf4j.utils.comm.threads.ThreadInputComm;
import org.ecf4j.utils.i18n.MessagesI18n;

/**
 * Classe abstrata do Comm
 * @author Pablo Fassina e Agner Munhoz
 * @version 1.0.0
 */
public class Comm {
	
	private boolean commEnabled = false;
	private InputStream in = null;
	private ThreadInputComm threadIn = null;
	private OutputStream out = null;
	private CommPort commPort;
	private int timeOut = 500;
	
	
	public InputStream getIn() {
		return in;
	}
	public OutputStream getOut() {
		return out;
	}
	public int getTimeOut() {
		return timeOut;
	}
	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}
	/**
	 * @return the commEnabled
	 * Retorna true se a comunica��o estiver ativa ou false caso contrario
	 */
	public boolean isCommEnabled() {
		return commEnabled;
	}
	/**
	 * Abre comunica��o pela porta serial/paralela
	 * @param porta
	 * @param velocidade
	 * @param bitsDados
	 * @param paridade
	 * @param bitsParada
	 * @throws CommException
	 */
	public void abrirPorta(String porta, Integer velocidade, Integer bitsDados,
			Integer paridade, Integer bitsParada) throws CommException {
        CommPortIdentifier portIdentifier;
		try {
			portIdentifier = CommPortIdentifier.getPortIdentifier(porta);
		} catch (NoSuchPortException e) {
			throw new CommException(MessagesI18n.BUNDLE.getString("erroPortaNaoEncontrada"));
		}
        if ( portIdentifier.isCurrentlyOwned() )
        {
            throw new CommException(MessagesI18n.BUNDLE.getString("erroPortaUsadaPorOutroPrograma"));
        }
        else
        {
			try {
				commPort = portIdentifier.open(this.getClass().getName(),2000);
			} catch (PortInUseException e) {
				throw new CommException(MessagesI18n.BUNDLE.getString("erroPortaUsadaPorOutroPrograma"));
			}
            
            if ( commPort instanceof SerialPort )
            {
                SerialPort serialPort = (SerialPort) commPort;
                try {
					serialPort.setSerialPortParams(velocidade,bitsDados,bitsParada,paridade);
	                /* Set notifyOnDataAvailable to true to allow event driven input.*/
					//serialPort.notifyOnDataAvailable(true);
	                
	                /* Set notifyOnBreakInterrup to allow event driven break handling.*/
					//serialPort.notifyOnBreakInterrupt(true);
	                
	                /* ajusta o controle de fluxo */
					serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN |
							SerialPort.FLOWCONTROL_RTSCTS_OUT);
	                
					//serialPort.setDTR(true);  /* data terminal ready */
					
				} catch (UnsupportedCommOperationException e) {
					throw new CommException(MessagesI18n.BUNDLE.getString("erroPortaUsadaPorOutroPrograma"));
				}
                
                try {
					in = serialPort.getInputStream();
					threadIn = new ThreadInputComm();
					threadIn.setIn(in);					
					out = serialPort.getOutputStream();
					//threadIn.start();
				} catch (IOException e) {
					throw new CommException(MessagesI18n.BUNDLE.getString("erroOperacaoDeEntradaSaidaInvalida"));
				}
				commEnabled = true;
            }
            else
            {
            	if ( commPort instanceof ParallelPort ){
            		ParallelPort parallelPort = (ParallelPort) commPort;
                    try {
    					in = parallelPort.getInputStream();
    					threadIn = new ThreadInputComm();
    					threadIn.setIn(in);					
    					out = parallelPort.getOutputStream();
    					//threadIn.start();
    				} catch (IOException e) {
    					throw new CommException(MessagesI18n.BUNDLE.getString("erroOperacaoDeEntradaSaidaInvalida"));
    				}
    				commEnabled = true;
            	}
            	else{
                	throw new CommException(MessagesI18n.BUNDLE.getString("erroPortaNaoEncontrada"));	
            	}                    
            }
        } 
        
	}
	
	public void fechar() {
		threadIn.setTerminated(true);
		commPort.close();
	}
	
	private void write(String value) throws CommException { // n�o funciona
        if (value.length()>0)
			try {
				out.write(value.getBytes(),0,value.length()-2);
			} catch (IOException e) {
				throw new CommException(MessagesI18n.BUNDLE.getString("erroOperacaoDeEntradaSaidaInvalida"));
			}
	}
	
	public void write(byte[] value) throws CommException {
		write(value, 0, value.length);
	}

	public void write(byte[] value, int arg1, int arg2) throws CommException {
        if (value.length>0)
			try {
				out.write(value,arg1, arg2);
			} catch (IOException e) {
				throw new CommException(MessagesI18n.BUNDLE.getString("erroOperacaoDeEntradaSaidaInvalida"));
			}
	}
	
	public String read() throws CommException {
		
		try {
			Thread.sleep(timeOut);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return threadIn.getBuffer(); 

	}
	
	public byte[] readDireto(int qtdeBytes) throws CommException {
		byte[] buffer = new byte[qtdeBytes];
		int len = 0;
		try {
			
			while (len < qtdeBytes){
				len += this.in.read(buffer, len, qtdeBytes - len);   //(buffer);
			}
		} catch (IOException e) {
			throw new CommException("");
		}
		return buffer;

	}
	
	public byte[] readDireto() throws CommException {
		byte[] buffer;
		int len = 0;
		try {
			buffer = new byte[this.in.available()];
			len = this.in.read(buffer);   //(buffer);
		} catch (IOException e) {
			throw new CommException("");
		}
		return buffer;

	}

	public String readString(byte etx) throws CommException {
		String result = "";
		byte[] buffer = new byte[1];
		try {
			this.in.read(buffer);   //(buffer);
			while(buffer[0] != etx){
				result += (char)buffer[0];
				this.in.read(buffer);   //(buffer);
			}
		} catch (IOException e) {
			throw new CommException("");
		}
		
		return result; 

	}

	
	public void clear() throws CommException {
		try {
			int len = 0;
			do{
				byte[] buffer = new byte[this.in.available()];
				
				len = this.in.read(buffer, 0, 2);   //(buffer);
			}while(len>0);
			
		} catch (IOException e) {
			//throw new CommException("");
		}
	}

	public String read(int timeOut) throws CommException {
		
		try {
			Thread.sleep(timeOut);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return threadIn.getBuffer(); 

	}
	
	
	
}
