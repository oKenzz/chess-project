import '../styles/components/PlayButton.css';
type Props = {
    link: string;
    text: string;
    onClick: (link: string) => void;
};

const PlayButton = ({ link, text, onClick }: Props) => {
    return (
        <button
            className="play-button"
            onClick={() => onClick(link)}>
            <p>{text}</p>
            <img src="/images/CircledPlay.png" alt="Play Button" />
        </button>
    );
};

export default PlayButton;
