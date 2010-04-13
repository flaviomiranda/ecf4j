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

/**
 * @author Pablo Fassina e Agner Munhoz
 *
 */
public class Comando {
	private String value = "";
	public Comando() {
		value = "";
	}
	public Comando(String arg){
		value = trataString(arg);
	}
	public Comando(byte[] arg) {
		value = new String(arg);
	}
	public Comando(int... charArray) {
		value = "";
		for (int i : charArray) {
			value = value+(char) i;				
		}
	}
	public void add(String arg) {
		value = value.concat(trataString(arg));
	}
	public void add(byte[] arg) {
		String str = new String(arg);
		value = value.concat(str);	
	}
	public void add(char arg) {
		value = value+arg;
	}
	public void add(int chr) {
		value = value+(char) chr;
	}
	public void add(int... charArray) {
		for (int i : charArray) {
			value = value+(char) i;				
		}
	}	
	public void clear() {
		value = "";
	}
	private String trataString(String arg){
		arg = arg.replaceAll("�", "A"); arg = arg.replaceAll("�", "a");
		arg = arg.replaceAll("�", "A"); arg = arg.replaceAll("�", "a");
		arg = arg.replaceAll("�", "A"); arg = arg.replaceAll("�", "a");
		arg = arg.replaceAll("�", "A"); arg = arg.replaceAll("�", "a");
		arg = arg.replaceAll("�", "A"); arg = arg.replaceAll("�", "a");
		arg = arg.replaceAll("�", "A"); arg = arg.replaceAll("�", "a");
		arg = arg.replaceAll("�", "A"); arg = arg.replaceAll("�", "a");
		arg = arg.replaceAll("�", "A"); arg = arg.replaceAll("�", "a");
		arg = arg.replaceAll("�", "A"); arg = arg.replaceAll("�", "a");
		arg = arg.replaceAll("�", "I"); arg = arg.replaceAll("�", "i");
		arg = arg.replaceAll("�", "I"); arg = arg.replaceAll("�", "i");
		arg = arg.replaceAll("�", "I"); arg = arg.replaceAll("�", "i");
		arg = arg.replaceAll("�", "I"); arg = arg.replaceAll("�", "i");
		arg = arg.replaceAll("�", "O"); arg = arg.replaceAll("�", "o");
		arg = arg.replaceAll("�", "O"); arg = arg.replaceAll("�", "o");
		arg = arg.replaceAll("�", "O"); arg = arg.replaceAll("�", "o");
		arg = arg.replaceAll("�", "O"); arg = arg.replaceAll("�", "o");
		arg = arg.replaceAll("�", "O"); arg = arg.replaceAll("�", "o");
		arg = arg.replaceAll("�", "U"); arg = arg.replaceAll("�", "u");
		arg = arg.replaceAll("�", "U"); arg = arg.replaceAll("�", "u");
		arg = arg.replaceAll("�", "U"); arg = arg.replaceAll("�", "u");
		arg = arg.replaceAll("�", "U"); arg = arg.replaceAll("�", "u");
		return arg;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return value;
	}
	
}
