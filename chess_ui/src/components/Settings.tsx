import { Modal } from 'flowbite-react';
import React, { useEffect, useState } from 'react';
import  {  ThemeEnum } from '../constants/theme';
import { useDispatch, useSelector } from 'react-redux';
import { ThemeState, setTheme } from '../config/slice';
const Settings = (
  { 
    showModal,
    setShowModal,
  
  } : { 
    showModal : boolean,
    setShowModal : (show: boolean) => void,
  }
) => {
  const [currentTheme, setCurrentTheme] = useState('default');
  const dispatch = useDispatch();
  const themeSettings =  useSelector((state: any) => state.theme) as ThemeState;
  useEffect(() => {
    setCurrentTheme( themeSettings.name );
  }, [themeSettings]);

  const handleThemeChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    const newTheme = event.target.value as ThemeEnum;
    dispatch(setTheme(newTheme));
    setCurrentTheme(newTheme);
  };

  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    // Save theme settings
    setShowModal(false);
  };

  return (
    <>
      <Modal show={showModal} onClose={() => setShowModal(false)}>
        <Modal.Header>
          <h3 className='text-3xl text-gray-700'
          >Settings</h3>
        </Modal.Header>
        <Modal.Body>
            <form onSubmit={handleSubmit}>
                <label className='form-label mb-3 mr-6 text-gray-700'>
                    Theme:
                    <select  className='form-select ml-3'
                      value={currentTheme} onChange={handleThemeChange}>
                        {Object.keys(ThemeEnum).map((theme) => (
                            <option key={theme} value={theme}>
                                {theme.charAt(0).toUpperCase() + theme.slice(1)}
                            </option>
                        ))}
                    </select>
                </label>
            </form>
        </Modal.Body>
      </Modal>
    </>
  );
};

export default Settings;
