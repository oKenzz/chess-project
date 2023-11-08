import { Chessboard } from "react-chessboard";
import { BoardOrientation } from "react-chessboard/dist/chessboard/types";
import CustomPieces from "../config/CustomPieces";


  
const Chess = ({ color }: {
    color: BoardOrientation;
}) => {

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
                customPieces={
                    CustomPieces
                }
        />
    );
};

export default  Chess;
