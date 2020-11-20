package com.calapaqui.tramites.report.repository;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import net.sf.jasperreports.engine.JRDefaultScriptlet;

public class MetodosFechasReportes extends JRDefaultScriptlet  {

	public String CalendarToString(Calendar fecha) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		return sdf.format(fecha.getTime());
	}
}
