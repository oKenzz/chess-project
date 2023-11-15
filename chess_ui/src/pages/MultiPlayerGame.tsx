import '../styles/GamePage.css';
import Chess from "../components/Chess";
import { motion } from 'framer-motion';
import LeftSidebar from '../components/LeftSidebar';
import CustomNavbar from '../components/CustomNavbar';
import { SocketClient } from '../socket/Client'; // Adjust the import path as necessary
import { useEffect } from 'react';

const MultiPlayerGame = () => {

    useEffect(() => {  
        // Connect to socket
        const socketClient = SocketClient.getInstance();
        socketClient.connect();

        const socket = socketClient.getSocket();
        socket.on('chat', ( data: string ) => {
            console.log(`Received message: ${data}`);
        });

        // Cleanup on unmount
        return () => {
            socketClient.disconnect();
        };
    }
    , []);

    return (
        <div className="game-grid" >
            {/* Headers */}
            <CustomNavbar  />
            {/* <Header /> */}
            <motion.div
                initial={{  opacity: 0}
            }
                animate={{  opacity: 1 , transition: { duration: 1, delay: 0.5}}}
                className="game-panel"
            >
                <Chess color='white' />
            </motion.div>

            <motion.div className="left-panel">
                <LeftSidebar />
            </motion.div>
            <motion.div className="right-panel"></motion.div>
        </div>
    );
};

export default MultiPlayerGame;
