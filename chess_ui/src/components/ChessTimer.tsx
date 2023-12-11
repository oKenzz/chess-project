
const ChessTimer = ( {time, isActive }:{time:number, isActive: boolean} ) => {
    const minutes = Math.floor(time / 60);
    const seconds = time - minutes * 60;
  
    const minutesStr = minutes < 10 ? `0${minutes}` : `${minutes}`;
    const secondsStr = seconds < 10 ? `0${seconds}` : `${seconds}`;
    
    return (

        <div 
            className={`
                w-32 flex justify-center items-center
                bg-white bg-opacity-50 rounded-lg px-3 py-2 mt-2
                shadow-md shadow-indigo-500/50
                ${isActive ? "border-4 border-red-500" : "border-2 border-gray-300"}
                `}
            >
            {
                time > 0 &&
                <p
                    className="text-3xl font-bold text-gray-800
                    ">
                    {minutesStr}:{secondsStr}
                </p>
            }
            {
                time <= 0 &&
                <p
                    className="text-3xl font-bold text-red-500
                    ">
                    Time's up!
                </p>
            }
        </div>
    );
}


export default ChessTimer;