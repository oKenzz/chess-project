import { io, Socket } from 'socket.io-client';

const URL = process.env.REACT_APP_SOCKET_URL || 'http://localhost:8000';

export class SocketClient {
    private static instance: SocketClient;
    private socket: Socket;
    private reconnectAttempts: number = 0;
    private maxReconnectAttempts: number = 3;
    private shouldReconnect: boolean = true;

    private constructor() {
        this.socket = io(URL, {
            autoConnect: false,
        });

        this.socket.on('connect_error', (error) => {
            console.error('Socket connection error:', error);
            this.attemptReconnect();
        });

        this.socket.on('connect', () => {
            console.log('Connected to the server.');
            this.resetReconnectionParams();
        });

        this.socket.on('disconnect', (reason) => {
            console.log(`Socket disconnected: ${reason}`);
            if (reason !== 'io client disconnect' && this.shouldReconnect) {
                // Attempt to reconnect only if the client didn't initiate the disconnection
                this.attemptReconnect();
            }
        });
    }

    private attemptReconnect() {
        if (this.reconnectAttempts < this.maxReconnectAttempts && this.shouldReconnect) {
            console.log(`Attempting to reconnect... (${this.reconnectAttempts + 1}/${this.maxReconnectAttempts})`);
            this.reconnectAttempts++;
            setTimeout(() => this.socket.connect(), 2000);
        } else {
            console.error('Maximum reconnection attempts reached. No further attempts will be made.');
            this.shouldReconnect = false;
        }
    }

    private resetReconnectionParams() {
        this.reconnectAttempts = 0;
        this.shouldReconnect = true;
    }

    public static getInstance(): SocketClient {
        if (!this.instance) {
            this.instance = new SocketClient();
        }
        return this.instance;
    }

    public connect() {
        this.shouldReconnect = true;
        this.socket.connect();
    }

    public disconnect() {
        this.removeAllListeners(); // Remove all listeners before disconnecting
        this.shouldReconnect = false;
        this.socket.disconnect();
    }

    public removeAllListeners() {
        this.socket.off(); // This removes all listeners attached to the socket
    }
    public getSocket(): Socket {
        return this.socket;
    }
}
