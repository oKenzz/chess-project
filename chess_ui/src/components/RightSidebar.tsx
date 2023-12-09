import { motion } from "framer-motion";
import ChessTimer from "./ChessTimer";
import { useEffect, useState } from "react";



const RightSidebar = (
    {
        isMyTurnProp,
        switchTurn,
    } : {
        isMyTurnProp: boolean,
        switchTurn: () => void,
    }

) => {
    const [opponentTime, setOpponentTime] = useState(60 * 10);
    const [myTime, setMyTime] = useState( 60 * 10);
    const [isMyTurn, setIsMyTurn] = useState( isMyTurnProp );

    const handleTimer = () => {
        if (isMyTurn) {
            setMyTime(myTime - 1);
        } else {
            setOpponentTime(opponentTime - 1);
        }
    }

    switchTurn = () => {
        setIsMyTurn(!isMyTurn);
    }
    useEffect(() => {
        const timer = setInterval(handleTimer, 1000);
        return () => clearInterval(timer);
    } , [isMyTurn, myTime, opponentTime]);

    return (
        <motion.div 
            className="right-panel py-10"
            initial={{ x: '100px', opacity: 0  }}
            animate={{ x: 0, opacity: 1 , transition: { duration: 1, delay: 0.5} }}
            transition={{  duration: 1 }}
        >

            <div>
                <p className="text-gray-800">Opponent:</p>
                <ChessTimer  time={opponentTime} />
            </div>

            <div>
                <p className="text-gray-800">You:</p>
                <ChessTimer time={myTime} />
            </div>

        </motion.div>
    );
}



export default RightSidebar;