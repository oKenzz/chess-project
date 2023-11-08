import { motion } from 'framer-motion';
import { buttonVariants } from '../config/Animations';

type Props = {
    link: string;
    text: string;
    onClick: (link: string) => void;
};

const PlayButton = ({ link, text, onClick }: Props) => {
    return (
        <motion.button
            variants={buttonVariants}
            initial="hidden"
            animate="visible"
            whileHover="hover"
            whileTap="tap"
            onClick={() => onClick(link)}
        >
            <p>{text}</p>
            <img src="/images/CircledPlay.png" alt="Play Button" />
        </motion.button>
    );
};

export default PlayButton;
