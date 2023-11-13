import { motion } from "framer-motion"
import { buttonVariants } from "../config/Animations"
import '../styles/components/ButtonImg.module.css';
import Tippy from '@tippyjs/react'
import 'tippy.js/dist/tippy.css'


const ButtonImg = ({id, img, alt, size, event}:{
    id?: string;
    img: string;
    alt: string;
    size: number;
    event: () => void;
})=> {
    return(
        <Tippy content={alt}> 

            <motion.button
            variants={buttonVariants}
            whileHover="hover"
            onClick={() => event()}
            id={id}
            
            >
                <img src={img} alt={alt} width={size} height={size}/>
            </motion.button>
        </Tippy>
    )
}

export default ButtonImg