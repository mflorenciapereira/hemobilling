package ar.uba.fi.hemobilling.mvc;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * La respuesta posible del controlador a la vista en forma de JSON.
 * El unico factor comun entre los distintos tipos de response
 * por ahora es el alias.
 */
@XStreamAlias("response")
public class Response{

}