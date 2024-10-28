import React from 'react';
import { BarChart } from '@mui/x-charts/BarChart';

const CustomBarChart = ({ data, colors, width = 900, height = 350 }) => {
    return (
        <BarChart
            series={[
                { data: data },
            ]}
            width={width}
            height={height}
            xAxis={[
                {
                    scaleType: 'band',
                    data: [0, 1, 2, 3, 4, 5],
                    colorMap: {
                        type: 'ordinal',
                        values: [0, 1, 2, 3, 4, 5],
                        colors: colors,
                        unknownColor: "#050505",
                    },
                    label: 'erreichte Punkte',
                },
            ]}
            yAxis={[
                {
                    label: 'Anzahl Fragen',
                },
            ]}
        />
    );
};

export default CustomBarChart;
