async function cargaUsuarios(){
    try{
        let response = await fetch("/api400/usuarios");
        let datos = await response.json();
        let tablacuerpo = document.querySelector("#listaususarios");
        let fila, columna, borrar;
        
        datos.forEach(user => {
            fila = document.createElement("tr");

            columna = document.createElement("td");
            columna.innerHTML = user.NOMBRE;
            fila.appendChild(columna);
            
            columna = document.createElement("td");
            columna.innerHTML = user.ROL;
            fila.appendChild(columna);

            columna = document.createElement("td");
            borrar = document.createElement("a");
            borrar.href = `/usuario/borrar/${user.IDUSU}`;
            borrar.innerText = "Borrar";
            columna.appendChild(borrar);
            fila.appendChild(columna);
            
            tablacuerpo.appendChild(fila);
        });
         
    }catch(error){
        console.error("Error en el Fetch:", error);
    }
}

document.addEventListener("DOMContentLoaded", cargaUsuarios);