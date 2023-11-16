import { motion } from "framer-motion";
import { buttonVariants } from "../config/Animations";

type Props = {
  link: string;
  text: string;
  onClick: (link: string) => void;
  disabled?: boolean;
};
const PlayIcon = () => {
  return (
    <svg
      xmlns="http://www.w3.org/2000/svg"
      fill="none"
      height="1em"
      viewBox="0 0 128 128"
    >
      <circle cx={64} cy={64} r={64} fill="#7B61FF" />
      <path
        fill="#fff"
        d="M40 97V32c0-2 2-4 4-3l58 31c2 1 2 4 0 6L45 99c-2 2-5 0-5-2Z"
      />
    </svg>
  );
};

const PlayButton = ({ link, text, onClick , disabled}: Props) => {
  if (disabled){
    return (
      <motion.button
        variants={buttonVariants}
        initial="hidden"
        animate="visible"
        whileHover="hover"
        className="
          flex items-center justify-between gap-5 rounded-3xl border-[3px]
         border-[#7B61FF] bg-white p-3 px-5 text-3xl font-black 
         text-[#34364C] shadow-blue transition duration-200 ease-in-out  md:text-5xl
          cursor-not-allowed 
         "
        disabled
      >
        <p>{text}</p>
        <PlayIcon />
      </motion.button>
    );
  }
  
  return (
    <motion.button
      variants={buttonVariants}
      initial="hidden"
      animate="visible"
      whileHover="hover"
      className="
        flex items-center justify-between gap-5 rounded-3xl border-[3px]
       border-[#7B61FF] bg-white p-3 px-5 text-3xl font-black 
       text-[#34364C] shadow-blue transition duration-200 ease-in-out  md:text-5xl"
      onClick={() => onClick(link)}
    >
      <p>{text}</p>
      <PlayIcon />
    </motion.button>
  );
};

export default PlayButton;
