
type ChatBubbleProps = {
    key: number;
    messageTime: string;
    messageContent: string;
    you: boolean;
};

const ChatBubble = ({ key, messageTime, messageContent, you }: ChatBubbleProps) => {
    return (
           <div className="flex flex-col gap-0.5 w-full max-w-[320px]"> 
           <div className={`flex items-center space-x-1 rtl:space-x-reverse
                ${you ? 'justify-end' : 'justify-start'}
              `}
           >
              <span className={`text-base font-semibold text-gray-900 dark:text-white`
                }
              >
                    {you ? 'You' : 'Opponent'}
              </span>
              <span className="text-sm font-normal text-gray-500 dark:text-gray-400">
                    {messageTime}
              </span>
           </div>
           <div className={`flex flex-col leading-1.5 py-2 px-4 border-gray-200 
                ${you ? 'rounded-t-xl rounded-es-xl bg-indigo-600' : 'rounded-e-xl rounded-es-xl  bg-gray-600'}
           `}>
              <p className="text-sm font-normal text-white
                break-words
              "> 
                    {messageContent}
              </p>
           </div>
        </div>
      
    );
};

export default ChatBubble;
