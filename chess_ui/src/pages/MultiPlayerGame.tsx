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
import { useSearchParams } from 'react-router-dom';
import { alertMessage, FEN } from '../constants/types';
import GameOver from '../components/GameOver';
import WaitingScreen from '../components/WatingScreen';
import * as socketListeners from '../socket/socketListeners';
import JoiningGameScreen from '../components/JoiningGameScreen';
import RightSidebar from '../components/RightSidebar';
import Chat from '../components/chat/Chat';

const MultiPlayerGame = () => {
    const [fen, setFen] = useState<FEN>('start');
    const socketRef = useRef<Socket | null>(null); // Create a socket ref as a mutable variable
    const [color, setColor] = useState<BoardOrientation>('white'); // Create a state for the color of the player
    const [roomCode, setRoomCode] = useState<string | null>('');
    const [alertMessage, setAlertMessage] = useState<alertMessage | null>(null);
    const [opponentIsReady, setOpponentIsReady] = useState<boolean>(false); 
    const [showJoiningGame, setShowJoiningGame] = useState<boolean>(true);
    const [isGameOver, setIsGameOver] = useState<boolean>(false); 
    const [gameOverMessage, setGameOverMessage] = useState<string>("");
    const [searchParams] = useSearchParams();
    const [isLoading, setLoading] = useState<boolean>(true);
    const [initialTime, setInitialTime] = useState<{ white: number, black: number}>( { white: 600, black: 600} );


    useEffect(() => {
        // Get the room code from the URL
        const roomCodeFromURL = searchParams.get("room");

        // const socketClient = SocketClient.getInstance();
        const socketClient = new SocketClient(roomCodeFromURL);
        socketClient.connect();
        socketRef.current = socketClient.getSocket();
        

        socketRef.current.emit('getGameState')

        // Setup socket listeners
        socketRef.current.on('chat', socketListeners.chatListener);
        socketRef.current.on('gameState', socketListeners.gameStateListener({setColor, setFen, setRoomCode, setOpponentIsReady,setLoading, setInitialTime}));
        socketRef.current.on('boardState', socketListeners.boardListener(setFen));
        socketRef.current.on('playerJoined', socketListeners.opponentJoinedListener({setAlertMessage}, setOpponentIsReady));
        socketRef.current.on('playerDisconnected', socketListeners.opponentDisconnectedListener({setAlertMessage}));
        socketRef.current.on('gameOver', socketListeners.gameOverListener(setIsGameOver, setGameOverMessage));
        
        return () => {
            // Cleanup socket listeners
            socketRef.current?.emit('surrender');
            socketClient.disconnect();
        }
    }, [searchParams]); // Empty dependency array for setup on mount and cleanup on unmount

    useEffect(() => {
        const url = new URL(window.location.href);
        url.searchParams.set('room', roomCode || '');
        window.history.replaceState({}, '', url.href);
        
    }, [roomCode]);

    useEffect(() => {
        if (isLoading && !opponentIsReady && !socketRef.current) {
            // Only set up polling if necessary (i.e., loading and opponent not ready, and no socket connection)
            const gameStateInterval = setInterval(() => {
                socketRef.current?.emit('getGameState');
            }, 3000); // Adjust interval as needed
    
            return () => clearInterval(gameStateInterval); // Clear interval on cleanup
        }
    }, [isLoading, opponentIsReady]); // Dependencies to control the effect
    
    // Joining game animation
    useEffect(() => {
        if (opponentIsReady) {
            setShowJoiningGame(true);
            const timer = setTimeout(() => {
                setShowJoiningGame(false);
            }, 2000); // Hide message after 3 seconds
            return () => clearTimeout(timer); // Clear timer on cleanup
        }
    }, [opponentIsReady]); // Effect runs when opponentIsReady changes

    return (
        isLoading && !opponentIsReady ? (
            <WaitingScreen roomCode={roomCode || ""} />
        ) : (
            <div className="game-grid" >
                {/* Headers */}
                <CustomNavbar  
                    roomCode={ roomCode }
                />
            
                
                <motion.div initial={{ opacity: 0}} animate={{  opacity: 1 , transition: { duration: 1, delay: 0.5}}} className="game-panel">
                    {
                        socketRef.current  &&
                        <Chess
                            fen={fen}
                            color={color} 
                            socket={socketRef.current}
                        />
                    }
            
                </motion.div>

                <motion.div className="left-panel">
                    <LeftSidebar  
                        isMultiplayer={true}
                        socket={socketRef.current}
                    />
                </motion.div>
                
                {
                    socketRef.current  &&
                    <RightSidebar 
                        color={color}
                        turn={fen[fen.length - 1]}
                        initTime={initialTime}
                        socket={socketRef.current}
                    />
                }

               {
                     socketRef.current &&
                     <Chat socket={socketRef.current} />
                }

                <Alert color={alertMessage?.type}
                    icon={HiInformationCircle} 
                    style={{display: alertMessage ? 'block' : 'none', position: "absolute", bottom: 10, right: 10}}
                    onDismiss={() =>  setAlertMessage(null)}
                >
                    <span className="font-medium">{ alertMessage?.message } </span>
                </Alert>
                {
                    /* Waiting for opponent screen */
                    !opponentIsReady &&
                    <WaitingScreen roomCode={roomCode || ""} />
                }
                {
                    /* Joining game screen */
                    showJoiningGame &&
                    <JoiningGameScreen />
                }

                {
                    /* Gameover screen */
                    isGameOver &&
                    <GameOver gameOverMessage={gameOverMessage}/>
                }
            </div>
        )
    );
};

export default MultiPlayerGame;