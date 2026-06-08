async function cargaDiblist(){
    try{
        let response = await fetch("/api400/dibujos");
        let datos = await response.json();
        let tablacuerpo = document.querySelector("#listadibujos");
        tablacuerpo.innerHTML = "";
        let fila, columna, img, enlace;
        
        datos.forEach(dib => {
            fila = document.createElement("tr");

            columna = document.createElement("td");
            columna.innerHTML = dib.IDDIB;
            fila.appendChild(columna);
            
            columna = document.createElement("td");
            img = document.createElement("img");
            img.classList.add("photolist")
            img.src = `/files/${dib.IMAGEN}`;
            columna.appendChild(img);
            fila.appendChild(columna);

            columna = document.createElement("td");
            columna.innerHTML = dib.TITULO;
            fila.appendChild(columna);
            
            columna = document.createElement("td");
            columna.innerHTML = dib.TEMATICA;
            fila.appendChild(columna);
            
            columna = document.createElement("td");
            columna.innerHTML = dib.PRECIO + "€";
            fila.appendChild(columna);

            columna = document.createElement("td");
            enlace = document.createElement("a");
            enlace.href = `/dibujo/editar/${dib.IDDIB}`;
            enlace.innerText = "Editar";
            columna.appendChild(enlace);
            fila.appendChild(columna);
            
            columna = document.createElement("td");
            enlace = document.createElement("a");
            enlace.href = `/dibujo/borrar/${dib.IDDIB}`;
            enlace.innerText = "Borrar";
            columna.appendChild(enlace);
            fila.appendChild(columna);
            
            tablacuerpo.appendChild(fila);
        });
         
    }catch(error){
        console.error("Error en el Fetch:", error);
    }
}

document.addEventListener("DOMContentLoaded", cargaDiblist);