function eliminar(id){
	
	Swal.fire({
		title: '¿Está seguro de eliminar?',
		  text: "Ya no se podrá revertir!",
		  icon: 'warning',
		  showCancelButton: true,
		  confirmButtonText: 'Si, eliminar!'
	}).then(function (result) {
	    if (result.value) {
	    	window.location.href = "delete/"+id
	    } 
	})
}