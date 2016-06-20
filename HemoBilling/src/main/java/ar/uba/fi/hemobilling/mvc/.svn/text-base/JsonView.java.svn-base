package ar.uba.fi.hemobilling.mvc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspWriter;

import org.springframework.web.servlet.View;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;

/**
 * Serializa en formato JSON los objetos del modelo utilizando XStream.
 */
public class JsonView implements View {

    public static final JsonView instance = new JsonView();
    public static final JsonView encodedInstance = new JsonView(true);
    
    public static final String JSON_ROOT = "root";
    private XStream xstream = new XStream(new CustomizedJsonHierarchicalStreamDriver());
    
    private JsonView() {
        this(false);
    }
    
    private JsonView(boolean encoded)
    {
        xstream.setMode(XStream.NO_REFERENCES);
        xstream.autodetectAnnotations(true);
        xstream.registerConverter(new DateConverter("dd/MM/yyyy", new String[]{"dd/MM/yy"}));
        xstream.registerConverter(new JsonMapConverter(xstream.getMapper()), 0);
        
        if (encoded)
            xstream.registerConverter(new HTMLConverter(), 0);
    }
    
    public String getContentType() {
        return "text/html; charset=UTF-8";
    }

    @SuppressWarnings("rawtypes")
    public void render( Map model, HttpServletRequest arg1, HttpServletResponse response) throws Exception {
        String json = "";
        json = xstream.toXML(model.get(JsonView.JSON_ROOT));
        doRender(response, json);
    }

	private void doRender(HttpServletResponse response, String json)
			throws IOException {
		try
        {
            PrintWriter writer = response.getWriter();
            writer.write(json);
        }
        catch(java.lang.IllegalStateException e)
        {
            PrintWriter writer = new PrintWriter(response.getOutputStream());
            writer.write(json);
        }
	}
    
    public void render(Object jsonRoot, HttpServletResponse response) throws IOException {
        String json = "";
        json = xstream.toXML(jsonRoot);
        doRender(response, json);
    }
    
    public void render(Object jsonRoot, JspWriter writer) throws IOException {
        String json = "";
        json = xstream.toXML(jsonRoot);
        writer.write(json);
    }
}
