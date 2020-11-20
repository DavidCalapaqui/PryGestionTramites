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

@Service
public class ReportService {
	
	@Autowired
	private ITramiteService srvTramite;
	
	public String exportReport(String reportFormat, String fecha) throws FileNotFoundException, JRException {
        String path = "C:\\Users\\david\\OneDrive\\Escritorio\\reports";
        
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaDate = null;
        try {
            fechaDate = formato.parse(fecha);
        } 
        catch (ParseException ex) 
        {
            System.out.println(ex);
        }
        List<Tramite> tramites = srvTramite.reporteFecha(fechaDate);
       
        //load file and compile its
        File file = ResourceUtils.getFile("src//main//resources//templates//reportes//reporteUnaFecha.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(tramites);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Java Techie");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        if (reportFormat.equalsIgnoreCase("html")) {
            JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "\\reporte_"+fecha+".html");
        }
        if (reportFormat.equalsIgnoreCase("pdf")) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\reporte_"+fecha+".pdf");
        }

        return "Reporte generado en: " + path;
    }
	
	public String exportReportTwoDates(String reportFormat, String desde, String hasta) throws FileNotFoundException, JRException {
        String path = "C:\\Users\\david\\OneDrive\\Escritorio\\reports";
        
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date desdeDate = null;
        Date hastaDate = null;
        try {
        	desdeDate = formato.parse(desde);
        	hastaDate = formato.parse(hasta);
        } 
        catch (ParseException ex) 
        {
            System.out.println(ex);
        }
        List<Tramite> tramitesEntreFechas = srvTramite.reporteEntreFechas(desdeDate, hastaDate);
       
        //load file and compile its
        File file = ResourceUtils.getFile("src//main//resources//templates//reportes//reporteEntreFechas.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(tramitesEntreFechas);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Java Techie");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        if (reportFormat.equalsIgnoreCase("html")) {
            JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "\\reporte_desde_"+desde+"hasta"+"_"+hasta+".html");
        }
        if (reportFormat.equalsIgnoreCase("pdf")) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\reporte_desde_"+desde+"hasta"+"_"+hasta+".pdf");
        }

        return "Reporte generado en: " + path;
    }
	
	
	
	
	
	
}
