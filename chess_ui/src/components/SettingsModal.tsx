import { motion } from "framer-motion";
import Settings from "./Settings";

type SettingsModalProps = {
    isSettingsModalOpen: boolean;
    setIsSettingsModalOpen(isSettingsModalOpen: boolean): void;
}

const SettingsModal = ( { isSettingsModalOpen, setIsSettingsModalOpen }: SettingsModalProps) => {
    return (    
        <motion.div
            initial={{ opacity: 0 }}
            animate={{ opacity: 1, transition: { duration: 0.5, delay: 0.5 } }}
            exit={{ opacity: 0, transition: { duration: 0.5 } }}
            className='absolute top-0 left-0 flex justify-center items-center'
            style={{display: isSettingsModalOpen ? 'flex' : 'none'}}
        >
            <Settings 
                showModal={isSettingsModalOpen}
                setShowModal={setIsSettingsModalOpen}
            />
        </motion.div>
    );
}

export default SettingsModal;

