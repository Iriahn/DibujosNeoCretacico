// Variable para guardar el menú desplegable con la clasificación de los dibujos
let desplegable = document.querySelector(".hamburguer");
// Variable para guardar contenedor del contenido del deplegable
let contenido = document.querySelector("#hamburguer-content");
// Variable para almacenar la posición del scroll vertical
let pos;

function mostrar(){
    contenido.classList.toggle("hamburguer-nocontent");
    /* Cada vez que se muestra el menú se actualiza la posición del scroll para poder hacer posterior 
    comprobación de scroll para cerrarlo */
    pos = window.scrollY;
}

function ocultar(){
    /* Para evitar que se active por microscroll, por ejemplo al querer cerrarlo manualmente,
    lo que bloquea abrirlo, comprueba si el scroll hecho es mayor a, por ejemplo 50 */
    if(window.scrollY - pos > 50 && !contenido.classList.contains("hamburguer-nocontent"))
      contenido.classList.add("hamburguer-nocontent");
}

// Solo se 
if(window.innerWidth < 760){
    window.addEventListener("scroll", ocultar);
    desplegable.addEventListener("click", mostrar);
}