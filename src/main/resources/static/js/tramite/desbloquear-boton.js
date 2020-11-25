setInterval(function() {
					DesbloquearBoton();
				}, 1);

				function validaNumericos(event) {
					if (event.charCode >= 48 && event.charCode <= 57) {
						return true;
					}
					return false;
				}

				function Cargar() {
					$("#lblEmptySolicitante").addClass("d-none");
					$("#lblIncorrectId").addClass("d-none");
					$("#lblIncorrectId").addClass("d-none");

					document.getElementById('btnSubmit').disabled = true;

				}

				function DesbloquearBoton() {

					let sol = $("#txtSol").val();
					let asunto = $("#txtAsunto").val();
					let auxArchivo = $("#auxFile").val();
					let idTram = $("#txtIdTram").val();
					let fechaDir = $("#fechaDir").val();
					let horaDir = $("#horaDir").val();

					let desbloquear = false;
					//&& fechaDir.length !=0 && horaDir.length != 0
					//si se está actualizando, no importa el archivo porque ya tiene uno	
					if (idTram.length != 0) {
						if (sol.length != 0 && asunto.length != 0) {
							desbloquear = true;
						} else {
							desbloquear = false;

						}
						//entonces se esta ingresando un nuevo

					}///else{
					//si no se está actualizando, se comprueba que el file esté lleno 
					if (document.getElementById("file").files.length != 0
							&& sol.length != 0 && asunto.length != 0) {

						desbloquear = true;
					}

					if ((desbloquear) === true) {
						document.getElementById('btnSubmit').disabled = false;
					} else {
						document.getElementById('btnSubmit').disabled = true;
					}
				}