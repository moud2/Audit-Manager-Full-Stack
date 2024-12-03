import React, { useRef, useEffect } from 'react';
import PropTypes from 'prop-types';
import { Chart, RadarController, RadialLinearScale, PointElement, LineElement, Filler, Tooltip, Legend } from 'chart.js';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';

// register Chart.js-components
Chart.register(RadarController, RadialLinearScale, PointElement, LineElement, Filler, Tooltip, Legend);

/**
 * A radar chart component built with Chart.js.
 * Displays a radar chart with dynamic data and options, and an optional title.
 * @param {Object} props - Component properties.
 * @param {Object} props.data - The data for the radar chart, following the Chart.js format.
 * @param {Object} props.options - Configuration options for the radar chart, following the Chart.js format.
 * @param {string} [props.title] - Optional title displayed above the radar chart.
 * @param {number} [props.size=400] - The size (width and height) of the radar chart.
 * @returns {JSX.Element} A radar chart component.
 */
const RadarChart = ({ data, options, title, size = 400 }) => {
    const canvasRef = useRef(null);

    useEffect(() => {
        // Init Chart
        const ctx = canvasRef.current.getContext('2d');
        const chartInstance = new Chart(ctx, {
            type: 'radar',
            data,
            options,
        });

        return () => {
            chartInstance.destroy();
        };
    }, [data, options]);

    return (
        <Box sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center', width: size }}>
            {title && (
                <Typography variant="h6" color="text.primary" sx={{ mb: 2 }}>
                    {title}
                </Typography>
            )}
            <Box sx={{ position: 'relative', width: size, height: size }}>
                <canvas ref={canvasRef} width={size} height={size}></canvas>
            </Box>
        </Box>
    );
};

// PropTypes-Definitions
RadarChart.propTypes = {
    data: PropTypes.object.isRequired,
    options: PropTypes.object.isRequired,
    title: PropTypes.string,
    size: PropTypes.number,
};

// default props
RadarChart.defaultProps = {
    title: '',
    size: 400,
};

export default RadarChart;
