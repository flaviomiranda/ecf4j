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
package org.ecf4j.balanca.toledo;

import java.math.BigDecimal;

import org.ecf4j.utils.bigdecimals.BigDecimalUtils;
import org.ecf4j.utils.bytes.ByteUtils;
import org.ecf4j.utils.comm.exceptions.CommException;

/**
 * Classe Balan�a Toledo8217
 * @author Pablo Fassina e Agner Munhoz
 * @version 1.0.0
 * @extends BalancaAbstract
 * @see BalancaAbstract
 */
public class Toledo8217 extends ToledoAbstract {

	@Override
	public void inicializar(String porta, Integer velocidade, Integer bitsDados,
			Integer paridade, Integer bitsParada) throws CommException {
		abrirComm(porta, 2400, 8, 0, 1);
	}

	@Override
	public BigDecimal lerBalanca() throws CommException, InterruptedException {
		comm.write(ByteUtils.newByteArray(5));
		Thread.sleep(200);
		
		byte[] bytes = comm.readDireto();//(7);
      
		//Thread.sleep(500);
		
		String peso = new String(bytes);
		String s = peso.substring(3, 6);
		
		peso = peso.substring(1, 3);
		peso = peso + "." + s;
		
		return BigDecimalUtils.newQtde(peso);
	}
	
	
	
	

}
