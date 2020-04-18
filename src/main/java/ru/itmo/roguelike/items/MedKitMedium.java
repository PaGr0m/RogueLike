package ru.itmo.roguelike.items;

import static ru.itmo.roguelike.items.BonusType.HP;

public class MedKitMedium extends Collectible{
    BonusType bonusType = HP;
    int bonusSize = 50;

    @Override
    public void draw() {

    }
}
