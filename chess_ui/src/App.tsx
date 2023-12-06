import React from "react";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import LandingPage from './pages/LandingPage';
import { Provider } from "react-redux";
import store from "./config/store";
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
      <Provider store={store}>
        <React.Suspense fallback={<div></div>}>
          <MultiPlayerGame />
        </React.Suspense>
      </Provider>
      )
  },
  {
    path: "/singleplayer",
    element: (
      <Provider store={store}>
        <React.Suspense fallback={<div></div>}>
          <SinglePlayerGame />
        </React.Suspense>
      </Provider>
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
