	
					
						function ConsultarTramites() {
							let desde = $('#desde').val();
							let hasta = $('#hasta').val();
							//let fechaAux = $('#fechaAux').val();
							//fechaAux = fecha;						
							//alert(fechaAux);
							
							if(desde.length != 0 ){
								$("#lblDesdeFecha").addClass("d-none");
								//$('#fechaAux').val(fecha);
								//console.log("Aux : " ,fechaAux );
								if(hasta.length != 0 ){
										
									$("#lblHastaFecha").addClass("d-none");
									$.ajax({
										url : "/tramite/getTramitesBetweenDates/"
												+ desde + "/" +hasta+"/",
										method : 'GET',
										success : function(response) {
											//console.log(response);
											let tramites = [];
											tramites = response;
											console.log('Tramites', tramites);
											
											if(tramites.length != 0){
												$("#lblErrNoTram").addClass("d-none");
												
												document.getElementById('lblTituloRep').innerHTML = 'Reporte de tramites ingresados desde '+ desde + ' hasta '+ hasta;
												$("#table").removeClass("d-none");
												$(".odd").remove();
												$(".other").remove();
												//for(var i=0; i<=tramites.length; i++){
												for(t of tramites){
													console.log(t);
													
													var fila = '<tr id="row" class="other"><td>'
													+ t.idTramite
													+ '</td><td>'
													+ t.solicitante.identificacion
													+ '</td><td>'
													+ t.solicitante.nombre
													+ '</td><td>'
													+ t.asunto
													+ '</td><td>'
													+ t.fechaIngreso.substring(0,10)											
													+ '</td><td>'
													+ t.horaIngreso
													+ '</td></tr>'; //esto seria lo que contendria la fila
													
													
													$('#dataTable tr:first').after(fila);
												
												}
											}else{
												$("#lblErrNoTram").removeClass("d-none");
												$("#table").addClass("d-none");
											}
										},
										error : function(err) {
											console.error(err);
											//console.error(err.responseText);
										}
									});
								}else{
									$("#lblHastaFecha").removeClass("d-none");	
								}
										
								
								
								
								
								}else{
									$("#lblDesdeFecha").removeClass("d-none");	
								}
								
							}
							
							
							function GenerarReporte(){
								let elementos = document.getElementsByName("format");
								let formato = "";
							
								let desde = $('#desde').val();
								let hasta = $('#hasta').val();
								$("#spnSpinner").removeClass("d-none");
								
								for(var i=0; i<elementos.length; i++) {
									if(elementos[i].checked === true){
											formato = elementos[i].value
										}	
									}
									
									$.ajax({
										url : "/reportTramite/reportTwoDates/"
												+ desde + "/"+hasta+"/"+formato+"/",
										method : 'GET',
										success : function(response) {
											$("#spnSpinner").addClass("d-none");
											console.log(response);
											//swal("Reporte Generado", response , "success");
											Swal.fire({
												title: 'Reporte entre fechas Generado',
												  text: response,
												  icon: 'success',
										
												  confirmButtonText: 'ok'
											}).then(function (result) {
											    if (result.value) {
											    	//window.location.href = "delete/"+id
											    	console.log(result);
											    } 
											})
										},
										error : function(err) {
											console.error(err);
											console.error(err.responseText);
										}
									});
									
								
							}
							