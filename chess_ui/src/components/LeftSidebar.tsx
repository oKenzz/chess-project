import { motion } from "framer-motion";

const LeftSidebar = () => {
    return (
        <motion.div 
            className="left-panel-buttons"
            initial={{ x: '-100px', opacity: 0  }}
            animate={{ x: 0, opacity: 1 , transition: { duration: 1, delay: 0.5} }}
            transition={{  duration: 1 }}
            
        >
            <img src="/images/Restart.png" alt="Restart"  width={50} height={50} />
            <img src="/images/flag.png" alt="Restart"     width={50} height={50} />
            <img src="/images/back.png" alt="Restart"     width={50} height={50} />
        </motion.div>
    );
};

export default LeftSidebar;
