public class Main {
    public static void main(String[] args) {
        GameEngine game = new GameEngine();
        
        try {
            game.startGame();
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
        
        System.out.println("\nThank you for playing Pokemon Ga-Ole!");
    }
}