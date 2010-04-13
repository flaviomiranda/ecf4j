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
 * Esse arquivo usa a biblioteca RXTX Copyright 1997-2007 by Trent Jarvi.
 * dispon�vel em: <http://users.frii.com/jarvi/rxtx/index.html> 21/09/2009.
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
package org.ecf4j.utils.files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Classe de manuseio de arquivos
 * @author Pablo Fassina e Agner Munhoz
 * @version 1.0.0
 */

public class FileUtils {
	/**
	* Salva o conte�do de uma vari�vel em um arquivo
	* @param arquivo
	* @param conteudo
	* @param adicionar se true adicionar no final do arquivo
	* @throws IOException
	*/
	public static void salvar(String arquivo, String conteudo, boolean adicionar)
	throws IOException {

		FileWriter fw = new FileWriter(arquivo, adicionar);
		BufferedWriter b = new BufferedWriter(fw);

		//fw.write(conteudo);
		//fw.close();
		
		String[] linhas = conteudo.split("\n");
		for(String l : linhas){
			b.write(l);
			b.newLine();
		}
		
		b.close();
	}
	
	public static void salvar(String path, String arquivo, String conteudo, boolean adicionar)
	throws Exception {
		String[] pastas = path.split("/");
		String root = "";
		String sep = "";
		for (int i = 0; i<pastas.length; i++) {
			root = root + sep + pastas[i].toString();
			sep = "/";
			File dir = new File(root);
			if (!dir.exists()) {
				if (!dir.mkdir()) {
					throw new Exception("N�o foi poss�vel criar a pasta "+path);
				}
			}
		}
		root = root + "/" + arquivo;
		salvar(root, conteudo, adicionar);
	}

	/**
	* Carrega o conte�do de um arquivo em uma String, se o aquivo
	* n�o existir, retornar� null.
	* @param arquivo
	* @return conte�do
	* @throws Exception
	*/
	public static String carregar(String arquivo)
	throws FileNotFoundException, IOException {

		File file = new File(arquivo);

		if (! file.exists()) {
			return null;
		}

		BufferedReader br = new BufferedReader(new FileReader(arquivo));
		StringBuffer bufSaida = new StringBuffer();

		String linha;
		while( (linha = br.readLine()) != null ){
			bufSaida.append(linha + "\n");
		}
		br.close();
		return bufSaida.toString();
	}

}
