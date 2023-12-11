import { BoardOrientation } from "react-chessboard/dist/chessboard/types";

export type FEN = string;
export type move = string; 
export type Player = { 
    isBot: boolean;
    isOccupied: boolean;
    isTimerRunning: boolean;
    startTime: number; // in milliseconds
    timeLeft: number;
    uuid: string;
}
export type GameStateResponse = {
    id: string;
    gameCreatedAt: number;
    fen: string;
    turn: string;
    playerColor: BoardOrientation;
    players:  Player[];
    gameOver: boolean;
}
export type alertMessage = {
    message: string;
    type:  'success' | 'warning' | 'failure' | 'info';
}



export type Message = {
    message: string;
    sentAt: string;
    sender: Player;
}