import { Socket } from 'socket.io-client';
import ButtonImg from './ButtonImg';
import { motion } from 'framer-motion';

interface LeftSidebarProps {
    isMultiplayer: boolean,
    socket: Socket | null;
}



const LeftSidebar = (
    { isMultiplayer, socket } : LeftSidebarProps
) => {

    const restart = () => {
        console.log("restart");
        socket?.emit('restart');
    }

    const surrender = () => {
        console.log("surrender");
        socket?.emit('surrender');
    }
     
    return (
        <motion.div 
            className="left-panel-buttons"
            initial={{ x: '-100px', opacity: 0  }}
            animate={{ x: 0, opacity: 1 , transition: { duration: 1, delay: 0.5} }}
            transition={{  duration: 1 }}
        >
            <ButtonImg img="/images/red-flag.png" alt="Surrender" size={50} event={() => {surrender()}}/>
            {
                !isMultiplayer &&
                <>
                    <ButtonImg img="/images/Restart.png" alt="Restart" size={50} event={() => {restart()}}/>
                    <ButtonImg img="/images/back.png" alt="Undo" size={50} event={() => {console.log("test")}} />
                </>            
            }
        </motion.div>
    );
}


export default LeftSidebar;
