import '../styles/GamePage.css';
import Chess from "../components/Chess";
import { motion } from 'framer-motion';
import LeftSidebar from '../components/LeftSidebar';
import CustomNavbar from '../components/CustomNavbar';
import { SocketClient } from '../socket/Client'; // Adjust the import path as necessary
import { useEffect, useRef, useState } from 'react';
import { type BoardOrientation } from "react-chessboard/dist/chessboard/types";
import { Socket } from 'socket.io-client';
import { Alert } from 'flowbite-react';
import { HiInformationCircle } from 'react-icons/hi';
import { alertMessage, FEN } from '../constants/types';
import GameOver from '../components/GameOver';
import * as socketListeners from '../socket/socketListeners';

const SinglePlayerGame = () => {
    const [fen, setFen] = useState<FEN>('start');
    const socketRef = useRef<Socket | null>(null); // Create a socket ref as a mutable variable
    const [color, setColor] = useState<BoardOrientation>('white'); // Create a state for the color of the player
    const [roomCode, setRoomCode] = useState<string | null>('');
    const [alertMessage, setAlertMessage] = useState<alertMessage | null>(null);
    const [isGameOver, setIsGameOver] = useState<boolean>(false); 
    const [gameOverMessage, setGameOverMessage] = useState<string>("");

    useEffect(() => {
        // Get the room code from the URL
        const socketClient = new SocketClient('singlePlayer');
        socketClient.connect();
        socketRef.current = socketClient.getSocket();
        
        // Get game state from server
        socketRef.current.emit('getGameState')

        // Setup socket listeners
        socketRef.current.on('chat', socketListeners.chatListener);
        socketRef.current.on('gameState', socketListeners.gameStateListener({setColor, setFen, setRoomCode}));
        socketRef.current.on('boardState', socketListeners.boardListener(setFen));
        socketRef.current.on('playerJoined', socketListeners.opponentJoinedListener({setAlertMessage}));
        socketRef.current.on('playerDisconnected', socketListeners.opponentDisconnectedListener({setAlertMessage}));
        socketRef.current.on('gameOver', socketListeners.gameOverListener(setIsGameOver, setGameOverMessage));
 
    }, []);

    return (
        <div className="game-grid" >
            {/* Headers */}
            <CustomNavbar  
                roomCode={ roomCode }
                disableRoomcode={true}
            />
            
            <motion.div initial={{ opacity: 0}} animate={{  opacity: 1 , transition: { duration: 1, delay: 0.5}}} className="game-panel">
                {
                    socketRef.current  &&
                    <Chess
                        isSinglePlayer={true}
                        fen={fen}
                        color={color} 
                        socket={socketRef.current}
                    />
                }
            </motion.div>

            <motion.div className="left-panel">
                <LeftSidebar isMultiplayer={false}/>
            </motion.div>
            <motion.div className="right-panel"></motion.div>

            <Alert color={alertMessage?.type}
                 icon={HiInformationCircle} 
                style={{display: alertMessage ? 'block' : 'none', position: "absolute", bottom: 10, right: 10}}
                onDismiss={() =>  setAlertMessage(null)}
            >
                <span className="font-medium">{ alertMessage?.message } </span>
            </Alert>

            {
                /* Gameover screen */
                isGameOver &&
                <GameOver gameOverMessage={gameOverMessage}/>
            }
        </div>
    );
};

export default SinglePlayerGame;
