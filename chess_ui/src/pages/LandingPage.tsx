import { ReactSVG } from 'react-svg'
import '../styles/LandingPage.css'
import PlayButton from '../components/PlayButton';

const LandingPage = () => {
    return(
      <main className="main-landingpage">
    
        <div className="div1"> </div>
        <div className="div2"> </div>

        <div className="div3"> 
            <div className='title'>
                <h1>Simple</h1>
                <h1>Chess</h1>
            </div>
        </div>

        <div className="div4"> 
            <div className="buttons">
            <PlayButton link='/game' text="Singleplayer" />
            <PlayButton link='/game' text="Multiplayer" />
            </div>
        </div>
      
        <ReactSVG id='background' src="/images/Background.svg" />
      </main>
  )};
  
  export default LandingPage;