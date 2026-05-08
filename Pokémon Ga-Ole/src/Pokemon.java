public class Pokemon {
    private String name;
    private int hp;
    private int maxHp;
    private String defenderType;
    private String moveType;
    private int attackPower;
    private int level;
    private String rarity;
    
    public Pokemon(String name, int hp, String defenderType, String moveType, int attackPower, int level, String rarity) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.defenderType = defenderType;
        this.moveType = moveType;
        this.attackPower = attackPower;
        this.level = level;
        this.rarity = rarity;
    }
    
    public boolean isDefeated() {
        return this.hp <= 0;
    }
    
    // Getters and Setters
    public String getName() { return name; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public String getDefenderType() { return defenderType; }
    public String getMoveType() { return moveType; }
    public int getAttackPower() { return attackPower; }
    public int getLevel() { return level; }
    public String getRarity() { return rarity; }
    
    public void setHp(int hp) { this.hp = hp; }
    
    @Override
    public String toString() {
        return String.format("Name: %s | HP: %d/%d | Level: %d | Type: %s/%s | Attack: %d | Rarity: %s", 
                           name, hp, maxHp, level, defenderType, moveType, attackPower, rarity);
    }
}