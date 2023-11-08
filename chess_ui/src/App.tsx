import React from 'react';
import { createBrowserRouter, RouterProvider} from "react-router-dom";
import {  motion } from 'framer-motion';
import "./styles/global.css";
import LandingPage from './pages/LandingPage';
import GamePage from './pages/GamePage';
import { pageVariants } from './config/Animations';

//Add page transitions to all pages
export function PageTransitions({ children }: {
  children: React.ReactNode;
}) {
  return (
    <motion.div
      variants={pageVariants}
      initial="initial"
      animate="enter"
      exit="exit"
      style={{ position: 'absolute', inset: 0 }}
    >
      {children}
    </motion.div>
  );
}

const routes = [
  {
    path: "/",
    element: (
      <PageTransitions>
        <LandingPage />
      </PageTransitions>
    ),
  },
  {
    path: "/game",
    element: (
        <GamePage />
    ),
  },
];
const router = createBrowserRouter(routes);
function App() {
  return (
    <RouterProvider router={router} />
  );
}

export default App;
