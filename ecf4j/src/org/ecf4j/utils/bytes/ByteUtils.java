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
package org.ecf4j.utils.bytes;

/**
 * Classe para tratamento de Byte
 * @author Pablo Fassina e Agner Munhoz
 * @version 1.0.0
 */
public class ByteUtils {
	
	public static String byteArrayToEan(byte... bytes ){
		byte[] bs = null;
		for(byte b: bytes){
			if ((b != 10)&&(b != 13))
				bs = encondeByteArray(bs,newByteArray(b));
		}
		String result = new String(bs);
		result = result.trim();
		return result;
	}
	
	public static byte[] encondeByteArray(byte[]... bytes ){
		byte[] result = null;
		int len = 0;
		for(byte[] bs: bytes){
			if (bs != null){
				len += bs.length;
			}
		}
		result = new byte[len];
		int i = 0;
		for(byte[] bs: bytes){
			if (bs != null){
				for(byte b: bs){
					result[i++] = b;
				}	
			}
		}
		return result;
	}
	                   
	public static byte[] newByteArray(int... bytes){
		byte[] result = new byte[bytes.length];
		int ind = 0;
		for(int i: bytes){
			result[ind++] = (byte) i;
		}
		return result;
	}
	
	public static byte[] subByteArray(byte[] b, int ini, int len){
		byte[] result = new byte[len];
		for(int i = ini; i < ini+len; i++){
			result[i-ini] = b[i];
		}
		return result;
	}

	public static String byteToString(byte b){
		String hexadecimal = "00";
		if (b < 0){
			hexadecimal = Integer.toHexString(b).substring(6);
		}else{
			hexadecimal = Integer.toHexString(b);
		}
		if (hexadecimal.length() < 2) 
			hexadecimal = "0" + hexadecimal;
		
		return hexadecimal;
	}
	
	public static boolean getBit(byte b, int bit){
		byte bAnd = 0;
		switch (bit) {
		case 0:
			bAnd = (byte)1;
			break;
		case 1:
			bAnd = (byte)2;
			break;
		case 2:
			bAnd = (byte)4;
			break;
		case 3:
			bAnd = (byte)8;
			break;
		case 4:
			bAnd = (byte)16;
			break;
		case 5:
			bAnd = (byte)32;
			break;
		case 6:
			bAnd = (byte)64;
			break;
		default:
			bAnd = (byte)128;
			break;
		}	
		return ((b & bAnd) == bAnd);
	}
	
}
