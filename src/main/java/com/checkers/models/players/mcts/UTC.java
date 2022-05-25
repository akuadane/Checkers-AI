package com.checkers.models.players.mcts;

import java.util.Collections;
import java.util.Comparator;

public class UTC {
    public static double uctValue(
            int parentVisit, double nodeWinScore, int nodeVisit) {
        if (nodeVisit == 0) {
            return Integer.MAX_VALUE;
        }
        return (nodeWinScore / (double) nodeVisit)
                + 1.41 * Math.sqrt(Math.log(parentVisit) / (double) nodeVisit);
    }

    public static MCTSNode findBestNodeWithUCT(MCTSNode node) {
        int parentVisit = node.getState().getPlays();
        return Collections.max(
                node.getChildren(),
                Comparator.comparing(c -> uctValue(parentVisit,
                        c.getState().getWins(), c.getState().getPlays())));
    }
}
