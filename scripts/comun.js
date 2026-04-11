// Añadir el año actual al pie de página
function drawFooterYear() {
  document.querySelector("#year").innerText = new Date().getFullYear();
}

/* Añadir o eliminar clase con efecto hover al icono de perfil para que cambie la 
  imagen con al pasar el cursor  */
function addhoversesion(){
  document.querySelector("#usericon").classList.add("usericonhover");
}
function removehoversesion(){
  document.querySelector("#usericon").classList.remove("usericonhover");
}

let element = document.querySelector(".dropdown");
element.addEventListener("mouseover", addhoversesion);
element.addEventListener("mouseout", removehoversesion);

drawFooterYear();