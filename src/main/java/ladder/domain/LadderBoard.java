package ladder.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LadderBoard {
    private static final int NAME_FORMAT_LEN = 6;
    private static final String ENTER = "\n";

    private final Ladder ladder;
    private final Players players;
    private final Rewards rewards;

    LadderBoard(Ladder ladder, Players players, Rewards rewards) {
        this.ladder = ladder;
        this.players = players;
        this.rewards = rewards;
    }

    public static LadderBoard of(Ladder ladder, Players players, Rewards rewards) {
        return new LadderBoard(ladder, players, rewards);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(formatNames(players) + ENTER);
        sb.append(ladder.toString());
        sb.append(formatNames(rewards) + ENTER);

        return sb.toString();
    }

    private String formatNames(Names names) {
        StringBuilder sb = new StringBuilder();

        String format = String.format("%%%ds", NAME_FORMAT_LEN);
        for (int i = 0; i < names.size(); i++) {
            sb.append(String.format(format, names.getName(i)));
        }

        return sb.toString();
    }

    public List<LadderMachingPair> play(Player player) {
        List<Integer> froms =
                Player.ALL.equals(player)
                        ? IntStream.range(0, players.size()).boxed().collect(Collectors.toList())
                        : Arrays.asList(players.indexOf(player));

        List<LadderMachingPair> pairs = new ArrayList<>();
        for (Integer from : froms) {
            Position to = ladder.nextPosition(new Position(0, players.size(), from));
            pairs.add(LadderMachingPair.of(players.getPlayer(from), rewards.getReward(to.toInt())));
        }
        return pairs;
    }
}
