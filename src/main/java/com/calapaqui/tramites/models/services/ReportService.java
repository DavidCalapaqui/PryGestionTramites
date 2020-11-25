package com.calapaqui.tramites.models.services;

import java.io.File;
import java.io.FileNotFoundException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.calapaqui.tramites.models.entities.Tramite;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

@Service
public class ReportService {

	@Autowired
	private ITramiteService srvTramite;

	// REPORTE UNA FECHA
	public String exportReport(String reportFormat, String fecha) throws FileNotFoundException, JRException {
		
		
		File file;
		JasperReport jasperReport;
		
		
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		Date fechaDate = null;
		try {
			fechaDate = formato.parse(fecha);
		} catch (ParseException ex) {
			System.out.println(ex);
		}

		List<Tramite> tramites = srvTramite.reporteFecha(fechaDate);
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(tramites);
		
		
		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("createdBy", "David Calapaqui");

	
		switch (reportFormat) {
		case "excel": {
			file = ResourceUtils.getFile("src//main//resources//templates//reportes//reporteExcel.jrxml");
			jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
			JRXlsxExporter exporter = new JRXlsxExporter();
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			File outputFile = new File(ObtenerOCrearDirectorio() + "\\reporte_" + fecha + ".xlsx");
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputFile));

			SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
			configuration.setDetectCellType(true);// Set configuration as you like it!!
			configuration.setCollapseRowSpan(false);
			configuration.setRemoveEmptySpaceBetweenColumns(true);
			configuration.setRemoveEmptySpaceBetweenRows(true);
			configuration.setMaxRowsPerSheet(100);
			
			exporter.setConfiguration(configuration);
			
			exporter.exportReport();
			
			
		}break;

		case "pdf": {
			file = ResourceUtils.getFile("src//main//resources//templates//reportes//reporteUnaFecha.jrxml");	
			jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
			JasperExportManager.exportReportToPdfFile(jasperPrint,
					ObtenerOCrearDirectorio() + "\\reporte_" + fecha + ".pdf");
		}break;

		}
		return "Reporte generado en: " + ObtenerOCrearDirectorio();
	}

	// REPORTE ENTRE FECHAS
	public String exportReportTwoDates(String reportFormat, String desde, String hasta)
			throws FileNotFoundException, JRException {
		File file;
		JasperReport jasperReport;
		
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		Date desdeDate = null;
		Date hastaDate = null;
		try {
			desdeDate = formato.parse(desde);
			hastaDate = formato.parse(hasta);
		} catch (ParseException ex) {
			System.out.println(ex);
		}
		List<Tramite> tramitesEntreFechas = srvTramite.reporteEntreFechas(desdeDate, hastaDate);
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(tramitesEntreFechas);
		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("createdBy", "David Calapaqui");
		
		switch (reportFormat) {
		case "excel": {
			file = ResourceUtils.getFile("src//main//resources//templates//reportes//reporteExcel.jrxml");
			jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
			JRXlsxExporter exporter = new JRXlsxExporter();
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			File outputFile = new File(ObtenerOCrearDirectorio() + "\\reporte_"+desde+"_hasta_"+hasta+".xlsx");
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputFile));

			SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
			configuration.setDetectCellType(true);// Set configuration as you like it!!
			configuration.setCollapseRowSpan(false);
			configuration.setRemoveEmptySpaceBetweenColumns(true);
			configuration.setRemoveEmptySpaceBetweenRows(true);
			configuration.setMaxRowsPerSheet(100);
			exporter.setConfiguration(configuration);
			
			exporter.exportReport();
			
			
		}break;

		case "pdf": {
			file = ResourceUtils.getFile("src//main//resources//templates//reportes//reporteEntreFechas.jrxml");	
			jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
			JasperExportManager.exportReportToPdfFile(jasperPrint,
					ObtenerOCrearDirectorio() + "\\reporte_desde_" + desde+"_hasta_"+hasta+".pdf");
		}break;

		}
		return "Reporte generado en: " + ObtenerOCrearDirectorio();
	
	}

	// OBTIENE LA CARPETA /ESCRITORIO/REPORTES y si no existe la crea y retorna el
	// path
	public String ObtenerOCrearDirectorio() {
		String usrName = System.getProperty("user.name");
		File dskReport = new File("C:\\Users\\" + usrName + "\\Downloads\\ReportesTramites");

		if (!dskReport.canExecute()) {
			dskReport.mkdir();
		}
		return dskReport.getAbsolutePath();
	}

}
