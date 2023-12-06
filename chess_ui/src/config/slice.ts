import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import Theme, { ChessTheme, ThemeEnum } from '../constants/theme';

export interface ThemeState {
  theme: ChessTheme;
  name: ThemeEnum;
}

const initialState: ThemeState = {
  theme: Theme.getTheme(Theme.getStorageTheme()), // Initialize with the default theme
  name: ThemeEnum.default
};

export const themeSlice = createSlice({
  name: 'theme',
  initialState,
  reducers: {
    setTheme: (state, action: PayloadAction<ThemeEnum>) => {
      // Update the theme based on the ThemeEnum value
      state.theme = Theme.getTheme(action.payload);
      state.name = action.payload;
      Theme.setStorageTheme(action.payload);
    },
  },
});

export const { setTheme } = themeSlice.actions;
export default themeSlice.reducer;
