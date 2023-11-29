"use strict";
const url = "ws://localhost:8025";
const socket = new WebSocket(url);
socket.onopen = () => {
    console.log("Connexion établie !");
    socket.send("Winter 5");
};
socket.onmessage = (event) => {
    console.log(event.data);
};
socket.onclose = () => {
    console.log("Connexion fermée !");
};
//fonction d'usage lorsque le bouton est cliqué dessus. Nous envoyons le contenu de la boîte de texte par websokets
//vers notre serveur java
let recupEntree = () => {
    const entree = document.getElementById("entree");
    socket.send(entree.value);
    entree.value = "";
};
// Obtenir le bouton submit
window.addEventListener('DOMContentLoaded', () => {
    const boutonSubmit = document.getElementById("envoi");
    console.log(boutonSubmit);
    boutonSubmit.addEventListener('click', recupEntree);
});
