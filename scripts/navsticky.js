// Hacer menú sticky sin depender de la cabecera
let menu = document.querySelector("#arriba");
let posicionMenu = document.querySelector("#cabecera").offsetHeight;

function menuSticky(){
  if(window.scrollY >= posicionMenu){
    menu.classList.add("navbarflotante");
    document.querySelector("main").classList.add("espacio");
  }
  else{
    menu.classList.remove("navbarflotante");
    document.querySelector("main").classList.remove("espacio");
  }
}

window.addEventListener("scroll", menuSticky);