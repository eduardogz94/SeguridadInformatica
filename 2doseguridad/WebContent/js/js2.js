function $$(id) { return document.getElementById(id); }

$$("formuploadajax").addEventListener("submit", function(e){
	e.preventDefault();
	let formData = new FormData($$("formuploadajax"));

     for(let pair of formData.entries()) {
   		console.log(pair[0]+ ', '+ pair[1]); 
	}
	
	fetch("localhost:8443/Asimetrico", {
	body: FormData,
	headers: {
		"Content-Type": "multipart/form-data",
	},
	method: "POST",
	}
	.then(data => console.log(data);
	)
	.catch(error => console.log(Error);
	);
});