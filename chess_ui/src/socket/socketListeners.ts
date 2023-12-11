import { BoardOrientation } from "react-chessboard/dist/chessboard/types";
import { GameStateResponse } from "../constants/types";
import { alertMessage, FEN } from '../constants/types';

export const chatListener = (data: string) => {
    console.log(`Received message: ${data}`);
};

export const boardListener = ( setFen: (fen: FEN) => void ) => {
    return (fenString: string) => {
        console.log(`Received fen: ${fenString}`);
        setFen(fenString);
    }
}

type GameStateProp = {
    setColor: (color: BoardOrientation) => void;
    setFen: (fen: string) => void;
    setRoomCode: (roomCode: string) => void;
    setOpponentIsReady?: (opponentIsReady: boolean) => void;
    setLoading: (loading: boolean) => void;
    setInitialTime?: (initialTime: { white: number, black: number}) => void;
}
export const gameStateListener = ({setColor, setFen, setRoomCode, setOpponentIsReady, setLoading, setInitialTime}: GameStateProp) => {
    return (gameState: string)=> {
        const JSONgameState = JSON.parse(gameState) as GameStateResponse;
        console.log(`Game ID is ${JSONgameState.id}\nGame was created at ${JSONgameState.gameCreatedAt}\nFEN is ${JSONgameState.fen}\nTurn is ${JSONgameState.turn}\nPlayer color is ${JSONgameState.playerColor}\nPlayers are ${JSONgameState.players ? JSONgameState.players.map((player) => player?.uuid) : null}`);
        setColor(JSONgameState.playerColor)
        setFen(JSONgameState.fen)
        setRoomCode(JSONgameState.id)
        if (JSONgameState.players[0].isOccupied && JSONgameState.players[1].isOccupied) {
            setOpponentIsReady && setOpponentIsReady(true);
        }
        setInitialTime && setInitialTime({white: JSONgameState.players[0].timeLeft, black: JSONgameState.players[1].timeLeft});
        setLoading(false);
    }   
};


type opponentListenerProps = {
    setAlertMessage: ( alertMessage: alertMessage | null) => void
}
export const opponentDisconnectedListener = ({setAlertMessage}: opponentListenerProps) => {
    return () => {
        console.log("Opponent disconnected");
        setAlertMessage({message: "Opponent disconnected", type: "failure"});
        setTimeout(() => {
            setAlertMessage(null);
        }, 5000);
    }
}

export const opponentJoinedListener = (
    {setAlertMessage}: opponentListenerProps,
    setOpponentIsReady?: (opponentIsReady: boolean) => void
)  =>{
    return (hasJoined: boolean) => {
        setAlertMessage({message: "Opponent joined", type: "success"});
        setOpponentIsReady && setOpponentIsReady(true);
        setTimeout(() => {
            setAlertMessage(null);
        }, 5000);
    }   
}


export const gameOverListener = ( setIsGameOver: (isGameOver: boolean) => void, setGameOverMessage: (gameOverMessage: string) => void) => {
    return  (message: string) => {
        console.log("Game over");
        setIsGameOver(true);
        setGameOverMessage(message);
    }
}