import { Chessboard } from "react-chessboard";
import type { BoardPosition, ChessboardProps, Piece } from "react-chessboard/dist/chessboard/types";
import { BoardOrientation } from "react-chessboard/dist/chessboard/types";
import CustomPieces from "../config/CustomPieces";
import { useState } from "react";
import { Socket } from "socket.io-client";
import { useSelector } from "react-redux";
import { ChessTheme } from "../constants/theme";
import { FEN } from "../constants/types";

interface ChessProps extends Omit<ChessboardProps, 'ref'> {
    isSinglePlayer?: boolean;
    fen: FEN;
    color: BoardOrientation;
    socket: Socket;
}

const Chess = ({ isSinglePlayer , fen , color, socket, ...otherProps }: ChessProps) => {
    
    const [squareStyles, setSquareStyles] = useState({});
    const [positions, setPositions] = useState<BoardPosition>({});
    const [pieceSquare, setPieceSquare] = useState<string | undefined>(undefined);
    const themeSettings = useSelector((state: any) => state.theme) as { theme: ChessTheme };   

    const movePiece: ChessboardProps['onPieceDrop'] = (sourceSquare, targetSquare, piece) => {
        setSquareStyles({});
        // Use the socket ref here
        sendPieceMovement(sourceSquare, targetSquare).then((hasPiecesMoved) => {
            if (hasPiecesMoved && isSinglePlayer) {

                // random delay to simulate computer thinking
                const delay = Math.floor(Math.random() * 1000) + 600;
                setTimeout(() => {
                    socket?.emit('computerMove');
                }, delay);
            }
            return hasPiecesMoved;
        });
        return false;
    };

    const movePieceOnClick: ChessboardProps['onSquareClick'] = (square) => {
        if (square !== pieceSquare){
            setSquareStyles({}) // Handle remove in a better way
        }
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
        return piece[0] === color[0];
    };
    
    const highlightSquare: ChessboardProps['onPieceDragBegin'] = (piece, sourceSquare) => {
        addHighlightSquare(sourceSquare)
    };

    const removeHighlightSquare: ChessboardProps['onPieceDragEnd'] = (piece, sourceSquare) => {
        setSquareStyles({})
    }


    const getPositionObject: ChessboardProps['getPositionObject'] = (currentPosition) => {
        setPositions(currentPosition)
    }

    const addHighlightSquare = (square: string) => { 
        
        socket?.emit("getPossibleMoves", square, (possibleMoves: string[]) => {
            const highlightStyles = possibleMoves.reduce((styles, move) => {
                return { 
                    ...styles,
                    [move]: {
                        background: "radial-gradient(circle at center, rgba(255, 255, 0, 0.9) 26%, transparent 35%)",
                        borderRadius: "50%",
                    }
                };
            }, {});

            setSquareStyles(highlightStyles);
        });
    }

    const sendPieceMovement = (sourceSquare: string | undefined, targetSquare: string) => {
        const moved_pos = JSON.stringify({
            from: sourceSquare,
            to: targetSquare,
        });
    
        return new Promise((resolve, reject) => {
            socket?.emit('move', moved_pos, (success: boolean) => {
                    resolve(success);
                });
        });
    };
    
    
    return (
        <Chessboard 
            id="BasicBoard"
            position={fen}
            boardOrientation={color}
            arePremovesAllowed={true} 
            customDarkSquareStyle={{backgroundColor:  themeSettings.theme.customDarkSquareStyle}}
            customLightSquareStyle={{backgroundColor: themeSettings.theme.customLightSquareStyle}}
            customPremoveDarkSquareStyle={{backgroundColor:  themeSettings.theme.customPremoveDarkSquareStyle}}
            customPremoveLightSquareStyle={{backgroundColor: themeSettings.theme.customPremoveLightSquareStyle}}
            showPromotionDialog={true}
            customPieces={CustomPieces}
            onPieceDrop={movePiece}
            onPieceDragBegin={highlightSquare}
            onPieceDragEnd={removeHighlightSquare}
            onSquareClick={movePieceOnClick}
            isDraggablePiece={IsDraggablePiece}
            getPositionObject={getPositionObject}
            customSquareStyles={squareStyles}
            autoPromoteToQueen={true}
            {...otherProps}
        />
    );
};

export default Chess;
