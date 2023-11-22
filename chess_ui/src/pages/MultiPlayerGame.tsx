import '../styles/GamePage.css';
import Chess from "../components/Chess";
import { motion } from 'framer-motion';
import LeftSidebar from '../components/LeftSidebar';
import CustomNavbar from '../components/CustomNavbar';
import { SocketClient } from '../socket/Client'; // Adjust the import path as necessary
import { useEffect, useRef, useState } from 'react';
import { FEN } from '../constants/types';
import type { ChessboardProps } from "react-chessboard/dist/chessboard/types";
import { Socket } from 'socket.io-client';
import { Chess as ChessGame }  from 'chess.js';



const MultiPlayerGame = () => {
    const [fen, setFen] = useState<FEN>('start');
    const socketRef = useRef<Socket | null>(null); // Create a socket ref as a mutable variable
    const game = useRef(new ChessGame());

    useEffect(() => {
        const socketClient = SocketClient.getInstance();
        socketClient.connect();
        socketRef.current = socketClient.getSocket(); // Update the ref with the socket instance


        const chatListener = (data: string) => {
            console.log(`Received message: ${data}`);
        };

        const gameStateListener = (data: FEN) => { 
            if (data.length > 0 || data !== null) {
                console.log(`Received game state: ${data}`);
                setFen(data);
                game.current.load(data);
            }
        };


        socketRef.current.on('chat', chatListener);
        socketRef.current.on('gameState', gameStateListener);

        return () => {
            socketClient.disconnect();
        };
    }, []); 

    const movePiece: ChessboardProps['onPieceDrop'] = (sourceSquare, targetSquare, piece) => {
        // Use the socket ref here
        if (socketRef.current) {
            const moved_pos = `${sourceSquare}-${targetSquare}`;
            console.log(`Moved piece from ${sourceSquare} to ${targetSquare}`);
            console.log(`Sending message: ${moved_pos}`);
            socketRef.current.emit('move', moved_pos , (success: boolean) => {
                if (success) {
                    console.log("Move successful");
                    return true;
                }
                console.log("Move unsuccessful");
                return false;
            });

            return true;
        }

        console.error("Socket is null");
        return false;
    };

    const testMovePiece: ChessboardProps['onPieceDrop'] = (sourceSquare, targetSquare, piece) => {
        if (socketRef.current) {
            // Attempt the move with chess.js
            try{
                const move = game.current.move({
                    from: sourceSquare,
                    to: targetSquare,
                    promotion: 'q' // Default to queen for promotion
                });
    
                if (move === null) return false; // invalid move
                const newFen = game.current.fen();
                setFen(newFen);
    
                // Emit the new FEN string to the server
                socketRef.current.emit('newGamePosition', newFen);
                return true;
            }
            catch (err) {
                console.log(err);
                return false;
            }

        }

        console.error("Socket is null");
        return false;
    };
    return (
        <div className="game-grid" >
            {/* Headers */}
            <CustomNavbar  />
            {/* <Header /> */}
            <motion.div
                initial={{  opacity: 0}
            }
                animate={{  opacity: 1 , transition: { duration: 1, delay: 0.5}}}
                className="game-panel"
            >
                <Chess 
                    color='white' 
                    position={fen}
                    onPieceDrop={testMovePiece}
                />
            </motion.div>

            <motion.div className="left-panel">
                <LeftSidebar />
            </motion.div>
            <motion.div className="right-panel"></motion.div>
        </div>
    );
};

export default MultiPlayerGame;
