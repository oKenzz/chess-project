import { motion } from "framer-motion";
import ChessTimer from "./ChessTimer";
import { useEffect, useState } from "react";
import { BoardOrientation } from "react-chessboard/dist/chessboard/types";
import { Socket } from "socket.io-client";

const RightSidebar = (
    {
        color,
        turn,
        intialTime,
        socket
    } : {
        color: BoardOrientation,
        turn: string,
        intialTime: number
        socket: Socket
    }

) => {
    // const [myTime, setMyTime] = useState(color[0] === "w" ? timers[0] : timers[1]);
    // const [opponentTime, setOpponentTime] = useState(color[0] === "w" ? timers[1] : timers[0]);
    const [myTime, setMyTime] = useState(intialTime);
    const [opponentTime, setOpponentTime] = useState(intialTime);

    useEffect(() => {
        // Listen to timer updates from the server
        socket.on("syncTimers", (updatedTimers) => {
            if (color === "white") {
                setMyTime(updatedTimers[0]);
                setOpponentTime(updatedTimers[1]);
            } else {
                setMyTime(updatedTimers[1]);
                setOpponentTime(updatedTimers[0]);
            }
        });

        return () => {
            // Cleanup
            socket.off("timerUpdate");
        };
    }, [color]); 


    const handleTimer = () => {
        if (turn === color[0]) {
            setMyTime(myTime - 1);
        } else {
            setOpponentTime(opponentTime - 1);
        }
    }

    useEffect(() => {
        const timer = setInterval(handleTimer, 1000);
        return () => clearInterval(timer);
    } , [myTime, opponentTime]);

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