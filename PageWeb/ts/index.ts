class WebsocketClient {
    private socket: WebSocket;

    constructor() {
        this.socket = new WebSocket("ws://localhost:8025");

        // Démarre l'écoute des événements
        this.socket.onopen = () => {
            console.log("Connexion au serveur réussie");
            this.socket.send("Winter 5");

        };

        this.socket.onmessage = (event) => {
            // Décode le message JSON
            let data: any = JSON.parse(event.data);

            // Affiche les données reçues
            console.log(data);
        };
    }

    // Envoie un message au serveur

}


const client = new WebsocketClient();
