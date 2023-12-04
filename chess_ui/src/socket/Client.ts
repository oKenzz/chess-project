import { io, Socket } from 'socket.io-client';

const URL = process.env.REACT_APP_SOCKET_URL || 'http://localhost:15025';

export class SocketClient {
    private socket: Socket;
    private roomCode: String | null;

    public constructor(roomCode: String | null) {
        this.socket = io(URL, {
            reconnection: true,
            reconnectionAttempts: 3,
            reconnectionDelay: 2000,
            query: {
                room: this.roomCode = roomCode
            },
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
