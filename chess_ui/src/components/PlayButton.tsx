import React from 'react';

type Props = {
    link: string;
    text: string
};

const PlayButton = ({ link, text}: Props) => {
    return (
        <button onClick={() => window.location.href = link}>
            <p>{text}</p>
            <img src="/images/CircledPlay.png"></img>
        </button>
    );
};

export default PlayButton;
