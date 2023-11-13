import { useState } from 'react';
import { ReactSVG } from 'react-svg';
import { motion, AnimatePresence } from 'framer-motion';
// Components
import PlayButton from '../components/PlayButton';

// Config
import { backgroundVariants, itemVariants } from '../config/Animations';

// Styles
import '../styles/LandingPage.css';

const LandingPage = () => {
    const [isExiting, setExiting] = useState(false);

    // Function to handle button click
    const handleButtonClick = (link: string) => {
        setExiting(true); // Trigger the exit animation
        setTimeout(() => {
            window.location.href = link; // Redirect after the animation
        }, 500); // Set the same duration as the exit animation
    };

    return (
        <AnimatePresence mode="wait">
            {!isExiting && (
                <motion.main
                    className="main-landingpage"
                    initial={{ opacity: 1 }}
                    exit={{ x: '100vw', transition: { duration: 0.5 } }}
                >
                    <div className="div1"> </div>
                    <div className="div2"> </div>

                    <motion.div className="div3" variants={itemVariants} initial="hidden" animate="visible"> 
                        <div className='title'>
                            <h1>Simple <span>Chess</span></h1>
                        </div>
                    </motion.div>

                    <div className="buttons">
                        <PlayButton link='/game' text="Singleplayer" onClick={() => handleButtonClick('/game')} />
                        <PlayButton link='/game' text="Multiplayer" onClick={() => handleButtonClick('/game')} />
                    </div>

                    <motion.div
                        variants={backgroundVariants}
                        initial="initial"
                        animate="animate"
                        style={{ position: 'absolute', right: 0, bottom: 0 }}
                    >
                        <ReactSVG id='background' src="/images/Background.svg" />
                    </motion.div>
                </motion.main>
            )}
        </AnimatePresence>
    );
};

export default LandingPage;
