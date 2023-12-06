import ButtonImg from './ButtonImg';
import { motion } from 'framer-motion';

const LeftSidebar = (
    { isMultiplayer } : { isMultiplayer: boolean }
) => {
     
    return (
        <motion.div 
            className="left-panel-buttons"
            initial={{ x: '-100px', opacity: 0  }}
            animate={{ x: 0, opacity: 1 , transition: { duration: 1, delay: 0.5} }}
            transition={{  duration: 1 }}
        >
            <ButtonImg img="/images/red-flag.png" alt="Surrender" size={50} event={() => {console.log("test")}}/>
            {
                !isMultiplayer &&
                <>
                    <ButtonImg img="/images/Restart.png" alt="Restart" size={50} event={() => {console.log("test")}}/>
                    <ButtonImg img="/images/back.png" alt="Undo" size={50} event={() => {console.log("test")}} />
                </>            
            }
        </motion.div>
    );
}


export default LeftSidebar;
