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
package org.ecf4j.utils.strings;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

import org.ecf4j.utils.bytes.ByteUtils;

/**
 * Classe de tratamento para String
 * @author Pablo Fassina e Agner Munhoz
 * @version 1.0.0
 */
public class StringUtils {
	
	public static final int ALINHAMENTO_ESQUERDA = 0;
	public static final int ALINHAMENTO_CENTRALIZADO = 1;
	public static final int ALINHAMENTO_DIREITA = 2;
	
	private static final String charSet = "!?.,:;/\\(){}[]<>*-+ &%$QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm0123456789\n\r";
	
	public static String limiteStr(String str, int len){
		if(str.length() > len){
			str = str.substring(0, len);
		}
		return str;
	}
	// ean = formartStr('1', 12, '0', StringUtils.ALINHAMENTO_DIREITA)
	public static String formatStr(String str, int len, char caracter, int alinhamento){
		
		if(str.length() > len){
			str = str.substring(0, len);
		}else{
			switch (alinhamento) {
			case ALINHAMENTO_DIREITA:
				for(int i = str.length(); i < len; i++){
					str = caracter + str;
				}
				break;
			case ALINHAMENTO_CENTRALIZADO:
				boolean flag = false; 
				for(int i = str.length(); i < len; i++){
					if (flag){
						str = caracter + str;
					}else{
						str = str + caracter;
					}
					flag = !flag;
				}	
				break;
			default:
				for(int i = str.length(); i < len; i++){
					str = str + caracter;
				}
				break;
			}
			
			
		}
		return str;
	}

	public static String formatStrLimite(String str, int len, char caracter, int alinhamento){
		
		if(str.length() > len){
			str = str.substring(0, len-1);
		}else{
			switch (alinhamento) {
			case ALINHAMENTO_DIREITA:
				for(int i = str.length(); i < len; i++){
					str = caracter + str;
				}
				break;
			case ALINHAMENTO_CENTRALIZADO:
				len = len / 2;
				for(int i = str.length(); i < len; i++){
					str = caracter + str;
				}	
				break;
			case ALINHAMENTO_ESQUERDA:
				for(int i = str.length(); i < len; i++){
					str = str + caracter;
				}
			break;
			}
			
		}
		return str;
	}

	public static String formatStr(String str, int len, int alinhamento){

		return formatStr(str, len, ' ', alinhamento);
	}
	
	public static String strToEcf(String str){
		if ((str!=null) && (str.length() > 0)){
			str = str.replaceAll("�", "a");
			str = str.replaceAll("�", "e");
			str = str.replaceAll("�", "i");
			str = str.replaceAll("�", "o");
			str = str.replaceAll("�", "u");
			str = str.replaceAll("�", "a");
			str = str.replaceAll("�", "e");
			str = str.replaceAll("�", "i");
			str = str.replaceAll("�", "o");
			str = str.replaceAll("�", "u");
			str = str.replaceAll("�", "a");
			str = str.replaceAll("�", "o");
			str = str.replaceAll("�", "a");
			str = str.replaceAll("�", "e");
			str = str.replaceAll("�", "i");
			str = str.replaceAll("�", "o");
			str = str.replaceAll("�", "u");
			str = str.replaceAll("�", "c");

			str = str.replaceAll("�", "a");
			str = str.replaceAll("�", "e");
			str = str.replaceAll("�", "i");
			str = str.replaceAll("�", "o");
			str = str.replaceAll("�", "u");
			
			str = str.replaceAll("�", "A");
			str = str.replaceAll("�", "E");
			str = str.replaceAll("�", "I");
			str = str.replaceAll("�", "O");
			str = str.replaceAll("�", "U");
			str = str.replaceAll("�", "A");
			str = str.replaceAll("�", "E");
			str = str.replaceAll("�", "I");
			str = str.replaceAll("�", "O");
			str = str.replaceAll("�", "U");
			str = str.replaceAll("�", "A");
			str = str.replaceAll("�", "O");
			str = str.replaceAll("�", "A");
			str = str.replaceAll("�", "E");
			str = str.replaceAll("�", "I");
			str = str.replaceAll("�", "O");
			str = str.replaceAll("�", "U");
			str = str.replaceAll("�", "C");

			str = str.replaceAll("�", "A");
			str = str.replaceAll("�", "E");
			str = str.replaceAll("�", "I");
			str = str.replaceAll("�", "O");
			str = str.replaceAll("�", "U");
			
			str = str.replaceAll("�", "a.");
			str = str.replaceAll("�", "o.");
			str = str.replaceAll("�", "o.");
			char c = ' ';
			for(int i = 0; i < str.length(); i++){
				c = str.charAt(i);
				if (charSet.indexOf(c) < 0) {
					str = str.replace(c, ' ');
				}
			}
		}
		return str;
	}
	
	public static String strToEcfLimiteLen(String str, int len){
		return limiteStr(strToEcf(str), len);
	}
	
	public static String strToEcf(int alinhamento, String str, int len){
		return formatStr(strToEcf(str), len, alinhamento);
	}

	public static String strToEcf(String str, int len){
		return formatStr(strToEcf(str), len, ALINHAMENTO_ESQUERDA);
	}

	public static String strToEcf(int alinhamento, String str, int len, char caracter){
		return formatStr(strToEcf(str), len, caracter, alinhamento);
	}

	public static String strToEcf(String str, int len, char caracter){
		return formatStr(strToEcf(str), len, caracter, ALINHAMENTO_ESQUERDA);
	}
	
	public static String strToEcf(int alinhamento, String str, int pagina, int len){
		pagina *= len;
		if (str.length() > pagina){
			if (str.length() > pagina+len){
				str = str.substring(pagina, pagina+len);
			}else{
				str = str.substring(pagina);
			}
		}else{
			str = "";
		}
		//return strToEcf(str);
		return formatStr(strToEcf(str), len, ' ', alinhamento);
	}

	/**
	 * Retorna String formatada para o Ecf dividida em p�ginas.
	 * @param str - String de entrada
	 * @param pagina - N�mero da p�gina apartir de 0
	 * @param len - Tamanho em caracters da p�gina
	 * @return Retorna uma String com len ou menos caracters ou 
	 * String em branco caso n�o exista a p�gina 
	 */
	public static String strToEcf(String str, int pagina, int len){
		pagina *= len;
		if (str.length() > pagina){
			if (str.length() > pagina+len){
				str = str.substring(pagina, pagina+len);
			}else{
				str = str.substring(pagina);
			}
		}else{
			str = "";
		}
		return strToEcf(str);
		//return formatStr(strToEcf(str), len, alinhamento);
	}
	
	public static String convertString(String str){
		// Create the encoder and decoder for ISO-8859-1
	    Charset charset = Charset.forName("ISO-8859-1");
	    CharsetDecoder decoder = charset.newDecoder();
	    CharsetEncoder encoder = charset.newEncoder();
	    
	    try {
	        // Convert a string to ISO-LATIN-1 bytes in a ByteBuffer
	        // The new ByteBuffer is ready to be read.
	        ByteBuffer bbuf = encoder.encode(CharBuffer.wrap(str));
	    
	        // Convert ISO-LATIN-1 bytes in a ByteBuffer to a character ByteBuffer and then to a string.
	        // The new ByteBuffer is ready to be read.
	        CharBuffer cbuf = decoder.decode(bbuf);
	        str = cbuf.toString();
	    } catch (CharacterCodingException e) {
	    }
	    /*
	    // Create a direct ByteBuffer.
	    // This buffer will be used to send and recieve data from channels.
	    ByteBuffer bbuf = ByteBuffer.allocateDirect(1024);
	    
	    // Create a non-direct character ByteBuffer
	    CharBuffer cbuf = CharBuffer.allocate(1024);
	    
	    // Convert characters in cbuf to bbuf
	    encoder.encode(cbuf, bbuf, false);
	    
	    // flip bbuf before reading from it
	    bbuf.flip();
	    
	    // Convert bytes in bbuf to cbuf
	    decoder.decode(bbuf, cbuf, false);
	    
	    // flip cbuf before reading from it
	    cbuf.flip();
		*/
		return str;

	}
	
	public static String bytesToString(int... bytesInt){		
		String s = "";
		byte[] bytes = new byte[bytesInt.length];
		try{
			int i = 0;
			for(int vlr : bytesInt){
				bytes[i] = (byte) vlr;
				i++;
			}
			s = new String(bytes);
		}catch (Exception e) {
			s = "";
		}
		
		return s;
	}
	
	public static String bcdToString(byte[] b){
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < b.length; i++) {
			
			StringBuilder s = new StringBuilder(ByteUtils.byteToString(b[i]));
			result.append(s);
		}
		return result.toString();

	}
	
	public static String ascToString(byte[] b){
		String result = new String(b);
		return result.trim();

	}
	
}
