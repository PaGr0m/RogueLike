package ru.itmo.roguelike.items;

import static ru.itmo.roguelike.items.BonusType.HP;

public class MedKitSmall extends Collectible {
    BonusType bonusType = HP;
    int bonusSize = 25;

    @Override
    public void draw() {

    }
}
