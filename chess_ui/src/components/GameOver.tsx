import { motion } from "framer-motion";


const GameOver = ({ gameOverMessage }: { gameOverMessage: string }) => (


    <motion.div
                        initial={{ opacity: 0 }}
                        animate={{ opacity: 0.8, transition: { duration: 2 } }}
                        exit={{ opacity: 0, transition: { duration: 2 } }}
                        className='absolute top-0 left-0 w-full h-full flex justify-center items-center bg-black opacity-50'
                    >
                        <div className='font-bold flex flex-col justify-center items-center gap-3'>
                            <p className='text-3xl text-white font-bold'>
                                Game over
                            </p>
                            <span className="text-6xl text-white font-bold">                    
                                {gameOverMessage}
                            </span>
                            <span className= "text-2xl">
                                <a href="/" className="text-blue-500 hover:underline">Play again</a>
                            </span>
                        </div>
    </motion.div>
);

export default GameOver;