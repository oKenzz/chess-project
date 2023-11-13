/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    './src/**/*.{js,jsx,ts,tsx}',
    'node_modules/flowbite-react/**/*.{js,jsx,ts,tsx}'
],
  theme: {
    extend: {
      boxShadow: {
        blue: '0px 4px 4px 0px rgba(0, 0, 0, 0.25);'
        
      }
    },
  },
  plugins: [require('flowbite/plugin')],
};
