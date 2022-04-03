import React, {useState} from 'react';
import {
    Accordion,
    AccordionDetails,
    AccordionSummary,
    Button,
    Grid,
    TextField,
    Typography
} from "@mui/material";
import {makeStyles} from "@mui/styles";

const useStyles = makeStyles(() => ({
    button: {
        height: '60px',
        width: '100%'
    },
    field: {
        width: '100%'
    }
}))

const Menu = ({connectByGameId, createGame, exit}) => {
    const [data, setData] = useState({
        name: "",
        gameId: ""
    });

    const [isOpen, setOpen] = useState(true);

    const classes = useStyles();

    const handleChange = (e) => {
        setData(prev => ({
            ...prev,
            [e.target.name]: e.target.value
        }))
    }

    const handleOpenMenu = () => {
        setOpen(prev => !prev);
    }


    return (
       <>
          <Accordion onChange={handleOpenMenu} expanded={isOpen}>
              <AccordionSummary  aria-controls="menu-content" id="menu-header">
                  <Typography>MENU</Typography>
              </AccordionSummary>
              <AccordionDetails>
                  <Grid container>
                      <Grid xs={12}>

                      </Grid>

                      <Grid xs={12}>
                          <TextField className={classes.field} onChange={handleChange} value={data.name} type="text" name="name" placeholder="Enter your name"/>
                          <TextField className={classes.field} onChange={handleChange} value={data.gameId} type="text" name="gameId"
                                     placeholder="Enter game ID"/>
                      </Grid>

                      <Grid xs={12}>
                          <Button className={classes.button} variant="outlined" onClick={e => {
                              createGame(data);
                              handleOpenMenu();
                          }}>Create new game
                          </Button>
                          <Button className={classes.button} variant="outlined" onClick={e => {
                              connectByGameId(data);
                              handleOpenMenu();
                          }}>Connect by game id
                          </Button>

                          <Button className={classes.button} variant="outlined" onClick={exit}>Exit</Button>
                      </Grid>
                  </Grid>
              </AccordionDetails>
          </Accordion>

       </>
    );
};

export default Menu;