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
package org.ecf4j.utils.comm.threads;

import java.io.IOException;
import java.io.InputStream;

import org.ecf4j.utils.comm.exceptions.CommException;
import org.ecf4j.utils.i18n.MessagesI18n;

/**
 * 
 * @author Pablo Fassina e Agner Munhoz
 */
public class ThreadInputComm extends Thread {
	
	private InputStream in = null;
	private int timeOut = 200;
	private boolean terminated = false;
	private String buffer = "";
		

	public synchronized String getBuffer() {
		String result = buffer;
		this.buffer = "";
		return result;
	}

	private void setBuffer(String buffer) {
		this.buffer += buffer;
	}
	
	public synchronized void clearBuffer(){
		this.buffer = "";
	}

	public boolean isTerminated() {
		return terminated;
	}

	public void setTerminated(boolean terminated) {
		this.terminated = terminated;
	}

	public int getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

	public InputStream getIn() {
		return in;
	}

	public void setIn(InputStream in) {
		this.in = in;
	}

	@Override
	public void run() {
		byte[] buffer = new byte[1024];
		int len = 0;
		 
		if (in != null){
			while(!terminated){
				
				try {
					len = this.in.read(buffer);
					setBuffer(new String(buffer,0,len));
				} catch (IOException e) {
					e.printStackTrace();
				}
				

			}
		}
		super.run();
	}
	
	
	
	

}
