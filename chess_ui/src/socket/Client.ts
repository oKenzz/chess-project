import { io, Socket } from 'socket.io-client';

const URL = process.env.REACT_APP_SOCKET_URL || 'http://localhost:15025';

export class SocketClient {
    private static instance: SocketClient;
    private socket: Socket;

    private constructor() {
        this.socket = io(URL, {
            reconnection: true,
            reconnectionAttempts: 3,
            reconnectionDelay: 2000,
        });

        // Event listeners
        this.socket.on('connect_error', (error) => {
            console.error('Socket connection error:', error);
            alert('Unable to connect to the server. Please check your network connection.');
        });

        this.socket.on('connect', () => {
            console.log('Connected to the server.');
        });

        this.socket.on('disconnect', (reason) => {
            console.log(`Socket disconnected: ${reason}`);
        });

        this.socket.on('reconnect_failed', () => {
            console.error('Failed to reconnect to the server.');
            alert('Failed to reconnect to the server after multiple attempts.');
        });
    }

    public static getInstance(): SocketClient {
        if (!SocketClient.instance) {
            SocketClient.instance = new SocketClient();
        }
        return SocketClient.instance;
    }

    public connect() {
        console.log('Initiating connection...');
        this.socket.connect();
    }

    public disconnect() {
        console.log('Disconnecting...');
        this.socket.disconnect();
    }

    public getSocket(): Socket {
        return this.socket;
    }
}

export default SocketClient;
