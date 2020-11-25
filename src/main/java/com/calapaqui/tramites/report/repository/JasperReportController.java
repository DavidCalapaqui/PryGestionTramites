package com.calapaqui.tramites.report.repository;

import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.calapaqui.tramites.models.entities.Tramite;
import com.calapaqui.tramites.models.services.ITramiteService;
import com.calapaqui.tramites.models.services.ReportService;

import net.sf.jasperreports.engine.JRException;

@RestController
@SpringBootApplication
@RequestMapping(value = "/reportTramite")
public class JasperReportController {
	
	@Autowired
	private ITramiteService srvTramite;	
	
	@Autowired
	private ReportService srvReporte;

	
	 @GetMapping(path="/report/{fechaAux}/{formato}")
	    public String generateReport(@PathVariable String fechaAux,@PathVariable String formato, RedirectAttributes flash) throws FileNotFoundException, JRException {
		 
		 System.out.println("Formato: "+formato);
		 //String fecha = "2020-11-11";   
		 flash.addFlashAttribute("success", "Reporte generado");
		 return srvReporte.exportReport(formato,fechaAux);
		
	    }
		
	 @GetMapping(path="/reportTwoDates/{desde}/{hasta}/{formato}")
	    public String generateReportTwoDates(@PathVariable String desde, @PathVariable String hasta,@PathVariable String formato, RedirectAttributes flash) throws FileNotFoundException, JRException {
		 
		 System.out.println("Formato: "+formato);
		 //String fecha = "2020-11-11";   
		 flash.addFlashAttribute("success", "Reporte generado");
		 return srvReporte.exportReportTwoDates(formato, desde, hasta);
		
	    }
		
		
		@GetMapping("/reportAllProcedure")
		public  List<Tramite> reporteTodosLosTramites() {
			
			return srvTramite.FindAll();
		}
			
		
	
	
	
}
