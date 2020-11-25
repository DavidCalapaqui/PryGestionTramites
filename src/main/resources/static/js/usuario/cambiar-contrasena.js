var currentPassword; 
			let boolBtn = false;
			let boolBtn2 = false;
			
			setInterval(function(){ HabilitarBoton(); }, 1);

				
				function ValidarContrasenaActual(){
					let respGET;
					
					currentPassword = document.getElementById("currentPassword").value;
					
					CoincidirContrasena(currentPassword).then(function(datosDevueltos){
						respGET =  datosDevueltos;
						
						if(respGET === false){
							$("#lblErrCurrentPassword").removeClass( "d-none" );
							boolBtn = false;
						}else{
							$("#lblErrCurrentPassword").addClass( "d-none" );
							boolBtn = true;
						}
						 
						console.log('boolbtn', boolBtn);
					}, function(errorLanzado){
					  throw new Error('Error!!', errorLanzado);
					})
					.catch(err=> console.log('Error: ', err));
				}
				
				
				function ValidarContrasenas(){
					let newPassword = $("#newPassword").val();
					let confirmPassword= $("#confirmPassword").val();
					
					//let sonInguales = CompararContrasena(newPassword, newPassword);
					
					
					
					if( newPassword.length > 8  ){
						//muestra contra min de 8 caracteres	
						
						$("#lblLegthError").addClass( "d-none" );
			
						if(newPassword === confirmPassword){
							$("#lblErrNewPassword").addClass( "d-none" );
							boolBtn2 = true;
						}else{
							$("#lblErrNewPassword").removeClass( "d-none" );
							boolBtn2 = false;
						}
						
					}else{
						
						$("#lblLegthError").removeClass( "d-none" );
						boolBtn2 = false;
						
					}
					
					console.log('boolBtn2', boolBtn2);

				}
				
				
				function CompararContrasena(nwPass, cfPass){
					if(nwPass !== cfPass){
						
						$("#lblErrNewPassword").removeClass( "d-none" );
						console.log('No coinciden');
						return false;	
					}else{
						$("#lblErrNewPassword").addClass( "d-none" );
							//boolBtn2 = true;
							return true;
			
					}
				}
				
		
			
		function CoincidirContrasena(currentPassword){
		
				return new Promise(function(resolve, reject){
					$.ajax({
						url: "/usuario/validCurrentPassword/" + currentPassword +"/",
						method: 'GET',
						success: function(response){
							resolve(response);
							console.log('Respuesta de la peticion: ',response);

						},
						error: function(err){
							reject(err);
							console.log('Error en la peticion GET');
							
						}
					});
				})
				
			}
		
		
		
		
		function HabilitarBoton(){
			if((boolBtn === true) &&  (boolBtn2 === true)){
				//$('#btnGuardar').attr("disabled", false);
				document.getElementById('btnGuardar').disabled = false;
			}else{
				document.getElementById('btnGuardar').disabled = true;
			}
			
			
		}
		
		
			
			function Cargar(){
				$("#lblErrCurrentPassword").addClass( "d-none" );
				$("#lblErrNewPassword").addClass( "d-none" );
				$("#lblLegthError").addClass( "d-none" );
				
				
				//$("#btnGuardar").attr("disable", true);
				
				document.getElementById('btnGuardar').disabled = true;
			}
			