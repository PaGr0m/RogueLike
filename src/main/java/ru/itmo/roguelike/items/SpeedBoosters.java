package ru.itmo.roguelike.items;

import static ru.itmo.roguelike.items.BonusType.SPEED;

public class SpeedBoosters extends Collectible{
    BonusType bonusType = SPEED;
    int bonusSize = 25;

    @Override
    public void draw() {

    }
}
