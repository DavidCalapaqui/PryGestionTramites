const MAXIMO_TAMANIO_BYTES = 2000000; // 1MB = 1 millón de bytes

// Obtener referencia al elemento
const $miInput = document.querySelector("#file");

$miInput.addEventListener("change", function() {
	// si no hay archivos, regresamos
	if (this.files.length <= 0)
		return;

	// Validamos el primer archivo únicamente
	const archivo = this.files[0];
	if (archivo.size > MAXIMO_TAMANIO_BYTES) {
		const tamanioEnMb = MAXIMO_TAMANIO_BYTES / 1000000;
		alert(`El archivo es demasiado grande. Maximo ${tamanioEnMb} MB`);
		// Limpiar
		$miInput.value = "";
	} else {
		// Validación pasada. Envía el formulario o haz lo que tengas que hacer
	}
});