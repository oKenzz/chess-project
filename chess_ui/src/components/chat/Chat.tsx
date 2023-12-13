import { useEffect, useRef, useState } from 'react';
import ChatBubble from './ChatBubble'; // Ensure this is correctly imported
import { SlArrowRight } from 'react-icons/sl';
import { motion, AnimatePresence } from 'framer-motion';
import ChatIcon from './ChatIcon'; // Ensure this is correctly imported
import { Socket } from 'socket.io-client';
import { chatBoxVariants, chatButtonVariants, chatTransition } from '../../config/Animations';
import { Message } from '../../constants/types';


const Chat = ({ socket}:{ socket: Socket;}) => {
    const [showChat, setShowChat] = useState(false);
    const [messages, setMessages] = useState<Message[]>([]);
    const [message, setMessage] = useState('');
    const messagesEndRef = useRef<HTMLDivElement>(null);
    const [newMessagesAmount, setNewMessagesAmount] = useState(0);
    const [prevChatLength, setPrevChatLength] = useState(0);

    const scrollToBottom = () => {
        messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
    }
    useEffect(() => {
        socket.on('chatMessage', (data) => {
                const newMessage = JSON.parse(data) as Message[];
                setMessages(newMessage);
                if (newMessage.length > prevChatLength && !showChat) {
                    setNewMessagesAmount(newMessage.length - prevChatLength);
                }
        });

        socket.emit('getChatMessages', null ,(data : string) => {
            const newMessage = JSON.parse(data) as Message[];
            setMessages(newMessage);
            if (newMessage.length > prevChatLength && !showChat) {
                setNewMessagesAmount(newMessage.length - prevChatLength);
            }            
        });
        return () => {
            socket.off('chatMessage');
        };
    }, [prevChatLength, showChat, socket]);

    const handleKeyDown = (event: React.KeyboardEvent) => {
        if (event.key === 'Enter') {
            event.preventDefault();  // Prevents the default action of the enter key
            sendMessage();
        }
    }

    const sendMessage = () => {
        if (message.trim() === '') return; // Check if message is not just empty spaces
        if (message.length > 100) return; // Check for message length
        socket.emit('message', message);
        setMessage('');
    }

    const checkMessageInput = ( value : string ) => {
        if (value.length > 100) return; // Check for message length
        setMessage(value);
    }

    const openChat = () => {
        setPrevChatLength(messages.length);
        setShowChat(true);
        setNewMessagesAmount(0);
        scrollToBottom();
    }
    useEffect(() => {
        if (showChat)
        {
            setNewMessagesAmount(0);
            setPrevChatLength(messages.length);
        }
        scrollToBottom();  // Call this function when messages update
    }, [messages, showChat]);

    return (
        <>
            <AnimatePresence>
                {showChat ? (
                    <motion.div
                        className="w-100 h-[92vh] absolute bottom-0 right-0 flex flex-row justify-between items-center z-50
                            border-t-2 border-[#7b61ff] 
                        "
                        initial="closed"
                        animate="open"
                        exit="closed"
                        variants={chatBoxVariants}
                        transition={chatTransition}
                        >   
                        <motion.button
                            className="w-full h-[100%] flex justify-center items-center bg-[#7b61ff] text-white px-2 hover:bg-[#7257fb] hover:shadow-lg"
                            onClick={() => setShowChat(false)}
                        >
                            <SlArrowRight className="w-6 h-6" />
                        </motion.button>
                        <div className="w-full h-full px-3 pb-3 pt-6 flex flex-col justify-between items-center bg-white">
                            {/* start from bottom and go up */}
                                <div className="
                                    chat_cards
                                    w-full h-[100%] flex flex-col items-start
                                    gap-4 overflow-y-auto pr-2 mb-3 pb-3
                                    scrollbar-track-slate-400 scrollbar-thumb-slate-500
                                    scrollbar-thin
                                "
                                        // First child margin top !important
                                >
                                {
                                    messages.map((message, index) => (
                                        <ChatBubble 
                                            key={index}
                                            messageContent={message.message}
                                            messageTime={message.sentAt}
                                            you={
                                                message.sender === socket.id
                                            }
                                        />
                                    ))
                                }
                                <div ref={messagesEndRef} />
                                {
                                    messages.length === 0 ? (
                                        <div className="flex flex-col items-center justify-center gap-2.5 w-full h-full">
                                            <p className="text-gray-500 font-medium text-sm">No messages yet</p>
                                        </div>
                                    ) : null
                                }
                            </div>                              
                            

                            <div className="flex items-center">
                                <input 
                                    type="text" 
                                    placeholder="Write your message" 
                                    className="w-100 h-auto flex flex-col px-2  border border-gray-300 rounded-l-lg focus:outline-none focus:border-[#7b61ff] 
                                        text-gray-600 font-medium text-sm
                                    "
                                    onChange={(e) =>  checkMessageInput(e.target.value)}
                                    onKeyDown={handleKeyDown}
                                    value={message}
                                />
                                <button 
                                    className="w-100 h-auto bg-[#7b61ff] text-white px-2 py-2 rounded-r-lg hover:bg-[#7b61ff] hover:opacity-90 hover:shadow-lg"
                                    onClick={sendMessage}
                                >Send
                                </button>
                            </div>
                        </div>
                    </motion.div>
                ) : (
                    <motion.button
                        className="w-12 h-12 fixed flex justify-center items-center bg-white border-2 border-black rounded-full p-2 hover:opacity-80 hover:shadow-lg hover:border-opacity-0 hover:scale-110 hover:bg-white
                            bottom-3 right-3
                            sm:w-16 sm:h-16 sm:right-10 sm:bottom-10
                        "
                        onClick={openChat}
                        initial="closed"
                        animate="open"
                        exit="closed"
                        variants={chatButtonVariants}
                        transition={chatTransition} 
                    >
                        <ChatIcon />
                        {
                            newMessagesAmount > 0 ? (
                                <div className="w-6 h-6 absolute top-0 right-0 flex justify-center items-center bg-[#7b61ff] text-white rounded-full">
                                    <p className="text-xs font-medium">{newMessagesAmount}</p>
                                </div>
                            ) : null
                        }
                    </motion.button>
                )}
            </AnimatePresence>
        </>
    );
};

export default Chat;
