import { createTheme } from "@mui/material/styles";
import { colors } from "./colors";

export const theme = createTheme({
  palette: {
    primary: {
      main: colors.primary,
      dark: colors.primaryDark,
      light: colors.primaryLight,
      contrastText: colors.white,
    },

    secondary: {
      main: colors.accentPurple,
    },

    success: {
      main: colors.success,
    },

    warning: {
      main: colors.warning,
    },

    error: {
      main: colors.danger,
    },

    text: {
      primary: colors.textDark,
      secondary: colors.textGrey,
    },

    background: {
      default: colors.background,
      paper: colors.white,
    },
  },

  typography: {
    fontFamily: "Roboto, Arial",
    button: {
      textTransform: "none",
      fontWeight: 600,
    },
  },

  shape: {
    borderRadius: 12,
  },
});
