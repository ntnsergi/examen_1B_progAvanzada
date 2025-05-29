package ec.edu.utpl.carreras.computacion.progAvanzada.semana1.view;

import java.util.*;
import java.util.concurrent.*;

public class Tournament {
    private static final int TOTAL_PLAYERS = 16;
    private final ExecutorService executor = Executors.newFixedThreadPool(8);

    public void start() {
        List<String> players = new ArrayList<>();
        for (int i = 1; i <= TOTAL_PLAYERS; i++) {
            players.add("Jugador " + i);
        }

        int round = 1;
        while (players.size() > 1) {
            System.out.println("\n=== Ronda " + round + " ===");
            List<Callable<MatchResult>> matches = new ArrayList<>();

            for (int i = 0; i < players.size(); i += 2) {
                String p1 = players.get(i);
                String p2 = players.get(i + 1);
                matches.add(new Match(p1, p2));
            }

            try {
                List<Future<MatchResult>> results = executor.invokeAll(matches);

                players.clear();
                for (Future<MatchResult> result : results) {
                    MatchResult matchResult = result.get();
                    System.out.println(matchResult);
                    players.add(matchResult.winner());
                }

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            round++;
        }

        System.out.println("\n🏆 ¡Ganador del torneo: " + players.get(0) + "!");
        executor.shutdown();
    }
}
