import java.util.*;

public class GameEngine {
    private Player player;
    private Scanner scanner;
    private Random random;
    private ScoreSystem scoreSystem;
    
    public GameEngine() {
        this.scanner = new Scanner(System.in);
        this.random = new Random();
        this.scoreSystem = new ScoreSystem();
    }

    public void startGame() {
        System.out.println("========================================");
        System.out.println("   Welcome to Pokemon Ga-Ole Game!     ");
        System.out.println("========================================");
        
        System.out.print("Enter your trainer name: ");
        String playerName = scanner.nextLine();
        player = new Player(playerName);
    
        System.out.println("\nHello, " + playerName + "! Let's begin your Pokemon adventure!");
        
        startAdventure();
        showPostBattleMenu();
    }
    
    private void startAdventure() {
        //Generate random set of Pokemon
        List<Pokemon> wildPokemon = generateRandomPokemonSet();
    
        //Allow player to catch one Pokemon
        pokemonCatchingPhase(wildPokemon);
        
        //Display wild Pokemon details and battle setup
        if (!player.getPokemonCollection().isEmpty()) {
            battleSetupPhase();
        } else {
            System.out.println("You need to catch some Pokemon first!");
        }
    }
    
    //Generate random set of Pokemon
    private List<Pokemon> generateRandomPokemonSet() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("GENERATING WILD POKEMON...");
        System.out.println("=".repeat(40));
        
        List<Pokemon> wildPokemon = PokemonDatabase.getRandomPokemonSet(3);
        
        System.out.println("3 Wild Pokemon have appeared!");
        for (int i = 0; i < wildPokemon.size(); i++) {
            System.out.println((i + 1) + ". " + wildPokemon.get(i));
        }
        
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
        
        return wildPokemon;
    }
    
    //Allow player to catch one out of 3 Pokemon
    private void pokemonCatchingPhase(List<Pokemon> wildPokemon) {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("POKEMON CATCHING...");
        System.out.println("=".repeat(40));
        
        System.out.println("Choose ONE Pokemon to catch:");
        for (int i = 0; i < wildPokemon.size(); i++) {
            System.out.println((i + 1) + ". " + wildPokemon.get(i));
        }
        
        System.out.print("Enter your choice (1-3): ");
        try {
            int choice = Integer.parseInt(scanner.nextLine()) - 1;
            
            if (choice >= 0 && choice < wildPokemon.size()) {
                Pokemon targetPokemon = wildPokemon.get(choice);
                
                // Catch attempt
                if (attemptCatch(targetPokemon)) {
                    System.out.println("\nSuccess! You caught " + targetPokemon.getName() + "!");
                    player.addPokemon(targetPokemon);
                } else {
                    System.out.println("Oh no! " + targetPokemon.getName() + " broke free!");
                }
                
                // Give player 2 more Pokemon for battle demonstration
                System.out.println("\nYou receive 2 additional Pokemon to start your journey:");
                List<Pokemon> starterPokemon = PokemonDatabase.getRandomPokemonSet(2);
                for (Pokemon starter : starterPokemon) {
                    player.addPokemon(starter);
                    System.out.println("Added: " + starter.getName());
                }
            } else {
                System.out.println("Invalid choice!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input!");
        }
        
        player.displayCollection();
        
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    private boolean attemptCatch(Pokemon pokemon) {
        // Catch probability based on rarity
        double catchChance;
        switch (pokemon.getRarity()) {
            case "Common":
                catchChance = 0.8;
                break;
            case "Uncommon":
                catchChance = 0.6;
                break;
            case "Rare":
                catchChance = 0.4;
                break;
            default:
                catchChance = 0.5;
        }
        
        return random.nextDouble() < catchChance;
    }
    
    //Display wild Pokemon details and battle setup
    private void battleSetupPhase() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("BATTLE SETUP...");
        System.out.println("=".repeat(40));
        
        // Generate 2 wild Pokemon for battle
        List<Pokemon> wildOpponents = PokemonDatabase.getRandomPokemonSet(2);
        
        System.out.println("Two wild Pokemon have appeared for battle!");
        System.out.println("Wild Pokemon 1: " + wildOpponents.get(0));
        System.out.println("Wild Pokemon 2: " + wildOpponents.get(1));
        
        // Allow player to select 2 Pokemon for battle
        List<Pokemon> playerTeam = selectBattleTeam();
        
        if (playerTeam.size() == 2) {
            System.out.println("\nYour battle team:");
            System.out.println("Pokemon 1: " + playerTeam.get(0));
            System.out.println("Pokemon 2: " + playerTeam.get(1));
            
            System.out.println("\nBattle setup complete!");
            System.out.println("Your team is ready to face the wild Pokemon!");
        } else {
            System.out.println("Battle cancelled - need exactly 2 Pokemon!");
        }
        
        //In Battle Score
        int turnsTaken = simulateBattle(playerTeam, wildOpponents);
        int score = calculateBattleScore(playerTeam, wildOpponents, turnsTaken);
        System.out.println("\nBattle Score: " + String.format("%08d", score));
        scoreSystem.saveScore(score);
        
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
        
    }
    
    private List<Pokemon> selectBattleTeam() {
        List<Pokemon> battleTeam = new ArrayList<>();
        List<Pokemon> availablePokemon = player.getAvailablePokemon();
        
        if (availablePokemon.size() < 2) {
            System.out.println("You need at least 2 Pokemon to battle!");
            return battleTeam;
        }
        
        System.out.println("\nSelect 2 Pokemon for battle:");
        
        for (int i = 0; i < 2; i++) {
            System.out.println("\nChoose Pokemon " + (i + 1) + ":");
            for (int j = 0; j < availablePokemon.size(); j++) {
                System.out.println((j + 1) + ". " + availablePokemon.get(j));
            }
            
            System.out.print("Enter your choice: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine()) - 1;
                
                if (choice >= 0 && choice < availablePokemon.size()) {
                    Pokemon selectedPokemon = availablePokemon.get(choice);
                    if (!battleTeam.contains(selectedPokemon)) {
                        battleTeam.add(selectedPokemon);
                        System.out.println("Added " + selectedPokemon.getName() + " to your battle team!");
                    } else {
                        System.out.println("You've already selected this Pokemon! Choose another.");
                        i--; // Retry selection
                    }
                } else {
                    System.out.println("Invalid choice! Try again.");
                    i--; // Retry selection
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
                i--; // Retry selection
            }
        }
        
        return battleTeam;
    }
    
    //Battle system 
    private int simulateBattle(List<Pokemon> playerTeam, List<Pokemon> wildOpponents) {
        int turns = 0;
        Random rand = new Random();

        for (Pokemon wild : wildOpponents) {
            int currentHp = wild.getHp();
            while (currentHp > 0) {
                turns++;
                Pokemon attacker = playerTeam.get(turns % 2);
                int damage = rand.nextInt(20) + 10;
                currentHp -= damage;
                System.out.printf("Turn %d: %s dealt %d damage to %s. Remaining HP: %d\n",
                        turns, attacker.getName(), damage, wild.getName(), Math.max(0, currentHp));
            }
            System.out.println(wild.getName() + " has fainted!");

            attemptCatchDefeatedPokemon(wild); // Calls your existing method
        }
        System.out.println("\nCongratulations! You won the battle! ");
        return turns;
    }
    
 // Damage formula using attackPower and type effectiveness
    private int calculateDamage(Pokemon attacker, Pokemon defender, Random rand) {
        int base = attacker.getAttackPower();
        String move = attacker.getMoveType();
        String defend = defender.getDefenderType();
        double multiplier = 1.0;

        // Simple type effectiveness
        if (move.equalsIgnoreCase("Fire") && defend.equalsIgnoreCase("Grass")) multiplier = 2.0;
        if (move.equalsIgnoreCase("Grass") && defend.equalsIgnoreCase("Water")) multiplier = 2.0;
        if (move.equalsIgnoreCase("Water") && defend.equalsIgnoreCase("Fire")) multiplier = 2.0;

        //  Same type = reduced damage
        if (move.equalsIgnoreCase(defend)) multiplier = 0.5;

        // Add small random variation (-2 to +2)
        int randomFactor = rand.nextInt(5) - 2;

        return Math.max(1, (int)(base * multiplier) + randomFactor);
    }

    // Attempt to catch a defeated Pokemon using a Poke Ball
    private void attemptCatchDefeatedPokemon(Pokemon wildPokemon) {
        System.out.println("\nCatch Time!");
        System.out.println("Select a Poke Ball:");

        // Create a list of PokeBall objects
        List<Pokeball> pokeballs = new ArrayList<>();
        pokeballs.add(new Pokeball("Poké Ball", 1.0));
        pokeballs.add(new Pokeball("Great Ball", 1.5));
        pokeballs.add(new Pokeball("Ultra Ball", 2.0));
        pokeballs.add(new Pokeball("Master Ball", 255.0)); // Guaranteed catch

        // Display all Poke Balls
        for (int i = 0; i < pokeballs.size(); i++) {
            System.out.println((i + 1) + ". " + pokeballs.get(i).getName());
        }

        // Get user input
        int choice = -1;
        while (choice < 1 || choice > pokeballs.size()) {
            System.out.print("Enter choice (1-" + pokeballs.size() + "): ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
            } else {
                scanner.next(); // discard invalid input
            }
        }

        // Get selected Poke Ball object
        Pokeball selectedBall = pokeballs.get(choice - 1);
        boolean caught;

        if (selectedBall.getName().equalsIgnoreCase("Master Ball")) {
            caught = true;
        } else {
            double hpRatio = (double)(wildPokemon.getMaxHp() - wildPokemon.getHp()) / wildPokemon.getMaxHp();
            double baseChance = 0.3 + (hpRatio * 0.5);
            double finalChance = Math.min(1.0, baseChance * selectedBall.getMultiplier());
            caught = Math.random() <= finalChance;
        }

        if (caught) {
            System.out.println("\nYou caught " + wildPokemon.getName() + " using a " + selectedBall.getName() + "!\n");
            	
         //Prompt to store in a disk
            System.out.println("Would you like to store this Pokémon in a Ga-Olé Disk?");
            System.out.println("1. Yes, create disk");
            System.out.println("2. No, just keep Pokémon");
            System.out.print("Enter choice (1-2): ");

            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (option == 1) {
                System.out.println("\nEnter disk info:");
                System.out.print("Grade (1–5): ");
                int grade = scanner.nextInt();
                scanner.nextLine();

                System.out.print("Z-Move name: ");
                String zMove = scanner.nextLine();

                System.out.print("Collection number: ");
                int collectionNumber = scanner.nextInt();
                scanner.nextLine();

                System.out.print("Defense: ");
                int defense = scanner.nextInt();
                scanner.nextLine();

                System.out.print("Speed: ");
                int speed = scanner.nextInt();
                scanner.nextLine();

                GaOleDisk newDisk = new GaOleDisk(wildPokemon, grade, zMove, collectionNumber, defense, speed);
                player.addGaOleDisk(newDisk); // Ensure `Player` class has `addDisk(GaOleDisk)` method
                System.out.println("\nStored in disk!\n");
            } else {
                player.addPokemon(wildPokemon);
                System.out.println(wildPokemon.getName() + " added to your party.\n");
            }
            player.addPokemon(wildPokemon);
        } else {
            System.out.println("\nThe " + wildPokemon.getName() + " escaped from the " + selectedBall.getName() + "!\n");
        }
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
 // Menu Control
    public void showPostBattleMenu() {
    	boolean running = true;
    	
        while (running) {
            System.out.println("\n======= MAIN MENU =======");
            System.out.println("1. Battle");
            System.out.println("2. View Top Scores");
            System.out.println("3. Display Ga-Ole Disks");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            String input = scanner.nextLine();

            switch (input) {
                case "1" -> startAdventure(); // Battle again
                case "2" -> scoreSystem.displayTopScore(); // Show top 5
                case "3" -> displayGaOleDisks();//displayGaoLeDisks(); NOT YET SET
                case "4" -> {// Exit
                    System.out.println("Exiting the game...");
                    running = false;
                    closeGame();
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

  //display disks method
    public void displayGaOleDisks() {
        List<GaOleDisk> disks = player.getGaOleDisks(); // or player.gaOleDisks if public

        if (disks.isEmpty()) {
            System.out.println("You don't have any Ga-Olé Disks yet.");
            return;
        }

        System.out.println("\n======= Your Ga-Olé Disks =======");
        for (GaOleDisk disk : disks) {
            System.out.println(disk); // uses toString() from GaOleDisk
            System.out.println("----------------------------------");
        }
    }
    
    // Score System
    private int calculateBattleScore(List<Pokemon> team, List<Pokemon> wildOpponents, int turnsTaken) {
        int score = 0;

        for (Pokemon p : team) {
            score += (p.getMaxHp() * 10); // Add based on team HP
        }

        score -= turnsTaken * 10;// Penalty for more turns

        
        int caughtCount = 0;
        for (Pokemon wild : wildOpponents) {
            boolean isCaught = player.getPokemonCollection().contains(wild);
            
            if (isCaught) {
                caughtCount++;
                score += 500; // Reward for catching
                
                switch (wild.getRarity()) {
                case "Rare" -> score += 1000;
                case "Uncommon" -> score += 800;
                case "Common" -> score += 500;
                }
            } else {
            	score -= 200;// Penalty for escaping pokemon
            }
        }
        return Math.max(score, 0);
    }

    
    public void closeGame() {
        scanner.close();
    }
}
