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
package org.ecf4j.balanca;

import java.math.BigDecimal;

import org.ecf4j.utils.comm.CommEquipto;
import org.ecf4j.utils.comm.exceptions.CommException;

/**
 * Classe abstrata de balan�as
 * @author Pablo Fassina e Agner Munhoz
 * @version 1.0.0
 * @extends CommEquipto
 * @see CommEquipto
 */
public abstract class BalancaAbstract extends CommEquipto {
	
	/**
	 * M�todo abre a comunica��o Serial/Paralela
	 * @param porta <i>String</i>
	 * @param velocidade <i>Integer</i>
	 * @param bitsDados <i>Integer</i>
	 * @param paridade <i>Integer</i>
	 * @param bitsParada <i>Integer</i>
	 * @throws CommException Exce��o de comunica��o
	 */
	public void abrirComm(String porta, Integer velocidade, Integer bitsDados,
			Integer paridade, Integer bitsParada) throws CommException {
		
		comm.abrirPorta(porta, velocidade, bitsDados, paridade, bitsParada);
	}
	
	/**
	 * M�todo fecha comunica��o Serial/paralela
	 */
	public void fecharComm(){
		
		comm.fechar();
	}
	
	/**
	 * M�todo captura o conte�do da porta da balan�a
	 * @return Valor capturado da balan�a
	 * @throws CommException Exce��o de comunica��o 
	 * @throws InterruptedException  Exce��o por interrup��o
	 */
	public abstract BigDecimal lerBalanca() throws CommException, InterruptedException;
	/**
	 * M�todo inicializa comunica��o com a balan�a
	 * @param porta <i>String</i>
	 * @param velocidade <i>Integer</i>
	 * @param bitsDados <i>Integer</i>
	 * @param paridade <i>Integer</i>
	 * @param bitsParada <i>Integer</i>
	 * @throws CommException Exce�ao de comunica��o
	 */
	public abstract void inicializar(String porta, Integer velocidade, Integer bitsDados,
			Integer paridade, Integer bitsParada) throws CommException;
	/**
	 * M�todo finaliza comunica��o com a balan�a
	 * @throws CommException Exce��o de comunica��o
	 */
	public abstract void finalizar() throws CommException;

}
