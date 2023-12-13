import { Navbar } from 'flowbite-react';
import EscButton from './EscButton';
import { ReactSVG } from 'react-svg';
import ButtonImg from './ButtonImg';
import { motion } from 'framer-motion';
import SettingsModal from './SettingsModal';
import { useState } from 'react';

const CustomNavbar = (   { roomCode, disableRoomcode} : { roomCode: string | null  , disableRoomcode?: boolean} ) => {
  const [isSettingsModalOpen, setIsSettingsModalOpen] = useState(false);
 
  return (
    <motion.div
        initial={{ y: '-8vh' }}
        animate={{ y: 0, transition: { duration: 0.5, delay: 0.5 }}}
    >

      <Navbar  fluid className="bg-gray-600 w-[100vw] py-auto z-50" >
        {/*  Desktop */}
        <div className='hidden md:flex w-[100vw] items-center place-content-between  px-5 h-100'>
          <EscButton/>
          <Navbar.Brand  href="/" >
              <ReactSVG src="/images/logo.svg"/>
          </Navbar.Brand>
          <ButtonImg id="settings_button" img='/images/Settings.svg' alt='Settings' size={50} 
          event={() => { setIsSettingsModalOpen(true) }}
          />
        </div>

        {/*  Mobile */}
        <Navbar.Brand  href="/" className='md:hidden w-100 px-2 py-1' >
              <ReactSVG src="/images/logo.svg" />
          </Navbar.Brand>
        <Navbar.Toggle />
        <Navbar.Collapse className='md:hidden bg-gray-600'>
            <div className='w-100 p-3 flex items-center gap-3
            border-y-2 border-gray-700 cursor-pointer'
              onClick={() => { setIsSettingsModalOpen(true) } }
            >

                <p className='text-white text-2xl font-bold'>
                    Settings
                  </p>                
                <ButtonImg id="settings_button" img='/images/Settings.svg' alt='Settings' size={50} 
              event={() => { }}
              />
            </div>
            <div className='w-100 p-3'>
              <EscButton  style={{'width': '100%'}}/>
            </div>
            {
            roomCode && !disableRoomcode &&
            (
              <motion.div
                    initial={{ opacity: 0 }}
                    animate={{ opacity: 1, transition: { duration: 0.5, delay: 0.5 } }}
                    className='w-[100vw] bg-white flex items-center justify-center -z-50 -mt-7 '
                >
                <p className='text-center text-gray-500'>Room Code:  { roomCode }</p>
              </motion.div>
            )
          }
        </Navbar.Collapse>
      </Navbar>
      
          {
        roomCode && !disableRoomcode &&
         (
          <motion.div
                initial={{ opacity: 0 }}
                animate={{ opacity: 1, transition: { duration: 0.5, delay: 0.5 } }}
                className='w-[100vw] h-8 bg-white flex items-center justify-center -z-50 '
            >
            <p className='text-center text-gray-500'>Room Code:  { roomCode }</p>
          </motion.div>
        )
      }
    {/* Settings Modal */}
    <SettingsModal 
        isSettingsModalOpen={isSettingsModalOpen}
        setIsSettingsModalOpen={setIsSettingsModalOpen}
    />
    </motion.div>
  );
}

export default CustomNavbar;


