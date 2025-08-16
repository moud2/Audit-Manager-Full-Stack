import React from "react";
import { Box, Typography } from "@mui/material";
import { CircularProgressWithLabel } from "./CircularProgressWithLabel";

/**
 * LoadingScreen
 *
 * Displays a circular loading indicator with a message.
 *
 * @param {number} progress - The loading progress percentage (0-100).
 * @param {string} message - The message to display under the progress indicator.
 * @param {boolean} small - If true, displays a smaller loading indicator.
 */
function LoadingScreen({ progress = 0, message = "Loading...", small = false }) {
    return (
        <Box
            display="flex"
            flexDirection="column"
            alignItems="center"
            justifyContent="center"
            sx={small ? { padding: "8px" } : { height: "100vh" }}
        >
            <CircularProgressWithLabel value={progress} size={small ? 40 : 80} />
            <Typography variant="body2" color="error" sx={{ mt: 1 }}>
                {message}
            </Typography>
        </Box>
    );
}

export default LoadingScreen;
