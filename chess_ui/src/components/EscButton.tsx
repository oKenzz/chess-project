import { motion } from 'framer-motion';
import { useNavigate } from 'react-router-dom';
import { useEffect, useState } from 'react';
import '../styles/components/EscButton.moudle.css';
const EscButton = ( style:{
    style?: React.CSSProperties
})=> {
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
        <motion.div 
            className={`escbutton ${isEscPressed ? 'pressed' : ''}`} // Add 'pressed' class when Esc is pressed
            onClick={navigateToLandingPage}
            initial={{ scale: 1 }}
            whileHover={{ scale: 1.05 }} // Scale up slightly on hover
            animate={{ scale: isEscPressed ? 0.95 : 1 }} // Scale down when Esc is pressed
            style={style.style}
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
    )
}

export default EscButton