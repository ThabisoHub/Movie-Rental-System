
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;




public class MovieRentalSystem {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Movie> movieInventory = new ArrayList<>();
    private static final List<Customer> customers = new ArrayList<>();
    private static final List<Staff> staffMembers = new ArrayList<>();
    private static final List<Rental> allRentals = new ArrayList<>();
    
    public static void main(String[] args) {
        System.out.println("Welcome to Movie Rental System ");
        System.out.println("=====================================\n");
        
        
        initializeSampleData();
        
        
        while (true) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice (1-9): ");
            
            try {
                switch (choice) {
                    case 1 -> displayMovieInventory();
                    case 2 -> rentMovie();
                    case 3 -> returnMovie();
                    case 4 -> addNewMovie();
                    case 5 -> registerNewCustomer();
                    case 6 -> viewCustomerRentals();
                    case 7 -> viewAllRentals();
                    case 8 -> displaySystemStats();
                    case 9 -> {
                        System.out.println("\n Thank you for using Movie Rental System! 🎬");
                        return;
                    }
                    default -> System.out.println(" Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println(" Error: " + e.getMessage());
            }
            
            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
        }
    }
    
    
    private static void initializeSampleData() {
        
        movieInventory.add(new Movie("M001", "The Matrix", "Sci-Fi", 1999, 89.99));
        movieInventory.add(new Movie("M002", "Inception", "Sci-Fi", 2010, 59.99));
        movieInventory.add(new Movie("M003", "The Godfather", "Crime", 1972, 69.99));
        movieInventory.add(new Movie("M004", "Pulp Fiction", "Crime", 1994, 79.49));
        movieInventory.add(new Movie("M005", "Titanic", "Romance", 1997, 39.99));
        movieInventory.add(new Movie("M006", "The Dark Knight", "Action", 2008, 49.49));
        movieInventory.add(new Movie("M007", "Forrest Gump", "Drama", 1994, 99.99));
        movieInventory.add(new Movie("M008", "The Lion King", "Animation", 1994, 109.99));
        
        
        customers.add(new Customer("C001", "John Doe", "john@email.com", "555-1234"));
        customers.add(new Customer("C002", "Jane Smith", "jane@email.com", "555-5678"));
        customers.add(new Customer("C003", "Mike Johnson", "mike@email.com", "555-9876"));
        
        
        staffMembers.add(new Staff("S001", "Alice Manager", "alice@store.com", "555-9999", "Manager", 450000));
        staffMembers.add(new Staff("S002", "Bob Clerk", "bob@store.com", "555-8888", "Clerk", 300000));
    }
    
    
    private static void displayMainMenu() {
        System.out.println("\n" + "=".repeat(50));
System.out.printf("| %-46s |\n", "MOVIE RENTAL SYSTEM - MAIN MENU");
System.out.println("=".repeat(50));
System.out.printf("| %-2s %-41s |\n", "1.", "View Movie Inventory");
System.out.printf("| %-2s %-41s |\n", "2.", "Rent a Movie");
System.out.printf("| %-2s %-41s |\n", "3.", "Return a Movie");
System.out.printf("| %-2s %-41s |\n", "4.", "Add New Movie (Staff Only)");
System.out.printf("| %-2s %-41s |\n", "5.", "Register New Customer");
System.out.printf("| %-2s %-41s |\n", "6.", "View Customer Rentals");
System.out.printf("| %-2s %-41s |\n", "7.", "View All Rentals");
System.out.printf("| %-2s %-41s |\n", "8.", "System Statistics");
System.out.printf("| %-2s %-41s |\n", "9.", "Exit");
System.out.println("=".repeat(50));
        
    }
    
    
    private static void displayMovieInventory() {
        System.out.println("\n MOVIE INVENTORY");
        System.out.println("=".repeat(80));
        System.out.println("ID   | Title                     | Genre        | Year | Price  | Status");
        System.out.println("-".repeat(80));
        
        for (Movie movie : movieInventory) {
            System.out.println(movie);
        }
        System.out.println("=".repeat(80));
        System.out.printf("Total Movies: %d | Available: %d | Rented: %d%n", 
                        movieInventory.size(), 
                        (int) movieInventory.stream().filter(Movie::isAvailable).count(),
                        (int) movieInventory.stream().filter(m -> !m.isAvailable()).count());
    }
    
 
    private static void rentMovie() {
        System.out.println("\n RENT A MOVIE");
        System.out.println("=".repeat(40));
        
        
        List<Movie> availableMovies = movieInventory.stream()
                .filter(Movie::isAvailable)
                .toList();
        
        if (availableMovies.isEmpty()) {
            System.out.println(" No movies available for rent.");
            return;
        }
        
        System.out.println("Available Movies:");
        for (int i = 0; i < availableMovies.size(); i++) {
            Movie movie = availableMovies.get(i);
            System.out.printf("%d. %s (%s) - R%.2f%n", 
                            i + 1, movie.getTitle(), movie.getGenre(), movie.getRentalPrice());
        }
        
        int movieChoice = getIntInput("Select movie (1-" + availableMovies.size() + "): ") - 1;
        if (movieChoice < 0 || movieChoice >= availableMovies.size()) {
            System.out.println(" Invalid movie selection.");
            return;
        }
        
        Movie selectedMovie = availableMovies.get(movieChoice);
        
        
        System.out.println("\nCustomers:");
        for (int i = 0; i < customers.size(); i++) {
            Customer customer = customers.get(i);
            System.out.printf("%d. %s (ID: %s)%n", i + 1, customer.getName(), customer.getCustomerID());
        }
        
        int customerChoice = getIntInput("Select customer (1-" + customers.size() + "): ") - 1;
        if (customerChoice < 0 || customerChoice >= customers.size()) {
            System.out.println(" Invalid customer selection.");
            return;
        }
        
        Customer selectedCustomer = customers.get(customerChoice);
        
        try {
            Rental rental = selectedCustomer.rentMovie(selectedMovie);
            allRentals.add(rental);
            
            
            if (!staffMembers.isEmpty()) {
                staffMembers.get(0).processRental(rental);
            }
            
            System.out.println(" Movie rented successfully!");
            System.out.printf(" Rental Details:%n");
            System.out.printf("   Customer: %s%n", selectedCustomer.getName());
            System.out.printf("   Movie: %s%n", selectedMovie.getTitle());
            System.out.printf("   Rental Date: %s%n", rental.getRentalDate());
            System.out.printf("   Due Date: %s%n", rental.getDueDate());
            System.out.printf("   Rental Fee: R%.2f%n", selectedMovie.getRentalPrice());
            
        } catch (IllegalStateException e) {
            System.out.println(" " + e.getMessage());
        }
    }
    
    
    private static void returnMovie() {
        System.out.println("\n RETURN A MOVIE");
        System.out.println("=".repeat(40));
        
        
        List<Customer> customersWithRentals = customers.stream()
                .filter(c -> !c.getCurrentRentals().isEmpty())
                .toList();
        
        if (customersWithRentals.isEmpty()) {
            System.out.println(" No active rentals found.");
            return;
        }
        
        System.out.println("Customers with active rentals:");
        for (int i = 0; i < customersWithRentals.size(); i++) {
            Customer customer = customersWithRentals.get(i);
            System.out.printf("%d. %s (ID: %s) - %d active rentals%n", 
                            i + 1, customer.getName(), customer.getCustomerID(), 
                            customer.getCurrentRentals().size());
        }
        
        int customerChoice = getIntInput("Select customer (1-" + customersWithRentals.size() + "): ") - 1;
        if (customerChoice < 0 || customerChoice >= customersWithRentals.size()) {
            System.out.println(" Invalid customer selection.");
            return;
        }
        
        Customer selectedCustomer = customersWithRentals.get(customerChoice);
        List<Rental> customerRentals = selectedCustomer.getCurrentRentals();
        
        System.out.println("\nActive rentals for " + selectedCustomer.getName() + ":");
        for (int i = 0; i < customerRentals.size(); i++) {
            Rental rental = customerRentals.get(i);
            String overdueStatus = rental.isOverdue() ? "  OVERDUE" : "";
            System.out.printf("%d. %s (Rented: %s, Due: %s)%s%n", 
                            i + 1, rental.getMovie().getTitle(), 
                            rental.getRentalDate(), rental.getDueDate(), overdueStatus);
        }
        
        int rentalChoice = getIntInput("Select rental to return (1-" + customerRentals.size() + "): ") - 1;
        if (rentalChoice < 0 || rentalChoice >= customerRentals.size()) {
            System.out.println(" Invalid rental selection.");
            return;
        }
        
        Rental selectedRental = customerRentals.get(rentalChoice);
        Movie movieToReturn = selectedRental.getMovie();
        
        try {
            selectedCustomer.returnMovie(movieToReturn);
            
            System.out.println(" Movie returned successfully!");
            System.out.printf(" Return Details:%n");
            System.out.printf("   Customer: %s%n", selectedCustomer.getName());
            System.out.printf("   Movie: %s%n", movieToReturn.getTitle());
            System.out.printf("   Rental Date: %s%n", selectedRental.getRentalDate());
            System.out.printf("   Return Date: %s%n", selectedRental.getReturnDate());
            System.out.printf("   Days Rented: %d%n", selectedRental.getDaysRented());
            
            double lateFee = selectedRental.calculateFee();
            if (lateFee > 0) {
                System.out.printf("   Late Fee: R%.2f%n", lateFee);
            } else {
                System.out.println("   Late Fee: R0.00");
            }
            
        } catch (IllegalArgumentException e) {
            System.out.println(" " + e.getMessage());
        }
    }
    
    
    private static void addNewMovie() {
        System.out.println("\n ADD NEW MOVIE (Staff Only)");
        System.out.println("=".repeat(40));
        
        
        System.out.println("Staff Authentication:");
        for (int i = 0; i < staffMembers.size(); i++) {
            System.out.printf("%d. %s (%s)%n", i + 1, staffMembers.get(i).getName(), staffMembers.get(i).getRole());
        }
        
        int staffChoice = getIntInput("Select staff member (1-" + staffMembers.size() + "): ") - 1;
        if (staffChoice < 0 || staffChoice >= staffMembers.size()) {
            System.out.println(" Invalid staff selection.");
            return;
        }
        
        Staff staff = staffMembers.get(staffChoice);
        
        
        System.out.println("\nEnter movie details:");
        String movieID = "M" + String.format("%03d", movieInventory.size() + 1);
        
        System.out.print("Movie Title: ");
        String title = scanner.nextLine().trim();
        
        System.out.print("Genre: ");
        String genre = scanner.nextLine().trim();
        
        int year = getIntInput("Release Year: ");
        double price = getDoubleInput("Rental Price: R");
        
        Movie newMovie = new Movie(movieID, title, genre, year, price);
        movieInventory.add(newMovie);
        
        staff.addMovieToInventory(newMovie);
        
        System.out.println(" Movie added successfully!");
        System.out.println("Movie Details: " + newMovie);
    }
    
   
    private static void registerNewCustomer() {
        System.out.println("\n REGISTER NEW CUSTOMER");
        System.out.println("=".repeat(40));
        
        String customerID = "C" + String.format("%03d", customers.size() + 1);
        
        System.out.print("Customer Name: ");
        String name = scanner.nextLine().trim();
        
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        
        System.out.print("Phone: ");
        String phone = scanner.nextLine().trim();
        
        Customer newCustomer = new Customer(customerID, name, email, phone);
        customers.add(newCustomer);
        
        System.out.println(" Customer registered successfully!");
        System.out.println("Customer Details: " + newCustomer);
    }
    
    
    private static void viewCustomerRentals() {
        System.out.println("\n VIEW CUSTOMER RENTALS");
        System.out.println("=".repeat(50));
        
        if (customers.isEmpty()) {
            System.out.println(" No customers registered.");
            return;
        }
        
        System.out.println("Select Customer:");
        for (int i = 0; i < customers.size(); i++) {
            Customer customer = customers.get(i);
            System.out.printf("%d. %s (ID: %s) - %d active rentals%n", 
                            i + 1, customer.getName(), customer.getCustomerID(), 
                            customer.getCurrentRentals().size());
        }
        
        int customerChoice = getIntInput("Select customer (1-" + customers.size() + "): ") - 1;
        if (customerChoice < 0 || customerChoice >= customers.size()) {
            System.out.println(" Invalid customer selection.");
            return;
        }
        
        Customer selectedCustomer = customers.get(customerChoice);
        List<Rental> rentals = selectedCustomer.getCurrentRentals();
        
        System.out.println("\n Rentals for " + selectedCustomer.getName());
        System.out.println("=".repeat(80));
        
        if (rentals.isEmpty()) {
            System.out.println("No active rentals.");
        } else {
            System.out.println("Rental | Movie Title               | Rented     | Due Date   | Status");
            System.out.println("-".repeat(80));
            for (Rental rental : rentals) {
                System.out.println(rental);
            }
        }
    }
    
   
    private static void viewAllRentals() {
        System.out.println("\n ALL SYSTEM RENTALS");
        System.out.println("=".repeat(80));
        
        if (allRentals.isEmpty()) {
            System.out.println("No rentals in the system.");
            return;
        }
        
        System.out.println("Rental | Movie Title               | Rented     | Return     | Status");
        System.out.println("-".repeat(80));
        
        for (Rental rental : allRentals) {
            System.out.println(rental);
        }
        
        System.out.printf("\nTotal Rentals: %d | Active: %d | Completed: %d%n",
                        allRentals.size(),
                        (int) allRentals.stream().filter(r -> !r.isReturned()).count(),
                        (int) allRentals.stream().filter(Rental::isReturned).count());
    }
    
    
    private static void displaySystemStats() {
        System.out.println("\n SYSTEM STATISTICS");
        System.out.println("=".repeat(50));
        
        int totalMovies = movieInventory.size();
        int availableMovies = (int) movieInventory.stream().filter(Movie::isAvailable).count();
        int rentedMovies = totalMovies - availableMovies;
        
        int totalCustomers = customers.size();
        int activeRentals = allRentals.stream()
                .filter(r -> !r.isReturned())
                .mapToInt(r -> 1)
                .sum();
        
        int overdueRentals = allRentals.stream()
                .filter(Rental::isOverdue)
                .mapToInt(r -> 1)
                .sum();
        
        double totalRevenue = allRentals.stream()
                .filter(Rental::isReturned)
                .mapToDouble(r -> r.getMovie().getRentalPrice() + r.calculateFee())
                .sum();
        
        System.out.printf(" Movies:%n");
        System.out.printf("   Total Movies: %d%n", totalMovies);
        System.out.printf("   Available: %d%n", availableMovies);
        System.out.printf("   Currently Rented: %d%n", rentedMovies);
        
        System.out.printf("%n Customers:%n");
        System.out.printf("   Total Customers: %d%n", totalCustomers);
        
        System.out.printf("%n Rentals:%n");
        System.out.printf("   Total Rentals: %d%n", allRentals.size());
        System.out.printf("   Active Rentals: %d%n", activeRentals);
        System.out.printf("   Overdue Rentals: %d%n", overdueRentals);
        
        System.out.printf("%n Revenue:%n");
        System.out.printf("   Total Revenue: R%.2f%n", totalRevenue);
        
        System.out.printf("%n Staff:%n");
        System.out.printf("   Total Staff: %d%n", staffMembers.size());
        
        
        Map<String, Long> genreCounts = movieInventory.stream()
                .collect(java.util.stream.Collectors.groupingBy(
                    Movie::getGenre, 
                    java.util.stream.Collectors.counting()));
        
        System.out.printf("%n Most Popular Genres:%n");
        genreCounts.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(3)
                .forEach(entry -> System.out.printf("   %s: %d movies%n", 
                        entry.getKey(), entry.getValue()));
    }
    
    
    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println(" Please enter a valid number.");
            }
        }
    }
    
   
    private static double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                double value = Double.parseDouble(input);
                if (value < 0) {
                    System.out.println(" Please enter a positive number.");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println(" Please enter a valid number.");
            }
        }
    }
}