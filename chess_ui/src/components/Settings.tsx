import { Button, Modal } from 'flowbite-react';
import React, { useState } from 'react';
import { ThemeEnum } from '../constants/theme';

const Settings = (
  { 
    showModal,
    setShowModal,
    changeTheme,
  } : { 
    showModal : boolean,
    setShowModal : (show: boolean) => void,
    changeTheme : (theme: ThemeEnum) => void,
  }
) => {
  const [theme, setTheme] = useState('light');

  const handleThemeChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    setTheme(event.target.value);
    changeTheme(event.target.value as ThemeEnum);
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
                      value={theme} onChange={handleThemeChange}>
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
