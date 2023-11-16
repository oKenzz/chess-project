import { Chessboard } from "react-chessboard";
import type { ChessboardProps } from "react-chessboard/dist/chessboard/types";
import { BoardOrientation } from "react-chessboard/dist/chessboard/types";
import CustomPieces from "../config/CustomPieces";

interface ChessProps extends Omit<ChessboardProps, 'ref'> {
    color: BoardOrientation;
}

const Chess = ({ color, ...otherProps }: ChessProps) => {
    return (
        <Chessboard 
            id="BasicBoard"
            boardOrientation={color}
            arePremovesAllowed={true} 
            customDarkSquareStyle={{backgroundColor: "#B7C0D8"}}
            customLightSquareStyle={{backgroundColor: "#E8EDF9"}}
            customPremoveDarkSquareStyle={{backgroundColor: "#d64040"}}
            customPremoveLightSquareStyle={{backgroundColor: "#eb6a6a"}}
            showPromotionDialog={true}
            customPieces={CustomPieces}
            {...otherProps}
        />
    );
};

export default Chess;
