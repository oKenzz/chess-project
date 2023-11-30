import '../styles/GamePage.css';
import Chess from "../components/Chess";
import { motion } from 'framer-motion';
import LeftSidebar from '../components/LeftSidebar';
import CustomNavbar from '../components/CustomNavbar';
import { SocketClient } from '../socket/Client'; // Adjust the import path as necessary
import { useEffect, useRef, useState } from 'react';
import { FEN } from '../constants/types';
import { BoardPosition, type BoardOrientation, type ChessboardProps, type Piece, Square } from "react-chessboard/dist/chessboard/types";
import { Socket } from 'socket.io-client';
import { Alert } from 'flowbite-react';
import { HiInformationCircle } from 'react-icons/hi';

type Player = { 
    timer: number;
    uuid: string;
}
type GameStateResponse = {
    id: string;
    gameCreatedAt: number;
    fen: string;
    turn: string;
    playerColor: BoardOrientation;
    players:  Player[] | null[];
}

const MultiPlayerGame = () => {
    const [fen, setFen] = useState<FEN>('start');
    const socketRef = useRef<Socket | null>(null); // Create a socket ref as a mutable variable
    const [color, setColor] = useState<BoardOrientation>('white'); // Create a state for the color of the player
    const [squareStyles, setSquareStyles] = useState({});
    const [roomCode, setRoomCode] = useState<string>('');
    const [warning, setWarning] = useState<string | null>(null);
    const [successMessage, setSuccessMessage] = useState<string | null>(null);
    const [pieceSquare, setPieceSquare] = useState<Square | null>(null);
    const [positions, setPositions] = useState<BoardPosition>({});

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
            const JSONgameState = JSON.parse(gameState) as GameStateResponse;
            console.log(`Game ID is ${JSONgameState.id}\nGame was created at ${JSONgameState.gameCreatedAt}\nFEN is ${JSONgameState.fen}\nTurn is ${JSONgameState.turn}\nPlayer color is ${JSONgameState.playerColor}\nPlayers are ${JSONgameState.players ? JSONgameState.players.map((player) => player?.uuid) : null}`);
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
            }, 5000);
        }

        const opponentJoinedListener = (hasJoined: boolean) => {
            console.log("Opponent joined");
            setSuccessMessage("Opponent joined");
            setTimeout(() => {
                setSuccessMessage(null);
            }, 5000);
        }

        socketRef.current.emit('getGameState')

        socketRef.current.on('chat', chatListener);
        socketRef.current.on('gameState', gameStateListener);
        socketRef.current.on('boardState', boardListener);
        socketRef.current.on('playerJoined', opponentJoinedListener);
        socketRef.current.on('playerDisconnected', opponentDisconnectedListener);

        return () => {
            socketClient.disconnect();
        };
    }, []); // Empty dependency array for setup on mount and cleanup on unmount
    

    const movePiece: ChessboardProps['onPieceDrop'] = (sourceSquare, targetSquare, piece) => {
        setSquareStyles({});
        // Use the socket ref here
        return sendPieceMovement(sourceSquare, targetSquare)
    };

    const movePieceOnClick: ChessboardProps['onSquareClick'] = (square) => {
        if (square !== pieceSquare){
            setSquareStyles({}) // Handle remove in a better way
        }
        console.log("Square pressed: " + square)
        const piece : Piece | undefined = positions[square]
        // Select Piece
        if(piece !== undefined && piece[0] === color[0]){
            addHighlightSquare(square.toString());
            setPieceSquare(square)
        } else {
            // Move Piece
            const sourceSquare : string | undefined = pieceSquare?.toString()
            const targetSquare : string = square.toString()
            sendPieceMovement(sourceSquare, targetSquare)
        }

    }

    const IsDraggablePiece: ChessboardProps['isDraggablePiece'] = ({ piece, sourceSquare }) => {
        // ex, piece = 'wP', color = 'white'
        // return true if piece[0] === color[0] (w === w)
        return piece[0] === color[0];
    };
    
    const highlightSquare: ChessboardProps['onPieceDragBegin'] = (piece, sourceSquare) => {
        addHighlightSquare(sourceSquare)
    };

    const removeHighlightSquare: ChessboardProps['onPieceDragEnd'] = (piece, sourceSquare) => {
        setSquareStyles({})
    }


    const getPositionObject: ChessboardProps['getPositionObject'] = (currentPosition) => {
        console.log(currentPosition)
        setPositions(currentPosition)
    }

    const addHighlightSquare = (square: string) => {
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

            setSquareStyles(highlightStyles);
        });
    }

    const sendPieceMovement = (sourceSquare : string | undefined, targetSquare: string) : boolean => {
        const moved_pos = JSON.stringify({
            from: sourceSquare,
            to: targetSquare,
        });
        console.log(`Moved piece from ${sourceSquare} to ${targetSquare}`);
        console.log(`Sending message: ${moved_pos}`);
        socketRef.current?.emit('move', moved_pos , (success: boolean) => {
            if (success) {
                console.log("Move successful");
                return true;
            }
            console.log("Move unsuccessful");
            return false;
        });
        return false;
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
                    arePremovesAllowed={true}   
                    customSquareStyles={squareStyles}
                    onPieceDrop={movePiece}
                    onSquareClick={movePieceOnClick}
                    isDraggablePiece={IsDraggablePiece}
                    onPieceDragBegin={highlightSquare}
                    onPieceDragEnd={removeHighlightSquare}
                    getPositionObject={getPositionObject}
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
