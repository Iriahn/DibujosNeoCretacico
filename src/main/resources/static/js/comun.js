// Añadir el año actual al pie de página
function drawFooterYear() {
  document.querySelector("#year").innerText = new Date().getFullYear();
}

drawFooterYear();

/* Añadir o eliminar clase con efecto hover al icono de perfil para que cambie la 
  imagen con al pasar el cursor  */
let usericon = document.querySelector("#usericon");
let desplegable = document.querySelector(".dropdown-content");

function addhoversession(){
  usericon.classList.add("usericonhover");
  desplegable.classList.remove("ocultar");
}
function removehoversession(){
  usericon.classList.remove("usericonhover");
  desplegable.classList.add("ocultar");
}
function hoversessionQuery(){
  if(usericon.classList.contains("usericonhover")){
    usericon.classList.remove("usericonhover");
    desplegable.classList.add("ocultar");
  }
  else{
    usericon.classList.add("usericonhover");
    desplegable.classList.remove("ocultar");
  }
}

let element = document.querySelector(".dropdown");

if(window.innerWidth < 760){
  element.addEventListener("click", hoversessionQuery);
}
else{
  element.addEventListener("mouseover", addhoversession);
  element.addEventListener("mouseout", removehoversession);
}