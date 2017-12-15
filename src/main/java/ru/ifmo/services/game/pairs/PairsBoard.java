package ru.ifmo.services.game.pairs;

import ru.ifmo.telegram.bot.services.telegramApi.classes.Button;
import ru.ifmo.telegram.bot.services.telegramApi.classes.Keyboard;

import java.util.*;
import java.util.stream.Collectors;

public class PairsBoard {
    //(0,0) - top left
    private List<List<PairsTile>> tiles;
    private final int SIZE = 6;
    private int openCard = 0;
    private boolean showed = false;

    PairsBoard() {
        tiles = new ArrayList<>(SIZE);
        for (int i = 0; i < SIZE; i++) {
            tiles.add(Arrays.stream(new PairsTile[SIZE]).map(it -> new PairsTile()).collect(Collectors.toList()));
        }
        List<Integer> numbers = new LinkedList<>();
        for (int i = 2; i <= SIZE * SIZE + 1; i++) {
            numbers.add(i / 2);
        }
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                int randomIndex = random.nextInt(numbers.size());
                tiles.get(i).get(j).setId((char)((char)((int) numbers.get(randomIndex)) + 'a'));
                numbers.remove(randomIndex);
            }
        }
    }

    public boolean select(int x, int y) {
        if (!tiles.get(x).get(y).isOpen() && !tiles.get(x).get(y).isSelect()) {
            tiles.get(x).get(y).revertSelect();
            tiles.get(x).get(y).open();
            return true;
        }
        return false;
    }

    public boolean makeTurn(int x1, int y1, int x2, int y2) {
        if (tiles.get(x2).get(2).isOpen() || tiles.get(x2).get(y2).isSelect()) {
            tiles.get(x1).get(y1).revertSelect();
            tiles.get(x1).get(y1).close();
            return false;
        }
        tiles.get(x2).get(y2).open();
        tiles.get(x2).get(y2).revertSelect();
        openCard += 2;
        showed = true;
        return true;
    }

    public boolean isFull() {
        return openCard == SIZE * SIZE;
    }

    void clear() {
        tiles.forEach(it -> it.forEach(PairsTile::clear));
    }

    public Keyboard getKeyboard() {
        Keyboard keyboard = new Keyboard();
        if (showed) {
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    keyboard.addButton(new Button("callback_data",
                            "/turn 0 0", tiles.get(i).get(j).toString()));
                }
                keyboard.addRow();
            }
        } else {
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    String data = "/skip";
                    if (!tiles.get(i).get(j).isOpen()) {
                        data = "/turn " + (i + 1) + " " + (j + 1);
                    }
                    keyboard.addButton(new Button("callback_data",
                            data, tiles.get(i).get(j).toString()));
                }
                keyboard.addRow();
            }
        }
        return keyboard;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        tiles.forEach(list -> {
            list.stream().map(PairsTile::toString).forEach(sb::append);
            sb.append('\n');
        });
        return sb.toString();
    }

    public void notSelectAll() {
        tiles.stream()
                .flatMap(Collection::stream)
                .filter(PairsTile::isSelect)
                .forEachOrdered(PairsTile::revertSelect);
    }
}