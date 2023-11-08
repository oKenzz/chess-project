import React from 'react';

type Props = {
    link: string;
    text: string
};

const PlayButton = ({ link, text}: Props) => {
    return (
        <button onClick={() => window.location.href = link}>
            <p>{text}</p>
            <img src="/images/CircledPlay.png" alt="Play Button Image"></img>
        </button>
    );
};

export default PlayButton;
