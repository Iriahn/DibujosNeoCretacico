async function cargaPrints(){
    try{
        let response = await fetch("/api400/prints");
        let datos = await response.json();
        let tablacuerpo = document.querySelector("#listaprints");
        tablacuerpo.innerHTML = "";
        let fila, columna, img, enlace;
        
        datos.forEach(pri => {
            fila = document.createElement("tr");

            columna = document.createElement("td");
            columna.innerHTML = pri.REDPREFERIDA;
            fila.appendChild(columna);

            columna = document.createElement("td");
            columna.innerHTML = pri.USERPREFERIDA;
            fila.appendChild(columna);
            
            columna = document.createElement("td");
            columna.innerHTML = pri.UNIDADES;
            fila.appendChild(columna);

            columna = document.createElement("td");
            columna.innerHTML = pri.TAMANO;
            fila.appendChild(columna);
            
            columna = document.createElement("td");
            columna.innerHTML = pri.TIPO;
            fila.appendChild(columna);
            
            columna = document.createElement("td");
            columna.innerHTML = pri.PRECIO + "€";
            fila.appendChild(columna);

            columna = document.createElement("td");
            enlace = document.createElement("a");
            enlace.href = `/prints/editar/${pri.IDPRI}`;
            enlace.innerText = "Editar";
            columna.appendChild(enlace);
            fila.appendChild(columna);
            
            columna = document.createElement("td");
            enlace = document.createElement("a");
            enlace.href = `/prints/borrar/${pri.IDPRI}`;
            enlace.innerText = "Borrar";
            columna.appendChild(enlace);
            fila.appendChild(columna);
            
            tablacuerpo.appendChild(fila);
        });
         
    }catch(error){
        console.error("Error en el Fetch:", error);
    }
}

document.addEventListener("DOMContentLoaded", cargaPrints);