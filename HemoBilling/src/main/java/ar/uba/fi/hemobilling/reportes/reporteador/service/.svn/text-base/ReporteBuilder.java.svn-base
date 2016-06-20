package ar.uba.fi.hemobilling.reportes.reporteador.service;

import java.util.Date;

import ar.com.fdvs.dj.core.DJConstants;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DJCalculation;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.BuilderException;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.DJBuilderException;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.GroupLayout;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;


public class ReporteBuilder {
	private static final String FORMATO_FECHA = "dd/MM/yyyy";
	
	private FastReportBuilder drb;
	

	private static Style getEstiloPorDefecto(){
		Style estiloPorDefecto = new Style();
		estiloPorDefecto.setHorizontalAlign(HorizontalAlign.LEFT);
		estiloPorDefecto.setVerticalAlign(VerticalAlign.MIDDLE);
		estiloPorDefecto.setStretchWithOverflow(false);
		
		return estiloPorDefecto;
	}
	
	public static Style getEstiloColumnaPorDefecto(){
		Style estiloColumna = getEstiloPorDefecto();
		estiloColumna.setFont(new Font(10, Font._FONT_ARIAL, true));
		estiloColumna.setBlankWhenNull(true);
		
		
		return estiloColumna;
	}
	
	public static Style getEstiloColumnaPorDefecto(ReportAlign alineacion){
		Style estiloColumna = getEstiloPorDefecto();
		estiloColumna.setBlankWhenNull(true);
		estiloColumna.setFont(new Font(12, Font._FONT_ARIAL,true));
		estiloColumna.setHorizontalAlign(getAlign(alineacion));
		
		return estiloColumna;
	}
	
	public static Style getEstiloColumnaMontoPorDefecto(){
		Style estiloMonto = getEstiloColumnaPorDefecto(ReportAlign.ALIGN_RIGHT);
		return estiloMonto;
	}
	
	public static Style getEstiloColumnaFechaPorDefecto(){
		return getEstiloColumnaFechaPorDefecto(ReportAlign.ALIGN_LEFT);
	}
	
	public static Style getEstiloColumnaFechaPorDefecto(ReportAlign alineacion){
		Style estiloColumna = getEstiloPorDefecto();
		estiloColumna.setPattern(FORMATO_FECHA);
		estiloColumna.setHorizontalAlign(getAlign(alineacion));
		return estiloColumna;
	}
	
	public static Style getEstiloEncabezadoColumnasPorDefecto(){
		Style estiloEncabezado = getEstiloPorDefecto();
		return estiloEncabezado; 
	}
	
	public static Style getEstiloEncabezadoColumnasPorDefecto(ReportAlign alineacion){
		Style estiloEncabezado = getEstiloPorDefecto();
		estiloEncabezado.setHorizontalAlign(getAlign(alineacion));
		return estiloEncabezado;
	}
	
	private static HorizontalAlign getAlign(ReportAlign alineacion){
		switch(alineacion){
			case ALIGN_CENTER: return HorizontalAlign.CENTER;
			case ALIGN_RIGHT: return HorizontalAlign.RIGHT;
			default: return HorizontalAlign.LEFT;
		}
	}
	
	public static Style getEstiloEncabezadoColumnasTextoMonto(){
		Style estiloEncabezado = getEstiloEncabezadoColumnasPorDefecto(ReportAlign.ALIGN_RIGHT);
		return estiloEncabezado; 
	}
	
	public static Style getEstiloTitulo(){
		Style estiloTitulo = getEstiloPorDefecto();
		estiloTitulo.setHorizontalAlign(HorizontalAlign.CENTER);
		estiloTitulo.setFont(new Font(12, Font._FONT_ARIAL, true));
		return estiloTitulo;
	}
	
	public static Style getEstiloSubtitulo(){
		Style estiloTitulo = getEstiloPorDefecto();
		estiloTitulo.setHorizontalAlign(HorizontalAlign.CENTER);
		estiloTitulo.setFont(new Font(15, Font._FONT_VERDANA, true));
		return estiloTitulo;
	}
	
	public static Style getEstiloColumnaCentrada(){
		Style estiloColumna = getEstiloPorDefecto();
		estiloColumna.setHorizontalAlign(HorizontalAlign.CENTER);
		return estiloColumna;
	}
	
	public ReporteBuilder(){
		this.drb = new FastReportBuilder ();
		this.drb.setUseFullPageWidth(true)
        .setIgnorePagination(true)
        .setAllowDetailSplit(true);
		;
	}
	
	public ReporteBuilder(String textWhenNoData){
		this();
		this.drb.setWhenNoData(textWhenNoData, getEstiloEncabezadoColumnasPorDefecto(), Boolean.TRUE, Boolean.FALSE);
	}
	
	public ReporteBuilder setMargenes(Integer arriba, Integer abajo, Integer izquierda, Integer derecha){
		this.drb.setMargins(arriba, abajo, izquierda, derecha);
		return this;
	}
	public ReporteBuilder setTemplateReporte(String rutaTemplateReporte){
		this.drb.setTemplateFile(rutaTemplateReporte);
		return this;
	}

	public ReporteBuilder addTitulo(String titulo, Style estilo, Integer alto){
		this.drb.setTitle(titulo)
				.setTitleStyle(estilo)
				.setTitleHeight(alto);
		return this;
		
	}
	
	public ReporteBuilder addTitulo(String titulo, Style estilo){
		this.drb.setTitle(titulo)
				.setTitleStyle(estilo);
		return this;
	}
	
	public ReporteBuilder addTitulo(String titulo){
		this.addTitulo(titulo, getEstiloTitulo());
		return this;
	}
	
	public ReporteBuilder addTitulo(String titulo, Integer alto){
		this.addTitulo(titulo, getEstiloTitulo(), alto);
		return this;
	}
	
	public ReporteBuilder addSubtitulo(String subtitulo, Integer alto){
		this.drb.setSubtitle(subtitulo)
				.setTitleStyle(getEstiloSubtitulo())
				.setTitleHeight(alto);
		return this;
	}
	
	public ReporteBuilder setAltoEncabezado(Integer alto){
		this.drb.setHeaderHeight(alto);
		return this;
	}
	
	public ReporteBuilder setAltoDetalle(Integer alto){
		this.drb.setDetailHeight(alto);
		return this;
	}
	
	public ReporteBuilder setAltoPie(Integer alto){
		this.drb.setFooterHeight(alto);
		return this;
	}
	
	public ReporteBuilder addColumna(String nombreColumna, String tipoDeDatoColumna, String tituloColumna, int anchoColumna) throws ColumnBuilderException{
		AbstractColumn columna = ColumnBuilder.getInstance()
										.setColumnProperty(nombreColumna, tipoDeDatoColumna)
										.setTitle(tituloColumna)
										.setWidth(anchoColumna)
										.build();
		
		this.drb.addColumn(columna);
		return this;
	}
	
	public ReporteBuilder addColumna(String nombreColumna, String tipoDeDatoColumna, String tituloColumna, int anchoColumna, Style estiloColumna) throws ColumnBuilderException{
		AbstractColumn columna = ColumnBuilder.getInstance()
									.setColumnProperty(nombreColumna, tipoDeDatoColumna)
									.setTitle(tituloColumna)
									.setStyle(estiloColumna)
									.setWidth(anchoColumna)
									.build();
		this.drb.addColumn(columna);
		return this;
	}
	
	public ReporteBuilder addColumna(String nombreColumna, String tipoDeDatoColumna, String tituloColumna, int anchoColumna, Style estiloColumna, Style estiloTituloColumna) throws ColumnBuilderException{
		AbstractColumn columna = ColumnBuilder.getInstance()
									.setColumnProperty(nombreColumna, tipoDeDatoColumna)
									.setTitle(tituloColumna)
									.setStyle(estiloColumna)
									.setWidth(anchoColumna)
									.setHeaderStyle(estiloTituloColumna)
									.build();
		this.drb.addColumn(columna);
		return this;
	}
	
	
	public ReporteBuilder addColumnaTexto(String nombreColumna,String tituloColumna, int anchoColumna) throws ColumnBuilderException {
		return addColumna(nombreColumna, String.class.getName(), tituloColumna, anchoColumna, ReporteBuilder.getEstiloColumnaPorDefecto(), ReporteBuilder.getEstiloEncabezadoColumnasPorDefecto());
	}
	
	public ReporteBuilder addColumnaTexto(String nombreColumna, String tituloColumna, int anchoColumna, ReportAlign alineacion) throws ColumnBuilderException {
		return addColumna(nombreColumna, String.class.getName(), tituloColumna, anchoColumna, ReporteBuilder.getEstiloColumnaPorDefecto(alineacion), ReporteBuilder.getEstiloEncabezadoColumnasPorDefecto(alineacion));
	}
	
	public ReporteBuilder addColumnaTexto(String nombreColumna,String tituloColumna, int anchoColumna, Style estiloColumna) throws ColumnBuilderException {
		return addColumna(nombreColumna, String.class.getName(), tituloColumna, anchoColumna, estiloColumna, ReporteBuilder.getEstiloEncabezadoColumnasPorDefecto());
	}
	
	public ReporteBuilder addColumnaTextoMonto(String nombreColumna,String tituloColumna, int anchoColumna) throws ColumnBuilderException {
		return addColumna(nombreColumna, String.class.getName(), tituloColumna, anchoColumna, ReporteBuilder.getEstiloColumnaMontoPorDefecto(), ReporteBuilder.getEstiloEncabezadoColumnasTextoMonto());
	}
	
	public ReporteBuilder addColumnaFecha(String nombreColumna,String tituloColumna, int anchoColumna) throws ColumnBuilderException {
		return addColumna(nombreColumna, Date.class.getName(), tituloColumna, anchoColumna, ReporteBuilder.getEstiloColumnaFechaPorDefecto(), ReporteBuilder.getEstiloEncabezadoColumnasPorDefecto());
	}
	
	public ReporteBuilder addColumnaFecha(String nombreColumna,String tituloColumna, int anchoColumna, ReportAlign alineacion) throws ColumnBuilderException {
		return addColumna(nombreColumna, Date.class.getName(), tituloColumna, anchoColumna, ReporteBuilder.getEstiloColumnaFechaPorDefecto(alineacion), ReporteBuilder.getEstiloEncabezadoColumnasPorDefecto(alineacion));
	}
	
	public ReporteBuilder addColumnaLong(String nombreColumna,String tituloColumna, int anchoColumna) throws ColumnBuilderException {
		return addColumna(nombreColumna, Long.class.getName(), tituloColumna, anchoColumna, ReporteBuilder.getEstiloColumnaPorDefecto(), ReporteBuilder.getEstiloEncabezadoColumnasPorDefecto());
	}
	
	public ReporteBuilder addColumnaLong(String nombreColumna,String tituloColumna, int anchoColumna, ReportAlign alineacion) throws ColumnBuilderException {
		return addColumna(nombreColumna, Long.class.getName(), tituloColumna, anchoColumna, ReporteBuilder.getEstiloColumnaPorDefecto(alineacion), ReporteBuilder.getEstiloEncabezadoColumnasPorDefecto(alineacion));
	}
	
	public ReporteBuilder addColumnaInteger(String nombreColumna,String tituloColumna, int anchoColumna) throws ColumnBuilderException {
		return addColumna(nombreColumna, Integer.class.getName(), tituloColumna, anchoColumna, ReporteBuilder.getEstiloColumnaPorDefecto(), ReporteBuilder.getEstiloEncabezadoColumnasPorDefecto());
	}
	
	public ReporteBuilder addColumnaInteger(String nombreColumna,String tituloColumna, int anchoColumna, ReportAlign alineacion) throws ColumnBuilderException {
		return addColumna(nombreColumna, Integer.class.getName(), tituloColumna, anchoColumna, ReporteBuilder.getEstiloColumnaPorDefecto(alineacion), ReporteBuilder.getEstiloEncabezadoColumnasPorDefecto(alineacion));
	}
	
	public ReporteBuilder addColumnaBoolean(String nombreColumna,String tituloColumna, int anchoColumna) throws ColumnBuilderException {
		return addColumna(nombreColumna, Boolean.class.getName(), tituloColumna, anchoColumna, ReporteBuilder.getEstiloColumnaPorDefecto(), ReporteBuilder.getEstiloEncabezadoColumnasPorDefecto());
	}
	
	public ReporteBuilder addColumnaBoolean(String nombreColumna,String tituloColumna, int anchoColumna, ReportAlign alineacion) throws ColumnBuilderException {
		return addColumna(nombreColumna, Boolean.class.getName(), tituloColumna, anchoColumna, ReporteBuilder.getEstiloColumnaPorDefecto(alineacion), ReporteBuilder.getEstiloEncabezadoColumnasPorDefecto(alineacion));
	}
	
	public ReporteBuilder addReporteConcatenado(DynamicReport subreporte, String idCollectionDataSource) throws DJBuilderException{
		this.drb.addConcatenatedReport(subreporte, new ClassicLayoutManager(), idCollectionDataSource, DJConstants.DATA_SOURCE_ORIGIN_PARAMETER, DJConstants.DATA_SOURCE_TYPE_COLLECTION, false);
		return this;
	}
	
	public DynamicReport build(){
		return this.drb.build();
	}

	public FastReportBuilder getDrb() {
		return drb;
	}

	public void addGroup(int iNumero) throws BuilderException{
		drb.addGroups(iNumero).setGroupLayout(iNumero,GroupLayout.EMPTY);
	}
	
	public void addSubReporte(int iNumeroGrupo
							, ReporteBuilder oSubReporte
							, String sAtributo) 
		throws DJBuilderException
	{
		drb.addSubreportInGroupFooter(iNumeroGrupo
									, oSubReporte.getDrb().build()
									, new ClassicLayoutManager()
									, sAtributo
									, DJConstants.DATA_SOURCE_ORIGIN_FIELD
									, DJConstants.DATA_SOURCE_TYPE_COLLECTION);
		drb.setUseFullPageWidth(false);
	}
	
	public void addField(String sAtributo, String sClase)
    {
        drb.addField(sAtributo, sClase);
    }
	
	
	public void setLandscape(){
		
		drb.setPageSizeAndOrientation(Page.Page_A4_Landscape());
		drb.setIgnorePagination(false);
		
		
	}
	
	public void agregarTotal(int numeroColumna,Style estilo){
		drb.setGrandTotalLegend("TOTAL:");
		drb.setGrandTotalLegendStyle(estilo);
		drb.addGlobalFooterVariable(numeroColumna, DJCalculation.SUM, estilo);
		
	}
	
	public void agregarTotalPesos(int numeroColumna,Style estilo){
		drb.setGrandTotalLegend("Total Pesos:");
		drb.setGrandTotalLegendStyle(estilo);
		drb.addGlobalFooterVariable(numeroColumna, DJCalculation.SUM, estilo);
		
	}
	
	public void setPortrait(){
		drb.setPageSizeAndOrientation(new Page(800, 595));
			
		
		
	}
	
	public ReporteBuilder addColumnaTextoSmall(String nombreColumna, String tituloColumna, int anchoColumna, ReportAlign alineacion) throws ColumnBuilderException {
		return addColumna(nombreColumna, String.class.getName(), tituloColumna, anchoColumna, ReporteBuilder.getEstiloColumnaPorDefectoSmall(alineacion), ReporteBuilder.getEstiloColumnaPorDefectoSmall(alineacion));
	}
	
	public static Style getEstiloColumnaPorDefectoSmall(ReportAlign alineacion){
		Style estiloColumna = getEstiloPorDefecto();
		estiloColumna.setBlankWhenNull(true);
		
		estiloColumna.setStretchWithOverflow(true);
		estiloColumna.setFont(new Font(12, Font._FONT_TIMES_NEW_ROMAN, true));
		estiloColumna.setHorizontalAlign(getAlign(alineacion));
		estiloColumna.setPaddingRight(5);
//		estiloColumna.setBorder(Border.PEN_2_POINT);
		estiloColumna.setPaddingLeft(5);

		
		return estiloColumna;
	}
	
	/** Fin */
}