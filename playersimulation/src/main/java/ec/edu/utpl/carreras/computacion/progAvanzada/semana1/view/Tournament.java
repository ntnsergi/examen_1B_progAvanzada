package ec.edu.utpl.carreras.computacion.progAvanzada.semana1.view;

import java.util.*;
import java.util.concurrent.*;

public class Tournament {
    private static final int TOTAL_PLAYERS = 16;
    private final ExecutorService executor = Executors.newFixedThreadPool(8);

    private static final String[] ROUND_NAMES = {
            "====OCTAVOS DE FINAL====",
            "====CUARTOS DE FINAL====",
            "====SEMIFINAL====",
            "====FINAL===="
    };

    public void start() {
        List<String> players = new ArrayList<>();
        for (int i = 1; i <= TOTAL_PLAYERS; i++) {
            players.add("Jugador " + i);
        }

        int round = 0;
        while (players.size() > 1) {
            System.out.println("\n" + ROUND_NAMES[round]);
            List<Callable<MatchResult>> matches = new ArrayList<>();

            int n = players.size();
            for (int i = 0; i < n / 2; i++) {
                String p1 = players.get(i);
                String p2 = players.get(n - 1 - i);
                matches.add(new Match(p1, p2));
            }

            try {
                List<Future<MatchResult>> results = executor.invokeAll(matches);

                List<String> winners = new ArrayList<>();
                for (Future<MatchResult> result : results) {
                    MatchResult matchResult = result.get();
                    System.out.println(matchResult);
                    winners.add(matchResult.winner());
                }

                players = winners; // Los ganadores avanzan

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            round++;
        }

        System.out.println("\n🏆 ¡Ganador del torneo: " + players.get(0) + "!");
        executor.shutdown();
    }
}
