package ar.uba.fi.hemobilling.mvc;

import ar.uba.fi.hemobilling.util.HTMLEntities;

import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;

/**
 * Implementa la logica de un custom converter de XStream serializando los strings
 * con un encoding de html definido por el estandar de la W3C. 
 * 
 * @author falberca
 *
 */
public class HTMLConverter extends AbstractSingleValueConverter {

    @SuppressWarnings("rawtypes")
    @Override
    public boolean canConvert(Class type) {
        return type.equals(String.class);
    }

    @Override
    public Object fromString(String str) {
        return HTMLEntities.unhtmlentities(str);
    }
    
    public String toString(Object obj) {
        return HTMLEntities.htmlentities(obj.toString(),true);
    }
}
