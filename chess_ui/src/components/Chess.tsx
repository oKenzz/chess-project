import { Chessboard } from "react-chessboard";
import type { ChessboardProps } from "react-chessboard/dist/chessboard/types";
import { BoardOrientation } from "react-chessboard/dist/chessboard/types";
import CustomPieces from "../config/CustomPieces";
import { ChessTheme } from "../constants/theme";

interface ChessProps extends Omit<ChessboardProps, 'ref'> {
    color: BoardOrientation;
    theme: ChessTheme;
}

const Chess = ({ color, theme, ...otherProps }: ChessProps) => {
    return (
        <Chessboard 
            id="BasicBoard"
            boardOrientation={color}
            arePremovesAllowed={true} 
            customDarkSquareStyle={{backgroundColor:  theme.customDarkSquareStyle}}
            customLightSquareStyle={{backgroundColor: theme.customLightSquareStyle}}
            customPremoveDarkSquareStyle={{backgroundColor:  theme.customPremoveDarkSquareStyle}}
            customPremoveLightSquareStyle={{backgroundColor: theme.customPremoveLightSquareStyle}}
            showPromotionDialog={true}
            customPieces={CustomPieces}
            {...otherProps}
        />
    );
};

export default Chess;
