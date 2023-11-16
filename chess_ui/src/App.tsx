import React from "react";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import LandingPage from './pages/LandingPage';
const MultiPlayerGame = React.lazy(() => import('./pages/MultiPlayerGame'));
const SinglePlayerGame = React.lazy(() => import('./pages/SinglePlayerGame'));
const routes = [
  
  {
    index: true,
    path: "/",
    element: <LandingPage />,
  },
  {
    path: "/multiplayer",
    element: (
        <React.Suspense fallback={<div></div>}>
          <MultiPlayerGame />
        </React.Suspense>
      )
  },
  {
    path: "/singleplayer",
    element: (
        <React.Suspense fallback={<div></div>}>
          <SinglePlayerGame />
        </React.Suspense>
      )
  },
];

const router = createBrowserRouter(routes);

function App() {
  return (
    <RouterProvider router={router} />
  );
}

export default App;
