public class GaOleDisk {
    private static int idCounter = 1000;
    private final int diskId;
    private Pokemon pokemon;
    private int usageCount;
    private boolean isCaptured;
    private int collectionNumber;

    private int grade;
    private String zMove;
    private int defense;
    private int speed;

    public GaOleDisk(Pokemon pokemon, int grade, String zMove, int collectionNumber, int defense, int speed) {
        this.diskId = idCounter++;
        this.pokemon = pokemon;
        this.usageCount = 0;
        this.isCaptured = false;
        this.grade = grade;
        this.zMove = zMove;
        this.collectionNumber = collectionNumber;
        this.defense = defense;
        this.speed = speed;
    }

    public int getDiskId() { return diskId; }
    public Pokemon getPokemon() { return pokemon; }
    public int getUsageCount() { return usageCount; }
    public boolean isCaptured() { return isCaptured; }
    public int getGrade() { return grade; }
    public String getZMove() { return zMove; }
    public int getCollectionNumber() { return collectionNumber; }
    public int getDefense() { return defense; }
    public int getSpeed() { return speed; }

    public String getPokemonName() { return pokemon.getName(); }
    public int getHp() { return pokemon.getHp(); }
    public int getMaxHp() { return pokemon.getMaxHp(); }
    public String getDefenderType() { return pokemon.getDefenderType(); }
    public String getMoveType() { return pokemon.getMoveType(); }
    public int getAttackPower() { return pokemon.getAttackPower(); }
    public int getLevel() { return pokemon.getLevel(); }
    public String getRarity() { return pokemon.getRarity(); }

    public void incrementUsage() { usageCount++; }
    public void markAsCaptured() { isCaptured = true; }
    public void resetHp() { pokemon.setHp(pokemon.getMaxHp()); }

    public String toString() {
        return String.format("""
            📀 Ga-Olé Disk #%d | Captured: %s | Usage: %d times
            Collection No: #%d | Grade: ★%d | Z-Move: %s
            Pokémon: %s | Level %d | Rarity: %s
            HP: %d/%d | ATK: %d | DEF: %d | SPD: %d
            Types: %s (Defender) / %s (Move)
            """,
            diskId, isCaptured ? "Yes" : "No", usageCount,
            collectionNumber, grade, zMove,
            getPokemonName(), getLevel(), getRarity(),
            getHp(), getMaxHp(), getAttackPower(), defense, speed,
            getDefenderType(), getMoveType()
        );
    }
}
