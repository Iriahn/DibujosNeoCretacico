// Variable para guardar el menú desplegable con la clasificación de los dibujos
let desplegador = document.querySelector(".hamburguer");
// Variable para guardar contenedor del contenido del deplegable
let contenido = document.querySelector("#hamburguer-content");
// Variables que recogen los elementos del menú desplegable
let opcdespuno = document.querySelectorAll(".nivel-uno");
let opcdespdos = document.querySelectorAll(".nivel-dos");
// Variable para almacenar la posición del scroll vertical
let pos;

function mostrar(){
    contenido.classList.toggle("hamburguer-nocontent");
    /* Cada vez que se muestra el menú se actualiza la posición del scroll para poder hacer posterior 
    comprobación de scroll para cerrarlo */
    pos = window.scrollY;
}

function ocultarscroll(){
    /* Para evitar que se active por microscroll, por ejemplo al querer cerrarlo manualmente,
    lo que bloquea abrirlo, comprueba si el scroll hecho es mayor a, por ejemplo 50 */
    if(window.scrollY - pos > 50 && !contenido.classList.contains("hamburguer-nocontent"))
      contenido.classList.add("hamburguer-nocontent");
}

function ocultar(){
    contenido.classList.add("hamburguer-nocontent");
}

// Solo se aplican las funciones si el ancho de ventana es tamaño móvil
if(window.innerWidth < 760){
    window.addEventListener("scroll", ocultarscroll);
    desplegador.addEventListener("click", mostrar);
    for(let i = 0; i < opcdespuno.length; i++){
        opcdespuno[i].addEventListener("click", ocultar);
    }
    for(let i = 0; i < opcdespdos.length; i++){
        opcdespdos[i].addEventListener("click", ocultar);
    }
}

async function cargaDibujos(){
    try{
        let response = await fetch("/api400/dibujos");
        let datos = await response.json();
        vaciarSecciones();

        datos.forEach(dib => {
            let dibujo = crearDibujo(dib);
            let seccion;

            if(dib.SUBCATEGORIA == "REGALOS"){
                seccion = document.querySelector("#sregalos");
                seccion.appendChild(dibujo);
            }else if(dib.CATEGORIA == "Tradicional"){
                switch (dib.TEMATICA) {
                    case "Jujutsu Kaisen": {
                        seccion = document.querySelector("#sjk");
                        seccion.appendChild(dibujo);
                        break;}
                    case "The Promised Neverland": {
                        seccion = document.querySelector("#stpn");
                        seccion.appendChild(dibujo);
                        break;}
                    case "Blue Lock": {
                        seccion = document.querySelector("#sbl");
                        seccion.appendChild(dibujo);
                        break;}
                    case "Inazuma Eleven": {
                        seccion = document.querySelector("#sie");
                        seccion.appendChild(dibujo);
                        break;}
                    case "Naruto": {
                        seccion = document.querySelector("#snaruto");
                        seccion.appendChild(dibujo);
                        break;}
                }
            } else{
                switch (dib.SUBCATEGORIA) {
                    case "ANIME": {
                        seccion = document.querySelector("#sanime");
                        seccion.appendChild(dibujo);
                        break;}
                    case "LOGOS": {
                        seccion = document.querySelector("#slogos");
                        seccion.appendChild(dibujo);
                        break;}
                    case "BANDERAS": {
                        seccion = document.querySelector("#sbanderas");
                        seccion.appendChild(dibujo);
                        break;}
                    case "PERSONAS": {
                        seccion = document.querySelector("#spersonas");
                        seccion.appendChild(dibujo);
                        break;}
                    case "VARIOS": {
                        seccion = document.querySelector("#svarios");
                        seccion.appendChild(dibujo);
                        break;}
                }
            }
            
        });
    }catch(error){
        console.error("Error en el Fetch:", error);
        let p = document.createElement("p");
        p.innerHTML = "Error al cargar los dibujos. Inténtelo más tarde.";
        p.style.textDecoration = underline;
        document.querySelector(".content").innerHTML = "";
        document.querySelector(".content").appendChild(p);
    }
}

function crearDibujo(dibujojson){
    let articulo = document.createElement("article");

    let img = document.createElement("img");
    img.classList.add("photo");
    img.alt = dibujojson.TITULO;
    img.src = `/files/${dibujojson.IMAGEN}`;
    articulo.appendChild(img);

    let info = document.createElement("div");
    info.classList.add("info");

    let titulo = document.createElement("h4");
    titulo.innerHTML = dibujojson.TITULO;
    info.appendChild(titulo);

    let descripcion = document.createElement("p");
    descripcion.innerHTML = dibujojson.DESCRIPCION;
    info.appendChild(descripcion);

    let precio = document.createElement("p");
    precio.classList.add("cost");
    precio.innerHTML = `Precio: ${dibujojson.PRECIO}€`;
    info.appendChild(precio);

    articulo.appendChild(info);

    return articulo;
}

function vaciarSecciones(){
    let seccion, titulo;

    // Digital
    seccion = document.querySelector("#sanime");
        seccion.innerHTML = "";
        titulo = document.createElement("h3");
        titulo.innerText = "Anime";
        titulo.id = "anime";
        seccion.appendChild(titulo);

    seccion = document.querySelector("#slogos");
        seccion.innerHTML = "";
        titulo = document.createElement("h3");
        titulo.innerText = "Logos";
        titulo.id = "logos";
        seccion.appendChild(titulo);

    seccion = document.querySelector("#sbanderas");
        seccion.innerHTML = "";
        titulo = document.createElement("h3");
        titulo.innerText = "Banderas";
        titulo.id = "banderas";
        seccion.appendChild(titulo);

    seccion = document.querySelector("#spersonas");
        seccion.innerHTML = "";
        titulo = document.createElement("h3");
        titulo.innerText = "Personas";
        titulo.id = "personas";
        seccion.appendChild(titulo);

    seccion = document.querySelector("#svarios");
        seccion.innerHTML = "";
        titulo = document.createElement("h3");
        titulo.innerText = "Varios";
        titulo.id = "varios";
        seccion.appendChild(titulo);

    //Tradicional
    seccion = document.querySelector("#sjk");
        seccion.innerHTML = "";
        titulo = document.createElement("h3");
        titulo.innerText = "Jujutsu Kaisen";
        titulo.id = "jk";
        seccion.appendChild(titulo);

    seccion = document.querySelector("#stpn");
        seccion.innerHTML = "";
        titulo = document.createElement("h3");
        titulo.innerText = "The Promised Neverland";
        titulo.id = "tpn";
        seccion.appendChild(titulo);

    seccion = document.querySelector("#sbl");
        seccion.innerHTML = "";
        titulo = document.createElement("h3");
        titulo.innerText = "Blue Lock";
        titulo.id = "bl";
        seccion.appendChild(titulo);

    seccion = document.querySelector("#sie");
        seccion.innerHTML = "";
        titulo = document.createElement("h3");
        titulo.innerText = "Inazuma Eleven";
        titulo.id = "ie";
        seccion.appendChild(titulo);

    seccion = document.querySelector("#snaruto");
        seccion.innerHTML = "";
        titulo = document.createElement("h3");
        titulo.innerText = "Naruto";
        titulo.id = "naruto";
        seccion.appendChild(titulo);

    //Regalos
    seccion = document.querySelector("#sregalos");
        seccion.innerHTML = "";
        titulo = document.createElement("h3");
        titulo.innerText = "Regalos";
        titulo.id = "regalos";
        let adver = document.createElement("p");
        adver.innerText = "Los dibujos mostrados en este apartado no están disponibles para pedir prints."
        seccion.appendChild(titulo);
        seccion.appendChild(adver);
}

document.addEventListener("DOMContentLoaded", cargaDibujos);