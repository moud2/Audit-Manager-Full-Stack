import { Radar } from 'react-chartjs-2';
import {
    Chart as ChartJS,
    RadialLinearScale,
    PointElement,
    LineElement,
    Filler,
    Tooltip,
    Legend,
} from 'chart.js';

ChartJS.register(RadialLinearScale, PointElement, LineElement, Filler, Tooltip, Legend);

/**
 * RadarChart component renders a radar chart with the given data.
 *
 * @component
 * @param {Object} props - The properties passed to the component.
 * @param {Array<string>} props.labels - Array of category names to display on the radar chart.
 * @param {Array<number>} props.currentData - Array of current progress percentages for each category.
 * @param {Array<number>} props.overallData - Array of overall progress percentages for each category.
 * @returns {JSX.Element} A radar chart displaying category progress data.
 */
function RadarChart({ labels, currentData, overallData }) {
    const data = {
        labels,
        datasets: [
            {
                label: 'Current Progress',
                data: currentData,
                backgroundColor: 'rgba(75, 192, 192, 0.2)',
                borderColor: 'rgba(75, 192, 192, 1)',
                borderWidth: 2,
                pointBackgroundColor: 'rgba(75, 192, 192, 1)',
                pointBorderColor: '#fff',
                pointHoverBackgroundColor: '#fff',
                pointHoverBorderColor: 'rgba(75, 192, 192, 1)',
            },
            {
                label: 'Overall Progress',
                data: overallData,
                backgroundColor: 'rgba(153, 102, 255, 0.2)',
                borderColor: 'rgba(153, 102, 255, 1)',
                borderWidth: 2,
                pointBackgroundColor: 'rgba(153, 102, 255, 1)',
                pointBorderColor: '#fff',
                pointHoverBackgroundColor: '#fff',
                pointHoverBorderColor: 'rgba(153, 102, 255, 1)',
            },
        ],
    };

    const options = {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
            r: {
                beginAtZero: true,
                max: 100,
            },
        },
        plugins: {
            legend: {
                position: 'top',
            },
            tooltip: {
                callbacks: {
                    label: function (tooltipItem) {
                        return `${tooltipItem.dataset.label}: ${tooltipItem.raw}%`;
                    },
                },
            },
        },
    };

    return (
        <div className="p-4" style={{ height: '400px', width: '100%' }}>
            <Radar data={data} options={options} />
        </div>
    );
}

export default RadarChart;
