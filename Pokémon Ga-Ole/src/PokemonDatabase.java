import java.util.*;

public class PokemonDatabase {
    private static List<Pokemon> pokemonList = new ArrayList<>();
    
    static {
        // Pokemon samples
        pokemonList.add(new Pokemon("Pikachu", 80, "Electric", "Electric", 25, 15, "Common"));
        pokemonList.add(new Pokemon("Charizard", 120, "Fire", "Fire", 35, 25, "Rare"));
        pokemonList.add(new Pokemon("Blastoise", 110, "Water", "Water", 30, 22, "Rare"));
        pokemonList.add(new Pokemon("Venusaur", 115, "Grass", "Grass", 32, 23, "Rare"));
        pokemonList.add(new Pokemon("Charmander", 70, "Fire", "Fire", 20, 10, "Common"));
        pokemonList.add(new Pokemon("Squirtle", 65, "Water", "Water", 18, 8, "Common"));
        pokemonList.add(new Pokemon("Bulbasaur", 68, "Grass", "Grass", 19, 9, "Common"));
        pokemonList.add(new Pokemon("Flareon", 95, "Fire", "Fire", 28, 18, "Uncommon"));
        pokemonList.add(new Pokemon("Vaporeon", 100, "Water", "Water", 26, 17, "Uncommon"));
        pokemonList.add(new Pokemon("Leafeon", 92, "Grass", "Grass", 27, 16, "Uncommon"));
    }
    
    //Generate random Pokemon
    public static Pokemon getRandomPokemon() {
        Random random = new Random();
        Pokemon original = pokemonList.get(random.nextInt(pokemonList.size()));
        return new Pokemon(original.getName(), original.getMaxHp(), original.getDefenderType(),
                          original.getMoveType(), original.getAttackPower(), original.getLevel(),
                          original.getRarity());
    }
    
    //Generate random Pokemon with specified count
    public static List<Pokemon> getRandomPokemonSet(int count) {
        List<Pokemon> randomSet = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            randomSet.add(getRandomPokemon());
        }
        return randomSet;
    }
}
