function CargarSelectDepartamentos(){
	
	$.ajax({
		url : "/direccion/list/",
		method : 'GET',
		success : function(response){
			
			console.log(response);
			$("#slcDirecciones").empty();			
			var count = 1;			
			if(count > 0){								
				$("#slcDirecciones").addClass('visible').removeClass('invisible');
				$.each( response, function(index, direccion ) {					
					$("#slcDirecciones").append("<option value='"+ direccion.idDireccion +"'>" + direccion.nombre +  "</option>");					
				});
			}
			else{
				$("#slcDirecciones").addClass('invisible').removeClass('visible');
			}
		},
				error : function(err){
			
			console.error(err);

			console.error(err.responseText);
		}		
	});
}
$(document).ready(CargarSelectDepartamentos());
