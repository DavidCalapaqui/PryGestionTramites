	let fechaAux = ""; 
					
					
						function ConsultarTramites() {
							let fecha = $('#fecha').val();
							//let fechaAux = $('#fechaAux').val();
							fechaAux = fecha;						
							//alert(fechaAux);
							
							if(fecha.length != 0){
								$("#lblErrFecha").addClass("d-none");
								$('#fechaAux').val(fecha);
								console.log("Aux : " ,fechaAux );
							
								$.ajax({
									url : "/tramite/getTramitesOneDate/"
											+ fecha + "/",
									method : 'GET',
									success : function(response) {
										//console.log(response);
										let tramites = [];
										tramites = response;
										console.log('Tramites', tramites);
										
										if(tramites.length != 0){
											$("#lblErrNoTram").addClass("d-none");
											
											document.getElementById('lblTituloRep').innerHTML = 'Reporte de tramites ingresados en '+ fecha;
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
									$("#lblErrFecha").removeClass("d-none");	
								}
								
							}
							
							
							function GenerarReporte(){
								
								let elementos = document.getElementsByName("format");
								let formato = "";
								console.log(elementos);
									
								for(var i=0; i<elementos.length; i++) {
									if(elementos[i].checked === true){
											formato = elementos[i].value
										}	
									}
								
								  //console.log(" Elemento: " + elementos[i].value + "\n Seleccionado: " + elementos[i].checked);
						
								$("#spnSpinner").removeClass("d-none");
								if(fechaAux.length != 0){
									
									$.ajax({
										url : "/reportTramite/report/"
												+ fechaAux + "/" + formato + "/",
										method : 'GET',
										success : function(response) {
											$("#spnSpinner").addClass("d-none");
											console.log(response);
											//swal("Reporte Generado", response , "success");
											Swal.fire({
												title: 'Reporte Generado',
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
							}
