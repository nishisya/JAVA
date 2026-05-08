import java.util.*;

public class Player {
    private String name;
    private List<Pokemon> pokemonCollection; //Pokemon owned by player
    
    public Player(String name) {
        this.name = name;
        this.pokemonCollection = new ArrayList<>();
    }
    
    public void addPokemon(Pokemon pokemon) {
        pokemonCollection.add(pokemon);
    }
    
    public List<Pokemon> getAvailablePokemon() {
        List<Pokemon> available = new ArrayList<>();
        for (Pokemon pokemon : pokemonCollection) {
            if (!pokemon.isDefeated()) { //check if Pokemon is not defeated
                available.add(pokemon);
            }
        }
        return available;
    }
    
    // Getters
    public String getName() { return name; }
    public List<Pokemon> getPokemonCollection() { return pokemonCollection; }
    
    //Print collection
    public void displayCollection() {
        System.out.println("\n" + name + "'s Pokemon Collection:");
        System.out.println("=" + "=".repeat(50));
        if (pokemonCollection.isEmpty()) {
            System.out.println("No Pokemon in collection.");
        } else {
            for (int i = 0; i < pokemonCollection.size(); i++) { //loop through all Pokemon
                System.out.println((i + 1) + ". " + pokemonCollection.get(i));
            }
        }
    }
}
