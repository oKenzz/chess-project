import { Chessboard } from "react-chessboard";
import { BoardOrientation } from "react-chessboard/dist/chessboard/types";

type ChessProps = {
    color: BoardOrientation;
};


const Chess = ({ color }: ChessProps) => {

    return (
        <Chessboard 
                id="BasicBoard"
                boardOrientation={color}
                arePremovesAllowed={true}
                customDarkSquareStyle={{backgroundColor: "#B7C0D8"}}
                customLightSquareStyle={{backgroundColor: "#E8EDF9"}}
                showPromotionDialog={true}
            
                
        />
    );
};

export default  Chess;
