<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

 
 

<div class="center_content">
      <h2>Generar informe</h2>
         <div class="form" >
         <form action="doGenerarInforme.htm" method="post" class="niceform">
         
                <fieldset>
                    

                    <dl>
                        <dt><label for="desde">Período desde:</label></dt>
                        <dd><input type="text" name="" id="" size="10" /></dd>
                    </dl>
                    <dl>
                        <dt><label for="hasta">Período hasta:</label></dt>
                        <dd><input type="text" name="" id="" size="10" /></dd>
                    </dl>
                     
                    
                    
                    <dl>
                        <dt><label for="razonSocial">Agrupar por</label></dt>
                        <dd>
                        <div id="over3">
                            <select size="1" name="agrupar" id="obraSocial">
                                
                                <option value="">Obra social</option>
                                <option value="">Especialidad</option>
                                <option value="">Centro de costo</option>
                                
                            </select>
                            </div>
                        </dd>
                    </dl>
                    
                    
                   
                   
                    
                     <dl class="submit">
                     
                     <dt>
                    <input type="submit" name="submit" id="submit" value="Ver informe" />
                    <input type="reset" name="submit" id="submit" value="Borrar" />
                    </dt>
                    
                     </dl>
                     
                      
                     
        
                     
                    
                </fieldset>
                
         </form>
         
         </div>
         </div>
         
         
      
