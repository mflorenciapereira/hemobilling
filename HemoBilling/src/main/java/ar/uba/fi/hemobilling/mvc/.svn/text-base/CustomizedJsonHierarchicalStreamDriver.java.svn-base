package ar.uba.fi.hemobilling.mvc;

import java.io.Writer;

import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;

public class CustomizedJsonHierarchicalStreamDriver  extends JsonHierarchicalStreamDriver {

    @Override
    public HierarchicalStreamWriter createWriter(Writer out) {
        return new CustomizedJsonWriter(out);
    }
}
