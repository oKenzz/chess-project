import '../styles/GamePage.css';
import Chess from "../components/Chess";
import { motion } from 'framer-motion';
import Header from '../components/Header';
import LeftSidebar from '../components/LeftSidebar';

const GamePage = () => {
    return (
        <div className="container" >
            {/* Headers */}
            <Header />
            <motion.div
                initial={{  opacity: 0}
            }
                animate={{  opacity: 1 , transition: { duration: 1, delay: 0.5}}}
                className="game-panel"
            >
                <Chess color='white' />
            </motion.div>

            <motion.div className="left-panel">
                <LeftSidebar />
            </motion.div>
            <motion.div className="right-panel"></motion.div>
        </div>
    );
};

export default GamePage;
