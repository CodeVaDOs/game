import Menu from "./Menu";
import {useState} from "react";
import SockJsClient from 'react-stomp';
import axios from "axios";
import GameField from "./GameField";

function App() {

    const [game, setGame] = useState(null);


    const createGame = ({name}) => {
        console.log(name)
        if (!name) return;
        axios.post("game/create", {name})
            .then(r => {
                setGame({
                    gameData: r.data,
                    playerTurn: "FIRST_PLAYER"
                });
            });
    }

    const connectByGameId = ({gameId, name}) => {
        if (!gameId || !name) return;
        axios.post(`game/connect/${gameId}`, {name})
            .then(r => {
                setGame({
                    gameData: r.data,
                    playerTurn: "SECOND_PLAYER"
                });
            });
    }

    const makeStep = (stepType) => {
        axios.post(`game/step/${game.gameData.id}`, {stepType, playerTurn: game.playerTurn});
            // .then(r => {
            //     setGame({
            //         gameData: r.data,
            //         playerTurn: "SECOND_PLAYER"
            //     });
            // });
    }

    const exit = () => {
        setGame(null);
    }

    return (
        <>
            <Menu createGame={createGame} connectByGameId={connectByGameId} exit={exit}/>

            <GameField makeStep={makeStep} game={game}/>

            {game && <SockJsClient
                url={"http://172.20.10.2:8080/game"}
                onMessage={(msg) => {
                    setGame(prev => ({
                        ...prev,
                        gameData: msg
                    }))
                }}
                topics={['/topic/game-progress/' + game.gameData.id]}/>}
        </>
    );
}

export default App;
