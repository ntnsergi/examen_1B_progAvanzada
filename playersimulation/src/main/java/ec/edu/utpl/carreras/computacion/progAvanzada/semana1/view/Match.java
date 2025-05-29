package ec.edu.utpl.carreras.computacion.progAvanzada.semana1.view;

import java.util.*;
import java.util.concurrent.Callable;

public class Match implements Callable<MatchResult> {
    private final String player1;
    private final String player2;
    private static final Random random = new Random();

    public Match(String player1, String player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    @Override
    public MatchResult call() throws Exception {
        int setsP1 = 0;
        int setsP2 = 0;
        List<String> setResults = new ArrayList<>();
        int setNumber = 1;

        while (setsP1 < 2 && setsP2 < 2) {
            int score1 = random.nextInt(6);
            int score2 = random.nextInt(6);
            while (score1 == score2) {
                score2 = random.nextInt(6);
            }

            String winner;
            if (score1 > score2) {
                setsP1++;
                winner = player1;
            } else {
                setsP2++;
                winner = player2;
            }

            setResults.add("Set " + setNumber + ": " + winner);
            setNumber++;

            Thread.sleep(1500 + random.nextInt(501)); // 1.5s - 2s
        }

        String matchWinner = setsP1 == 2 ? player1 : player2;
        return new MatchResult(player1, player2, setResults, matchWinner);
    }
}

