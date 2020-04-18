package ru.itmo.roguelike.items;

import static ru.itmo.roguelike.items.BonusType.HP;

public class MedKitBig extends Collectible{
    BonusType bonusType = HP;
    int bonusSize = 75;

    @Override
    public void draw() {

    }
}
