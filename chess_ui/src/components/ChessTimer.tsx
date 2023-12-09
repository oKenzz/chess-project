
const ChessTimer = ( {time}:{time:number} ) => {
    const minutes = Math.floor(time / 60);
    const seconds = time - minutes * 60;
  
    const minutesStr = minutes < 10 ? `0${minutes}` : `${minutes}`;
    const secondsStr = seconds < 10 ? `0${seconds}` : `${seconds}`;
    
    return (
        <>
            {
                time <= 0 &&
                <div 
                    className="
                        w-32 flex justify-center items-center
                        bg-white bg-opacity-50 rounded-lg px-3 py-2 mt-2
                        shadow-md shadow-indigo-500/50
                    ">

                    <p
                        className="text-3xl font-bold text-gray-800
                        ">
                    00:00</p>
                </div>
                
            }
            {
                time > 0 &&
                <div 
                    className="
                        w-32 flex justify-center items-center
                        bg-white bg-opacity-50 rounded-lg px-3 py-2 mt-2
                        shadow-md shadow-indigo-500/50
                    ">

                    <p
                        className="text-3xl font-bold text-gray-800
                        ">
                    {minutesStr}:{secondsStr}</p>
                </div>
            }
        </>
    );
}


export default ChessTimer;