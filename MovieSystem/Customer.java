import java.util.ArrayList;
import java.util.List;

public class Customer extends Person {
    private final String customerID;
    private final List<Rental> currentRentals;
    
    public Customer(String customerID, String name, String email, String phone) {
        super(name, email, phone);
        this.customerID = customerID;
        this.currentRentals = new ArrayList<>();
    }
    
   
    public Rental rentMovie(Movie movie) {
        if (!movie.isAvailable()) {
            throw new IllegalStateException("Movie is not available for rent");
        }
        
        movie.rent(); 
        Rental rental = new Rental(this, movie);
        currentRentals.add(rental);
        
        return rental;
    }
    
    
    public void returnMovie(Movie movie) {
        Rental rentalToReturn = null;
        
        
        for (Rental rental : currentRentals) {
            if (rental.getMovie().equals(movie) && !rental.isReturned()) {
                rentalToReturn = rental;
                break;
            }
        }
        
        if (rentalToReturn != null) {
            rentalToReturn.processReturn();
            movie.returnMovie(); 
            currentRentals.remove(rentalToReturn);
            
            double fee = rentalToReturn.calculateFee();
            if (fee > 0) {
                System.out.println("Late fee: R" + String.format("%.2f", fee));
            }
        } else {
            throw new IllegalArgumentException("No active rental found for this movie");
        }
    }
    
    // Getters
    public String getCustomerID() { return customerID; }
    public List<Rental> getCurrentRentals() { return new ArrayList<>(currentRentals); }
    
    @Override
    public String toString() {
        return String.format("Customer[ID=%s, Name=%s, Email=%s, Active Rentals=%d]", 
                           customerID, name, email, currentRentals.size());
    }
} 
