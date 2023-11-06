import { ReactSVG } from 'react-svg'
import '../styles/LandingPage.css'
import PlayButton from '../components/PlayButton';

const LandingPage = () => {
    return(
      <main>
        <div className='title'>
          <h1>Simple</h1>
          <h1>Chess</h1>
        </div>
        <div className="buttons">
          <PlayButton link='/game' text="Singleplayer" />
          <PlayButton link='/game' text="Multiplayer" />
        </div>

        <ReactSVG id='background' src="/images/Background.svg" />
      </main>
  )};
  
  export default LandingPage;