
//valida que un campo posea solo caracteres numericos y que no sea vacio
function validateNumeric(value){
	var validator = /^[0-9]+$/;
	if(validator.test(value)){
		return true;
	}else{
		return false;
	}
}

//valida que el campo no este vacio y no tenga solo espacios en blanco
function validateNotEmptyString(campo) {
	for ( i = 0; i < campo.length; i++ ) {
        if ( campo.charAt(i) != " " ) {
                return true;
        }
	}
	return false;
        
}


//Valida la longitud de un campo
function validateLongitud(valor, minimo, maximo){
	return (minimo <= valor.length) && (valor.length <= maximo);
}

//Valida que la longitud de un campo sea mayor o igual al su valor mínimo
function validateLongitudMayorA(valor, minimo){
	return (minimo <= valor.length);
}

//valida el formato valido de monton dddd (,dd opcional) d = digito 
function validateFormatoImporte(str) { 
    str = str.replace(/^\s+|\s+$/g,"");
    
    var validator = /^(\d+)((\.|,)\d{1,2})?$/;
	if(validator.test(str)){
		return true;
	}else{
		return false;
	}
}

//valida el formato valido de un mail
function validateEmail(valor) {
	var validator = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	var str = new String(valor);
	str = str.toUpperCase();
	return validator.test(str);
}



function hasWhiteSpace(s) {
  return /\s/g.test(s);
}

//Valida el formato de la fecha ingresada
//validateFormat
function validarFormato(fecha){
	var patron = /^\d{2}\/\d{2}\/\d{4}$/;
	
	if(!patron.test(fecha)){
		return false;
	}
	
	var dia = parseInt(fecha.substring(0,2),10);
	var mes = parseInt(fecha.substring(3, 5),10);
  var anio = parseInt(fecha.substring(6),10);
  
	var numDias = 0; 
	switch(mes){
		case 1: case 3: case 5: case 7: case 8: case 10: case 12:
	    	numDias=31;
	    	break;
	    case 4: case 6: case 9: case 11:
	    	numDias=30;
	    	break;
	    case 2:
	    	if ( ( anio % 100 != 0) && ((anio % 4 == 0) || (anio % 400 == 0))) {
	    		numDias=29
	    	} else {
	    		numDias=28
	    	};
	    	break;
	    default:
	    	return false;
	}
	 
	if (dia>numDias || dia==0){
		return false;
	}
	
	return true;
}

function convert2Date(string) {
	var date = new Date();
	var dia =string.substring(0, 2);
	var mes = string.substring(3, 5);
	var anio = string.substring(6); 
	// es importante que primero se haga el set del date, para no producir un bug.
	date.setDate(dia);
	date.setMonth(mes - 1); //en javascript los meses van de 0 a 11
	date.setYear(anio);
	return date;
}

/* Input de tipo date */
function setHourToZero(d) {
	var d1 = new Date(d);
	d1.setHours(0);
	d1.setMinutes(0);
	d1.setSeconds(0);
	d1.setMilliseconds(0);
	return d1;
}

/*
* compara dos fechas. Retorna un numero menor a cero
* si la primer fecha es menor a la segunda. Un numero
* mayor a cero en caso contrario y cero si ambas fechas
* son iguales
*/
function compareDates(d1, d2) {
	var milli_d1 = setHourToZero(d1).getTime();
	var milli_d2 = setHourToZero(d2).getTime();
	return milli_d1 - milli_d2;
}
/* Inputs tipo date */
function validateBetween(fecha, fechaDesde, fechaHasta) {
	return compareDates(fechaDesde, fecha) <= 0 &&
		compareDates(fecha, fechaHasta) <= 0;
}


//Suma dias a unDate y retorna un nuevo objeto de tipo Date con la nueva fecha
function addDaysToDate(dias, unDate){
  var dateTemp= new Date(unDate);
  dateTemp.setDate(dateTemp.getDate()+dias);
  return dateTemp;
}

//Suma meses a la fecha pasada como parametro y retorna un nuevo objeto de tipo Date con la nueva fecha
function addMonthsToDate(meses, unDate){
	var dateTemp= new Date(unDate);
	dateTemp.setMonth(dateTemp.getMonth()+meses);
	return dateTemp;
}

/*Obtiene el dia habil anterior al dia pasado como parametro
*/
function getDiaHabilAnterior(fecha) {
	rta = addDaysToDate(-1, fecha);
	switch(rta.getUTCDay()){
	case 0: //Domingo
		rta = addDaysToDate(-2, rta);
		break;
	case 6: //Sabado
		rta = addDaysToDate(-1, rta);
		break;
	}
	
	return rta;
}


function convert2String(date, incluirHora, fomartoAmPm){
	var fechaArray = convert2Array(date); 
	var fechaString = fechaArray[0]+"/"+fechaArray[1]+"/"+fechaArray[2];
	
	if (incluirHora){
		var hora = fechaArray[3];
	 	var minutos = fechaArray[4];
	 	var segundos = fechaArray[5];
	 	
	 	var horaString = "";
	 	
	 	if (fomartoAmPm){
	 		var ap = "am";
	 		if (hora   > 11) { ap = "pm";        }
	 		if (hora   > 12) { hora = hora - 12; hora = (hora < 10) ? '0' + hora : hora;}
	 		if (hora   == 0) { hora = 12;        }
	
	 		horaString = hora+":"+minutos+":"+segundos+" "+ap;
	 	}
	 	else{
	 		horaString = hora+":"+minutos+":"+segundos;
	 	}
	 	fechaString += " "+horaString;
	}
	return fechaString;
}

function convert2Array(date) {
	var d  = date.getDate();
	var day = (d < 10) ? '0' + d : d;
	var m = date.getMonth() + 1;
	var month = (m < 10) ? '0' + m : m;
	var yy = date.getYear();
	var year = (yy < 1000) ? yy + 1900 : yy;
	var hours = date.getHours();
	var min = date.getMinutes();
	var minutes = (min < 10) ? '0' + min : min;
	var sec = date.getSeconds();
	var seconds = (sec < 10) ? '0' + sec : sec;

	var arrayFecha = new Array();
	arrayFecha[0] = day;
	arrayFecha[1] = month;
	arrayFecha[2] = year;
	arrayFecha[3] = hours;
	arrayFecha[4] = minutes;
	arrayFecha[5] = seconds;
	 
	return arrayFecha;
}

//valida que la fecha desde sea menor o igual a la fecha hasta
function validarDesdeMenorIgualHasta(fechaDesde, fechaHasta) {
	return compareDates(fechaDesde, fechaHasta) <= 0
}

//valida que la fecha sea mayor o igual a la fecha actual
function validarFechaMayorIgualHoy(fecha){
	return compareDates(new Date(), fecha) <= 0;
}

/*
 * Valida que la fecha este dentro del rango de 
 * dos meses para atras y el ultimo dia habil 
 */
function validaRangoCorrecto(fecha) {
	var hoy = new Date();
	var fechaDesde = addMonthsToDate(-2, hoy);
	fechaDesde.setDate(1);
	var fechaHasta = getDiaHabilAnterior(hoy);
	return validateBetween(fecha, fechaDesde, fechaHasta);
}

/*
 * Valida que la fecha es posterior a dos meses para atras 
 */
function validaRangoHaciaAtras(fecha) {
	var hoy = new Date();
	var fechaDesde = addMonthsToDate(-2, hoy);
	fechaDesde.setDate(1);
	return compareDates(fechaDesde, fecha) <= 0;
}

function validarEsFeriado(fecha, feriados){
	var fechaAux = fecha.split("/");
	var dia = fechaAux[0].charAt(0) == 0 ? fechaAux[0].substring(1, fechaAux[0].length) : fechaAux[0];
	var mes = fechaAux[1].charAt(0) == 0 ? fechaAux[1].substring(1, fechaAux[1].length) : fechaAux[1];
	var anio = fechaAux[2];
		
	for (var i = 0; i < feriados.length; i++) {
		if (feriados[i] == dia + "/" + mes + "/" + anio) {
			return true;
		}
	}
	
	return false;
}

/**
 * Valida que la fecha pasada como parametro sea un dia habil.
 * 
 * @param fecha en formato dd/mm/aaaa
 * @param feriados lista con los feriados.
 * @returns {Boolean}
 */
function validarEsDiaHabil(fecha,feriados){
	var esFeriado=validarEsFeriado(fecha,feriados);
	if(esFeriado){
		return false;
	}
	var datosFecha=fecha.split('/');
	var mes=datosFecha[1]-1;
	var objDate=new Date(datosFecha[2],mes,datosFecha[0]);
	if(objDate.getDay()==0 || objDate.getDay()==6){
		return false;
	}
	return true;
}

function isNumber(n) {
	  if((parseFloat(n) == parseInt(n)) && !isNaN(n)){
	      return true;
	  } else { 
	      return false;
	  } 
}
