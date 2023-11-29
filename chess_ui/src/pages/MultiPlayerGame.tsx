import '../styles/GamePage.css';
import Chess from "../components/Chess";
import { motion } from 'framer-motion';
import LeftSidebar from '../components/LeftSidebar';
import CustomNavbar from '../components/CustomNavbar';
import { SocketClient } from '../socket/Client'; // Adjust the import path as necessary
import { useEffect, useRef, useState } from 'react';
import { FEN } from '../constants/types';
import type { BoardOrientation, ChessboardProps, Piece } from "react-chessboard/dist/chessboard/types";
import { Socket } from 'socket.io-client';
import { Alert } from 'flowbite-react';
import { HiInformationCircle } from 'react-icons/hi';


const MultiPlayerGame = () => {
    const [fen, setFen] = useState<FEN>('start');
    const socketRef = useRef<Socket | null>(null); // Create a socket ref as a mutable variable
    const [color, setColor] = useState<BoardOrientation>('white'); // Create a state for the color of the player
    const [squareStyles, setSquareStyles] = useState({});
    const [roomCode, setRoomCode] = useState<string>('');
    const [warning, setWarning] = useState<string | null>(null);
    const [successMessage, setSuccessMessage] = useState<string | null>(null);
    const [piece, setPiece] = useState<Piece | null>(null);

    useEffect(() => {
        const socketClient = SocketClient.getInstance();
        socketClient.connect();
        socketRef.current = socketClient.getSocket();
        // Get the initial game state
        
        const chatListener = (data: string) => {
            console.log(`Received message: ${data}`);
        };
    
        const boardListener = (fenString: string) => {
            console.log(`Received fen: ${fenString}`);
            setFen(fenString);
        }
        const gameStateListener = (gameState: string) => {
            const JSONgameState = JSON.parse(gameState)
            console.log(`Game Code: ${JSONgameState.id} \nGame State: ${JSONgameState.fen} \nPlayer Color: ${JSONgameState.playerColor}`);
            setColor(JSONgameState.playerColor)
            setFen(JSONgameState.fen)
            setRoomCode(JSONgameState.id)
        };
        const opponentDisconnectedListener = () => {
            console.log("Opponent disconnected");
            socketRef.current?.emit('getGameState');
            setWarning("Opponent disconnected ");
            setTimeout(() => {
                setWarning(null);
            }, 3000);
        }

        const opponentJoinedListener = () => {
            console.log("Opponent joined");
            setSuccessMessage("Opponent joined");
            setTimeout(() => {
                setSuccessMessage(null);
            }, 3000);
        }

        socketRef.current.emit('getGameState')
        socketRef.current.on('chat', chatListener);
        socketRef.current.on('gameState', gameStateListener);
        socketRef.current.on('boardState', boardListener);
        socketRef.current.on('playerDisconnected', opponentDisconnectedListener);
        socketRef.current.on('playerJoined', opponentJoinedListener);

        return () => {
            socketClient.disconnect();
        };
    }, []); // Empty dependency array for setup on mount and cleanup on unmount
    

    const movePiece: ChessboardProps['onPieceDrop'] = (sourceSquare, targetSquare, piece) => {
        setSquareStyles({});
        // Use the socket ref here
        if (socketRef.current) {
            const moved_pos = JSON.stringify({
                from: sourceSquare,
                to: targetSquare,
            });
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
        }
        return false;
    };


    const IsDraggablePiece: ChessboardProps['isDraggablePiece'] = ({ piece, sourceSquare }) => {
        // ex, piece = 'wP', color = 'white'
        // return true if piece[0] === color[0] (w === w)
        return piece[0] === color[0];
    };
    
    const highlightSquare: ChessboardProps['onPieceDragBegin'] = (piece, sourceSquare) => {
        socketRef.current?.emit("getPossibleMoves", sourceSquare, (possibleMoves: string[]) => {
            const highlightStyles = possibleMoves.reduce((styles, move) => {
                return { 
                    ...styles,
                    [move]: {
                        background: "radial-gradient(circle at center, rgba(255, 255, 0, 1) 25%, transparent 30%)",
                        borderRadius: "50%",
                    }
                };
            }, {});
    
            setSquareStyles(highlightStyles);
        });
    };

    const removeHighlightSquare: ChessboardProps['onPieceDragEnd'] = (piece, sourceSquare) => {
        setSquareStyles({})
    }

    const highlightOnPieceClick: ChessboardProps['onSquareClick'] = (square) => {
        socketRef.current?.emit("getPossibleMoves", square, (possibleMoves: string[]) => {
            const highlightStyles = possibleMoves.reduce((styles, move) => {
                return { 
                    ...styles,
                    [move]: {
                        background: "radial-gradient(circle at center, rgba(255, 255, 0, 1) 25%, transparent 30%)",
                        borderRadius: "50%",
                    }
                };
            }, {});
    
            if(piece == null){
                setSquareStyles(highlightStyles);
            } else {
                
            }
        });
    }
    
    
    return (
        <div className="game-grid" >
            {/* Headers */}
            <CustomNavbar  
                roomCode={roomCode}
            />
            {/* <Header /> */}
            <motion.div
                initial={{  opacity: 0}
            }
                animate={{  opacity: 1 , transition: { duration: 1, delay: 0.5}}}
                className="game-panel"
            >
                <Chess 
                    color={color} 
                    position={fen}
                    onPieceDrop={movePiece}
                    onPieceDragBegin={highlightSquare}
                    onPieceDragEnd={removeHighlightSquare}
                    isDraggablePiece={IsDraggablePiece}
                    customSquareStyles={squareStyles}
                    onSquareClick={highlightOnPieceClick}
                />
            </motion.div>

            <motion.div className="left-panel">
                <LeftSidebar />
            </motion.div>
            <motion.div className="right-panel"></motion.div>
            <Alert color="failure" icon={HiInformationCircle} 
                style={{display: warning ? 'block' : 'none', position: "absolute", bottom: 10, right: 10}}
                onDismiss={() => setWarning(null)}     
            >
                <span className="font-medium">{warning} </span>
            </Alert>

            <Alert color="success" icon={HiInformationCircle} 
                style={{display: successMessage ? 'block' : 'none', position: "absolute", bottom: 10, right: 10}}
                onDismiss={() => setWarning(null)}
            >
                <span className="font-medium">{ successMessage } </span>
            </Alert>
        </div>
    );
};

export default MultiPlayerGame;
