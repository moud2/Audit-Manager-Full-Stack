import React from 'react';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import CircularProgress from '@mui/material/CircularProgress';
import {BarChart} from '@mui/x-charts/BarChart';
import LinearProgress from '@mui/material/LinearProgress';
import PropTypes from 'prop-types';

function LinearProgressWithLabel({ value }) {
    return (
        <Box sx={{ display: 'flex', alignItems: 'center' }}>
            <Box sx={{ width: '100%', mr: 1 }}>
                <LinearProgress variant="determinate" value={value} />
            </Box>
            <Box sx={{ minWidth: 35 }}>
                <Typography variant="body2" color="text.secondary">{`${Math.round(value)}%`}</Typography>
            </Box>
        </Box>
    );
}

LinearProgressWithLabel.propTypes = {
    value: PropTypes.number.isRequired,  // Prop validation for 'value'
};

function CircularProgressWithLabel({ value }) {
    return (
        <Box sx={{ position: 'relative', display: 'inline-flex' }}>
            <CircularProgress variant="determinate" value={value} size={80} />
            <Box
                sx={{
                    top: 0,
                    left: 0,
                    bottom: 0,
                    right: 0,
                    position: 'absolute',
                    display: 'flex',
                    alignItems: 'center',
                    justifyContent: 'center',
                }}
            >
                <Typography variant="caption" component="div" color="text.secondary">
                    {`${Math.round(value)}%`}
                </Typography>
            </Box>
        </Box>
    );
}

CircularProgressWithLabel.propTypes = {
    value: PropTypes.number.isRequired,  // Prop validation for 'value'
};

function Evaluation() {
    let progress = 70;

    const colors = ['#a50026', '#d73027', '#fdae61', '#d9ef8b', '#66bd63', '#006837'];

    return (
        <div>
            <div id="title">
                <h1 className="text-center text-4xl m-6">Evaluation</h1>
            </div>

            <div id="result" className="flex flex-col justify-center items-center h-20">
                <Box className="text-center" sx={{ width: '80%' }}>
                    <LinearProgressWithLabel value={progress} />
                </Box>
                <p className="text-center text-xl">Gesamtfortschritt</p>
            </div>

            <div id="categories" className="flex flex-row justify-center items-center">
                {colors.map((color, index) => (
                    <div key={index} className="ml-6 m-8 text-center">
                        <CircularProgressWithLabel value={progress} />
                        <p className="text-center">Kategorie {index + 1}</p>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default Evaluation;
