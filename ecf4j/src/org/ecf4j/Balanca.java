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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.ecf4j.balanca.BalancaAbstract;
import org.ecf4j.balanca.BalancaListener;
import org.ecf4j.balanca.exceptions.BalancaInativaException;
import org.ecf4j.balanca.toledo.Toledo8217;
import org.ecf4j.utils.Ecf4jFactory;
import org.ecf4j.utils.bigdecimals.BigDecimalUtils;
import org.ecf4j.utils.comm.exceptions.CommException;

/**
 * Classe controladora de Balan�a serial
 * @author Pablo Fassina e Agner Munhoz
 * @version 1.0.0
 */
public class Balanca {
	
	private List<BalancaListener> balancaListeners = new ArrayList<BalancaListener>();
	private Thread thread = null;
	public BalancaAbstract balanca = null;
	private BigDecimal pesoAnterior = BigDecimalUtils.newQtde();
	private boolean terminated = false;
	
	/**
	 * M�todo construtor da classe
	 */
	public Balanca(){		
		balanca = null;
		criarTherad();
	}
	
	/**
	 * M�todo cria a Thread que faz a leitura da porta serial da balan�a
	 */
	public void criarTherad(){
		thread = new Thread(){

			@Override
			public void run() {
				BigDecimal p = BigDecimalUtils.newQtde();
				while (!terminated){
					try {
						p = balanca.lerBalanca();
						if(BigDecimalUtils.isDifferent(p, pesoAnterior)){
							pesoAnterior = p;
							notifyBalancaListeners(p);
						}
					} catch (Exception e) {}
				}
			}			
		};
		thread.setPriority(Thread.MIN_PRIORITY);
	}
	
	/**
	 * M�todo faz a leitura do peso enviado pela balan�a na norta serial
	 * @return peso
	 * @throws BalancaInativaException Exce��o por balan�a inativa
	 * @throws CommException Exce��o de comunica��o
	 * @throws InterruptedException Exce��o por interrups�o
	 */
	public BigDecimal lerBalanca() throws BalancaInativaException, CommException, InterruptedException{
		if (balanca != null){
			return balanca.lerBalanca();
		}else{
			throw new BalancaInativaException();
		}
	}
	
	/**
	 * Metodo de inicializa��o da balan�a
	 * @param codigo <i><String/i>
	 * @param porta <i>String</i>
	 * @param velocidade <i>Integer</i>
	 * @param bitsDados <i>Integer</i>
	 * @param paridade <i>Integer</i>
	 * @param bitsParada <i>Integer</i>
	 * @throws CommException Exce��o de comunica��o
	 */
	public void inicializar(String codigo, String porta, Integer velocidade, Integer bitsDados,
			Integer paridade, Integer bitsParada) throws CommException{
		balanca = Ecf4jFactory.createBalanca(codigo);
		balanca.inicializar(porta, velocidade, bitsDados, paridade, bitsParada);
		
		if(!balancaListeners.isEmpty()){
			thread.start();
		}
	}
	
	/**
	 * M�todo que finaliza comunica��o com a balan�a
	 * @throws CommException Exce��o de comunica��o
	 */
	public void finalizar() throws CommException{
		terminated = true;
		balanca.finalizar();
	}
	
	/**
	 * M�todo adiciona uma BalancaListener a uma lista
	 * @param b <i>BalancaListener</i>
	 */
	public void addBalancaListener(BalancaListener b){
		balancaListeners.add(b);
	}
	
	/**
	 * M�todo remove uma BalancaListener de uma lista
	 * @param b <i>BalancaListener</i>
	 */
	public void removeBalancaListener(BalancaListener b){
		balancaListeners.remove(b);
	}
	
	/**
	 * M�todo passa o peso da balan�a para a BalancaListener
	 * @param peso <i>BigDecimal</i>
	 */
	public synchronized void notifyBalancaListeners(BigDecimal peso){
		if (!terminated){
			for(BalancaListener b : balancaListeners){
				b.onChangeWeight(peso);
			}	
		}
	}
}
