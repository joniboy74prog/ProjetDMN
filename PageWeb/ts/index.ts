class WebsocketClient {
    private socket: WebSocket;

    constructor() {
        this.socket = new WebSocket("ws://localhost:3000");

        // Démarre l'écoute des événements
        this.socket.onopen = () => {
            console.log("Connexion au serveur réussie");
        };

        this.socket.onmessage = (event) => {
            // Décode le message JSON
            let data: any = JSON.parse(event.data);

            // Affiche les données reçues
            console.log(data);
        };
    }

    // Envoie un message au serveur
    public send(message: string) {
        this.socket.send(message);
    }
}


const client = new WebsocketClient();
