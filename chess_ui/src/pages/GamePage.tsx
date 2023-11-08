import '../styles/GamePage.css'
import Chess from "../components/Chess";

const GamePage = () => {
    return(

      <div className="container">
        <div className="headers"></div>
        <div className="game-panel">
          <Chess color='white' />
        </div>
        <div className="left-panel"></div>
        <div className="right-panel"></div>
      </div>
    
    )
  };
  
  export default GamePage;