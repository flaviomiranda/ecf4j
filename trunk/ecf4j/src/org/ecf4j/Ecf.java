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
import java.util.Date;
import java.util.List;

import org.ecf4j.ecf.Aliquota;
import org.ecf4j.ecf.EcfAbstract;
import org.ecf4j.ecf.EcfEstado;
import org.ecf4j.ecf.FormaPagamento;
import org.ecf4j.ecf.LayoutCheque;
import org.ecf4j.ecf.TotalizadorNaoFiscal;
import org.ecf4j.ecf.exceptions.EcfException;
import org.ecf4j.utils.Ecf4jFactory;
import org.ecf4j.utils.comm.exceptions.CommException;


/** 
 * Classe ECF controladora
 * @author Pablo Fassina e Agner Munhoz
 * @version 1.0.0
 */
public class Ecf {
	
	private EcfAbstract ecf = null;
	
	public Ecf(){
		
	}
	
	/**
	 * M�todo de inicializa��o do ECF
	 * @param codigo C�digo do ECF
	 * @param porta Porta utilizada pelo ECF
	 * @throws EcfException Exce��o do ECF
	 * @throws CommException Exce��o da comunica��o
	 */
	public void inicializar(String codigo, String porta) throws EcfException, CommException{
		try {
			ecf = Ecf4jFactory.createEcf(codigo);
			ecf.inicializar(porta);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	public void inicializar(EcfAbstract ecf, String porta) throws EcfException{
		this.ecf = ecf;
	}
		
	/**
	 * M�todo de finaliza��o do ECF
	 */
	public void finalizar(){
		ecf.finalizar();
		ecf = null;
	}
	
	/**
	 * M�todo verifica se a porta esta habilitada
	 * @return boolean 
	 * <li> True - Se a porta esteja habilitada
	 * <li> False - Caso a porta esteja desabilitada
	 * @throws EcfException Exce��o do ECF
	 */
	public boolean isCommEnabled() throws EcfException {
		try {
			return ecf.isCommEnabled();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo retorna o fabricante do ECF
	 * @return String - Fabricante do ECF
	 * @throws EcfException Exce��o do ECF
	 */
	public String fabricante() throws EcfException{
		try {
			return ecf.fabricante();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo retorna o modelo do ECF
	 * @return String - Modelo do ECF
	 * @throws EcfException Exce��o do ECF
	 */
	public String modelo() throws EcfException{
		try {
			return ecf.modelo();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo verifica se o modelo j� foi homologado
	 * @return boolean
	 * <li> True - Caso esteja homologado
	 * <li> False - Caso n�o esteja homologado
	 * @throws EcfException Exce��o do ECF
	 */
	public boolean isHomologado() throws EcfException{
		try {
			return ecf.isHomologado();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
		
	}
	
	/**
	 * M�todo verifica se o sinal da gaveta esta invertido
	 * @return boolean
	 * <li> True - Caso esteja com Sinal invertido
	 * <li> False - Caso n�o esteja com sinal invertido
	 * @throws EcfException Exce��o do ECF
	 */
	public boolean isGavetaSinalInvertido() throws EcfException {
		try {
			return ecf.isGavetaSinalInvertido();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo seta o tipo do sinal da gaveta no ECF
	 * @param gavetaSinalInvertido boolean - indicando se o sinal esta invertido
	 * @throws EcfException Exce��o do ECF
	 */
	public void setGavetaSinalInvertido(boolean gavetaSinalInvertido) throws EcfException {
		try {
			ecf.setGavetaSinalInvertido(gavetaSinalInvertido);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	//Informa��es do ECF----------------------------------------------------------------
	
	/**
	 * M�todo verifica se h� pouco papel no ECF
	 * @return boolean
	 * <li> True - Se houver Pouco Papel
	 * <li> False - Caso n�o esteja com Pouco Papel
	 * @throws EcfException Exce��o do ECF 
	 */
	public boolean isPoucoPapel() throws EcfException{
		try {
			return ecf.isPoucoPapel();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}	
	}
	
	/**
	 * M�todo verifica se n�o h� papel no ECF
	 * @return boolean
	 * <li> True - Se estiver Sem Papel 
	 * <li> False - Se n�o estiver Sem Papel
	 * @throws EcfException Exce��o do ECF
	 */
	public boolean isSemPapel() throws EcfException{
		try {
			return ecf.isSemPapel();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo verifica se � permitido cancelar o �ltimo cupom fiscal emitido
	 * @return boolean
	 * <li> True - Caso seja poss�vel cancelar 
	 * <li> False - Caso n�o seja poss�vel cancelar
	 * @throws EcfException Exce��o do ECF
	 * @throws CommException Exce��o da comunica��o
	 */
	public boolean isPermiteCancelamentoCupomFiscal() throws EcfException, CommException{
		try {
			return ecf.isPermiteCancelamentoCupomFiscal();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo verifica se n�o h� espa�o na mem�ria fiscal
	 * @return boolean
	 * <li> True - Se n�o houver mais espa�o
	 * <li> False - Se ainda houver espa�o na mem�ria fiscal
	 * @throws EcfException Exce��o do ECF
	 * @throws CommException Exce��o de comunica��o
	 */
	public boolean isMemoriaFiscalSemEspaco() throws EcfException, CommException{
		try {
			return ecf.isMemoriaFiscalSemEspaco();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo verifica se � permitido cancelar o �ltimo cupom n�o fiscal emitido 
	 * @return boolean
	 * <li> True - Caso seja poss�vel cancelar
	 * <li> False - Caso n�o seja poss�vel cancelar
	 * @throws EcfException Exce��o do ECF
	 * @throws CommException Exce��o de comunica��o
	 */
	public boolean isPermiteCancelamentoNaoFiscal() throws EcfException, CommException{
		try {
			return ecf.isPermiteCancelamentoNaoFiscal();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo verifica se � permitido estornar comprovante
	 * @return boolean
	 * <li> True - Caso seja poss�vel estornar
	 * <li> False - Caso n�o seja poss�vel estornar
	 * @throws EcfException Exce��o do ECF
	 * @throws CommException Exce��o de comunica��o
	 */
	public boolean isPermiteEstornoComprovante() throws EcfException, CommException{
		try {
			return ecf.isPermiteEstornoComprovante();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo verifica se o ECF est� com o Hor�rio de ver�o ativo
	 * @return boolean
	 * <li> True - Se estiver ativo
	 * <li> False - Se estiver inativo
	 * @throws EcfException Exce��o do ECF
	 * @throws CommException Exce��o de comunica��o
	 */
	public boolean isHorarioVerao() throws EcfException, CommException{
		try {
			return ecf.isHorarioVerao();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo ativa/desativa hor�rio de ver�o no ECF
	 * @param isHorarioVerao parametro indicando se Hor�rio de ver�o est� ativo
	 * @throws EcfException Exce��o do ECF
	 * @throws CommException Exce��o de comunica��o
	 */
	public void setHorarioVerao(boolean isHorarioVerao) throws EcfException, CommException{
		try {
			ecf.setHorarioVerao(isHorarioVerao);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo verifica se o ECF est� truncando em seus calculos
	 * @return boolean
	 * <li> True - Se estiver truncando
	 * <li> False - Se n�o estiver truncando
	 * @throws EcfException Exce��o do ECF
	 * @throws CommException Exce��o de comunica��o
	 */
	public boolean isTruncando() throws EcfException, CommException{
		try {
			return ecf.isTruncando();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo busca no ECF seu n�mero serial
	 * @return String - N�mero serial do ECF
	 * @throws EcfException Exce��o do ECF
	 * @throws CommException Exce��o de comunica��o
	 */
	public String getNumeroSerial() throws EcfException, CommException{
		try {
			return ecf.getNumeroSerial();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo busca no ECF o n�mero do �ltimo cupom emitido
	 * @return int - N�mero do �ltimo cupom emitido
	 * @throws EcfException Exce��o do ECF
	 * @throws CommException Exce��o de comunica��o
	 */
	public int getNumeroCupom() throws EcfException, CommException{
		try {
			return ecf.getNumeroCupom();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo busca no ECF o n�mero do �ltimo cupom emitido
	 * @return int - N�mero do �ltimo cupom emitido
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public int getCOO() throws CommException, EcfException{
		try {
			return ecf.getCOO();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo busca no ECF o contador de reinicios de opera��o 
	 * @return int - Contador de reinicios de opera��o
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public int getCRO() throws CommException, EcfException{
		try {
			return ecf.getCRO();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo busca no ECF o contador de redu��es Z
	 * @return int - Contador de Redu��es Z
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public int getCRZ() throws CommException, EcfException{
		try {
			return ecf.getCRZ();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo busca no ECF o seu n�mero
	 * @return int - N�mero do ECF
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public int getNumeroEcf() throws CommException, EcfException{
		try {
			return ecf.getNumeroEcf();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo busca do ECF o n�mero da loja
	 * @return int - N�mero da Loja
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public int getNumeroLoja()  throws CommException, EcfException{
		try {
			return ecf.getNumeroLoja();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo busca no ECF a data do ultimo movimento
	 * @return Date - Data de movimento no ECF
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public Date getDataMovimento() throws CommException, EcfException{
		try {
			return ecf.getDataMovimento();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo busca no ECF a data e hora atual
	 * @return Date - Data e hora atual
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public Date getDataHora() throws CommException, EcfException{
		try {
			return ecf.getDataHora();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo busca o Grande Total atual do ECF 
	 * @return BigDecimal - Grande Total do ECF
	 * @throws CommException Exce��o de comunica��o 
	 * @throws EcfException Exce��o do ECF
	 */
	public BigDecimal getGrandeTotal() throws CommException, EcfException{
		try {
			return ecf.getGrandeTotal();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo calcula o total da venda bruta atual do ECF
	 * @return BigDecimal - Total da Venda Bruta atual
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public BigDecimal getVendaBruta() throws CommException, EcfException{
		try {
			return ecf.getVendaBruta();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo busca no EFC o n�mero do �ltimo item vendido 
	 * @return int - N�mero do �ltimo item
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public int getNumeroUltimoItem() throws CommException, EcfException{
		try {
			return ecf.getNumeroUltimoItem();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo verifica se a gaveta conectada ao ECF est� aberta
	 * @return boolean
	 * <li> True - Gaveta Aberta
	 * <li> False - Gaveta fechada
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public boolean isGavetaAberta() throws CommException, EcfException{
		try {
			return ecf.isGavetaAberta();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo envia comando a gaveta conectada ao ECF para abri-la
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public void abrirGaveta() throws CommException, EcfException{
		try {
			ecf.abrirGaveta();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo retorna o estado do ECF
	 * @return EcfEstado - Estado do ECF
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public EcfEstado getEstado() throws CommException, EcfException{
		try {
			return ecf.getEstado();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo busca o valor do grande Total no momento da �ltima Redu��o Z
	 * @return BigDecimal - Grande Total da �ltima redu��o z
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public BigDecimal getGrandeTotalReducaoZ()throws CommException, EcfException{
		try {
			return ecf.getGrandeTotalReducaoZ();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo busca do ECF o valor de acresc�mos da �ltima redu��o z 
	 * @return BigDecimal - Acresc�mos da �ltima Redu��o Z
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public BigDecimal getAcrescimoReducaoZ() throws CommException, EcfException{
		try{
			return ecf.getAcrescimoReducaoZ();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}

	/**
	 * M�todo busca do ECF o valor de descontos da �ltima redu��o z
	 * @return BigDecimal - Descontos da �ltima Reducao Z
	 * @throws CommException
	 * @throws EcfException
	 */
	public BigDecimal getDescontoReducaoZ() throws CommException, EcfException {
		try{
			return ecf.getDescontoReducaoZ();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo busca no ECF o n�mero do cupom da �ltima redu��o z
	 * @return int - COO da �ltima Redu��o Z
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public int getCOOReducaoZ() throws CommException, EcfException {
		try{
			return ecf.getCOOReducaoZ();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo busca no ECF valor total de Sangrias registradas na �ltima redu��o z
	 * @return BigDecimal - Valor total de Sangrias da �ltima Redu��o Z
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public BigDecimal getSangriaReducaoZ() throws CommException, EcfException {
		try{
			return ecf.getSangriaReducaoZ();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo busca no ECF valor total de Suprimentos registradas na �ltima redu��o z
	 * @return BigDecimal - Valor total de Suprimentos da �ltima Redu��o Z
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public BigDecimal getSuprimentoReducaoZ() throws CommException, EcfException {
		try{
			return ecf.getSuprimentoReducaoZ();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo busca no ECF valor total de Substritui��es Tribut�rias registradas na �ltima redu��o z
	 * @return BigDecimal - Valor total de Substritui��es Tribut�rias da �ltima Redu��o Z
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public BigDecimal getSubstituicao() throws CommException, EcfException{
		try{
			return ecf.getSubstituicao();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo busca no ECF valor total de N�o Tributados registrados na �ltima redu��o z
	 * @return BigDecimal - Valor total de N�o Tributados da �ltima Redu��o Z
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public BigDecimal getNaoTributado() throws CommException, EcfException{
		try{
			return ecf.getNaoTributado();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo busca no ECF valor total de Isentos registrados na �ltima redu��o z
	 * @return BigDecimal - Valor total de Isentos da �ltima Redu��o Z
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public BigDecimal getIsento() throws CommException, EcfException{
		try{
			return ecf.getIsento();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo busca o COO inicial da �ltima redu��o z
	 * @return int - COO inicial da �ltima Redu��o Z
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public int getCOOInicial() throws CommException, EcfException{
		try{
			return ecf.getCOOInicial();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo busca o COO final da �ltima redu��o z
	 * @return int - COO final da �ltima Redu��o Z
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public int getCOOFinal() throws CommException, EcfException{
		try{
			return ecf.getCOOFinal();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo busca no ECF o Contador de Reinicios de Opera��o registrado na �ltima reduc�o z
	 * @return int - CRO registrado na �ltima Redu��o Z
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public int getCROReducaoZ() throws CommException, EcfException{
		try{
			return ecf.getCROReducaoZ();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo busca no ECF o totalizador parcial de Acresc�mos
	 * @return BigDecimal - Totalizador de Acresc�mos
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public BigDecimal getAcrescimo() throws CommException, EcfException{
		try{
			return ecf.getAcrescimo();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Metodo busca no ECF o totalizador parcial de cancelamentos com incid�ncia em servi�os
	 * @return BigDecimal - Totalizador de Cancelamentos ISS
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public BigDecimal getCancelamentoISS()throws CommException, EcfException{
		try {
			return ecf.getCancelamentoISS();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Metodo busca no ECF o totalizador parcial de cancelamentos com incid�ncia em produtos
	 * @return BigDecimal - Totalizador de Cancelamentos ICMS
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public BigDecimal getCancelamentoICMS()throws CommException, EcfException{
		try {
			return ecf.getCancelamentoICMS();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Metodo busca no ECF o totalizador parcial de descontos com incid�ncia em servi�os
	 * @return BigDecimal - Totalizador de Descontos ISS
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public BigDecimal getDescontoISS()throws CommException, EcfException{
		try {
			return ecf.getDescontoISS();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Metodo busca no ECF o totalizador parcial de descontos com incid�ncia em produtos
	 * @return BigDecimal - Totalizador de Descontos ICMS
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public BigDecimal getDescontoICMS()throws CommException, EcfException{
		try {
			return ecf.getDescontoICMS();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Metodo busca no ECF o totalizador parcial de acresc�mos com incid�ncia em produtos
	 * @return BigDecimal - Totalizador de Acresc�mos ICMS
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public BigDecimal getAcrescimoICMS()throws CommException, EcfException{
		try {
			return ecf.getAcrescimoICMS();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * Metodo busca no ECF o totalizador parcial de acresc�mos com incid�ncia em servi�os
	 * @return BigDecimal - Totalizador de Acresc�mos ISS
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public BigDecimal getAcrescimoISS()throws CommException, EcfException{
		try {
			return ecf.getAcrescimoISS();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo busca no ECF o valor da Venda Bruta registrada na �ltima redu��o z
	 * @return BigDecimal - valor da Venda Bruta na �ltima Redu��o Z
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public BigDecimal getVendaBrutaReducaoZ() throws CommException, EcfException{
		try {
			return ecf.getVendaBrutaReducaoZ();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo busca no ECF o totalizador parcial de cancelamentos
	 * @return BigDecimal - Totalizador de Cancelamentos
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public BigDecimal getCancelamento() throws CommException, EcfException{
		try {
			return ecf.getCancelamento();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo busca no ECF o totalizador parcial de descontos
	 * @return BigDecimal - Totalizador de Descontos
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public BigDecimal getDesconto() throws CommException, EcfException{
		try {
			return ecf.getDesconto();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	public BigDecimal getSubtotalCupom() throws CommException, EcfException{
		try {
			return ecf.getSubtotalCupom();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	public BigDecimal getValorPagoUltimoCupom() throws CommException, EcfException{
		try {
			return ecf.getValorPagoUltimoCupom();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
		
	
	// Relat�rios --------------------------------------------------------------
	/**
	 * M�todo envia comando ao ECF para que seja emitida a Leitura X
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public void leituraX() throws CommException, EcfException{
		try {
			ecf.leituraX();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo envia comando ao ECF para que seja emitida a Redu��o Z
	 * @param dataMovimentacao <i>Date</i>  
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public void reducaoZ(Date dataMovimentacao) throws CommException, EcfException{
		try {
			ecf.reducaoZ(dataMovimentacao);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo envia comando ao ECF para que este, imprima um relat�rio da mem�ria fiscal filtrado por datas
	 * @param dataInicial <i>Date</i>
	 * @param dataFinal <i>Date</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public void leituraMemoriaFiscalData(Date dataInicial, Date dataFinal) 
			throws EcfException, CommException{
		try {
			ecf.leituraMemoriaFiscalData(dataInicial, dataFinal);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo envia comando ao ECF para que este, retorne via porta serial um relat�rio da mem�ria fiscal filtrado por datas
	 * @param dataInicial <i>Date</i>
	 * @param dataFinal <i>Date</i>
	 * @return String - Relat�rio da mem�ria fiscal para exibir em video
	 * @throws EcfException Exce��o do ECF
	 * @throws CommException Exce��o de comunica��o
	 */
	public String leituraMemoriaFiscalDataSerial(Date dataInicial, Date dataFinal) 
			throws EcfException, CommException{
		try {
			return ecf.leituraMemoriaFiscalDataSerial(dataInicial, dataFinal);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo envia comando ao ECF para que este, imprima um relat�rio da mem�ria fiscal de maneira sint�tica filtrado por datas
	 * @param dataInicial <i>Date</i>
	 * @param dataFinal <i>Date</i>
	 * @throws EcfException Exce��o do ECF
	 * @throws CommException Exce��o de comunica��o
	 */
	public void leituraMemoriaFiscalDataSimplificado(Date dataInicial, Date dataFinal) 
			throws EcfException, CommException{
		try {
			ecf.leituraMemoriaFiscalDataSimplificado(dataInicial, dataFinal);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo envia comando ao ECF para que este, retorne via porta serial um relat�rio da mem�ria fiscal sint�tico filtrado por datas
	 * @param dataInicial <i>Date</i>
	 * @param dataFinal <i>Date</i>
	 * @return String - Relat�rio da mem�ria fiscal para exibir em video
	 * @throws EcfException Exce��o do ECF
	 * @throws CommException Exce��o de comunica��o
	 */
	public String leituraMemoriaFiscalDataSimplificadoSerial(Date dataInicial, Date dataFinal) 
			throws EcfException, CommException{
		try {
			return ecf.leituraMemoriaFiscalDataSimplificadoSerial(dataInicial, dataFinal);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo envia comando ao ECF para que este, imprime um relat�rio da mem�ria fiscal filtrado por CRZ
	 * @param reducaoInicial <i>int</i> - CRZ inicial
	 * @param reducaoFinal <i>int</i> - CRZ final
	 * @throws EcfException Exce��o do ECF
	 * @throws CommException Exce��o de comunica��o
	 */
	public void leituraMemoriaFiscalReducao(int reducaoInicial, int reducaoFinal) throws EcfException, CommException{
		try {
			ecf.leituraMemoriaFiscalReducao(reducaoInicial, reducaoFinal);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo envia comando ao ECF para que este, retorne via porta serial um relat�rio da mem�ria fiscal filtrado por CRZ
	 * @param reducaoInicial <i>int</i> - CRZ inicial
	 * @param reducaoFinal <i>int</i> - CRZ final
	 * @return String - Relat�rio da mem�ria fiscal para exibir em video
	 * @throws EcfException Exce��o do ECF
	 * @throws CommException Exce��o de comunica��o
	 */
	public String leituraMemoriaFiscalReducaoSerial(int reducaoInicial, int reducaoFinal) throws EcfException, CommException{
		try {
			return ecf.leituraMemoriaFiscalReducaoSerial(reducaoInicial, reducaoFinal);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo envia comando ao ECF para que este, imprime um relat�rio da mem�ria fiscal sint�tico filtrado por CRZ
	 * @param reducaoInicial <i>int</i> - CRZ inicial
	 * @param reducaoFinal <i>int</i> - CRZ final
	 * @throws EcfException Exce��o do ECF
	 * @throws CommException Exce��o de comunica��o
	 */
	public void leituraMemoriaFiscalReducaoSimplificado(int reducaoInicial, int reducaoFinal) throws EcfException, CommException{
		try {
			ecf.leituraMemoriaFiscalReducaoSimplificado(reducaoInicial, reducaoFinal);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo envia comando ao ECF para que este, retorne via porta serial um relat�rio da mem�ria fiscal sint�tico filtrado por CRZ
	 * @param reducaoInicial <i>int</i> - CRZ inicial
	 * @param reducaoFinal <i>int</i> - CRZ final
	 * @return String - Relat�rio da mem�ria fiscal para exibir em video
	 * @throws EcfException Exce��o do ECF
	 * @throws CommException Exce��o de comunica��o
	 */
	public String leituraMemoriaFiscalReducaoSimplificadoSerial(int reducaoInicial, int reducaoFinal) throws EcfException, CommException{
		try {
			return ecf.leituraMemoriaFiscalReducaoSimplificadoSerial(reducaoInicial, reducaoFinal);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo abre um relat�rio gerencial no ECF tipo "default"
	 * @throws EcfException Exce��o do ECF
	 * @throws CommException Exce��o de comunica��o
	 */
	public void abrirRelatorioGerencial() throws CommException, EcfException{
		try {
			ecf.abrirRelatorioGerencial();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo imprime uma linha no relat�rio gerencial aberto
	 * @param texto <i>String</i> texto a ser impresso
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public void linhaRelatorioGerencial(String texto) throws CommException, EcfException{
		try {
			ecf.linhaRelatorioGerencial(texto);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo fecha o relat�rio gerencial
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public void fechaRelatorio() throws CommException, EcfException{
		try {
			ecf.fecharRelatorio();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	// Cupom Fiscal ------------------------------------------------------------
	/**
	 * M�todo abre um cupom fiscal
	 * @param cpfCnpj <i>String</i>
	 * @param nome <i>String</i>
	 * @param endereco <i>String</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public void abrirCupom(String cpfCnpj, String nome, String endereco) throws CommException, EcfException{
		try {
			ecf.abrirCupom(cpfCnpj, nome, endereco);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo vende um item sem desconto ou acr�scimo no ECF
	 * @param codigo <i>String</i>
	 * @param descricao <i>String</i>
	 * @param codAliquota <i>String</i>
	 * @param quantidade <i>BigDecimal</i>
	 * @param valorUnitario <i>BigDecimal</i>
	 * @param unidade <i>String</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public void venderItem(String codigo, String descricao, String codAliquota, 
			BigDecimal quantidade, BigDecimal valorUnitario, String unidade) 
			throws CommException, EcfException{
		try {
			ecf.venderItem(codigo, descricao, codAliquota, quantidade, valorUnitario, unidade);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}		
	}
	
	/**
	 * M�todo vende um item com acr�scimo por valor no ECF
	 * @param codigo <i>String</i>
	 * @param descricao <i>String</i>
	 * @param codAliquota <i>String</i>
	 * @param quantidade <i>BigDecimal</i>
	 * @param valorUnitario <i>BigDecimal</i>
	 * @param unidade <i>String</i>
	 * @param acrescimo <i>BigDecimal</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public void venderItemAcrescimo(String codigo, String descricao, String codAliquota, 
			BigDecimal quantidade, BigDecimal valorUnitario, String unidade, BigDecimal acrescimo) 
			throws CommException, EcfException{
		try {
			ecf.venderItemAcrescimo(codigo, descricao, codAliquota, quantidade, valorUnitario, unidade, acrescimo);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo vende um item com acr�scimo percentual no ECF
	 * @param codigo <i>String</i>
	 * @param descricao <i>String</i>
	 * @param codAliquota <i>String</i>
	 * @param quantidade <i>BigDecimal</i>
	 * @param valorUnitario <i>BigDecimal</i>
	 * @param unidade <i>String</i>
	 * @param acrescimo <i>BigDecimal</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public void venderItemAcrescimoPerc(String codigo, String descricao, String codAliquota, 
			BigDecimal quantidade, BigDecimal valorUnitario, String unidade, BigDecimal acrescimo) 
			throws CommException, EcfException{
		try {
			ecf.venderItemAcrescimoPerc(codigo, descricao, codAliquota, quantidade, valorUnitario, unidade, acrescimo);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}		
	}
	
	/**
	 * M�todo vende um item com desconto por valor no ECF
	 * @param codigo <i>String</i>
	 * @param descricao <i>String</i>
	 * @param codAliquota <i>String</i>
	 * @param quantidade <i>BigDecimal</i>
	 * @param valorUnitario <i>BigDecimal</i>
	 * @param unidade <i>String</i>
	 * @param desconto <i>BigDecimal</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public void venderItemDesconto(String codigo, String descricao, String codAliquota, 
			BigDecimal quantidade, BigDecimal valorUnitario, String unidade, BigDecimal desconto) 
			throws CommException, EcfException{
		try {
			ecf.venderItemDesconto(codigo, descricao, codAliquota, quantidade, valorUnitario, unidade, desconto);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo vende item com desconto percentual no ECF
	 * @param codigo <i>String</i>
	 * @param descricao <i>String</i>
	 * @param codAliquota <i>String</i>
	 * @param quantidade <i>BigDecimal</i>
	 * @param valorUnitario <i>BigDecimal</i>
	 * @param unidade <i>String</i>
	 * @param desconto <i>BigDecimal</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public void venderItemDescontoPerc(String codigo, String descricao, String codAliquota, 
			BigDecimal quantidade, BigDecimal valorUnitario, String unidade, BigDecimal desconto) 
			throws CommException, EcfException{
		try {
			ecf.venderItemDescontoPerc(codigo, descricao, codAliquota, quantidade, valorUnitario, unidade, desconto);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo imprime o TOTAL vendido no cupom at� o momento
	 * @param desconto <i>BigDecimal</i>
	 * @param acrescimo <i>BigDecimal</i>
	 * @param descAcresPerc <i>boolean</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public void subtotalizarCupom(BigDecimal desconto, BigDecimal acrescimo, boolean descAcresPerc)
			throws CommException, EcfException{
		try {
			ecf.subtotalizarCupom(desconto, acrescimo, descAcresPerc);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo imprime o valor pago com determinada forma de pagamento
	 * @param codFormaPagamento <i>String</i>
	 * @param valor <i>BigDecimal</i>
	 * @param observacao <i>String</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public void efetuarPagamento(String codFormaPagamento, BigDecimal valor, String observacao) 
			throws CommException, EcfException{
		try {
			ecf.efetuarPagamento(codFormaPagamento, valor, observacao);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo finaliza o cupom aberto
	 * @param observacao <i>String</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public void fecharCupom(String observacao)throws CommException, EcfException{
		try {
			ecf.fecharCupom(observacao);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo canleca cupom aberto ou o �ltimo cupom finalizado
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public void cancelarCupom() throws CommException, EcfException{
		try {
			ecf.cancelarCupom();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo cancela determinado item do cupom aberto
	 * @param item <i>int</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public void cancelarItem(int item)throws CommException, EcfException{
		try {
			ecf.cancelarItem(item);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo cancela o �ltimo item registrado, caso o cupom esteja aberto
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public void cancelarUltimoItem()throws CommException, EcfException{
		try {
			ecf.cancelarUltimoItem();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	
	// Cupom N�o Fiscal --------------------------------------------------------	
	/**
	 * M�todo emite um cupom n�o fiscal  <i>(completo)</i>
	 * @param codTotalizadorNaoFiscal <i>String</i>
	 * @param valor <i>BigDecimal</i>
	 * @param codFormaPagamento <i>String</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public void emitirNaoFiscal(String codTotalizadorNaoFiscal, BigDecimal valor, 
			String codFormaPagamento) throws CommException, EcfException{
		try {
			ecf.emitirNaoFiscal(codTotalizadorNaoFiscal, valor, codFormaPagamento);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo abre um cupom n�o fiscal
	 * @param cpfCnpj <i>String</i>
	 * @param nomeConsumidor <i>String</i>
	 * @param enderecoConsumidor <i>Strinf</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public void abrirNaoFiscal(String cpfCnpj, String nomeConsumidor, 
			String enderecoConsumidor) throws CommException, EcfException{
				try {
					ecf.abrirNaoFiscal(cpfCnpj, nomeConsumidor, enderecoConsumidor);
				} catch (NullPointerException e) {
					throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
				}	
			}
	
	/**
	 * M�todo registra um item no cupom n�o fiscal aberto
	 * @param codTotalizadorNaoFiscal <i>String</i>
	 * @param valor <i>BigDecimal</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public void registrarItemNaoFiscal(String codTotalizadorNaoFiscal, BigDecimal valor) 
			throws CommException, EcfException{
		try {
			ecf.registrarItemNaoFiscal(codTotalizadorNaoFiscal, valor);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo subtotaliza Cupom N�o fiscal
	 * @param desconto <i>BigDecimal</i>
	 * @param acrescimo <i>BigDecimal</i>
	 * @param descAcresPerc <i>boolean</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public void subtotalizarNaoFiscal(BigDecimal desconto, BigDecimal acrescimo, boolean descAcresPerc) 
			throws CommException, EcfException{
		try {
			ecf.subtotalizarNaoFiscal(desconto, acrescimo, descAcresPerc);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo imprime o valor pago com determinada forma de pagamento
	 * @param codFormaPagamento <i>String</i>
	 * @param valor <i>BigDecimal</i>
	 * @param observacao <i>String</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public void efetuarPagamentoNaoFiscal(String codFormaPagamento, BigDecimal valor, 
			String observacao) throws CommException, EcfException{
		try {
			ecf.efetuarPagamentoNaoFiscal(codFormaPagamento, valor, observacao);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo finaliza cupom n�o fiscal aberto
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public void fecharNaoFiscal() throws CommException, EcfException{
		try {
			ecf.fecharNaoFiscal();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo canleca cupom n�o fiscal aberto ou o �ltimo cupom n�o fiscal finalizado
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o de ECF
	 */
	public void cancelarNaoFiscal() throws CommException, EcfException{
		try {
			ecf.cancelarNaoFiscal();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo imprime segunda via de cupom n�o fiscal
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public void reimprimirCupomNaoFiscal() throws CommException, EcfException{
		try {
			ecf.reimprimirCupomNaoFiscal();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	//Comandos de autentica��o--------------------------------------------------
	/**
	 * M�todo imprime uma marca de autentica��o em documentos
	 * @param linhasAvanco <i>int</i>
	 * @param linhaAdicional <i>String</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public void autenticarDocumento(int linhasAvanco, String linhaAdicional) 
			throws CommException, EcfException{
		try {
			ecf.autenticarDocumento(linhasAvanco, linhaAdicional);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	// Cupom N�o Fiscal --------------------------------------------------------
	/**
	 * M�todo emite comprovantes <i>(completo)</i>
	 * @param codFormaPagamento <i>String</i>
	 * @param valor <i>BigDecimal</i>
	 * @param COO <i>int</i>
	 * @param cpfConsumidor <i>String</i>
	 * @param nomeConsumidor <i>String</i>
	 * @param enderecoConsumidor <i>String</i>
	 * @param texto <i>String</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o de ECF
	 */
	public void emitirComprovante(String codFormaPagamento, BigDecimal valor, int COO, String cpfConsumidor, 
			String nomeConsumidor, String enderecoConsumidor, String texto)throws CommException, EcfException{
		try {
			ecf.emitirComprovante(codFormaPagamento, valor, COO, cpfConsumidor, nomeConsumidor, enderecoConsumidor, texto);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo para abrir comprovantes
	 * @param codFormaPagamento <i>String</i>
	 * @param valor <i>BigDecimal</i>
	 * @param COO <i>int</i>
	 * @param cpfConsumidor <i>String</i>
	 * @param nomeConsumidor <i>String</i>
	 * @param enderecoConsumidor <i>String</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o de ECF
	 */
	public void abrirComprovante(String codFormaPagamento, BigDecimal valor, 
			int COO, String cpfConsumidor, String nomeConsumidor, String enderecoConsumidor)
			throws CommException, EcfException{
		try {
			ecf.abrirComprovante(codFormaPagamento, valor, COO, cpfConsumidor, nomeConsumidor, enderecoConsumidor);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}		
	}
	
	/**
	 * M�todo imprime linhas em um comprovante aberto
	 * @param texto <i>String</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public void imprimirLinhaComprovante(String texto) throws CommException, EcfException{
		try {
			ecf.imprimirLinhaComprovante(texto);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo finaliza comprovante aberto
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o de ECF
	 */
	public void fecharComprovante() throws CommException, EcfException{
		try {
			ecf.fecharComprovante();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo estorna comprovante emitido
	 * @param cpf <i>String</i>
	 * @param nome <i>String</i>
	 * @param endereco <i>String</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public void estornarComprovante(String cpf, String nome, String endereco) 
			throws CommException, EcfException{
		try {
			ecf.estornarComprovante(cpf, nome, endereco);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo imprime segunda via do �ltimo comprovante emitido
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public void imprimirSegundaVia() throws CommException, EcfException{
		try {
			ecf.imprimirSegundaVia();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	
	//ALIQUOTAS-----------------------------------------------------------------
	/**
	 * M�todo busca todas as aliquotas cadastradas no ECF e retorna em uma lista
	 * @return List - Lista de aliquotas 
	 */
	public List<Aliquota> getAliquotaList (){
		return ecf.getAliquotasList();
	}
	
	/**
	 * M�todo carrega as aliquotas do ECF 
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public void carregarAliquotas() throws CommException, EcfException{
		try {
			ecf.carregarAliquotas();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo programa uma nova aliquota no ECF
	 * @param codigo <i>String</i>
	 * @param aliquota <i>BigDecimal</i>
	 * @param incidencia <i>String</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public void programarAliquota(String codigo, BigDecimal aliquota, String incidencia) 
			throws CommException, EcfException{
		try {
			ecf.programarAliquota(codigo, aliquota, incidencia);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo busca uma aliquota a partir de um c�digo
	 * @param codigo <i>String</i>
	 * @return Aliquota
	 * @throws EcfException Exce��o do ECF
	 */
	public Aliquota getAliquota(String codigo) throws EcfException{
		try {
			return ecf.getAliquota(codigo);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	//FORMAS DE PAGAMENTO--------------------------------------------------------
	/**
	 * M�todo busca todas as formas de pagamento cadastradas no ECF e retorna em uma lista
	 * @return Lista de Formas de Pagamento
	 */
	public List<FormaPagamento> getFormasPagamentoList(){
		return ecf.getFormasPagamentoList();
	}
	
	/**
	 * M�todo carrega as formas de pagamento do ECF 
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public void carregarFormasPagamento() throws CommException, EcfException{
		try {
			ecf.carregarFormasPagamento();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo programa uma nova forma de pagamento no ECF
	 * @param codigo <i>String</i>
	 * @param descricao <i>String</i>
	 * @param permiteVincular <i>boolean</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public void programarFormaPagamento(String codigo, String descricao, boolean permiteVincular) 
			throws CommException, EcfException{
		try {
			ecf.programarFormaPagamento(codigo, descricao, permiteVincular);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo busca uma forma de pagamento a partir de um c�digo
	 * @param codigo <i>String</i>
	 * @return FormaPagamento
	 * @throws EcfException Exce��o do ECF
	 */
	public FormaPagamento getFormaPagamento(String codigo) throws EcfException{
		try {
			return ecf.getFormaPagamento(codigo);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo busca uma forma de pagamento a partir de uma descri��o
	 * @param descricao <i>String</i>
	 * @return FormaPagamento
	 * @throws EcfException Exce��o do ECF
	 */
	public FormaPagamento getFormaPagamentoPorDescricao(String descricao) throws EcfException{
		try {
			return ecf.getFormaPagamentoPorDescricao(descricao);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	
	//TOTALIZADORES----------------------------------------------------------------
	/**
	 * M�todo busca todas os totalizadores cadastradas no ECF e retorna em uma lista
	 * @return
	 */
	public List<TotalizadorNaoFiscal> getTotalizadoresNaoFiscaisList(){
		return ecf.getTotalizadorsNaoFiscaisList();
	}
	
	/**
	 * M�todo carrega os totalizadores n�o fiscais do ECF 
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public void carregarTotalizadoresNaoFiscais() throws CommException, EcfException{
		try {
			ecf.carregarTotalizadoresNaoFiscais();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo programa um novo totalizador n�o fiscal no ECF
	 * @param codigo <i>String</i>
	 * @param descricao <i>String</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public void programarTotalizadorNaoFiscal(String codigo, String descricao) 
			throws CommException, EcfException{
		try {
			ecf.programarTotalizadorNaoFiscal(codigo, descricao);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}	
	}
	
	/**
	 * M�todo busca um totalizador n�o fiscal a partir de um c�digo
	 * @param codigo <i>String</i>
	 * @return TotalizadorNaoFiscal
	 * @throws EcfException Exce��o do ECF
	 */
	public TotalizadorNaoFiscal getTotalizadorNaoFiscal(String codigo) throws EcfException{
		try {
			return ecf.getTotalizadorNaoFiscal(codigo);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo busca um totalizador n�o fiscal a partir de uam descri��o
	 * @param descricao <i>String</i>
	 * @return TotalizadorNaoFiscal
	 * @throws EcfException Exce��o do ECF
	 */
	public TotalizadorNaoFiscal getTotalizadorNaoFiscalPorDescricao(String descricao) throws EcfException{
		try {
			return ecf.getTotalizadorNaoFiscalPorDescricao(descricao);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	
	//----------------------------------------------------------------------------
	/**
	 * M�todo programa no ECF o nome da moeda no singular
	 * @param nome <i>String</i>
	 * @throws CommException Ece��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public void programarMoedaSingular(String nome) throws CommException, EcfException{
		try {
			ecf.programarMoedaSingular(nome);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo programa no ECF o nome da moeda no plural
	 * @param nome <i>String</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public void programarMoedaPlural(String nome) throws CommException, EcfException{
		try {
			ecf.programarMoedaPlural(nome);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo imprime cheques no ECF de acordo com os modelos dos banco 
	 * @param banco <i>String</i>
	 * @param valor <i>BigDecimal</i>
	 * @param favorecido <i>String</i>
	 * @param cidade <i>String</i>
	 * @param data <i>Date</i>
	 * @param observacao <i>Straing</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public void imprimirCheque(String banco, BigDecimal valor, String favorecido,
			String cidade, Date data, String observacao) throws CommException, EcfException{
		try {
			ecf.imprimirCheque(banco, valor, favorecido, cidade, data, observacao);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo cancela a impress�o no cheque. Esse m�todo s� ser� executado se o ECF estiver aguardando o Cheque
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public void cancelarImpressaoCheque() throws CommException, EcfException{
		try {
			ecf.cancelarImpressaoCheque();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo verifica se o ECF est� aguardando a coloca��o do cheque para impress�o
	 * @return boolean
	 * <li> True - Caso esteja aguardando
	 * <li> False - Caso n�o esteja aguardando
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public boolean isAguardandoCheque() throws CommException, EcfException{
		try {
			return ecf.isAguardandoCheque();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo verifica se o ECF est� imprimindo cheque
	 * @return boolean
	 * <li> True - Caso estaja imprimindo cheque
	 * <li> False - Caso n�o esteja imprimindo cheque
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public boolean isImprimindoCheque() throws CommException, EcfException{
		try {
			return ecf.isImprimindoCheque();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo faz com que o ECF pule linhas em sua impress�o
	 * @param qtdeLinhas <i>int</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public void pularLinhas(int qtdeLinhas) throws CommException, EcfException{
		try {
			ecf.pularLinhas(qtdeLinhas);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo faz com que o ECF corte o papel de impress�o
	 * @param corteParcial <i>boolean</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public void cortarPapel(boolean corteParcial) throws CommException, EcfException{
		try {
			ecf.cortarPapel(corteParcial);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	
	//----------------------------------------------------------------------------
	/**
	 * M�todo Carrega os layouts de cheque em uma lista
	 * @throws EcfException Exce��o do ECF
	 */
	public void carregarLayoutCheque()throws EcfException{
		try {
			ecf.carregarLayoutCheque();
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo carrega os layouts de cheques em uma lista a partir de um XML
	 * @param xml <i>String</i>
	 * @throws EcfException Exce��o do ECF
	 */
	public void carregarLayoutCheque(String xml)throws EcfException{
		try {
			ecf.carregarLayoutCheque(xml);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo carrega os layouts de cheques a partir do codigo do banco
	 * @param banco <i>String</i>
	 * @return LayoutCheque
	 * @throws EcfException Exce��o do ECF
	 */
	public LayoutCheque getLayoutCheque(String banco) throws EcfException{
		try {
			return ecf.getLayoutCheque(banco);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	/**
	 * M�todo insere um novo layout de cheque
	 * @param layouts <i>List</i>
	 * @throws EcfException
	 */
	public void setLayouts(List<LayoutCheque> layouts) throws EcfException {
		try {
			ecf.setLayouts(layouts);
		} catch (NullPointerException e) {
			throw new EcfException(EcfException.ERRO_COMUNICACAO_NAO_INICIALIZADA);
		}
	}
	
	public Integer getNumColunas(){
		return ecf.getNumColunas();
	}
}
