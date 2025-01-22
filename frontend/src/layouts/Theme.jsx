import { createTheme, ThemeProvider as MuiThemeProvider } from '@mui/material/styles';

const theme = createTheme({
    palette: {
        primary: {
            main: '#c4171f', // Dein Rot für Schrift und Umrandung
        },
        background: {
            default: '#e0e0e0', // Optional: Grau für die offene Leiste
        },
    },
    components: {
        MuiButton: {
            defaultProps: {
                variant: 'outlined', // Setzt outlined als Standard
            },
        },
    },
});

const CustomThemeProvider = ({ children }) => {
    return <MuiThemeProvider theme={theme}>{children}</MuiThemeProvider>;
};

export default CustomThemeProvider;