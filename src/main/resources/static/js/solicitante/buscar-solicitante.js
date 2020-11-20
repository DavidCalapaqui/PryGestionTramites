function searchByIdentificacion(){
	
	//lblEmptySolicitante
	var criteria = $("#txtIdentificacion").val(); 
	
	$.ajax({
		url: "/solicitante/search/" + criteria+"/",
		method: 'GET',
		success : function(response){
			$("#solicitanteId").empty();
			var count = 1;
			
			if(count >0){
				$("#solicitanteId").addClass('visible').removeClass('invisible');
				$.each(response, function(index, solicitante){
					$("#solicitanteId").append("<option selected  th:value='"+ solicitante.idSolicitante +"'>" + solicitante.nombre +  "</option>")
					
				});
			}
			else{
				$("#solicitanteId").addClass('invisible').removeClass('visible');
				console.log("No hay slicitantes para esa id: " + criteria);
			}
			
		},
		error : function(err){
			console.error(err);
			console.error(err.responseText);
		}
		
	});
}

function ValidarSubmit(){
	
}

function searchOneByIdentificacion(){
	var criteria = $("#txtIdentificacion").val(); 
	
	if(criteria.length === 10 || criteria.length === 13 ){
		$("#lblIncorrectId").addClass( "d-none" );
		return new Promise(function(resolve, reject){
			$.ajax({
				url: "/solicitante/searchOne/" + criteria+"/",
				method: 'GET',
				success : function(response){
					
					if(!response){
						$("#txtNombreSol").val('No existe este solicitante.');
						$("#txtSol").val("");

					}
					
					console.log(response);
					
					var id = response.idSolicitante.toString();
					var nombre = response.nombre;
					console.log(id);
					console.log(nombre);
					
					$("#txtSol").val(id);
					$("#txtNombreSol").val(nombre);
					
					resolve(response);
					
				},
				error : function(err){
					console.error(err);
					$("#txtSol").val("");
					reject(err);
					console.error(err.responseText);
					throw new Error('Error!!', err);
					$("#lblEmptySolicitanter").removeClass( "d-none" );
					$("#txtNombreSol").val('No existe este solicitante.');				}
			});
		})
	}else{
		$("#lblIncorrectId").removeClass( "d-none" );
	}
	
	
	
	
	/*$.ajax({
		url: "/solicitante/searchOne/" + criteria+"/",
		method: 'GET',
		success : function(response){
			
			if(!response){
				$("#txtNombreSol").val('No existe este solicitante.');
			}
			
			console.log(response);
			
			var id = response.idSolicitante.toString();
			var nombre = response.nombre;
			console.log(id);
			console.log(nombre);
			
			$("#txtSol").val(id);
			$("#txtNombreSol").val(nombre);
			
			
		},
		error : function(err){
			console.error(err);
			console.error(err.responseText);
			 throw new Error('Error!!', errorLanzado);
		}
		
	});*/
}

function CargarDatosToUpdate(){
	 var idTramite = $("#txtIdTram").val();
	 console.log("idTramite: " + idTramite);
	
	 if(idTramite != null){
		 $.ajax({
				url: "/tramite/searchOneById/" + idTramite+"/",
				method: 'GET',
				success : function(response){
					console.log(response);
					
					var identificacion = response.solicitante.identificacion;
					var nombreSol = response.solicitante.nombre;
					var asunto = response.asunto;
					var idSol = response.solicitante.idSolicitante;
					var doc = response.documento;
					
					$("#txtIdentificacion").val(identificacion);
					$("#txtNombreSol").val(nombreSol);
					$("#txtAsunto").val(asunto);
					$("#txtSol").val(idSol);
					$("#auxFile").val(doc);
					
				},
				error : function(err){
					console.error(err);
					console.error(err.responseText);
				}
			});
	 }
}

function CargarDatosToAssign(){

	 var idTramite = $("#txtIdTram").val();
	 console.log("idTramite: " + idTramite);
	
	 if(idTramite != null){
		 $.ajax({
				url: "/tramite/searchOneById/" + idTramite+"/",
				method: 'GET',
				success : function(response){
					console.log(response);
					
					var identificacion = response.solicitante.identificacion;
					var nombreSol = response.solicitante.nombre;
					var asunto = response.asunto;
					var idSol = response.solicitante.idSolicitante;
					var doc = response.documento;
					
					$("#txtIdentificacion").val(identificacion);
					$("#txtNombreSol").val(nombreSol);
					$("#txtAsunto").val(asunto);
					$("#txtSol").val(idSol);
					$("#auxFile").val(doc);
					
				},
				error : function(err){
					console.error(err);
					console.error(err.responseText);
				}
			});
	 }
	 
	
}

function ValidarSubmit(){
	
	
	
	
	
	
	
	
	
	
	
	return true
}

$(document).ready(CargarDatosToUpdate());

	