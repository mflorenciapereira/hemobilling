package ar.uba.fi.hemobilling.mvc;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.collections.AbstractCollectionConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

/**
 * Convierte un mapa java a un objeto Json acorde a su representacion, es mas practico manipular al mapa 
 * como objeto que como un array de array por eso es necesaria esta adaptacion.
 * 
 * @author falberca
 *
 */

public class JsonMapConverter extends AbstractCollectionConverter {

    public JsonMapConverter(Mapper mapper) {
        super(mapper);
    }

    @Override
    @SuppressWarnings("rawtypes")
	public boolean canConvert(Class type) {
        return type.equals(HashMap.class)
                || type.equals(Hashtable.class)
                || type.getName().equals("java.util.LinkedHashMap");
    }

    @Override
    @SuppressWarnings("rawtypes")
    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        Map map = (Map) source;
        for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext();) {
            Map.Entry entry = (Map.Entry) iterator.next();
            
            writer.startNode(entry.getKey().toString());
            
            context.convertAnother(entry.getValue());

            writer.endNode();
        }
    }

    @Override
    @SuppressWarnings("rawtypes")
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        Map map = (Map) createCollection(context.getRequiredType());
        populateMap(reader, context, map);
        return map;
    }

    @SuppressWarnings("unchecked")
    protected void populateMap(HierarchicalStreamReader reader, UnmarshallingContext context, @SuppressWarnings("rawtypes") Map map) {
        while (reader.hasMoreChildren()) {
            reader.moveDown();
            
            Object key = reader.getNodeName();

            reader.moveDown();
            Object value = readItem(reader, context, map);
            reader.moveUp();

            map.put(key, value);

            reader.moveUp();
        }
    }
}
