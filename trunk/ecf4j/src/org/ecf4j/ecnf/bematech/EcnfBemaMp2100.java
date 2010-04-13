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
package org.ecf4j.ecnf.bematech;

import java.math.BigDecimal;

import org.ecf4j.utils.bigdecimals.BigDecimalUtils;
import org.ecf4j.utils.bytes.ByteUtils;
import org.ecf4j.utils.comm.exceptions.CommException;
import org.ecf4j.utils.integers.IntegerUtils;
import org.ecf4j.utils.strings.StringUtils;

/**
 * Classe ECNF espec�fica Bematech MP2100
 * @author Pablo Fassina e Agner Munhoz
 * @version 1.0.0
 * @extends EcnfBematechAbstract
 * @see EcnfBematechAbstract 
 */
public class EcnfBemaMp2100 extends EcnfBematechAbstract {

	
	//----------------------------------------------------------------
	//IMPRESS�O
	@Override
	protected void imprimirLinhaCondensadaAbstract(String linha) throws CommException {
		ativarModoCondensado();
		String ss[] = linha.split("\n");
		int len = 64;
		String p = "";
		int i = 0;
		
		for(String s: ss){
			p = "";
			i = 0;	
			p = StringUtils.strToEcf(alinhamento, s, i++, len);
			
			while(!p.trim().equals("")){
				executaComando(preparaComando((p+"\n").getBytes()));;
				p = StringUtils.strToEcf(alinhamento, s, i++, len);
			}
		}
		ativarModoNormal();	
	}
	
	@Override
	public void imprimirEan13(String ean) throws CommException {
		ean = StringUtils.formatStr(ean, 12, '0', StringUtils.ALINHAMENTO_ESQUERDA);
		executaComando(preparaComando(ByteUtils.newByteArray(29,107,67,12), ean.getBytes()));
		
	}

	@Override
	public void imprimirEan8(String ean) throws CommException {
		ean = StringUtils.formatStr(ean, 7, '0', StringUtils.ALINHAMENTO_ESQUERDA);
		executaComando(preparaComando(ByteUtils.newByteArray(29,107,3), 
				ByteUtils.encondeByteArray(ean.getBytes(), ByteUtils.newByteArray(0))));
	}
	
	
	//-------------------------------------------------------------------
	@Override
	public void cortarPapel() throws CommException {
		executaComando(preparaComando(ByteUtils.newByteArray(27,119)));	    
	}
	
	@Override
	public void cortarParcialmentePapel() throws CommException {
		executaComando(preparaComando(ByteUtils.newByteArray(27,109)));		
	}

	@Override
	public String modelo() {
		return "Mp-2100";
	}

	@Override
	public String getEstado() throws CommException {
		executaComando(preparaComando(ByteUtils.newByteArray(5)));
		try{
			Thread.sleep(200);
		}catch (Exception e) {
			// TODO: handle exception
		}
		byte[] b = getRetorno(1);
		return new String(b);
	}

	@Override
	public void imprimirLinha(String str1, String str2) throws CommException {
		int len = getNumColunas() - str1.length();
		str2 = StringUtils.formatStr(str2, len, StringUtils.ALINHAMENTO_DIREITA);
		imprimirLinha(str1+str2);
	}

	@Override
	public void imprimirSeparador() throws CommException {
		imprimirLinhaAbstract(StringUtils.formatStr("-", getNumColunas(), '-', StringUtils.ALINHAMENTO_ESQUERDA));
	}

	@Override
	protected void imprimirLinhaAbstract(String linha) throws CommException {
		String ss[] = linha.split("\n");
		int len = getNumColunas();
		String p = "";
		int i = 0;
		
		for(String s: ss){
			p = "";
			i = 0;	
			p = StringUtils.strToEcf(alinhamento, s, i++, len);
			
			while(!p.trim().equals("")){
				executaComando(preparaComando((p+"\n").getBytes()));;
				p = StringUtils.strToEcf(alinhamento, s, i++, len);
			}
		}
	}

	@Override
	protected void imprimirLinhaExpandidaAbstract(String linha)
			throws CommException {
		String ss[] = linha.split("\n");
		int len = 24;
		String p = "";
		int i = 0;
		
		for(String s: ss){
			p = "";
			i = 0;	
			p = StringUtils.strToEcf(alinhamento, s, i++, len);
			
			while(!p.trim().equals("")){
				ativarModoExpandido();
				executaComando(preparaComando((p+"\n").getBytes()));;
				p = StringUtils.strToEcf(alinhamento, s, i++, len);
			}
		}
		ativarModoNormal();
		
	}

	@Override
	public void imprimirItem(int numItem, String codProduto,
			String descProduto, String un, BigDecimal qtdeItem,
			BigDecimal valorItem) throws CommException {
		String item = IntegerUtils.intToStr(numItem, 3) + " ";
		String qtde = BigDecimalUtils.qtdeToString(qtdeItem) + " ";
		String valor = BigDecimalUtils.moedaToString(valorItem);
		if (!un.equals("")) {
			un = StringUtils.limiteStr(un, 2) + "x";
		}
		String produto = codProduto+" "+descProduto + " ";
		int len = 4 + qtde.length()+valor.length() + un.length() + produto.length();	
		
		
		if (len <= getNumColunas()){
			imprimirLinha(alinhaItem(item+produto, un+qtde+valor, getNumColunas() - len));	
		} else {
			imprimirLinhaCondensada(alinhaItem(item+produto, un+qtde+valor, 60 - len));	
		}
	}

	@Override
	public void imprimirLinhaExpandida(String str1, String str2)
			throws CommException {
		int len = 24 - str1.length();
		str2 = StringUtils.formatStr(str2, len, StringUtils.ALINHAMENTO_DIREITA);
		imprimirLinhaExpandida(str1+str2);
	}

	@Override
	public void abrirGaveta() throws CommException {
		executaComando(preparaComando(ByteUtils.newByteArray(27,118, 100)));
	}

	@Override
	protected void desativarNegritoAbstract() throws CommException {
		executaComando(preparaComando(ByteUtils.newByteArray(27,70)));
	}

	@Override
	protected void desativarItalicoAbstract() throws CommException {
		executaComando(preparaComando(ByteUtils.newByteArray(27,53)));
	}

	@Override
	public void ativarModoNormal() throws CommException {
		executaComando(preparaComando(ByteUtils.newByteArray(27,77)));
	}

	@Override
	public Integer getNumColunas() {
		return 48;
	}

}
