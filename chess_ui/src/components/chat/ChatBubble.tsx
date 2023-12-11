
type ChatBubbleProps = {
    senderName: string;
    messageTime: string;
    messageContent: string;
    you: boolean;
};

const ChatBubble = ({ senderName, messageTime, messageContent, you }: ChatBubbleProps) => {
    return (
        <div className="flex items-start gap-2.5 w-full">
            <div className={`flex flex-col w-[100%] leading-1.5 p-4 border-gray-200  rounded-e-xl rounded-es-xl dark:bg-gray-700
            ${you ? 'bg-blue-100' : 'bg-gray-100'}`}>
                
                <div className="flex items-center space-x-2 rtl:space-x-reverse">
                    <span className="font-extrabold text-gray-900 dark:text-white 
                        font-[Roboto] text-lg
                    ">{senderName}</span>
                    <span className="text-sm font-normal text-gray-500 dark:text-gray-400">{messageTime}</span>
                </div>
                <p className="text-sm py-2.5 text-gray-800 dark:text-white
                    font-sans font-bold
                
                ">{messageContent}</p>
            </div>
        </div>
      
    );
};

export default ChatBubble;
