package ar.uba.fi.hemobilling.util;

public class PrecioToStringConverter {
 
	private static final String[] UNIDADES = { "", "UN ", "DOS ", "TRES ",
			"CUATRO ", "CINCO ", "SEIS ", "SIETE ", "OCHO ", "NUEVE ", "DIEZ ",
			"ONCE ", "DOCE ", "TRECE ", "CATORCE ", "QUINCE ", "DIECISEIS",
			"DIECISIETE", "DIECIOCHO", "DIECINUEVE", "VEINTE" };
 
	private static final String[] DECENAS = { "VENTI", "TREINTA ", "CUARENTA ",
			"CINCUENTA ", "SESENTA ", "SETENTA ", "OCHENTA ", "NOVENTA ",
			"CIEN " };
 
	private static final String[] CENTENAS = { "CIENTO ", "DOSCIENTOS ",
			"TRESCIENTOS ", "CUATROCIENTOS ", "QUINIENTOS ", "SEISCIENTOS ",
			"SETECIENTOS ", "OCHOCIENTOS ", "NOVECIENTOS " };
 

	public static String convertNumberToLetter(String number) {
		return convertNumberToLetter(Double.parseDouble(number));
	}
 
	public static String convertNumberToLetter(double number)
			throws NumberFormatException {
		String converted = new String();
 
		// Validamos que sea un numero legal
		double doubleNumber = Math.rint(number*100)/100;
		if (doubleNumber > 999999999)
			throw new NumberFormatException(
					"El numero es mayor de 999'999.999, "
							+ "no es posible convertirlo");
 
		String splitNumber[] = String.valueOf(doubleNumber).replace('.', '#')
				.split("#");
 

		int millon = Integer.parseInt(String.valueOf(getDigitAt(splitNumber[0],
				8))
				+ String.valueOf(getDigitAt(splitNumber[0], 7))
				+ String.valueOf(getDigitAt(splitNumber[0], 6)));
		if (millon == 1)
			converted = "UN MILLON ";
		if (millon > 1)
			converted = convertNumber(String.valueOf(millon)) + "MILLONES ";
 

		int miles = Integer.parseInt(String.valueOf(getDigitAt(splitNumber[0],
				5))
				+ String.valueOf(getDigitAt(splitNumber[0], 4))
				+ String.valueOf(getDigitAt(splitNumber[0], 3)));
		if (miles == 1)
			converted += "MIL ";
		if (miles > 1)
			converted += convertNumber(String.valueOf(miles)) + "MIL ";
 
		int cientos = Integer.parseInt(String.valueOf(getDigitAt(
				splitNumber[0], 2))
				+ String.valueOf(getDigitAt(splitNumber[0], 1))
				+ String.valueOf(getDigitAt(splitNumber[0], 0)));
		if (cientos == 1)
			converted += "UN ";
 
		if (millon + miles + cientos == 0)
			converted += "CERO ";
		if (cientos > 1)
			converted += convertNumber(String.valueOf(cientos));
 
		converted += "PESOS";
		
		return converted;
 
//		int centavos = Integer.parseInt(String.valueOf(getDigitAt(
//				splitNumber[1], 2))
//				+ String.valueOf(getDigitAt(splitNumber[1], 1))
//				+ String.valueOf(getDigitAt(splitNumber[1], 0)));
//		if (centavos == 1)
//			converted += " CON UN CENTAVO";
//		if (centavos > 1)
//			converted += " CON " + convertNumber(String.valueOf(centavos))
//					+ "CENTAVOS";
// 
//		return converted;
	}
 

	private static String convertNumber(String number) {
		if (number.length() > 3)
			throw new NumberFormatException(
					"La longitud maxima debe ser 3 digitos");
 
		String output = new String();
		if (getDigitAt(number, 2) != 0)
			output = CENTENAS[getDigitAt(number, 2) - 1];
 
		int k = Integer.parseInt(String.valueOf(getDigitAt(number, 1))
				+ String.valueOf(getDigitAt(number, 0)));
 
		if (k <= 20)
			output += UNIDADES[k];
		else {
			if (k > 30 && getDigitAt(number, 0) != 0)
				output += DECENAS[getDigitAt(number, 1) - 2] + "Y "
						+ UNIDADES[getDigitAt(number, 0)];
			else
				output += DECENAS[getDigitAt(number, 1) - 2]
						+ UNIDADES[getDigitAt(number, 0)];
		}
 
		// Caso especial con el 100
		if (getDigitAt(number, 2) == 1 && k == 0)
			output = "CIEN";
 
		return output;
	}
 
	private static int getDigitAt(String origin, int position) {
		if (origin.length() > position && position >= 0)
			return origin.charAt(origin.length() - position - 1) - 48;
		return 0;
	}
 
}