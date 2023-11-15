import { io, Socket } from 'socket.io-client';

const URL = process.env.SOCKET_URL || 'http://localhost:8000';

export class SocketClient {
    private static instance: SocketClient;
    private socket: Socket;
    private reconnectAttempts: number = 0;
    private maxReconnectAttempts: number = 3;
    private shouldReconnect: boolean = true; // New flag to control reconnection

    private constructor() {
        this.socket = io(URL, {
            autoConnect: false,
            ackTimeout: 10000,
        });

        this.socket.on('connect_error', (error) => {
            console.error('Socket connection error:', error);
            if (this.reconnectAttempts < this.maxReconnectAttempts && this.shouldReconnect) {
                console.log(`Attempting to reconnect... (${this.reconnectAttempts + 1}/${this.maxReconnectAttempts})`);
                this.reconnectAttempts++;
                setTimeout(() => this.socket.connect(), 2000); // Attempt to reconnect after 2 seconds
            } else {
                console.error('Maximum reconnection attempts reached. No further attempts will be made.');
                this.shouldReconnect = false; // Prevent further reconnection attempts
            }
        });

        this.socket.on('connect', () => {
            console.log('Connected to the server.');
            this.reconnectAttempts = 0; // Reset the attempts counter upon successful connection
            this.shouldReconnect = true; // Allow reconnections in future if needed
        });

        this.socket.on('disconnect', (reason) => {
            console.log(`Socket disconnected: ${reason}`);
            if (reason === 'io server disconnect' && this.shouldReconnect) {
                // The server disconnected the socket, attempt to reconnect
                this.socket.connect();
            }
        });
    }

    public static getInstance(): SocketClient {
        if (!this.instance) {
            this.instance = new SocketClient();
        }
        return this.instance;
    }

    public connect() {
        if (this.shouldReconnect) {
            this.socket.connect();
        }
    }

    public disconnect() {
        this.socket.disconnect();
    }

    public getSocket(): Socket {
        return this.socket;
    }
}
