
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Rental {
    private final String rentalID;
    private final Customer customer;  // Association: Rental belongs to Customer
    private final Movie movie;        // Aggregation: Rental uses Movie but doesn't own it
    private final LocalDate rentalDate;
    private LocalDate returnDate;
    private boolean isReturned;
    private static final int RENTAL_PERIOD_DAYS = 7; 
    private static final double LATE_FEE_PER_DAY = 30.0;
    
    private static int rentalCounter = 1000; 
    
    public Rental(Customer customer, Movie movie) {
        this.rentalID = "R" + (++rentalCounter);
        this.customer = customer;
        this.movie = movie;
        this.rentalDate = LocalDate.now();
        this.returnDate = null;
        this.isReturned = false;
    }
    
 
    public void processReturn() {
        if (!isReturned) {
            this.returnDate = LocalDate.now();
            this.isReturned = true;
        }
    }
    

    public double calculateFee() {
        if (returnDate == null) return 0.0;
        
        LocalDate dueDate = rentalDate.plusDays(RENTAL_PERIOD_DAYS);
        if (returnDate.isAfter(dueDate)) {
            long daysLate = ChronoUnit.DAYS.between(dueDate, returnDate);
            return daysLate * LATE_FEE_PER_DAY;
        }
        return 0.0;
    }
    
    
    public int getDaysRented() {
        LocalDate endDate = isReturned ? returnDate : LocalDate.now();
        return (int) ChronoUnit.DAYS.between(rentalDate, endDate);
    }
    
    
    public boolean isOverdue() {
        if (isReturned) return false;
        LocalDate dueDate = rentalDate.plusDays(RENTAL_PERIOD_DAYS);
        return LocalDate.now().isAfter(dueDate);
    }
    
    // Getters
    public String getRentalID() { return rentalID; }
    public Customer getCustomer() { return customer; }
    public Movie getMovie() { return movie; }
    public LocalDate getRentalDate() { return rentalDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public boolean isReturned() { return isReturned; }
    public LocalDate getDueDate() { return rentalDate.plusDays(RENTAL_PERIOD_DAYS); }
    
    @Override
    public String toString() {
        String status = isOverdue() ? " OVERDUE" : (isReturned ? " Returned" : " Active");
        return String.format("%-6s | %-25s | %10s | %10s | %s", 
                           rentalID, movie.getTitle(), rentalDate, 
                           (returnDate != null ? returnDate.toString() : "Not returned"), status);
    }
}
