
type ChatBubbleProps = {
    key: number;
    messageTime: string;
    messageContent: string;
    you: boolean;
};

const ChatBubble = ({ key, messageTime, messageContent, you }: ChatBubbleProps) => {
    return (
<div className="flex flex-col gap-0.5 w-full max-w-[320px]">
  <div className={`flex items-center space-x-1 rtl:space-x-reverse ${you ? 'justify-end' : 'justify-start'}`}>
    <span className={`text-sm font-medium ${you ? 'text-indigo-500' : 'text-gray-800'} dark:text-white`}>
      {you ? 'You' : 'Opponent'}
    </span>
    <span className="text-xs font-normal text-gray-500 dark:text-gray-400">
      {messageTime}
    </span>
  </div>
  <div className={`flex flex-col leading-snug py-1.5 px-2.5 rounded-md
        ${you ? 'bg-indigo-500 border-indigo-600' : 'bg-gray-500 border-gray-600'}
   `}>
    <p className="text-xs font-normal text-white break-words"> 
      {messageContent}
    </p>
  </div>
</div>

      
    );
};

export default ChatBubble;
