import { motion } from 'framer-motion';
import { ReactSVG } from 'react-svg';
import { useNavigate } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { buttonVariants } from '../config/Animations';

const Header = () => {
    const navigate = useNavigate();
    const [isEscPressed, setIsEscPressed] = useState(false);
    const [isEscReleased, setIsEscReleased] = useState(false);

    const navigateToLandingPage = () => {
        setTimeout(() => {
            navigate('/');
        }, 250); 
    };

    useEffect(() => {
        const handleKeyDown = (event: { key: string; }) => {
            if (event.key === 'Escape') {
                setIsEscPressed(true); // Set the state to true when key is pressed
            }
        };

        const handleKeyUp = (event: { key: string; }) => {
            if (event.key === 'Escape') {
                setIsEscReleased(true); // Set the state to true when key is released
            }
        };

        // Attach the event listeners
        window.addEventListener('keydown', handleKeyDown);
        window.addEventListener('keyup', handleKeyUp);

        // Cleanup the event listeners on component unmount
        return () => {
            window.removeEventListener('keydown', handleKeyDown);
            window.removeEventListener('keyup', handleKeyUp);
        };
    }, []); 

    useEffect(() => {
        if (isEscReleased) {
            // Delay the navigation slightly to allow the button press effect to be seen
            const timeoutId = setTimeout(() => {
                navigate('/'); // Navigate back to landing page
            }, 200);

            return () => clearTimeout(timeoutId); // Clear the timeout if the component unmounts
        }
    }, [ isEscReleased, navigate]);

    return (
        <motion.header 
            initial={{ y: '-8vh' }}
            animate={{ y: 0, transition: { duration: 0.5, delay: 0.5 }}}
            className="header"
        > 
            <motion.div 
                className={`header-left ${isEscPressed ? 'pressed' : ''}`} // Add 'pressed' class when Esc is pressed
                onClick={navigateToLandingPage}
                initial={{ scale: 1 }}
                whileHover={{ scale: 1.05 }} // Scale up slightly on hover
                animate={{ scale: isEscPressed ? 0.95 : 1 }} // Scale down when Esc is pressed
            >
                <div className="fill-bg"></div> {/* This div will animate on hover and when Esc is pressed */}
                <img 
                    src="/images/ESCButton.png"
                    alt="Esc"
                    width={42}
                    height={42}
                />
                <span>Leave</span>
            </motion.div>

            <div className="header-center">
                <ReactSVG src="/images/logo.svg" />
            </div>

            <div className="header-right">
                <ReactSVG src="/images/Settings.svg" />
            </div>
        </motion.header>
    );
};

export default Header;
