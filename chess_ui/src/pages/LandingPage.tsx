import { useState } from "react";
import { ReactSVG } from "react-svg";
import { motion, AnimatePresence } from "framer-motion";
import PlayButton from "../components/PlayButton";
import { backgroundVariants, itemVariants } from "../config/Animations";

const LandingPage = () => {
  const [isExiting, setExiting] = useState(false);

  const handleButtonClick = (link: string) => {
    setExiting(true);
    setTimeout(() => {
      window.location.href = link;
    }, 500);
  };

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
            <h1 className="font-roboto font-900 relative mt-10 text-center text-6xl text-[#7B61FF] md:text-[8rem]">
              Simple <span className="text-[#34364C]">Chess</span> 
            </h1>
          </motion.div>
          <div className="relative flex h-full min-h-[inherit] flex-col p-8 md:flex-row ">
            <div className="min-height-[inherit] flex h-full flex-[3] flex-col justify-center md:items-end">
              <div className="flex flex-col gap-8 pb-[50%]">
                <PlayButton
                  link="/singleplayer"
                  text="Singleplayer"
                  onClick={() => handleButtonClick("/singleplayer")}
                  disabled = {true}

                />
                <PlayButton
                  link="/multiplayer"
                  text="Multiplayer"
                  onClick={() => handleButtonClick("/multiplayer")}
                />
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
