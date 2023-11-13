import React from "react";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import LandingPage from './pages/LandingPage';
const GamePage = React.lazy(() => import('./pages/GamePage'));

const routes = [
  {
    index: true,
    path: "/",
    element: <LandingPage />,
  },
  {
    path: "/game",
    element: (
        <React.Suspense fallback={<div>Loading...</div>}>
          <GamePage />
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
