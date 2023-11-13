import { Navbar } from 'flowbite-react';
import EscButton from './EscButton';
import { ReactSVG } from 'react-svg';
import ButtonImg from './ButtonImg';

const CustomNavbar = () => {
  return (
    <Navbar  fluid className="bg-gray-600 w-[100vw] py-auto" >
      {/*  Desktop */}
      <div className='hidden md:flex w-[100vw] items-center place-content-between  px-5 h-100'>
        <EscButton/>
        <Navbar.Brand  href="/" >
            <ReactSVG src="/images/logo.svg"/>
        </Navbar.Brand>
        <ButtonImg id="settings_button" img='/images/Settings.svg' alt='Settings' size={50} event={() => {}}/>
      </div>

      {/*  Mobile */}
      <Navbar.Brand  href="/" className='md:hidden w-100 px-2 py-1' >
            <ReactSVG src="/images/logo.svg" />
        </Navbar.Brand>
      <Navbar.Toggle />
      <Navbar.Collapse className='md:hidden bg-white'>
          {/* <div className='flex w-100 p-2'>
              <ButtonImg id="settings_button" img='/images/Settings.svg' alt='Settings' size={20} event={() => {}}/>
              <p className='color-gray-500'>Settings</p>
          </div> */}
          <div className='w-100 p-3'>
            <EscButton  style={{'width': '100%'}}/>
          </div>

      </Navbar.Collapse>

    </Navbar>
  );
}

export default CustomNavbar;
