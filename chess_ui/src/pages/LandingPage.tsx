import { useState } from "react";
import { ReactSVG } from "react-svg";
import { motion, AnimatePresence } from "framer-motion";
import PlayButton from "../components/PlayButton";
import { backgroundVariants, itemVariants } from "../config/Animations";
import { useNavigate } from "react-router-dom";
import { Button, Label, Modal, TextInput } from 'flowbite-react';


const MultiplayerForm = ({openModal, setOpenModal} : {
  openModal: boolean;
  setOpenModal: (value: boolean) => void;
}) => {
  const [roomCode, setRoomCode] = useState("")
  const [roomCodeError, setRoomCodeError] = useState("" as string | null)
  const [roomCodeValid, setRoomCodeValid] = useState(false)
  const navigate = useNavigate()

  const handleOnChange = (event : any) => {
    event.target.value = event.target.value.toUpperCase()
    setRoomCode(event.target.value)
    setRoomCodeValid( checkRoomCode( event.target.value))
  }
  const checkRoomCode =  ( room: string) => {
    if (room.length === 0){
      setRoomCodeError(null)
      return false
    }
    if (room.length !== 4){
      setRoomCodeError("Room code must be 4 characters")
      return false
    }
    if (!/^[a-zA-Z0-9]+$/.test(room)){
      setRoomCodeError("Room code must be alphanumeric")
      return false
    }
    setRoomCodeError(null)
    return true
  }

  const handleSubmit = (event : any) => { 
    event.preventDefault()
    if (!checkRoomCode( roomCode)){
      return
    }

    setRoomCodeError(null)
    navigate(`/multiplayer?room=${roomCode}`);
  };

  const onCloseModal = () => {
    setOpenModal(false)
  }

  return (
  <>
      <Modal show={openModal} size="md" onClose={onCloseModal} popup>
        <Modal.Header />
        <Modal.Body>
        <form 
          onSubmit={handleSubmit}
        >
          <div className="space-y-4">
            <div>
              <p className="text-center text-[#34364C] font-bold text-2xl mb-2">Public Games</p>
              <Button className="w-full" outline gradientDuoTone="purpleToBlue"
                onClick={() => navigate('/multiplayer?room=quickPlay')}
              >
                Quick Play</Button>
            </div>
            <hr />
            <div>
              <p className="text-center text-[#34364C] font-bold text-2xl mb-2">Private Games</p>
              <Button className="w-full"
                  onClick={() => navigate('/multiplayer?room=create')} 
                  outline gradientDuoTone="purpleToBlue"
                >Create New Game</Button>
              <hr className="my-3"/>
                <div className="grid grid-cols-3 gap-2">
                  <TextInput
                    id="text"
                    type="text"
                    placeholder="ex. 73AC"
                    min={4}
                    max={4}
                    value={roomCode}
                    onChange={(e) =>  handleOnChange(e)}
                    required
                    className="col-span-2"
                    autoComplete="off"
                  />
                  <Button className="w-full" 
                    outline gradientDuoTone="purpleToBlue" type="submit"
                    disabled={ !roomCodeValid }
                  >Join</Button>
              </div>
              {roomCodeError && <p className="text-red-500 text-center -mb-2 mt-2">{roomCodeError}</p>}
            </div>

          </div>
          </form>
        </Modal.Body>
      </Modal>
      </>
    );
}


const LandingPage = () => {
  const [isExiting, setExiting] = useState(false);
  const [openModal, setOpenModal] = useState(false);
  const navigate = useNavigate();

  const handleMutliplayerClick = () => {
    setOpenModal(true)
  }
  const handleSingleplayerClick = () => {
    navigate("/singleplayer")
  }

  return (
    <AnimatePresence mode="wait">
      {!isExiting && (
        <motion.main
          className="relative flex h-screen w-full flex-col items-stretch"
          initial={{ opacity: 1 }}
          exit={{ x: "100vw", transition: { duration: 0.5 } }}
        >
          {/* background image */}
          <motion.div
            variants={backgroundVariants}
            initial="initial"
            animate="animate"
            className="-z-index-1 absolute bottom-0 right-0"
          >
            <ReactSVG src="/images/Background.svg" />
          </motion.div>

          <motion.div
            variants={itemVariants}
            initial="hidden"
            animate="visible"
          >
            <h1 className="font-roboto font-900 relative mt-16 text-center text-9xl text-[#7B61FF] md:text-[12rem]">
              Simple <span className="text-[#34364C]">Chess</span> 
            </h1>
          </motion.div>
          <div className="relative flex h-full min-h-[inherit] flex-col p-8 md:flex-row ">
            <div className="min-height-[inherit] flex h-full flex-[3] flex-col justify-center md:items-end">
              <div className="flex flex-col gap-6 pb-[50%]">
                <PlayButton
                  link="/singleplayer"
                  text="Singleplayer"
                  onClick={() =>  handleSingleplayerClick()}
                  disabled = {true}

                />
                <PlayButton
                  link="/multiplayer"
                  text="Multiplayer"
                  onClick={() =>  handleMutliplayerClick()}
                />
                <MultiplayerForm openModal={openModal} setOpenModal={setOpenModal}/>
              </div>
            </div>
            <div className="flex-[3]"></div>
          </div>
        </motion.main>
      )}
    </AnimatePresence>
  );
};

export default LandingPage;
