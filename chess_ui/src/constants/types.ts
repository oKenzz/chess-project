import { BoardOrientation } from "react-chessboard/dist/chessboard/types";

export type FEN = string;
export type move = string; 
export type Player = { 
    timer: number;
    uuid: string;
}
export type GameStateResponse = {
    id: string;
    gameCreatedAt: number;
    fen: string;
    turn: string;
    playerColor: BoardOrientation;
    players:  Player[] | null[];
    gameOver: boolean;
}
export type alertMessage = {
    message: string;
    type:  'success' | 'warning' | 'failure' | 'info';
}



