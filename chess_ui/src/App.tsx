import React from 'react';
import { createBrowserRouter, RouterProvider, } from "react-router-dom";
import "./styles/global.css";
import LandingPage from './pages/LandingPage';
import GamePage from './pages/GamePage'


const router = createBrowserRouter([
  {
    path: "/",
    element: <LandingPage />,
  },
  {
    path: "/game",  
    element: <GamePage />
  }
]);

function App() {
  return (
    <RouterProvider router={router} />
  );
}

export default App;
