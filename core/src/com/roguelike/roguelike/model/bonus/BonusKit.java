package com.roguelike.roguelike.model.bonus;

public class BonusKit {
    private final int healthBonus;

    public BonusKit(int healthBonus) {
        this.healthBonus = healthBonus;
    }

    public int getHealthBonus() {
        return healthBonus;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private int healthBonus = 0;

        public BonusKit build() {
            return new BonusKit(healthBonus);
        }

        public Builder setHealthBonus(int healthBonus) {
            this.healthBonus = healthBonus;
            return this;
        }
    }
}
