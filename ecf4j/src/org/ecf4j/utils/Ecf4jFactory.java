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
package org.ecf4j.utils;

import org.ecf4j.balanca.BalancaAbstract;
import org.ecf4j.balanca.padrao.BalancaPadrao;
import org.ecf4j.balanca.toledo.Toledo8217;
import org.ecf4j.ecf.EcfAbstract;
import org.ecf4j.ecf.bematech.EcfBemaMp20;
import org.ecf4j.ecf.bematech.EcfBemaMp2100;
import org.ecf4j.ecf.bematech.EcfBemaMp25;
import org.ecf4j.ecf.bematech.EcfBemaMp40;
import org.ecf4j.ecnf.EcnfAbstract;
import org.ecf4j.ecnf.bematech.EcnfBemaMp20;
import org.ecf4j.ecnf.bematech.EcnfBemaMp2100;
import org.ecf4j.ecnf.bematech.EcnfBemaMp4000;
import org.ecf4j.ecnf.bematech.EcnfBemaMp100;
import org.ecf4j.ecnf.epson.EcnfEpsonTMU220PD;
import org.ecf4j.scanner.ScannerAbstract;
import org.ecf4j.scanner.metrologic.MetrologicPadrao;
import org.ecf4j.scanner.ncr.ScannerNcrPadrao;
import org.ecf4j.scanner.padrao.ScannerPadrao;

/**
 * Classe identifica o equipamento utilizado a partir do c�digo informado
 * @author Pablo Fassina e Agner Munhoz
 */
public class Ecf4jFactory {
	
	//ECF------------------------------------------------
	public static final String ECF_BEMA_MP20 = "BM001";
	public static final String ECF_BEMA_MP25 = "BM002";
	public static final String ECF_BEMA_MP40 = "BM003";
	public static final String ECF_BEMA_MP2100 = "BM004";
	
	public static Object[][] getEcfLista(){
		return new Object[][]{
			new Object[]{ ECF_BEMA_MP20, EcfBemaMp20.class, "Bematech MP-20"},
			new Object[]{ ECF_BEMA_MP25, EcfBemaMp25.class, "Bematech MP-25"},
			new Object[]{ ECF_BEMA_MP40, EcfBemaMp40.class, "Bematech MP-40"},
			new Object[]{ ECF_BEMA_MP2100, EcfBemaMp2100.class, "Bematech MP-2100"}

		};
	}

	public static EcfAbstract createEcf(String codigo){
		Object[][] ecfClasses = getEcfLista();
		for(Object[] ecfDef: ecfClasses){
			if (((String)ecfDef[0]).equalsIgnoreCase(codigo)){
				Class ecfClass = (Class)ecfDef[1];
				try {
					return (EcfAbstract) ecfClass.newInstance();
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}
		}
		return null;
	}
	
	
	//ECNF-------------------------------------------------
	public static final String ECNF_BEMA_MP20 = "BM001";
	public static final String ECNF_BEMAMP_2001 = "BM002";
	public static final String ECNF_BEMAMP_100 = "BM003";
	public static final String ECNF_BEMAMP_4000 = "BM004";
	public static final String ECNF_EPSON_TMU220PD = "EPS001";
	
	public static Object[][] getEcnfLista(){
		return new Object[][]{
				new Object[]{ECNF_BEMA_MP20, EcnfBemaMp20.class, "Bematech MP-20"},
				new Object[]{ECNF_BEMAMP_2001, EcnfBemaMp2100.class, "Bematech MP-2100"},
				new Object[]{ECNF_BEMAMP_4000, EcnfBemaMp4000.class, "Bematech MP-4000"},
				new Object[]{ECNF_BEMAMP_100, EcnfBemaMp100.class, "Bematech MP-100"},
				new Object[]{ECNF_EPSON_TMU220PD, EcnfEpsonTMU220PD.class, "Epson TM-U220PD"}
		};
	}
	
	public static EcnfAbstract createEcnf(String codigo){
		
		Object[][] ecnfClasses = getEcnfLista();
		for(Object[] ecnfDef: ecnfClasses){
			if (((String)ecnfDef[0]).equalsIgnoreCase(codigo)){
				Class ecnfClass = (Class)ecnfDef[1];
				try {
					return (EcnfAbstract) ecnfClass.newInstance();
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}
		}
		
		return null;
	}
	
	//SCANNER
	public static final String SCANNER_PADRAO = "PDR01";
	public static final String SCANNER_NCRPADRAO = "NCR01";
	public static final String SCANNER_METROLOGIC_PADRAO = "MTL01";
	
	public static Object[][] getScannerLista(){
		return new Object[][]{
				new Object[]{SCANNER_PADRAO, ScannerPadrao.class, "Scanner Padr�o"},
				new Object[]{SCANNER_NCRPADRAO, ScannerNcrPadrao.class, "Scanner NCR Padr�o"},
				new Object[]{SCANNER_METROLOGIC_PADRAO, MetrologicPadrao.class, "Scanner Metrologic Padr�o"}
		};
	}
	
	public static ScannerAbstract createScanner(String codigo){
		if ((codigo == null)||(codigo.equals(""))){
			return new ScannerPadrao();
		}else{
			Object[][] scannerClasses = getScannerLista();
			for(Object[] scannerDef: scannerClasses){
				if (((String)scannerDef[0]).equalsIgnoreCase(codigo)){
					Class scannerClass = (Class)scannerDef[1];
					try {
						return (ScannerAbstract) scannerClass.newInstance();
					} catch (Exception e) {
						e.printStackTrace();
						return null;
					}
				}
			}	
		}
		
		return null;
	}
	
	//BALAN�A
	public static final String BALANCA_PADRAO = "PDR01";
	public static final String BALANCA_TLD8217 = "TLD01";
	
	
	public static Object[][] getBalancaLista(){
		return new Object[][]{
				new Object[]{BALANCA_PADRAO, BalancaPadrao.class, "Balan�a Padr�o"},
				new Object[]{BALANCA_TLD8217, Toledo8217.class, "Toledo 8217"}
		};
	}
	
	public static BalancaAbstract createBalanca(String codigo){
		if((codigo == null)||(codigo.equals(""))){
			return new BalancaPadrao();
		}
		Object[][] balancaClasses = getBalancaLista();
		for(Object[] balancaDef: balancaClasses){
			if (((String)balancaDef[0]).equalsIgnoreCase(codigo)){
				Class balancaClass = (Class)balancaDef[1];
				try {
					return (BalancaAbstract) balancaClass.newInstance();
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}
		}
		
		return null;
	}
	
	
}
