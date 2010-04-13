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
package org.ecf4j.utils.datetimes;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.ecf4j.ecf.exceptions.EcfException;

/**
 * Classe para tratamento de DateTime
 * @author Pablo Fassina e Agner Munhoz
 * @version 1.0.0
 */
public class DateTimeUtils {
	
	public static Date stringToDate(String str, String mask) throws EcfException{
		Date dtTmp = null;
		try {
			dtTmp = new SimpleDateFormat(mask).parse(str);
		} catch (ParseException e) {
			throw new EcfException();
		}
		return dtTmp;
	}
	
	public static String dateToString(Date date, String mask)	{
		DateFormat dateFormat = new SimpleDateFormat(mask);
	    return dateFormat.format(date);
	}
	
	public static int dayOfDate(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		int day = cal.get(Calendar.DAY_OF_MONTH);
		
		return day;
	}
	
	public static int monthOfDate(Date date){
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		int month = cal.get(Calendar.MONTH) + 1;
		return month;
	}
	
	public static int yearOfDate(Date date){
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		int year = cal.get(Calendar.YEAR);
		
		return year;
	}
	
	public static String mesPorExtenso(Date date){
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		int month = monthOfDate(date);
		
		String mes = "";
		
		if(month == 1)
			mes = "Janeiro";
		if(month == 2)
			mes = "Fevereiro";
		if(month == 3)
			mes = "Mar�o";
		if(month == 4)
			mes = "Abril";
		if(month == 5)
			mes = "Maio";
		if(month == 6)
			mes = "Junho";
		if(month == 7)
			mes = "Julho";
		if(month == 8)
			mes = "Agosto";
		if(month == 9)
			mes = "Setembro";
		if(month == 10)
			mes = "Outubro";
		if(month == 11)
			mes = "Novembro";
		if(month == 12)
			mes = "Dezembro";
		
		return mes;
	}


}
