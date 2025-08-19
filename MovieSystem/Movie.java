import java.util.*;


public class Movie {
    private final String movieID;
    private String title;
    private String genre;
    private int year;
    private boolean isAvailable;
    private double rentalPrice;
    
    // Constructor
    public Movie(String movieID, String title, String genre, int year, double rentalPrice) {
        this.movieID = movieID;
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.isAvailable = true; 
        this.rentalPrice = rentalPrice;
    }
    
    
    public void rent() {
        if (isAvailable) {
            this.isAvailable = false;
        } else {
            throw new IllegalStateException("Movie is not available for rent");
        }
    }
    
    public void returnMovie() {
        this.isAvailable = true;
    }
    
    // Getters and Setters
    public String getMovieID() { return movieID; }
    public String getTitle() { return title; }
    public String getGenre() { return genre; }
    public int getYear() { return year; }
    public boolean isAvailable() { return isAvailable; }
    public double getRentalPrice() { return rentalPrice; }
    
    public void setTitle(String title) { this.title = title; }
    public void setGenre(String genre) { this.genre = genre; }
    public void setYear(int year) { this.year = year; }
    public void setRentalPrice(double rentalPrice) { this.rentalPrice = rentalPrice; }
    
    @Override
    public String toString() {
        String status = isAvailable ? " Available" : " Rented";
        return String.format("%-4s | %-25s | %-12s | %4d | R%5.2f | %s",
                             movieID, title, genre, year, rentalPrice, status);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Movie movie = (Movie) obj;
        return Objects.equals(movieID, movie.movieID);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(movieID);
    }
}
