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
package org.ecf4j.ecf;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.ecf4j.ecf.exceptions.EcfException;
import org.ecf4j.utils.bigdecimals.BigDecimalUtils;
import org.ecf4j.utils.comm.CommEquipto;
import org.ecf4j.utils.comm.exceptions.CommException;
import org.ecf4j.utils.files.FileUtils;

import com.thoughtworks.xstream.XStream;

/**
 * Classe abstrata do ECF
 * @author Pablo Fassina e Agner Munhoz
 * @version 1.0.0
 * @extends CommEquipto
 * @see CommEquipto
 */
public abstract class EcfAbstract extends CommEquipto {
	
	protected boolean commEnabled = false;
	
	private List<Aliquota> aliquotas = new ArrayList<Aliquota>();
	private List<FormaPagamento> formasPagamento = new ArrayList<FormaPagamento>();
	private List<TotalizadorNaoFiscal> totalizadoresNaoFiscais = new ArrayList<TotalizadorNaoFiscal>();
	private boolean gavetaSinalInvertido = false;
	private List<LayoutCheque> layoutsCheque = new ArrayList<LayoutCheque>();
	
	
	
	/**
	 * M�todo verifica o tipo de sinal da gaveta
	 * @return boolean
	 * <li> True - Caso Sinal esteja invertido
	 * <li> False - Caso o sinal n�o esteja invertido
	 */
	public boolean isGavetaSinalInvertido() {
		return gavetaSinalInvertido;
	}
	/**
	 * M�todo altera o valor do flag de sinal invertido
	 * @param gavetaSinalInvertido <i>boolean</i>
	 */
	public void setGavetaSinalInvertido(boolean gavetaSinalInvertido) {
		this.gavetaSinalInvertido = gavetaSinalInvertido;
	}
	
	/**
	 * M�todo prepara o comando para ser enviado para o ECF
	 * @param cmd <i>byte[]</i>
	 * @return resposta do ECF
	 */
	protected byte[] preparaComando(byte[] cmd){
		return preparaComando(cmd, "");
	}
	/**
	 * M�todo prepara o comando para ser enviado para o ECF
	 * @param cmd <i>byte[]</i>
	 * @param prm <i>String</i>
	 * @return resposta do ECF
	 */
	protected byte[] preparaComando(byte[] cmd, String prm){
		return preparaComando(cmd, prm.getBytes());
	}
	/**
	 * M�todo abstrato inicializa comunica��o com o ECF
	 * @param porta <i>String</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract void inicializar(String porta) throws CommException, EcfException;
	/**
	 * M�todo abstrato finaliza comunica��o com ECF
	 */
	public abstract void finalizar();
	/**
	 * M�todo abstrato prepara o comando para ser enviado para o ECF
	 * @param cmd <i>byte[]</i>
	 * @param prm <i>byte[]</i>
	 * @return resposta do ECF
	 */
	protected abstract byte[] preparaComando(byte[] cmd, byte[] prm);
	/**
	 * M�todo abstrato verifica um retorno simples do ECF
	 * @throws EcfException Exce��o do ECF
	 * @throws CommException Exce��o de comunica��o
	 */
	protected abstract void verificaRetorno() throws EcfException, CommException;
	/**
	 * M�todo abstrato verifica um retorno com uma determinada quantidade de caracteres de resposta do ECF
	 * @param len <i>int</i>
	 * @return Pode retornar um erro do ECF 
	 * @throws EcfException Exce��o do ECF 
	 * @throws CommException Exce��o de comunica��o
	 */
	protected abstract byte[] getRetorno(int len) throws EcfException, CommException;
	/**
	 * M�todo abstrato envia para o ECF um comando formatado
	 * @param cmd <i>byte[]</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	protected abstract void executaComando(byte[] cmd) throws CommException, EcfException;
	/**
	 * M�todo abstrato busca o fabricante do ECF
	 * @return Fabricanto do ECF
	 */
	public abstract String fabricante();
	/**
	 * M�todo abstrato busca o modelo do ECF
	 * @return Modelo do ECF
	 */
	public abstract String modelo();
	/**
	 * M�todo abstrato verifica se o modelo est� homologado
	 * @return boolean
	 * <li> True - Caso esteja homologado
	 * <li> False - Caso n�o esteja homologado
	 */
	public abstract boolean isHomologado();
	/**
	 * M�todo abstrato busca a quantidade de caracteres por linha no ECF
	 * @return N�mero de colunas
	 */
	public abstract Integer getNumColunas();
	
	
	@Override
	public String toString() {
		return fabricante()+ " " + modelo();
	}
	
	//Informa��es do ECF----------------------------------------------------------
	/**
	 * M�todo abstrato verifica se h� pouco papel no ECF
	 * @return boolean
	 * <li> True - Caso esteja com pouco papel
	 * <li> False - Caso n�o esteja com pouco papel
	 */
	public abstract boolean isPoucoPapel();
	/**
	 * M�todo abstrato verifica se o ECF est� sem papel
	 * @return Boolean
	 * <li> True - Caso esteja sem papel no ECF
	 * <li> False - Caso n�o esjeta sem papel no ECF
	 */
	public abstract boolean isSemPapel();
	/**
	 * M�todo abstrato verifica no ECF se � poss�vel cancelar o �ltimo cupom fiscal emitido
	 * @return boolean 
	 * <li> True - Caso seja poss�vel cancelar o �ltimo cupom fiscal
	 * <li> False -Caso n�o seja poss�vel cancelar o �ltimo cupom fiscal 
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract boolean isPermiteCancelamentoCupomFiscal() throws CommException, EcfException;
	/**
	 * M�todo abstrato verifica se h� espa�o na mem�ria fiscal
	 * @return boolean
	 * <li> True - Caso n�o haja mais espa�o na mem�ria fiscal
	 * <li> False - Caso ainda haja espa�o na mem�ria fiscal
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract boolean isMemoriaFiscalSemEspaco() throws CommException, EcfException;
	/**
	 * M�todo abstrato verifica no ECF se � poss�vel cancelar o �ltimo cupom n�o fiscal
	 * @return boolean
	 * <li> True - Caso seja poss�vel cancelar o �ltimo cupom n�o fiscal emitido
	 * <li> False - Caso n�o seja poss�vel cancelar o �ltimo cupom n�o fiscal emitido
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract boolean isPermiteCancelamentoNaoFiscal() throws CommException, EcfException;
	/**
	 * M�todo abstrato verifica no ECF se � poss�vel estornar comprovante
	 * @return boolean
	 * <li> True - Caso seja poss�vel estornar comprovante
	 * <li> False - Caso n�o seja poss�vel estornar comprovante
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract boolean isPermiteEstornoComprovante() throws CommException, EcfException;
	/**
	 * M�todo abstrato verifica no ECF se o flag de hor�rio de ver�o est� marcado
	 * @return boolean
	 * <li> True - Caso o ECF esteja trabalhando em hor�rio de ver�o
	 * <li> False - Caso o ECF n�o esteja trabalhando em hor�rio de ver�o
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract boolean isHorarioVerao() throws CommException, EcfException;
	/**
	 * M�todo abstrato seta o flag de hor�rio de ver�o do ECF
	 * @param isHorarioVerao <i>boolean</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract void setHorarioVerao(boolean isHorarioVerao) throws CommException, EcfException;
	/**
	 * M�todo abstrato verifica se o ECF est� truncando em seus calculos
	 * @return boolean
	 * <li> True - Caso o ECF esteja truncando
	 * <li> False - Caso o ECF n�o esteja truncando
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract boolean isTruncando() throws CommException, EcfException;
	/**
	 * M�todo abstrato busca no n�mero serial do ECF
	 * @return <i>String</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract String getNumeroSerial() throws CommException, EcfException;
	/**
	 * M�todo busca no ECF o n�mero do �ltimo cupom
	 * @return COO (Contador de Ordem de Opera��es)
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public int getNumeroCupom() throws CommException, EcfException{
		return getCOO();
	}
	/**
	 * M�todo abstrato busca no ECF o n�mero do �ltimo cupom
	 * @return COO (Contador de Ordem de Opera��es)
	 *@throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract int getCOO() throws CommException, EcfException;
	/**
	 * M�todo abstrato busca no ECF o n�mero contador de reinicios de opera��o
	 * @return CRO(Contador dee Rein�cios do Opera��es)
	 * @throws CommException Exce�ao de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract int getCRO() throws CommException, EcfException;
	/**
	 * M�todo abstrato busca no ECF o n�mero contador de redu��es z
	 * @return CRZ(Contador de Redu��es Z)
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract int getCRZ() throws CommException, EcfException;
	/**
	 * M�todo abstrato busca o n�mero do ECF
	 * @return N�mero do ECF
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract int getNumeroEcf() throws CommException, EcfException;
	/**
	 * M�todo abstrato busca o n�mero da loja do ECF
	 * @return N�mero da loja do ECF
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract int getNumeroLoja()  throws CommException, EcfException;
	/**
	 * M�todo abstrato busca no ECF a data da movimenta��o 
	 * @return Data da movimenta��o
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract Date getDataMovimento() throws CommException, EcfException;
	/**
	 * M�todo abstrato busca no ECF a data e hora atual do ECF
	 * @return Data e Hora no ECF
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract Date getDataHora() throws CommException, EcfException;
	/**
	 * M�todo abstrato busca o grande total atual do ECF
	 * @return Grande Total atual
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract BigDecimal getGrandeTotal() throws CommException, EcfException;
	/**
	 * M�todo abstrato busca a venda bruta atual do ECF
	 * @return Venda Bruta atual
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract BigDecimal getVendaBruta() throws CommException, EcfException;
	/**
	 * M�todo abstrato busca no ECF o n�mero do �ltimo item informado
	 * @return N�mero do �ltimo item
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract int getNumeroUltimoItem() throws CommException, EcfException;
	/**
	 * M�todo abstrato verifica estado da gaveta
	 * @return boolean
	 * <li> True - Caso a gaveta esteja aberta
	 * <li> False - Caso a gaveta esteja fechada
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract boolean isGavetaAberta() throws CommException, EcfException;
	/**
	 * M�todo abstrato que abre a gaveta
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract void abrirGaveta() throws CommException, EcfException;
	/**
	 * M�todo abstrato verifica o estado da ECF
	 * @return Estado da ECF
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */	
	public abstract EcfEstado getEstado() throws CommException, EcfException;
	/**
	 * M�todo abstrato retorna o Grande Total da �ltima Redu��o Z
	 * @return Grande Total da �ltima Redu��o Z
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */	
	public abstract BigDecimal getGrandeTotalReducaoZ()throws CommException, EcfException;
	/**
	 * M�todo abstrato retorna o valor total de servi�os cancelados do dia
	 * @return Valor Total de Servi�os Cancelados
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */	
	public abstract BigDecimal getCancelamentoISS()throws CommException, EcfException;
	/**
	 * M�todo abstrato retorna o valor total de produtos cancelados do dia
	 * @return Valor Total de Produtos Cancelados
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */	
	public abstract BigDecimal getCancelamentoICMS()throws CommException, EcfException;
	/**
	 * M�todo abstrato retorna o valor total de descontos em servi�os do dia
	 * @return Valor Total de Descontos em Servi�os
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */	
	public abstract BigDecimal getDescontoISS()throws CommException, EcfException;
	/**
	 * M�todo abstrato retorna o valor total de descontos em produtos do dia
	 * @return Valor Total de Descontos em Produtos
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */	
	public abstract BigDecimal getDescontoICMS()throws CommException, EcfException;
	/**
	 * M�todo abstrato retorna o valor total de acrescimos em produtos do dia
	 * @return Valor Total de Acrescimos em Produtos
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */	
	public abstract BigDecimal getAcrescimoICMS()throws CommException, EcfException;
	/**
	 * M�todo abstrato retorna o valor total de acr�scimos em servi�os do dia
	 * @return Valor Total de Acr�scimos em Servi�os
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract BigDecimal getAcrescimoISS()throws CommException, EcfException;
	/**
	 * M�todo abstrato retorna o valor da Venda Bruta registrada na �ltima redu��o Z
	 * @return Valor Total da Venda Bruta da �ltima Redu��o Z
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract BigDecimal getVendaBrutaReducaoZ() throws CommException, EcfException;
	/**
	 * M�todo abstrato retorna o valor de Descontos registrados na �ltima redu��o Z
	 * @return Valor Total de Descontos na �ltima Redu��o Z
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract BigDecimal getDescontoReducaoZ() throws CommException, EcfException;
	/**
	 * M�todo abstrato retorna o valor de Acr�scimos registrados na �ltima redu��o Z
	 * @return Valor Total de Acr�scimos na �ltima Redu��o Z
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract BigDecimal getAcrescimoReducaoZ() throws CommException, EcfException;
	/**
	 * M�todo abstrato retorna o valor de Sangria registrado na �ltima redu��o Z
	 * @return Valor Total de Sangria na �ltima Redu��o Z
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract BigDecimal getSangriaReducaoZ() throws CommException, EcfException;
	/**
	 * M�todo abstrato retorna o valor de Suprimento registrado na �ltima redu��o Z
	 * @return Valor Total de Suprimento na �ltima Redu��o Z
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract BigDecimal getSuprimentoReducaoZ() throws CommException, EcfException;
	/**
	 * M�todo abstrato busca no ECF valor total de Substritui��es Tribut�rias registradas na �ltima redu��o z
	 * @return Valor total de Substritui��es Tribut�rias da �ltima Redu��o Z
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract BigDecimal getSubstituicao() throws CommException, EcfException;
	/**
	 * M�todo abstrato busca no ECF valor total de N�o Tributados registradas na �ltima redu��o z
	 * @return Valor total de N�o Tributados da �ltima Redu��o Z
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract BigDecimal getNaoTributado() throws CommException, EcfException;
	/**
	 * M�todo abstrato busca no ECF valor total de Isentos registradas na �ltima redu��o z
	 * @return Valor total de Isentos da �ltima Redu��o Z
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract BigDecimal getIsento() throws CommException, EcfException;
	/**
	 * M�todo abstrato busca o COO (Contador de Ordem de Opera��o) inicial registrado na �ltima redu��o z
	 * @return COO Inicial da �ltima Redu��o Z
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract int getCOOInicial() throws CommException, EcfException;
	/**
	 * M�todo abstrato busca o COO (Contador de Ordem de Opera��o) final registrado na �ltima redu��o z
	 * @return COO Final da �ltima Redu��o Z
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract int getCOOFinal() throws CommException, EcfException;
	/**
	 * M�todo abstrato busca o COO (Contador de Ordem de Opera��o) da �ltima redu��o z
	 * @return COO da �ltima Redu��o Z
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract int getCOOReducaoZ() throws CommException, EcfException;
	/**
	 * M�todo abstrato busca o CRO (Contador de Reinicios de Opera��o) registrado na �ltima redu��o z
	 * @return CRO Registrado na �ltima Redu��o Z
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract int getCROReducaoZ() throws CommException, EcfException;
	/**
	 * M�todo abstrato busca no ECF o totalizador parcial de cancelamentos
	 * @return Totalizador parcial de cancelamentos
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract BigDecimal getCancelamento() throws CommException, EcfException;
	/**
	 * M�todo abstrato busca no ECF o totalizador parcial de descontos
	 * @return Totalizador parcial de descontos
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract BigDecimal getDesconto() throws CommException, EcfException;
	/**
	 * M�todo abstrato busca no ECF o totalizador parcial de acr�scimos
	 * @return Totalizador parcial de acr�scimos
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract BigDecimal getAcrescimo() throws CommException, EcfException;
	
	/**
	 * M�todo abstrato busca no ECF o Subtotal do cupom
	 * @return Subtotal do cupom
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract BigDecimal getSubtotalCupom() throws CommException, EcfException;
	
	/**
	 * M�todo abstrato busca no ECF o valor do �ltimo cupom fiscal
	 * @return Valor Do �ltimo Cupom Fiscal
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract BigDecimal getValorPagoUltimoCupom() throws CommException, EcfException;

	// Relat�rios --------------------------------------------------------------
	/**
	 * M�todo abstrato envia comando ao ECF para que seja emitida a Leitura X
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract void leituraX() throws CommException, EcfException;
	/**
	 * M�todo abstrato envia comando ao ECF para que seja emitida a Redu��o Z
	 * @param dataMovimentacao <i>Date</i>  
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract void reducaoZ(Date dataMovimentacao) throws CommException, EcfException;
	/**
	 * M�todo abstrato envia comando ao ECF para que este, imprima um relat�rio da mem�ria fiscal filtrado por datas
	 * @param dataInicial <i>Date</i>
	 * @param dataFinal <i>Date</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract void leituraMemoriaFiscalData(Date dataInicial, Date dataFinal) throws EcfException, CommException;
	/**
	 * M�todo abstrato envia comando ao ECF para que este, retorne via porta serial um relat�rio da mem�ria fiscal filtrado por datas
	 * @param dataInicial <i>Date</i>
	 * @param dataFinal <i>Date</i>
	 * @return String - Relat�rio da mem�ria fiscal para exibir em video
	 * @throws EcfException Exce��o do ECF
	 * @throws CommException Exce��o de comunica��o
	 */
	public abstract String leituraMemoriaFiscalDataSerial(Date dataInicial, Date dataFinal) throws EcfException, CommException;
	/**
	 * M�todo abstrato envia comando ao ECF para que este, imprima um relat�rio da mem�ria fiscal de maneira sint�tica filtrado por datas
	 * @param dataInicial <i>Date</i>
	 * @param dataFinal <i>Date</i>
	 * @throws EcfException Exce��o do ECF
	 * @throws CommException Exce��o de comunica��o
	 */
	public abstract void leituraMemoriaFiscalDataSimplificado(Date dataInicial, Date dataFinal) throws EcfException, CommException;
	/**
	 * M�todo abstrato envia comando ao ECF para que este, retorne via porta serial um relat�rio da mem�ria fiscal sint�tico filtrado por datas
	 * @param dataInicial <i>Date</i>
	 * @param dataFinal <i>Date</i>
	 * @return String - Relat�rio da mem�ria fiscal para exibir em video
	 * @throws EcfException Exce��o do ECF
	 * @throws CommException Exce��o de comunica��o
	 */
	public abstract String leituraMemoriaFiscalDataSimplificadoSerial(Date dataInicial, Date dataFinal) throws EcfException, CommException;
	/**
	 * M�todo abstrato envia comando ao ECF para que este, imprime um relat�rio da mem�ria fiscal filtrado por CRZ
	 * @param reducaoInicial <i>int</i> - CRZ inicial
	 * @param reducaoFinal <i>int</i> - CRZ final
	 * @throws EcfException Exce��o do ECF
	 * @throws CommException Exce��o de comunica��o
	 */
	public abstract void leituraMemoriaFiscalReducao(int reducaoInicial, int reducaoFinal) throws EcfException, CommException;
	/**
	 * M�todo abstrato envia comando ao ECF para que este, retorne via porta serial um relat�rio da mem�ria fiscal filtrado por CRZ
	 * @param reducaoInicial <i>int</i> - CRZ inicial
	 * @param reducaoFinal <i>int</i> - CRZ final
	 * @return String - Relat�rio da mem�ria fiscal para exibir em video
	 * @throws EcfException Exce��o do ECF
	 * @throws CommException Exce��o de comunica��o
	 */
	public abstract String leituraMemoriaFiscalReducaoSerial(int reducaoInicial, int reducaoFinal) throws EcfException, CommException;
	/**
	 * M�todo abstrato envia comando ao ECF para que este, imprime um relat�rio da mem�ria fiscal sint�tico filtrado por CRZ
	 * @param reducaoInicial <i>int</i> - CRZ inicial
	 * @param reducaoFinal <i>int</i> - CRZ final
	 * @throws EcfException Exce��o do ECF
	 * @throws CommException Exce��o de comunica��o
	 */
	public abstract void leituraMemoriaFiscalReducaoSimplificado(int reducaoInicial, int reducaoFinal) throws EcfException, CommException;
	/**
	 * M�todo abstrato envia comando ao ECF para que este, retorne via porta serial um relat�rio da mem�ria fiscal sint�tico filtrado por CRZ
	 * @param reducaoInicial <i>int</i> - CRZ inicial
	 * @param reducaoFinal <i>int</i> - CRZ final
	 * @return String - Relat�rio da mem�ria fiscal para exibir em video
	 * @throws EcfException Exce��o do ECF
	 * @throws CommException Exce��o de comunica��o
	 */
	public abstract String leituraMemoriaFiscalReducaoSimplificadoSerial(int reducaoInicial, int reducaoFinal) throws EcfException, CommException;
	/**
	 * M�todo abstrato abre um relat�rio gerencial no ECF tipo "default"
	 * @throws EcfException Exce��o do ECF
	 * @throws CommException Exce��o de comunica��o
	 */
	public abstract void abrirRelatorioGerencial() throws CommException, EcfException;
	/**
	 * M�todo abstrato imprime uma linha no relat�rio gerencial aberto
	 * @param texto <i>String</i> texto a ser impresso
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract void linhaRelatorioGerencial(String texto) throws CommException, EcfException;
	/**
	 * M�todo abstrato fecha o relat�rio gerencial
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract void fecharRelatorio() throws CommException, EcfException;
	
	// Cupom Fiscal ------------------------------------------------------------
	/**
	 * M�todo abstrato abre um cupom fiscal
	 * @param cpfCnpj <i>String</i>
	 * @param nome <i>String</i>
	 * @param endereco <i>String</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract void abrirCupom(String cpfCnpj, String nome, String endereco) throws CommException, EcfException;
	
	/**
	 * M�todo abstrato vende um item sem desconto ou acr�scimo no ECF 
	 * @param codigo <i>String</i>
	 * @param descricao <i>String</i>
	 * @param codAliquota <i>String</i>
	 * @param quantidade <i>BigDecimal</i>
	 * @param valorUnitario <i>BigDecimal</i>
	 * @param unidade <i>Strng</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract void venderItem(String codigo, String descricao, String codAliquota, 
			BigDecimal quantidade, BigDecimal valorUnitario, String unidade) 
			throws CommException, EcfException;
	
	/**
	 * M�todo abstrato vende um item com desconto de valor no ECF
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
	public abstract void venderItemDesconto(String codigo, String descricao, String codAliquota, 
			BigDecimal quantidade, BigDecimal valorUnitario, String unidade, BigDecimal desconto)
			throws CommException, EcfException;
	
	/**
	 * M�todo abstrato vende um item com desconto percentual no ECF
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
	public abstract void venderItemDescontoPerc(String codigo, String descricao, String codAliquota, 
			BigDecimal quantidade, BigDecimal valorUnitario, String unidade, BigDecimal desconto) 
			throws CommException, EcfException;
	
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
	public abstract void venderItemAcrescimo(String codigo, String descricao, String codAliquota, 
			BigDecimal quantidade, BigDecimal valorUnitario, String unidade, BigDecimal acrescimo) 
			throws CommException, EcfException;
	
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
	public abstract void venderItemAcrescimoPerc(String codigo, String descricao, String codAliquota, 
			BigDecimal quantidade, BigDecimal valorUnitario, String unidade, BigDecimal acrescimo) 
			throws CommException, EcfException;
	
	/**
	 * M�todo abstrato imprime o TOTAL vendido no cupom at� o momento
	 * @param desconto <i>BigDecimal</i>
	 * @param acrescimo <i>BigDecimal</i>
	 * @param descAcresPerc <i>boolean</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract void subtotalizarCupom(BigDecimal desconto, BigDecimal acrescimo, boolean descAcresPerc)throws CommException, EcfException;
	/**
	 * M�todo abstrato imprime o valor pago com determinada forma de pagamento
	 * @param codFormaPagamento <i>String</i>
	 * @param valor <i>BigDecimal</i>
	 * @param observacao <i>String</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract void efetuarPagamento(String codFormaPagamento, BigDecimal valor, String observacao)throws CommException, EcfException;
	/**
	 * M�todo abstrato finaliza o cupom aberto
	 * @param observacao <i>String</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract void fecharCupom(String observacao)throws CommException, EcfException;
	/**
	 * M�todo abstrato canleca cupom aberto ou o �ltimo cupom finalizado
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract void cancelarCupom() throws CommException, EcfException;
	/**
	 * M�todo abstrato cancela determinado item do cupom aberto
	 * @param item <i>int</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract void cancelarItem(int item)throws CommException, EcfException;
	/**
	 * M�todo abstrato cancela o �ltimo item registrado, caso o cupom esteja aberto
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract void cancelarUltimoItem() throws CommException, EcfException;
	
	// Cupom N�o Fiscal --------------------------------------------------------
	/**
	 * M�todo abstrato emite um cupom n�o fiscal  <i>(completo)</i>
	 * @param codTotalizadorNaoFiscal <i>String</i>
	 * @param valor <i>BigDecimal</i>
	 * @param codFormaPagamento <i>String</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract void emitirNaoFiscal(String codTotalizadorNaoFiscal, BigDecimal valor, String codFormaPagamento) throws CommException, EcfException;
	/**
	 * M�todo abstrato abre um cupom n�o fiscal
	 * @param cpfCnpj <i>String</i>
	 * @param nomeConsumidor <i>String</i>
	 * @param enderecoConsumidor <i>Strinf</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract void abrirNaoFiscal(String cpfCnpj, String nomeConsumidor, String enderecoConsumidor) throws CommException, EcfException;//77
	/**
	 * M�todo abstrato registra um item no cupom n�o fiscal aberto
	 * @param codTotalizadorNaoFiscal <i>String</i>
	 * @param valor <i>BigDecimal</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract void registrarItemNaoFiscal(String codTotalizadorNaoFiscal, BigDecimal valor) throws CommException, EcfException;
	/**
	 * M�todo abstrato subtotaliza Cupom N�o fiscal
	 * @param desconto <i>BigDecimal</i>
	 * @param acrescimo <i>BigDecimal</i>
	 * @param descAcresPerc <i>boolean</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract void subtotalizarNaoFiscal(BigDecimal desconto, BigDecimal acrescimo, boolean descAcresPerc) throws CommException, EcfException;
	/**
	 * M�todo abstrato imprime o valor pago com determinada forma de pagamento
	 * @param codFormaPagamento <i>String</i>
	 * @param valor <i>BigDecimal</i>
	 * @param observacao <i>String</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract void efetuarPagamentoNaoFiscal(String codFormaPagamento, BigDecimal valor, String observacao) throws CommException, EcfException;
	/**
	 * M�todo abstrato finaliza cupom n�o fiscal aberto
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract void fecharNaoFiscal() throws CommException, EcfException;
	/**
	 * M�todo abstrato canleca cupom n�o fiscal aberto ou o �ltimo cupom n�o fiscal finalizado
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o de ECF
	 */
	public abstract void cancelarNaoFiscal() throws CommException, EcfException;
	
	//Comandos de autentica��o--------------------------------------------------
	/**
	 * M�todo abstrato imprime uma marca de autentica��o em documentos
	 * @param linhasAvanco <i>int</i>
	 * @param linhaAdicional <i>String</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract void autenticarDocumento(int linhasAvanco, String linhaAdicional) throws CommException, EcfException;
	
	// Cupom N�o Fiscal --------------------------------------------------------
	/**
	 * M�todo abstrato emite comprovantes <i>(completo)</i>
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
	public abstract void emitirComprovante(String codFormaPagamento, BigDecimal valor, int COO, String cpfConsumidor, String nomeConsumidor, String enderecoConsumidor, String texto)throws CommException, EcfException;
	/**
	 * M�todo abstrato para abrir comprovantes
	 * @param codFormaPagamento <i>String</i>
	 * @param valor <i>BigDecimal</i>
	 * @param COO <i>int</i>
	 * @param cpfConsumidor <i>String</i>
	 * @param nomeConsumidor <i>String</i>
	 * @param enderecoConsumidor <i>String</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o de ECF
	 */
	public abstract void abrirComprovante(String codFormaPagamento, BigDecimal valor, int COO, String cpfConsumidor, String nomeConsumidor, String enderecoConsumidor)throws CommException, EcfException;//66
	/**
	 * M�todo abstrato imprime linhas em um comprovante aberto
	 * @param texto <i>String</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract void imprimirLinhaComprovante(String texto) throws CommException, EcfException;
	/**
	 * M�todo abstrato finaliza comprovante aberto
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o de ECF
	 */
	public abstract void fecharComprovante() throws CommException, EcfException;
	/**
	 * M�todo abstrato estorna comprovante emitido
	 * @param cpf <i>String</i>
	 * @param nome <i>String</i>
	 * @param endereco <i>String</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract void estornarComprovante(String cpf, String nome, String endereco) throws CommException, EcfException;
	/**
	 * M�todo abstrato imprime segunda via do �ltimo comprovante emitido
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract void imprimirSegundaVia() throws CommException, EcfException;
	/**
	 * M�todo abstrato reimprime �ltimo comprovante emitido
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract void reimprimirCupomNaoFiscal() throws CommException, EcfException;
	
	/**
	 * M�todo inicializa comunica��o com o ECF, carrega aliquotas, formas de pagamento, totalizados n�o fiscal e layouts de cheque
	 * @param porta <i>String</i>
	 * @param velocidade <i>Integer</i>
	 * @param bitsDados <i>Integer</i>
	 * @param paridade <i>Integer</i>
	 * @param bitsParada <i>Integer</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	protected void inicializacao(String porta, Integer velocidade, Integer bitsDados,
			Integer paridade, Integer bitsParada) throws CommException, EcfException{
		comm.abrirPorta(porta, velocidade, bitsDados, paridade, bitsParada);
		commEnabled = true;
		carregarAliquotas();
		carregarFormasPagamento();
		carregarTotalizadoresNaoFiscais();
		carregarLayoutCheque();
	}
	
	/**
	 * M�todo finaliza comunica��o com o ECF
	 */
	protected void finalizacao(){
		comm.fechar();
	}

	
	//ALIQUOTAS
	/**
	 * M�todo busca todas as aliquotas cadastradas no ECF e retorna em uma lista
	 * @return List - Lista de aliquotas 
	 */
	public List<Aliquota> getAliquotasList() {
		return aliquotas;
	}
	
	/**
	 * M�todo carrega as aliquotas do ECF 
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public void carregarAliquotas() throws CommException, EcfException{
		aliquotas.clear();
		aliquotas = getAliquotas();
	}
	
	/**
	 * M�todo abstrato carrega as aliquotas em uma lista
	 * @return Lista de aliquotas
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exe��o do ECF
	 */
	protected abstract List<Aliquota> getAliquotas() throws CommException, EcfException;
	
	/**
	 * M�todo programa Aliquotas no ECF
	 * @param codigo <i>String</i>
	 * @param aliquota <i>BigDecimal</i>
	 * @param incidencia <i>String</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public void programarAliquota(String codigo, BigDecimal aliquota, String incidencia) 
			throws CommException, EcfException{
		boolean cadastrar = true;
		for(Aliquota aliq : aliquotas){
			if(aliq.getCodigo().equals(codigo)){
				cadastrar = false;
				if((!BigDecimalUtils.isEqualTo(aliq.getAliquota(), aliquota)) ||
						(!aliq.getIncidencia().equalsIgnoreCase(incidencia))){
					throw new EcfException(EcfException.ERRO_ALIQUOTA_COM_DIVERGENCIA);
				}
			}
		}
		if(cadastrar){
			programarAliquotaAbstract(codigo, aliquota, incidencia);
			carregarAliquotas();
		}
	}
	/**
	 * M�todo abstrato programa aliquota no ECF
	 * @param codigo <i>String</i>
	 * @param aliquota <i>BigDecimal</i>
	 * @param incidencia <i>String</i>
	 * @throws CommException Exce��o deo comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	protected abstract void programarAliquotaAbstract(String codigo, BigDecimal aliquota, String incidencia) 
			throws CommException, EcfException;
	
	/**
	 * M�todo busca uma aliquota a partir de um c�digo
	 * @param codigo <i>String</i>
	 * @return Aliquota
	 */
	public Aliquota getAliquota(String codigo){
		for(Aliquota a : aliquotas){
			if(a.getCodigo().equals(codigo)){
				return a;
			}
		}
		return null;
	}
	
	//FORMA DE PAGAMENTO
	/**
	 * M�todo busca todas as formas de pagamento cadastradas no ECF e retorna em uma lista
	 * @return Lista de Formas de Pagamento
	 */
	public List<FormaPagamento> getFormasPagamentoList(){
		return formasPagamento;
	}
	
	/**
	 * M�todo carrega as formas de pagamento do ECF 
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public void carregarFormasPagamento() throws CommException, EcfException{
		formasPagamento.clear();
		formasPagamento = getFormasPagamento();
	}
	/**
	 * M�todo abstrato carrega formas de pagemento para uma lista
	 * @return Lista de formas de pagamento
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	protected abstract List<FormaPagamento> getFormasPagamento() throws CommException, EcfException;
	
	/**
	 * M�todo programa Formas de Pagamento
	 * @param codigo <i>String</i>
	 * @param descricao <i>String</i>
	 * @param permiteVincular <i>boolean</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public void programarFormaPagamento(String codigo, String descricao, boolean permiteVincular) 
			throws CommException, EcfException{
		
		FormaPagamento formaPagto = getFormaPagamento(codigo);
		if(formaPagto == null){
			
			
			programarFormaPagamentoAbstract(codigo, descricao, permiteVincular);
			formasPagamento.add(new FormaPagamento(codigo, descricao, permiteVincular, BigDecimalUtils.newMoeda()));
		}
	}
	/**
	 * M�todo abstrato programa Formas de Pagamento
	 * @param codigo <i>String</i>
	 * @param descricao <i>String</i>
	 * @param permiteVincular <i>boolean</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	protected abstract void programarFormaPagamentoAbstract(String codigo, String descricao, 
			boolean permiteVincular) throws EcfException, CommException;
	
	/**
	 * M�todo busca uma forma de pagamento a partir de um c�digo
	 * @param codigo <i>String</i>
	 * @return Forma de Pagamento
	 */
	public FormaPagamento getFormaPagamento(String codigo){
		for(FormaPagamento f : formasPagamento){
			if(f.getCodigo().equals(codigo)){
				return f;
			}
		}	
		return null;
	}
	
	/**
	 * M�todo busca uma forma de pagamento a partir de uma descri��o
	 * @param descricao <i>String</i>
	 * @return Forma de Pagamento
	 */
	public FormaPagamento getFormaPagamentoPorDescricao(String descricao){
		for(FormaPagamento f : formasPagamento){
			if(f.getDescricao().trim().equalsIgnoreCase(descricao)){
				return f;
			}
		}
		return null;
	}
	
	
	//TOTALIZADORES
	/**
	 * M�todo busca todas os totalizadores cadastradas no ECF e retorna em uma lista
	 * @return
	 */
	public List<TotalizadorNaoFiscal> getTotalizadorsNaoFiscaisList(){
		return totalizadoresNaoFiscais;
	}
	
	/**
	 * M�todo carrega os totalizadores n�o fiscais do ECF 
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public void carregarTotalizadoresNaoFiscais() throws CommException, EcfException{
		totalizadoresNaoFiscais.clear();
		totalizadoresNaoFiscais = getTotalizadoresNaoFiscais();
	}
	/**
	 * M�todo abstrato carrega os totalizadore n�o fiscal em uma lista
	 * @return lista de totalizadores n�o fiscal
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	protected abstract List<TotalizadorNaoFiscal> getTotalizadoresNaoFiscais() 
			throws CommException, EcfException;
			
	/**
	 * M�todo programa totalizado n�o fiscal no ECF
	 * @param codigo <i>String</i>
	 * @param descricao <i>String</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public void programarTotalizadorNaoFiscal(String codigo, String descricao) 
			throws CommException, EcfException{
		programarTotalizadorNaoFiscalAbstract(codigo, descricao);
		carregarTotalizadoresNaoFiscais();		
	}
	/**
	 * M�todo abstrato programa totalizado n�o fiscal no ECF
	 * @param codigo <i>String</i>
	 * @param descricao <i>String</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	protected abstract void programarTotalizadorNaoFiscalAbstract(String codigo, String descricao) 
			throws CommException, EcfException;
	
	/**
	 * M�todo busca no ECF um totalizador n�o fiscal a partir de um c�digo
	 * @param codigo <i>String</i>
	 * @return Totalizador N�o Fiscal
	 */
	public TotalizadorNaoFiscal getTotalizadorNaoFiscal(String codigo){
		for(TotalizadorNaoFiscal t : totalizadoresNaoFiscais){
			if(t.getCodigo().equals(codigo)){
				return t;
			}
		}
		return null;
	}
	
	/**
	 * M�todo busca no ECF um totalizador n�o fiscal a partir de uma descri��o
	 * @param descricao <i>String</i>
	 * @return Totalizador N�o Fiscal
	 */
	public TotalizadorNaoFiscal getTotalizadorNaoFiscalPorDescricao(String descricao){
		for(TotalizadorNaoFiscal t : totalizadoresNaoFiscais){
			if(t.getDescricao().equals(descricao)){
				return t;
			}
		}
		return null;
	}
	
	//--------------------------------------------------------------------------------------------------------

	/**
	 * M�todo abstrato programa no ECF o nome da moeda no singular
	 * @param nome <i>String</i>
	 * @throws CommException Ece��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract void programarMoedaSingular(String nome) throws CommException, EcfException;
	/**
	 * M�todo abstrato programa no ECF o nome da moeda no plural
	 * @param nome <i>String</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract void programarMoedaPlural(String nome) throws CommException, EcfException;
	/**
	 * M�todo abstrato imprime cheques no ECF de acordo com os modelos dos banco 
	 * @param banco <i>String</i>
	 * @param valor <i>BigDecimal</i>
	 * @param favorecido <i>String</i>
	 * @param cidade <i>String</i>
	 * @param data <i>Date</i>
	 * @param observacao <i>Straing</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract void imprimirCheque(String banco, BigDecimal valor, String favorecido, 
			String cidade, Date data, String observacao) throws CommException, EcfException;
	/**
	 * M�todo abstrato cancela a impress�o no cheque. Esse m�todo s� ser� executado se o ECF estiver aguardando o Cheque
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract void cancelarImpressaoCheque() throws CommException, EcfException;
	/**
	 * M�todo abstrato verifica se o ECF est� aguardando a coloca��o do cheque para impress�o
	 * @return boolean
	 * <li> True - Caso esteja aguardando
	 * <li> False - Caso n�o esteja aguardando
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract boolean isAguardandoCheque() throws CommException, EcfException;
	/**
	 * M�todo abstrato verifica se o ECF est� imprimindo cheque
	 * @return boolean
	 * <li> True - Caso estaja imprimindo cheque
	 * <li> False - Caso n�o esteja imprimindo cheque
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract boolean isImprimindoCheque() throws CommException, EcfException;
	
	/**
	 * M�todo abstrato faz com que o ECF pule linhas em sua impress�o
	 * @param qtdeLinhas <i>int</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract void pularLinhas(int qtdeLinhas) throws CommException, EcfException;
	/**
	 * M�todo abstrato faz com que o ECF corte o papel de impress�o
	 * @param corteParcial <i>boolean</i>
	 * @throws CommException Exce��o de comunica��o
	 * @throws EcfException Exce��o do ECF
	 */
	public abstract void cortarPapel(boolean corteParcial) throws CommException, EcfException;
	/**
	 * M�todo abstrato Carrega os layouts de cheque em uma lista
	 * @return lista de Layouts de cheque
	 * @throws EcfException
	 */
	protected abstract List<LayoutCheque> carregarLayoutsChequeDefault() throws EcfException;
	
	//--------------------------------------------------------------------------------------------------------
	
	/**
	 * M�todo verifica se a porta esta habilitada
	 * @return boolean 
	 * <li> True - Se a porta esteja habilitada
	 * <li> False - Caso a porta esteja desabilitada
	 * @throws EcfException Exce��o do ECF
	 */
	public boolean isCommEnabled() {
		return commEnabled;
	}
	
	
	//------------------------------------------------------------------------
	/**
	 * M�todo Carrega os layouts de cheque
	 * @throws EcfException Exce��o do ECF
	 */
	public void carregarLayoutCheque()throws EcfException{
		layoutsCheque.clear();
		layoutsCheque = getLayoutsCheque();
	}
	
	/**
	 * M�todo carrega os layouts de cheques a partir de um XML
	 * @param xml <i>String</i>
	 * @throws EcfException Exce��o do ECF
	 */
	public void carregarLayoutCheque(String xml)throws EcfException{
		layoutsCheque.clear();
		layoutsCheque = getLayoutsCheque(xml);
	}
	
	/**
	 * M�todo carrega os layouts de cheques em uma lista
	 * @param xml <i>String</i>
	 * @return Lista de Layouts de Cheques
	 * @throws EcfException Exce��o do ECF
	 */
	protected List<LayoutCheque> getLayoutsCheque() throws EcfException{
		List<LayoutCheque> lista = null;
		List<LayoutCheque> result = new ArrayList<LayoutCheque>();
		try {
			String arq = FileUtils.carregar("EcfLayoutsCheque.xml");
			if (arq != null){
				XStream xStream = new XStream();
				lista = (List) xStream.fromXML(arq);
				for(LayoutCheque l: lista){
					if((l.getFabricante().equalsIgnoreCase(fabricante())) &&  
							(l.getModelo().equalsIgnoreCase(modelo()))){
						result.add(l);
					}
				}
				if (lista.isEmpty()){
					lista = null;
				}
			}
		} catch (Exception e) {
			lista = null;
			e.printStackTrace();
		}
		
		if(lista == null){
			result = carregarLayoutsChequeDefault();
			if(lista != null){
				XStream xStream = new XStream();
				String arq = xStream.toXML(result);
				try {
					FileUtils.salvar("EcfLayoutsCheque.xml", arq, false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	/**
	 * M�todo carrega os layouts de cheques em uma lista a partir de um XML
	 * @param xml <i>String</i>
	 * @return Lista de Layouts de Cheques
	 * @throws EcfException Exce��o do ECF
	 */	
	protected List<LayoutCheque> getLayoutsCheque(String xml) throws EcfException{
		List<LayoutCheque> lista = null;
		try {
			if (xml != null){
				XStream xStream = new XStream();
				lista = (List) xStream.fromXML(xml);
				for(LayoutCheque l: lista){
					if((!l.getFabricante().equalsIgnoreCase(fabricante())) || 
							(!l.getModelo().equalsIgnoreCase(modelo()))){
						lista.remove(l);
					}
				}
				if (lista.isEmpty()){
					lista = null;
				}
			}
		} catch (Exception e) {
			lista = null;
			e.printStackTrace();
		}
		
		if(lista == null){
			lista = getLayoutsCheque();
		}
		return lista;
	}

	/**
	 * M�todo carrega os layouts de cheques a partir do codigo do banco
	 * @param banco <i>String</i>
	 * @return LayoutCheque
	 * @throws EcfException Exce��o do ECF
	 */
	public LayoutCheque getLayoutCheque(String banco) throws EcfException{
		if(layoutsCheque != null){
			for(LayoutCheque l : layoutsCheque){
				if(l.getBanco().equals(banco)){
					return l;
				}
			}	
		}
		throw new EcfException(EcfException.ERRO_MODELO_CHEQUE_INEXISTENTE);
	}
	
	

	/**
	 * M�todo insere um novo layout de cheque
	 * @param layouts <i>List</i>
	 * @throws EcfException
	 */
	public void setLayouts(List<LayoutCheque> layouts) {
		this.layoutsCheque = layouts;
	}
	
	/**
	 * M�todo busca a Lista de Layouts de Cheques
	 * @return lista de Layouts de cheque
	 */
	public List<LayoutCheque> getLayouts() {
		return layoutsCheque;
	}
}
