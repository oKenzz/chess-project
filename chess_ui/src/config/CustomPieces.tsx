interface PieceCreator {
    (args: {
      isDragging: boolean;
      squareWidth: number;
      droppedPiece: any;
      targetSquare: any;
      sourceSquare: any;
    }): JSX.Element;
  }
  
  // Define the type for the customPieces object
  interface CustomPieces {
    [pieceCode: string]: PieceCreator;
  }
  
  // Define global styles for all pieces
// Define global styles for all pieces
  const globalPieceStyle = (isDragging: boolean, squareWidth: number) => {
    return isDragging ? {
      width: squareWidth * 0.9,
      opacity: 1,
    } : {
      width: squareWidth * 0.8,
      opacity: 1,
      margin: squareWidth * 0.1,
    };
  };

  const customPieces: CustomPieces = {
    wP: ({ isDragging, squareWidth }) => {
      return <img src="/images/pieces/Piece=Pawn, Side=White.svg" style={globalPieceStyle(isDragging, squareWidth)} alt="White Pawn" />;
    },
    wB: ({ isDragging, squareWidth }) => {
      return <img src="/images/pieces/Piece=Bishop, Side=White.svg" style={globalPieceStyle(isDragging, squareWidth)} alt="White Bishop" />;
    },
    wN: ({ isDragging, squareWidth }) => {
      return <img src="/images/pieces/Piece=Knight, Side=White.svg" style={globalPieceStyle(isDragging, squareWidth)} alt="White Knight" />;
    },
    wR: ({ isDragging, squareWidth }) => {
      return <img src="/images/pieces/Piece=Rook, Side=White.svg" style={globalPieceStyle(isDragging, squareWidth)} alt="White Rook" />;
    },
    wQ: ({ isDragging, squareWidth }) => {
      return <img src="/images/pieces/Piece=Queen, Side=White.svg" style={globalPieceStyle(isDragging, squareWidth)} alt="White Queen" />;
    },
    wK: ({ isDragging, squareWidth }) => {
      return <img src="/images/pieces/Piece=King, Side=White.svg" style={globalPieceStyle(isDragging, squareWidth)} alt="White King" />;
    },
    bP: ({ isDragging, squareWidth }) => {
      return <img src="/images/pieces/Piece=Pawn, Side=Black.svg" style={globalPieceStyle(isDragging, squareWidth)} alt="Black Pawn" />;
    },
    bB: ({ isDragging, squareWidth }) => {
      return <img src="/images/pieces/Piece=Bishop, Side=Black.svg" style={globalPieceStyle(isDragging, squareWidth)} alt="Black Bishop" />;
    },
    bN: ({ isDragging, squareWidth }) => {
      return <img src="/images/pieces/Piece=Knight, Side=Black.svg" style={globalPieceStyle(isDragging, squareWidth)} alt="Black Knight" />;
    },
    bR: ({ isDragging, squareWidth }) => {
      return <img src="/images/pieces/Piece=Rook, Side=Black.svg" style={globalPieceStyle(isDragging, squareWidth)} alt="Black Rook" />;
    },
    bQ: ({ isDragging, squareWidth }) => {
      return <img src="/images/pieces/Piece=Queen, Side=Black.svg" style={globalPieceStyle(isDragging, squareWidth)} alt="Black Queen" />;
    },
    bK: ({ isDragging, squareWidth }) => {
      return <img src="/images/pieces/Piece=King, Side=Black.svg" style={globalPieceStyle(isDragging, squareWidth)} alt="Black King" />;
    },
  };
  
  
export default customPieces;