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

import org.ecf4j.ecnf.EcnfAbstract;
import org.ecf4j.ecnf.EcnfException;
import org.ecf4j.utils.Ecf4jFactory;
import org.ecf4j.utils.comm.exceptions.CommException;

/**
 * Classe controladora do ECNF
 * @author Pablo Fassina e Agner Munhoz
 * @version 1.0.0
 */
public class Ecnf {
	
	private EcnfAbstract ecnf = null;

	/**
	 * M�todo inicializa comunica��o com ECNF
	 * @param codigo <i>String</i>
	 * @param porta <i>String</i>
	 * @throws EcnfException Exce��o do ECNF
	 * @throws CommException Exce��o do comunica��o
	 */
	public void inicilizar(String codigo, String porta) throws EcnfException, CommException{
		try{
			ecnf = Ecf4jFactory.createEcnf(codigo);
			System.out.println(ecnf.modelo());
			ecnf.inicializar(porta);
		}catch(NullPointerException e){
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo finaliza comunica��o com ECNF
	 */
	public void finalizar(){
		ecnf.finalizar();
		ecnf = null;
	}
	
	/**
	 * M�todo carrega configura��es do cupom
	 * @throws EcnfException Exce��o do ECNF
	 */
	public void carregarCupomConfig() throws EcnfException{
		try {
			ecnf.carregarCupomConfig();
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}		
	}
	
	/**
	 * M�todo carrega as configura��es do cupom a partir de um XML
	 * @param xml <i>String</i>
	 * @throws EcnfException Exce��o do ECNF
	 */
	public void carregarCupomConfig(String xml) throws EcnfException{
		try {
			ecnf.carregarCupomConfig(xml);
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo retorna o alinhamento utilizado
	 * @return alinhamento
	 * @throws EcnfException Exce��o do ECNF
	 */
	public int getAlinhamento() throws EcnfException {
		try {
			return ecnf.getAlinhamento();
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}

	/**
	 * M�todo seta um novo alinhamento a ser utilizado pela ECNF
	 * @param alinhamento <i>int</i>
	 * @throws EcnfException Exce��o do ECNF
	 */
	public void setAlinhamento(int alinhamento) throws EcnfException {
		try {
			ecnf.setAlinhamento(alinhamento);
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}

	/**
	 * M�todo verifica se texto a ser impresso ser� sublinhado
	 * @return boolean
	 * <li> True - Caso esteja sublinhado
	 * <li> False - Caso n�o esteja sublinhado
	 * @throws EcnfException Exce��o do ECNF
	 */
	public boolean isSublinhado() throws EcnfException {
		try {
			return ecnf.isSublinhado();
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}

	/**
	 * M�todo seta se o texto a ser impresso ser� sublinhado ou n�o
	 * @param sublinhado <i>boolean</i>
	 * @throws EcnfException Exce��o do ECNF
	 */
	public void setSublinhado(boolean sublinhado) throws EcnfException {
		try {
			ecnf.setSublinhado(sublinhado);
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}

	/**
	 * M�todo verifica se texto a ser impresso ser� it�lico
	 * @return boolean
	 * <li> True - Caso esteja it�lico
	 * <li> False - Caso n�o esteja it�lico
	 * @throws EcnfException Exce��o do ECNF
	 */
	public boolean isItalico() throws EcnfException {
		try {
			return ecnf.isItalico();
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}

	/**
	 *  M�todo seta se o texto a ser impresso ser� it�lico ou n�o
	 * @param italico <i>boolean</i>
	 * @throws EcnfException Exce��o do ECNF
	 */
	public void setItalico(boolean italico) throws EcnfException {
		try {
			ecnf.setItalico(italico);
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}

	/**
	 * M�todo verifica se texto a ser impresso ser� negrito
	 * @return boolean
	 * <li> True - Caso esteja negrito
	 * <li> False - Caso n�o esteja negrito
	 * @throws EcnfException Exce��o do ECNF
	 */
	public boolean isNegrito() throws EcnfException {
		try {
			return ecnf.isNegrito();
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}

	/**
	 *  M�todo seta se o texto a ser impresso ser� negrito ou n�o
	 * @param negrito <i>boolean</i>
	 * @throws EcnfException Exce��o do ECNF
	 */
	public void setNegrito(boolean negrito) throws EcnfException {
		try {
			ecnf.setNegrito(negrito);
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}

	/**
	 * M�todo for�a a ativa��o do modo it�lico
	 * @throws EcnfException Exce��o do ECNF
	 */
	public void ativarItalico() throws EcnfException{
		try {
			ecnf.ativarItalico();
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo for�a a desativa��o do modo it�lico
	 * @throws EcnfException Exce��o do ECNF
	 */
	public void desativarItalico() throws EcnfException{
		try {
			ecnf.desativarItalico();
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo for�a a ativa��o do modo sublinhado
	 * @throws EcnfException Exce��o do ECNF
	 */
	public void ativarSublinhado() throws EcnfException{
		try {
			ecnf.ativarSublinhado();
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}	
	}
	
	/**
	 * M�todo for�a a desativa��o do modo sublinhado
	 * @throws EcnfException Exce��o do ECNF
	 */
	public void desativarSublinhado() throws EcnfException{
		try {
			ecnf.desativarSublinhado();
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}	
	}
	
	/**
	 * M�todo for�a a ativa��o do modo negrito
	 * @throws EcnfException Exce��o do ECNF
	 */
	public void ativarNegrito() throws EcnfException{
		try {
			ecnf.ativarNegrito();
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo for�a a desativa��o do modo negrito
	 * @throws EcnfException Exce��o do ECNF
	 */
	public void desativarNegrito() throws EcnfException{
		try {
			ecnf.desativarNegrito();
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo imprime uma linha no modo expandido
	 * @param linha <i>String</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcnfException Exce��o do ECNF
	 */
	public void imprimirLinhaExpandida(String linha) throws CommException, EcnfException{
		try {
			ecnf.imprimirLinhaExpandida(linha);
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo imprime uma linha no modo condensado
	 * @param linha <i>String</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcnfException Exce��o do ECNF
	 */
	public void imprimirLinhaCondensada(String linha) throws CommException, EcnfException{
		try {
			ecnf.imprimirLinhaCondensada(linha);
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo imprime uma linha
	 * @param linha <i>String</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcnfException Exce��o do ECNF
	 */
	public void imprimirLinha(String linha) throws CommException, EcnfException{
		try {
			ecnf.imprimirLinha(linha);
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo imprime cabe�alho do cupom
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcnfException Exce��o do ECNF
	 */
	public void imprimirCabecalho() throws CommException, EcnfException {
		try {
			ecnf.imprimirCabecalho();
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}

	/**
	 * M�todo imprime rodap� do cupom
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcnfException Exce��o do ECNF
	 */
	public void imprimirRodape() throws CommException, EcnfException{
		try {
			ecnf.imprimirRodape();
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}	
	}

	/**
	 * M�todo pula linhas nas impress�o do ECNF
	 * @param numLinhas <i>int</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcnfException Exce��o do ECNF
	 */
	public void avancarLinhas() throws CommException{
		ecnf.avancarLinhas();
	}
	
	public String alinhaItem(String str1, String str2, int dif){
		return ecnf.alinhaItem(str1, str2, dif);
	}
	
	public void imprimirItem(int numItem, String codProduto, String descProduto, 
			String un, BigDecimal qtdeItem, BigDecimal valorItem) throws EcnfException{
		try {
			ecnf.imprimirItem(numItem, codProduto, descProduto, un, qtdeItem, valorItem);
		} catch (CommException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	public void imprimirLinha(String str1, String str2)throws EcnfException, CommException{
		try {
			ecnf.imprimirLinha(str1, str2);
		} catch (CommException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}

	public void imprimirLinhaExpandida(String str1, String str2)throws EcnfException, CommException{
		try {
			ecnf.imprimirLinhaExpandida(str1, str2);
		} catch (CommException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	public void imprimirSeparador() throws CommException{
		ecnf.imprimirSeparador();
	}
	
	public void pularLinha(int numLinhas) throws CommException, EcnfException{
		try {
			ecnf.pularLinha(numLinhas);
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo verifica se a porta serial/paralela est� habilitada
	 * @return boolean 
	 * <li> True - Porta habilitada
	 * <li> False - Porta desabilitada
	 * @throws EcnfException
	 */
	public boolean isCommEnabled() throws EcnfException {
		try {
			return ecnf.isCommEnabled();
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo busca o fabricante do ECNF
	 * @return fabricante
	 * @throws EcnfException Exce��o do ECNF
	 */
	public String fabricante() throws EcnfException{
		try {
			return ecnf.fabricante();
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo busca o modelo do ECNF
	 * @return modelo
	 * @throws EcnfException Exce��o do ECNF
	 */
	public String modelo() throws EcnfException{
		try {
			return ecnf.modelo();
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo imprime codigos de barras modelo EAN13
	 * @param ean <i>String</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcnfException Exce��o do ECNF
	 */
	public void imprimirEan13(String ean) throws CommException, EcnfException{
		try {
			ecnf.imprimirEan13(ean);
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo imprime codigos de barras modelo EAN8
	 * @param ean <i>String</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcnfException Exce��o do ECNF
	 */
	public void imprimirEan8(String ean) throws CommException, EcnfException{
		try {
			ecnf.imprimirEan8(ean);
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo for�a a ativa��o do modo normal no tamanho da fonte de impress�o
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcnfException Exce��o do ECNF
	 */
	public void ativarModoNormal() throws CommException, EcnfException{
		try {
			ecnf.ativarModoNormal();
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo for�a a ativa��o do modo condensado no tamanho da fonte de impress�o
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcnfException Exce��o do ECNF
	 */
	public void ativarModoCondensado() throws CommException, EcnfException{
		try {
			ecnf.ativarModoCondensado();
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo for�a a ativa��o do modo expandido no tamanho da fonte de impress�o
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcnfException Exce��o do ECNF
	 */
	public void ativarModoExpandido() throws CommException, EcnfException{
		try {
			ecnf.ativarModoExpandido();
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo corta o papel integralmente no ECNF
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcnfException Exce��o do ECNF
	 */
	public void cortarPapel() throws CommException, EcnfException{
		try {
			ecnf.cortarPapel();
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo corta o papel parcialmente no ECNF
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcnfException Exce��o do ECNF
	 */
	public void cortarParcialmentePapel() throws CommException, EcnfException{
		try {
			ecnf.cortarParcialmentePapel();
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo abre a gaveta conectada no ECNF
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcnfException Exce��o do ECNF
	 */
	public void abrirGaveta() throws CommException, EcnfException{
		try {
			ecnf.abrirGaveta();
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}	
	}
	
	/**
	 * M�todo verifica se a gaveta est� aberta
	 * @return boolean
	 * <li> True - Caso a gaveta esteja aberta
	 * <li> False - Caso a gaveta estaja fechada
	 * @throws CommException
	 * @throws EcnfException
	 */
	public boolean isGavetaAberta() throws CommException, EcnfException{
		try {
			return ecnf.isGavetaAberta();
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo pula uma linha na impress�o do ECNF
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcnfException Exce��o do ECNF
	 */
	public void pularLinha() throws CommException, EcnfException{
		try {
			ecnf.pularLinha();
		} catch (NullPointerException e) {
			throw new EcnfException(EcnfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo verifica a quantidade de caracteres por linha no modelo de ECF  instanciado
	 * @return N�mero de colunas
	 */
	public Integer getNumColunas(){
		return ecnf.getNumColunas();
	}

}
