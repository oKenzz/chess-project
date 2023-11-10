import { motion } from "framer-motion";
import ButtonImg from "./ButtonImg";

const LeftSidebar = () => {
    return (
        <motion.div 
            className="left-panel-buttons"
            initial={{ x: '-100px', opacity: 0  }}
            animate={{ x: 0, opacity: 1 , transition: { duration: 1, delay: 0.5} }}
            transition={{  duration: 1 }}
        >

            <ButtonImg img="/images/Restart.png" alt="Restart" size={50} event={() => {console.log("test")}}/>

            <ButtonImg img="/images/flag.png" alt="Surrender" size={50} event={() => {console.log("test")}}/>

            <ButtonImg img="/images/back.png" alt="Undo" size={50} event={() => {console.log("test")}} />



            {/* <img src="/images/Restart.png" alt="Restart"  width={50} height={50} />

            <img src="/images/flag.png" alt="Surrender"     width={50} height={50} />

            <img src="/images/back.png" alt="Undo"     width={50} height={50} /> */}

        </motion.div>
    );
};

export default LeftSidebar;
