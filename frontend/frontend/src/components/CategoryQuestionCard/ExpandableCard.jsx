import {Card, CardActions, CardContent, CardHeader, Collapse, IconButton} from "@mui/material";
import {styled} from "@mui/material/styles";
import {useState} from "react";
import {ExpandMoreOutlined} from "@mui/icons-material";
import DeleteIcon from '@mui/icons-material/Delete';


const ExpandMore = styled((props) => {
    const { expand, ...other } = props;
    return <IconButton {...other} />;
})(({ theme }) => ({
    marginLeft: 'auto',
    transition: theme.transitions.create('transform', {
        duration: theme.transitions.duration.shortest,
    }),
    variants: [
        {
            props: ({ expand }) => !expand,
            style: {
                transform: 'rotate(0deg)',
            },
        },
        {
            props: ({ expand }) => !!expand,
            style: {
                transform: 'rotate(180deg)',
            },
        },
    ],
}));

export default function ExpandableCard({children, onExpandChange, title, onDelete}) {
    const [expanded, setExpanded] = useState(false);

    const handleExpandClick = () => {
        onExpandChange?.(!expanded)
        setExpanded(!expanded);
    };

        const handleDeleteClick = () => {
        onDelete?.();
    };


    return (
        <Card sx={{padding: 0}}>
            <CardHeader            
                data-cy="expandable-card-title"
                title={title}
                action={
                    <div style={{ display: 'flex', alignItems: 'center' }}>
                        <IconButton data-cy="expandable-card-delete-button" onClick={handleDeleteClick}>
                            <DeleteIcon />
                        </IconButton>
                                
                        <ExpandMore
                            expand={expanded}
                            onClick={handleExpandClick}
                            aria-expanded={expanded}
                            aria-label="show more"
                        >
                            <ExpandMoreOutlined data-cy="expandable-card-button"/>
                        </ExpandMore>
                    </div>
                }
            >
            </CardHeader>
            <Collapse in={expanded} timeout="auto" unmountOnExit>
                <CardContent sx={{
                    padding: "0 0 0 0 !important",
                }}>
                    {children}
                </CardContent>
            </Collapse>
        </Card>
    );



}