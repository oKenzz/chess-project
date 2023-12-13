# Chess Project

## Description

Welcome to the Chess Project! This repository contains a React-based user interface and a Maven-powered backend for a comprehensive digital chess experience. Engage in matches against an AI opponent or challenge friends in a multiplayer setting.

## Demo
https://chess-project-phi.vercel.app/

## Features

- **React Frontend**: A modern, responsive user interface for an immersive chess experience.
- **Maven Backend**: A robust Java backend using Maven for dependency management and build automation.
- **AI Opponent**: Test your skills against a computer-controlled adversary.
- **Local Multiplayer**: Play with friends on the same machine.
- **Game Saving**: Pause and resume your game at any time.
- **Chess Timer**: Keep your matches engaging with a time-controlled environment.
- **Undo Feature**: Step back and revise your strategy by undoing moves.

## Installation

### Chess Backend Setup

To set up the Maven-based backend, follow these steps:

```bash
# Navigate to the backend directory
cd chess_backend
./mvnw spring-boot:run
```

### Chess UI Setup

To set up the React frontend, you will need to have Node.js and npm installed. Then run the following commands:

```bash
# Navigate to the frontend directory
cd chess_ui
yarn install
yarn start dev
```

The React application will run on `localhost:3000` by default.

## Usage

After setting up both the backend and the frontend, open your web browser and go to `http://localhost:3000` to start playing chess.

## How to Play

- Launch the chess UI from your browser.
- The backend will manage the game logic and state.
- Select your game mode: AI or Multiplayer.
- Use the React interface to move the chess pieces according to the rules of chess.
- Win by checkmating your opponent's king!


## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
