import { motion } from "framer-motion";
import ChessTimer from "./ChessTimer";
import { useEffect, useState } from "react";
import { BoardOrientation } from "react-chessboard/dist/chessboard/types";
import { Socket } from "socket.io-client";


const RightSidebar = (
    {
        initTime,
        color,
        turn,
        socket
    } : {
        initTime: { white: number, black: number}
        color: BoardOrientation,
        turn: string,
        socket: Socket
    }

) => {
    const [myTime, setMyTime] = useState( initTime[color] );
    const [opponentTime, setOpponentTime] =  useState( initTime[color === "white" ? "black" : "white"] );

    useEffect(() => {
        // Listen to timer updates from the server
        socket.on("syncTimers", (updatedTimers:  number[]) => {
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
                <ChessTimer  time={opponentTime} isActive={turn !== color[0]} />
            </div>

            <div>
                <p className="text-gray-800">You:</p>
                <ChessTimer time={myTime} isActive={turn === color[0]} />
            </div>

        </motion.div>
    );
}



export default RightSidebar;