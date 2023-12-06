export type ChessTheme = {
    customDarkSquareStyle: string;
    customLightSquareStyle: string;
    customPremoveDarkSquareStyle: string;
    customPremoveLightSquareStyle: string;
}
export enum ThemeEnum {
    default = "default",
    classic = "classic",
    dark = "dark",
    christmas = "christmas"
}

class Theme {
    static defaultTheme: ChessTheme = {
        customDarkSquareStyle: '#B7C0D8',
        customLightSquareStyle: '#E8EDF9',
        customPremoveDarkSquareStyle: '#d64040',
        customPremoveLightSquareStyle: '#eb6a6a',
    }
    static classicTheme: ChessTheme = {
        customDarkSquareStyle: '#b58863',
        customLightSquareStyle: '#f0d9b5',
        customPremoveDarkSquareStyle: '#b58863',
        customPremoveLightSquareStyle: '#f0d9b5',
    }
    static darkTheme: ChessTheme = {
        customDarkSquareStyle: '#4a4a4a', // A darker shade for better contrast
        customLightSquareStyle: '#c2c2c2', // A lighter shade for distinction, but not too bright
        customPremoveDarkSquareStyle: '#686868', // A slightly different shade for premove indication
        customPremoveLightSquareStyle: '#e0e0e0', // A soft light color for premove on light squares
    }
    static christmasTheme: ChessTheme = {
        customDarkSquareStyle: '#769656',
        customLightSquareStyle: '#eee',
        customPremoveDarkSquareStyle: '#769656',
        customPremoveLightSquareStyle: '#eee',
    }
    public static setStorageTheme = ( theme: ThemeEnum ) => {
        localStorage.setItem('theme', theme);
    }
    public static getStorageTheme = () : ThemeEnum => {
        try {
            const  theme = localStorage.getItem('theme') || ThemeEnum.default as ThemeEnum;
            return theme as ThemeEnum;
        } catch (error) {
            console.log(error);
            return ThemeEnum.default;
        }
    }
    public static getTheme = ( theme: ThemeEnum ) => {
        switch (theme) {
            case ThemeEnum.default:
                return Theme.defaultTheme;
            case ThemeEnum.classic:
                return Theme.classicTheme;
            case ThemeEnum.dark:
                return Theme.darkTheme;
            case ThemeEnum.christmas:
                return Theme.christmasTheme;
            default:
                return Theme.defaultTheme;
        }
    }
    
 
}

export default Theme;
