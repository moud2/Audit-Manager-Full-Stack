import React from 'react';
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import CircularProgress from '@mui/material/CircularProgress';
import {BarChart} from '@mui/x-charts/BarChart';
import LinearProgress from '@mui/material/LinearProgress';


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
                <CircularProgress variant="determinate" {...props} sx={{width: '200px', height: '200px'}}/>
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

            <div id="result" className={"flex flex-col justify-center items-center h-20"}>
                <Box className={"text-center"} sx={{width: '80%'}}>
                    <LinearProgressWithLabel value={progress}/>
                </Box>
                <p className={"text-center"}>Gesamtfortschritt</p>
            </div>

            <div id="categories" className={"flex flex-row justify-center items-center"}>
                <div className={"ml-6 m-8 text-center"}>
                    <CircularProgressWithLabel value={progress} className={"size-60"}/>
                    <p className={"text-center"}>Kategorie 1</p>
                </div>
                <div className={"ml-6 m-8 text-center"}>
                    <CircularProgressWithLabel value={progress} className={"size-60"}/>
                    <p className={"text-center"}>Kategorie 2</p>
                </div>
                <div className={"ml-6 m-8 text-center"}>
                    <CircularProgressWithLabel value={progress} className={"size-60"}/>
                    <p className={"text-center"}>Kategorie 3</p>
                </div>
                <div className={"ml-6 m-8 text-center"}>
                    <CircularProgressWithLabel value={progress} className={"size-60"}/>
                    <p className={"text-center"}>Kategorie 4</p>
                </div>
                <div className={"ml-6 m-8 text-center"}>
                    <CircularProgressWithLabel value={progress} className={"size-60"}/>
                    <p className={"text-center"}>Kategorie 5</p>
                </div>
                <div className={"ml-6 m-8 text-center"}>
                    <CircularProgressWithLabel value={progress} className={"size-60"}/>
                    <p className={"text-center"}>Kategorie 6</p>
                </div>
            </div>

            <div id="result_per_question" className={"flex flex-row justify-center items-center"}>
                <BarChart
                    series={[
                        {data: [4, 2, 8, 4, 1, 3], stack: 'A', label: 'Anzahl Fragen nach Punktzahl'},
                    ]}
                    barLabel={(item, context) => {
                        if ((item.value ?? 0) > 10) {
                            return 'High';
                        }
                        return context.bar.height < 60 ? null : item.value?.toString();
                    }}
                    width={1200}
                    height={350}
                />
            </div>

        </div>

    )
}

export default Evaluation;