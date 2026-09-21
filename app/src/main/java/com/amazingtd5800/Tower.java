package com.amazingtd5800;

/**
 * The tower catalogue the instructions screen shows: name, price, damage, range, fire rate and page text.
 * Original: dq and its eleven tower subclasses (jo, fi, fc, fb, ki, u, nx, oc, pa, nu, kn).
 */
public enum Tower {

    AUTOBOW("Autobow", 20, 10, 48, 3100, "The Autobow is a cheap basic tower that upgrades well. "
            + "It fires arrows and has decent range. Upgrades make the tower fire faster, increases its "
            + "range and power. At upgrade level 3 arrows have 30% chance of poisoning the enemy "
            + "(extra damage). "),

    SLOW("Slow Tower", 25, 3, 35, 2800, "The Slow Tower fires arrows which does little damage but may "
            + "slow down the enemy for a short while. Upgrades make the tower fire faster, increases its "
            + "range, power and probability of slowing down the enemy. At upgrade level 6 arrows will "
            + "pierce enemy shields. This tower has quite short range. "),

    MORTAR("Mortar", 40, 2, 36, 5000, "This tower fires devastating bombs which damages all enemies in "
            + "the blast radious. It can really make the difference if placed in the right spot. Upgrades "
            + "shortens the reload time, increases the range, power, and blast radious. At upgrade level "
            + "4 the bombs have 50% chance of slowing down the enemies for a short period. "),

    CHAINGUN("Chaingun", 35, 4, 36, 450, "The Chaingun fires 40 bullets with high rate before it needs "
            + "to reload. Its high fire rate and short range makes it suitable to place in corners and "
            + "crossings of the path. Upgrades make the tower fire and reload faster, increases its range "
            + "and power. "),

    FORCE_FIELD("Force Field Tower", 40, 4, 58, 170, "The Force Field Tower generates a force field when "
            + "built in range of another Force Field Tower. Enemies passing through the field gets hurt "
            + "and their shields are disabled for a moment. Multiple towers can be connected to build a "
            + "chain of force fields. The tower is automatically connected to the last built Force Field "
            + "Tower within range. This tower is specially good against slow enemies. Upgrades make all "
            + "connected towers more powerful. "),

    PULSED_LASER("Pulsed Laser", 50, 16, 60, 4700, "The Pulsed Laser fires a laser beam with high "
            + "accuracy. It has good range but recharges slowly. Upgrades make the tower recharge faster, "
            + "increases its power and enables the beam to hit up to three targets at once. From upgrade "
            + "level 6 the laser beam will stun the enemies for a short while. "),

    DETECTOR("Detector", 20, 0, 30, 10, "The Detector has the unique ability of detecting invisible "
            + "enemies. It is cheap, but it does no damage to the enemies. Instead it enables the other "
            + "towers to fire at invisible enemies. Upgrades increases its range and the time that "
            + "invisible enemies stay detected. "),

    MONEY("Money Tower", 30, 0, 40, 3500, "The Money Tower does no damage to enemies, but it has a 60% "
            + "chance of increasing the money value of the enemy for a while. Upgrades increase its "
            + "range, the duration and the amount of extra money. A small yellow square by the health bar "
            + "indicates that an enemy is extra valuable. "),

    SNIPER("Sniper Tower", 50, 24, 70, 4500, "The Sniper Tower fires powerful high velocity bullets that "
            + "can pierce enemy shields. It has long range but low fire rate. Upgrades make the tower fire "
            + "faster, increases its range and power. "),

    TRACKING("Tracking Laser", 60, 3, 38, 360, "The Tracking Laser has perfect accuracy. When a target "
            + "gets in range a laser beam locks on it until it is destroyed or out of range again. But the "
            + "range is short and the laser is not so powerful. Upgrades make the beam more powerful and "
            + "increases the range. This tower can be unlocked by clearing 1 levels without loosing a life. "),

    MISSILE("Missile Tower", 80, 5, 700, 7000, "The Missile Tower fires homing missiles with area "
            + "damage. It has extremely good range but the precision is not very reliable. The tower can "
            + "be placed anywhere and still reach its randomly selected targets. Upgrades improve "
            + "reloading time and make the missiles more powerful. This tower can be unlocked by clearing "
            + "3 levels without loosing a life. ");

    /** Tower name; the original took it from getClass().getName(). Original: dq.b(). */
    private final String name;
    /** Tower price. Original: dq.a(). */
    private final int price;
    /** Damage. Original: dq.k(). */
    private final int power;
    /** Range. Original: dq.j(). */
    private final int range;
    /** Reload time, from which the fire rate is computed. Original: dq.b(). */
    private final int reload;
    private final String text;

    Tower(String name, int price, int power, int range, int reload, String text) {
        this.name = name;
        this.price = price;
        this.power = power;
        this.range = range;
        this.reload = reload;
        this.text = text;
    }

    public String displayName() {
        return name;
    }

    public int price() {
        return price;
    }

    public int power() {
        return power;
    }

    public int range() {
        return range;
    }

    /** Shots per second the reload time works out to. Original: dq.l(). */
    public int rate() {
        return (int) (10000f / reload + 0.5f);
    }

    /** Instructions-page text about this tower. Original: di.a(). */
    public String text() {
        return text;
    }
}
