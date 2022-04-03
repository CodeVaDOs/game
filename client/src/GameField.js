import React from 'react';
import {
    Alert,
    Box,
    Button,
    CircularProgress,
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableRow,
    Typography
} from "@mui/material";
import moment from "moment";


const GameField = ({game, makeStep}) => {


    if (!game) return <></>;


    const player = (game.gameData.activeGameStepStatus === "WAITING_FOR_FIRST_PLAYER" ? "FIRST_PLAYER" : "SECOND_PLAYER");
    const showButtons = (game.gameData.activeGameStepStatus === "COMPLETED" || !game.gameData.activeGameStepStatus) || (player === game.playerTurn);

    return (
        <div>
            {game.gameData.activeGameStepStatus}
            {game.gameData.status === "NEW" && <Alert severity="info">Waiting for second player</Alert>}
            {game.gameData.status === "IN_PROGRESS" && <Alert severity="success">Let's start!</Alert>}
            <br/>
            <Typography variant="h5" align="center">ROUND: {game.gameData.roundNumber} | GAME
                ID: {game.gameData.id}</Typography>
            <Table>
                <TableHead>
                    <TableRow>
                        <TableCell align="center">{game.gameData.firstPlayerName}</TableCell>
                        <TableCell align="center">{game.gameData.secondPlayerName}</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    <TableRow>
                        <TableCell align="center">{game.gameData.firstPlayerScore}</TableCell>
                        <TableCell align="center">{game.gameData.secondPlayerScore}</TableCell>
                    </TableRow>
                </TableBody>
            </Table>

            <br/>

            {(game.gameData.activeGameStepStatus !== "COMPLETED" && game.gameData.activeGameStepStatus) && player !== game.playerTurn &&
            <Typography align="center"><CircularProgress/></Typography>}

            {showButtons && (
                <Box sx={{
                    display: "flex",
                    justifyContent: "space-around",
                    alignItems: "center"
                }}>
                    <Button variant="contained" onClick={() => {
                        makeStep("STONE")
                    }}>STONE
                    </Button>
                    <Button variant="contained" onClick={() => {
                        makeStep("SCISSORS")
                    }}>SCISSORS
                    </Button>
                    <Button variant="contained" onClick={() => {
                        makeStep("PAPER")
                    }}>PAPER
                    </Button>
                </Box>
            )}

            {game.gameData?.stepList &&
            <Table>
                <TableHead>
                    <TableRow>
                        <TableCell>Round date</TableCell>
                        <TableCell>Your step</TableCell>
                        <TableCell>Opponent step</TableCell>
                        <TableCell>Result</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {game.gameData?.stepList.sort((a,b) => {
                        return new Date(b.roundDate) - new Date(a.roundDate)
                    }).map(s => (
                        <TableRow>
                            <TableCell>{moment(s.roundDate).format("HH:mm:ss")}</TableCell>
                            <TableCell>{game.playerTurn === "FIRST_PLAYER" ? s.firstPlayerStepType : s.secondPlayerStepType}</TableCell>
                            <TableCell>{game.playerTurn === "FIRST_PLAYER" ? s.secondPlayerStepType : s.firstPlayerStepType}</TableCell>
                            <TableCell>{s.winner === game.playerTurn ? "You win" : "You lose"}</TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>}

        </div>
    );
};

export default GameField;