package com.calapaqui.tramites.report.repository;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.ui.Model;
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
//	
//	//Genera el reporte
//		@GetMapping("/makeReportOneDate/{fechaAux}")
//		public  List<Tramite> reporteUnDia(@PathVariable(value="fechaAux") String fechaAux, Model model)  throws FileNotFoundException, JRException {
//			String formato = "pdf";
//			String fecha = "11/11/2020";
//			return srvReporte.exportReport(formato, fecha);
//		}
////		
//	    @GetMapping("/report")
//	    public String generateReport() throws FileNotFoundException, JRException {
//		 String formato = "pdf";
//		 String fecha = "2020-11-11";   
//		 return srvReporte.exportReport(formato,fecha);
//	    }
	
	 @GetMapping(path="/report/{fechaAux}")
	    public String generateReport(@PathVariable String fechaAux, RedirectAttributes flash) throws FileNotFoundException, JRException {
		 String formato = "pdf";
		 //String fecha = "2020-11-11";   
		 flash.addFlashAttribute("success", "Reporte generado");
		 return srvReporte.exportReport(formato,fechaAux);
		
	    }
		
	 @GetMapping(path="/reportTwoDates/{desde}/{hasta}")
	    public String generateReportTwoDates(@PathVariable String desde, @PathVariable String hasta, RedirectAttributes flash) throws FileNotFoundException, JRException {
		 String formato = "pdf";
		 //String fecha = "2020-11-11";   
		 flash.addFlashAttribute("success", "Reporte generado");
		 return srvReporte.exportReportTwoDates(formato, desde, hasta);
		
	    }
		
		
		@GetMapping("/reportAllProcedure")
		public  List<Tramite> reporteTodosLosTramites() {
			
			return srvTramite.FindAll();
		}
			
		
	
	
	
}
