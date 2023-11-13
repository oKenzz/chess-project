
// Play Button Animation
export const buttonVariants = {
    hidden: {
        scale: 0.8, // start a bit smaller
        opacity: 0 // start transparent
    },
    visible: {
        scale: 1, // animate to normal size
        opacity: 1, // animate to fully visible
        transition: {
            duration: 0.1, // the duration of the animation
            ease: 'easeInOut', // easing function to use for a smooth effect
            delay:0.5,
        }
    },
    hover: {
        scale: 1.1, // grow when hovered
        transition: {
            duration: 0.1, // the duration of the animation
            ease: 'easeInOut', // easing function to use for a smooth effect
        },
    },
    tap: {
        scale: 0.95, // slightly shrink when tapped or clicked
        transition: {
            duration: 0.1 // how long the tap effect should take
        }
    }
};


// Landign page animation
export const backgroundVariants = {
    initial: {
        x: '100vw', // Start off-screen to the right
        y: '100vh', // Start off-screen at the bottom
        rotate: 90, // Starts rotated 90 degrees
        opacity: 0,
        originX: 1, // Rotation origin at the right
        originY: 1, // Rotation origin at the bottom
        zIndex: -99 // Ensure the background is behind other elements
    },
    animate: {
        x: 0, // End at the natural x position
        y: 0, // End at the natural y position
        rotate: 0, // End rotation at 0 degrees
        opacity: 1,
        transition: {
            duration: 1, // Slower transition duration
            ease: "easeInOut", // Ease in and out for a smooth animation
        }
    }
};
export const itemVariants = {
    hidden: { 
        opacity: 0,
        y: 50
    },
    visible: { 
        opacity: 1,
        y: 0,
        transition: {
            type: "spring",
            stiffness: 100,
            delay: 0.5,
            duration: 5
        }
    }
};


// General page animation
export const pageExitVariants = {
    hidden: {
      x: '100vw', // Slide out to the right
      transition: {
        duration: 0.5,
        ease: 'easeInOut',
      },
    },
  };


// Game page animation
export const pageVariants = {
    initial: { opacity: 0 },
    enter: { opacity: 1, transition: { duration: 0.5 } },
    exit: { opacity: 0, transition: { duration: 0.5 } },
};

export const pageTransition = {
    type: "tween",
    ease: "anticipate",
    duration: 0.5
};