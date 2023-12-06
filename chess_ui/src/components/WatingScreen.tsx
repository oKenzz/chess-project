import { Spinner } from "flowbite-react";
import CloudAnimation from "./CloudAnimation";
import { useState } from "react";

type WaitingScreenProps = {
    roomCode: string;
}
const WaitingScreen = ({ roomCode }: WaitingScreenProps) =>{
    const [showEntertainment, setShowEntertainment] = useState(false);
    return (
        
            <div className='absolute top-0 left-0 w-full h-full flex justify-center items-center '>
                <div  className='absolute top-0 left-0 w-full h-full bg-black opacity-50'
                    style={{ zIndex: 999}}>
                </div>
                <div className='
                absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2
                flex flex-col justify-center items-center gap-8
                '
                    style={{ zIndex: 1000}}
                >  
                    {
                        roomCode ? 
                        <p className='text-3xl text-white font-bold'>Room code: {roomCode}</p>
                        : null
                    }
                    <p className='text-3xl text-white font-bold'>Waiting for opponent</p>
                    <Spinner  
                        className='w-36 h-36 ' 
                    />
                </div>
                <CloudAnimation />
                <button className='
                absolute bottom-5 right-5 z-0
                bg-white text-gray-700 font-bold rounded-lg p-2
                ' style={{ zIndex: 999}} onClick={() => setShowEntertainment(true)}>Short attention span?</button>

            {
                showEntertainment &&
                <iframe className='absolute bottom-0 right-0 z-0' style={{ zIndex: 999}}  width="730" height="415" src="https://www.youtube.com/embed/bXVcXbhhxcI?si=ZB1JEc8yGdHu6YoZ?rel=0&amp;autoplay=1&mute=1" title="YouTube video player"  allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" ></iframe>
            }
        </div>
    );
}

export default WaitingScreen;