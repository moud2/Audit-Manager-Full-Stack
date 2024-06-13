import React from 'react';
import LinearProgress from '@mui/material/LinearProgress';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import CircularProgress from '@mui/material/CircularProgress';
import {BarChart} from '@mui/x-charts/BarChart';


function Evaluation() {

    let progress = 70;


    // Setup function progress bar
    function LinearProgressWithLabel(props) {
        return (
            <Box sx={{display: 'flex', alignItems: 'center'}}>
                <Box sx={{width: '100%', mr: 1}}>
                    <LinearProgress variant="determinate" {...props} />
                </Box>
                <Box sx={{minWidth: 35}}>
                    <Typography variant="body2" color="text.secondary">{`${Math.round(
                        props.value,
                    )}%`}</Typography>
                </Box>
            </Box>
        );
    }

    // Setup progress circle
    function CircularProgressWithLabel(props) {
        return (
            <Box sx={{position: 'relative', display: 'inline-flex'}}>
                <CircularProgress variant="determinate" {...props} />
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
                        {`${Math.round(props.value)}%`}
                    </Typography>
                </Box>
            </Box>
        );
    }

    return (
        <div>
            <div id="title">
                <h1 className="text-center text-4xl m-20">Evaluation</h1>
            </div>

            <div id="result" className={"flex justify-center h-20"}>
                <Box sx={{width: '80%'}}>
                    <LinearProgressWithLabel value={progress}/>
                    <p className={"justify-center"}>Gesamtfortschritt</p>
                </Box>
            </div>

            <div id="categories">
                <CircularProgressWithLabel value={progress}/>
            </div>

            <div id="result_per_question">
                <BarChart
                    series={[
                        {data: [4, 2, 5, 4, 1], stack: 'A', label: 'Series A1'},
                        {data: [2, 8, 1, 3, 1], stack: 'A', label: 'Series A2'},
                        {data: [14, 6, 5, 8, 9], label: 'Series B1'},
                    ]}
                    barLabel={(item, context) => {
                        if ((item.value ?? 0) > 10) {
                            return 'High';
                        }
                        return context.bar.height < 60 ? null : item.value?.toString();
                    }}
                    width={600}
                    height={350}
                />
            </div>
        </div>

    )
}

export default Evaluation;