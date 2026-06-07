async function cargaSelect(){
    try{
        let response = await fetch("/api400/dibujos");
        let datos = await response.json();
        let select = document.querySelector("#printdibujo");
        select.innerHTML= "";
        let opcion;

        datos.forEach(dib => {
            if(dib.SUBCATEGORIA != "REGALOS"){
                opcion = document.createElement("option");
                opcion.value = dib.IDDIB;
                opcion.textContent = dib.TITULO;
                select.appendChild(opcion);
            }            
        });
    }catch(error){
        console.error("Error en el Fetch:", error);
    }
}

document.addEventListener("DOMContentLoaded", cargaSelect);