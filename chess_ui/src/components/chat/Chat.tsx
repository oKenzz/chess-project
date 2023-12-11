import React, { useState } from 'react';
import ChatBubble from './ChatBubble'; // Ensure this is correctly imported
import { SlArrowRight } from 'react-icons/sl';
import { motion, AnimatePresence } from 'framer-motion';
import ChatIcon from './ChatIcon'; // Ensure this is correctly imported

const Chat = () => {
    const [showChat, setShowChat] = useState(false);

    // Define animation variants
    const chatBoxVariants = {
        open: { opacity: 1, x: 0 },
        closed: { opacity: 0, x: "100%" },
    };

    const chatButtonVariants = {
        open: { scale: 1, opacity: 1 },
        closed: { scale: 0, opacity: 0 },
    };
    const transition = { type: "easeInOut", duration: 0.4 };
    return (
        <>
            <AnimatePresence>
                {showChat ? (
                    <motion.div
                        className="w-100 h-[89vh] absolute bottom-0 right-0 flex flex-row justify-between items-center"
                        initial="closed"
                        animate="open"
                        exit="closed"
                        variants={chatBoxVariants}
                        transition={transition}
                        >   
                        <motion.button
                            className="w-auto h-[100%] flex justify-center items-center bg-[#7b61ff] text-white px-2 hover:bg-[#7b61ff] hover:shadow-lg"
                            onClick={() => setShowChat(false)}
                        >
                            <SlArrowRight className="w-6 h-6" />
                        </motion.button>
                        <div className="w-full h-[100%] px-6 py-4 flex flex-col justify-between items-center bg-white">
                            <div className="w-full h-full flex flex-col content-start justify-end gap-3 mb-3">
                                <ChatBubble 
                                    messageContent="You are about to lose"
                                    senderName="Opponent"
                                    messageTime="12:00"
                                    you={false}
                                />
                                <ChatBubble 
                                    messageContent="Nah, I'm about to win"
                                    senderName="You"
                                    messageTime="12:00"
                                    you={true}
                                />
                            </div>
                            
                            <div className="flex items-center">
                                <input 
                                    type="text" 
                                    placeholder="Write your message" 
                                    className="w-100 h-[4vh] flex flex-col px-3 py-5 border border-gray-300 rounded-l-lg focus:outline-none focus:border-[#7b61ff] 
                                        text-gray-600 font-medium text-sm
                                    "
                                />
                                <button 
                                    className="w-100 h-[4vh] bg-[#7b61ff] text-white px-4 py-2 rounded-r-lg hover:bg-[#7b61ff] hover:opacity-90 hover:shadow-lg"
                                >Send
                                </button>
                            </div>
                        </div>
                    </motion.div>
                ) : (
                    <motion.button
                        className="w-16 h-16 fixed bottom-10 right-10 flex justify-center items-center bg-white border-2 border-black rounded-full p-2 hover:opacity-80 hover:shadow-lg hover:border-opacity-0 hover:scale-110 hover:bg-white"
                        onClick={() => setShowChat(true)}
                        initial="closed"
                        animate="open"
                        exit="closed"
                        variants={chatButtonVariants}
                        transition={transition} 
                    >
                        <ChatIcon />
                    </motion.button>
                )}
            </AnimatePresence>
        </>
    );
};

export default Chat;
