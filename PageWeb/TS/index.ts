const url = "ws://localhost:8025";
const socket = new WebSocket(url);
socket.onopen = () => {
    console.log("Connexion établie !");

};

socket.onmessage = (event) => {
    console.log(event.data);
};

socket.onclose = () => {
    console.log("Connexion fermée !");
};
//cette structure de données est utilisée pour envoyer des messages au serveur java
//elle sera vide pour l'instant
const message_a_envoyer= {

};
//fonction d'usage lorsque le bouton est cliqué dessus. Nous envoyons le contenu de la boîte de texte par websokets
//vers notre serveur java
//@ts-ignore
let recupEntree = (message_a_envoyer)=>{
    const entree = document.getElementById("entree") as HTMLInputElement;
    message_a_envoyer["entrees"]=entree.value;
    entree.value ="";
}

window.addEventListener('DOMContentLoaded', () => {
    const boutonSubmit = document.getElementById("envoi") as HTMLInputElement;
    const fichier = document.getElementById("fichier") as HTMLInputElement;
    //le bouton cliqué, le contenu doit être envoyé au serveur
    //TODO: traiter le cas où le fichier est null ou invalide
    boutonSubmit.onclick = () => {
        console.log("chargement conclu");
        const fr = new FileReader();
        //erreur normale, ça compile toujours. On traitera les cas null plus tard
        //lecture du fichier saisie par l'utilisateur
        //@ts-ignore
        fr.readAsText(fichier.files[0]);
        fr.onload=function(){
            //@ts-ignore
            message_a_envoyer["fichier"]=fr.result;
            //OBLIGATOIRE D'APPELER LA FONCTION ICI, SINON LE FICHIER N'EST PAS CHARGÉ
            recupEntree(message_a_envoyer);
            socket.send(JSON.stringify(message_a_envoyer));
        }
        console.log(message_a_envoyer);
    }
    console.log(boutonSubmit);
});
