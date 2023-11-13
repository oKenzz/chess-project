import { useState } from 'react';
import { ReactSVG } from 'react-svg';
import { motion, AnimatePresence } from 'framer-motion';
import PlayButton from '../components/PlayButton';
import { backgroundVariants, itemVariants } from '../config/Animations';

const LandingPage = () => {
    const [isExiting, setExiting] = useState(false);

    const handleButtonClick = (link: string) => {
        setExiting(true);
        setTimeout(() => {
            window.location.href = link;
        }, 500);
    };

    return (
        <AnimatePresence mode="wait">
            {!isExiting && (
                <motion.main
                    className="min-w-full min-h-screen overflow-hidden"
                    initial={{ opacity: 1 }}
                    exit={{ x: '100vw', transition: { duration: 0.5 } }}
                >
                    <div className="container mx-auto px-4 lg:px-0">
                        <motion.div 
                            className="text-center"
                            variants={itemVariants}
                            initial="hidden"
                            animate="visible"
                        >
                            <h1 className='text-[12vw] lg:text-[8vw] font-roboto font-extrabold text-[#7B61FF]'>
                                Simple <span className="text-[#34364C]">Chess</span>
                            </h1>
                        </motion.div>

                        <div className="flex flex-col items-start gap-4 mt-4">
                            <PlayButton link='/game' text="Singleplayer" onClick={() => handleButtonClick('/game')} />
                            <PlayButton link='/game' text="Multiplayer" onClick={() => handleButtonClick('/game')} />
                        </div>
                    </div>

                    <motion.div
                        variants={backgroundVariants}
                        initial="initial"
                        animate="animate"
                        className="absolute right-0 bottom-0 "
                    >
                        <ReactSVG src="/images/Background.svg" className="w-full h-auto" />
                    </motion.div>

                </motion.main>
            )}
        </AnimatePresence>
    );
};

export default LandingPage;
