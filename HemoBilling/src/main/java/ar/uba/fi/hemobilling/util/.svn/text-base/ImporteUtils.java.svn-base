package ar.uba.fi.hemobilling.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class ImporteUtils 
{
	private static Pattern patternAR = Pattern.compile("^((\\d+)|((\\d+)((\\.)\\d{3})+))((,)\\d{1,2})?$");
	private static Pattern patternUS = Pattern.compile("^((\\d+)|((\\d+)((,)\\d{3})+))((\\.)\\d{1,2})?$");
	
	private static String separadorDecimalAR;
	private static String separadorDecimalUS;
	private static String groupingSeparatorAR;
	private static String groupingSeparatorUS;
	
	private static NumberFormat nf = NumberFormat.getInstance(new Locale("es","AR"));
	
	public ImporteUtils()
	{
		DecimalFormatSymbols df_AR = DecimalFormatSymbols.getInstance(new Locale("es","AR"));
		DecimalFormatSymbols df_US = DecimalFormatSymbols.getInstance(new Locale("en","US"));
		
		separadorDecimalAR = String.valueOf(df_AR.getDecimalSeparator());
		separadorDecimalUS = String.valueOf(df_US.getDecimalSeparator());
		
		groupingSeparatorAR = String.valueOf(df_AR.getGroupingSeparator());
		groupingSeparatorUS = String.valueOf(df_US.getGroupingSeparator());
	}
	
	public String formatearImporte (BigDecimal importe){
		
		if (importe == null) return null;
		
		return nf.format(importe.setScale(2,RoundingMode.HALF_UP).doubleValue());
		
	}
	
	public BigDecimal getImporteFromString (String importe){
		
		if (patternAR.matcher(importe).matches()){
			String importeNuevo = StringUtils.replace(importe,groupingSeparatorAR, StringUtils.EMPTY);
			importeNuevo = StringUtils.replace(importeNuevo,separadorDecimalAR, separadorDecimalUS);
			return new BigDecimal(importeNuevo);
		}
		
		if (patternUS.matcher(importe).matches()){
			String importeNuevo = StringUtils.replace(importe,groupingSeparatorUS, StringUtils.EMPTY);
			return new BigDecimal(importeNuevo);
		}
		
		throw new NumberFormatException();
		
	}
	
}
