import { motion } from "framer-motion";

const JoiningGameScreen = () => {
    return (    
        <motion.div
            initial={{ opacity: 1 }}
            animate={{ opacity: 0.8, transition: { duration: 2 } }}
            className='absolute top-0 left-0 w-full h-full flex justify-center items-center bg-black opacity-50'
        >
            <p className='text-3xl text-white font-bold'>Joining game<span className="dots"></span></p>
        </motion.div>
    );
}

export default JoiningGameScreen;