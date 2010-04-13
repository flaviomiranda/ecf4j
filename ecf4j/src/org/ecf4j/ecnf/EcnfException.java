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
package org.ecf4j.ecnf;

import org.ecf4j.utils.i18n.MessagesI18n;

/**
 * Classe de exce��es do ECNF
 * @author Pablo Fassina e Agner Munhoz
 * @version 1.0.0
 * @extends Exception
 */
public class EcnfException extends Exception {
	
	public static final int ERRO_ABSTRACT = 0;
	public static final int ERRO_COMANDO_NAO_EXECUTADO = 1;
	public static final int ERRO_COMANDO_INEXISTENTE = 2;
	public static final int ERRO_COMUNICACAO_NAO_INICIALIZADA = 3;
	public static final int ERRO_INICIALIZAR_ECF = 4;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int tipo = 0;
	
	public EcnfException(int tipo){
		super(defineMensagem(tipo));
		this.tipo = tipo;
	
	}
	
	public EcnfException(){
		super("outros");
		this.tipo = 0;
	
	}

	public int getTipo() {
		return tipo;
	}
	
	
	private static String defineMensagem(int tipo){
		switch (tipo) {
		case ERRO_ABSTRACT:
			return "Abstract";
		case ERRO_COMANDO_INEXISTENTE:
			return MessagesI18n.BUNDLE.getString("erroComandoInexistente");
		case ERRO_COMUNICACAO_NAO_INICIALIZADA:
			return MessagesI18n.BUNDLE.getString("erroComunicacaoNaoInicializada");
		case ERRO_INICIALIZAR_ECF:
			return MessagesI18n.BUNDLE.getString("erroInicialiazarEcf");
		default:
			return MessagesI18n.BUNDLE.getString("erroComandoNaoExecutado");
		}
	}
	

}
