package ec.edu.utpl.carreras.computacion.progAvanzada.semana1.view;

import java.util.List;

public record MatchResult(String player1, String player2, List<String> sets, String winner) {

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(player1).append(" vs ").append(player2).append("\n");
        for (String set : sets) {
            sb.append(set).append("\n");
        }
        sb.append("Ganador del partido: ").append(winner).append("\n");
        return sb.toString();
    }
}

