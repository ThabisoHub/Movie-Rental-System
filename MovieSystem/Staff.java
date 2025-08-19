class Staff extends Person {
    private final String staffID;
    private String role;
    private double salary;
    
    public Staff(String staffID, String name, String email, String phone, String role, double salary) {
        super(name, email, phone);
        this.staffID = staffID;
        this.role = role;
        this.salary = salary;
    }
    
    
    public void processRental(Rental rental) {
        System.out.println(" Staff " + name + " processed rental: " + 
                         rental.getMovie().getTitle() + " for " + rental.getCustomer().getName());
    }
    
   
    public void addMovieToInventory(Movie movie) {
        System.out.println(" Staff " + name + " added movie to inventory: " + movie.getTitle());
    }
    
    // Getters
    public String getStaffID() { return staffID; }
    public String getRole() { return role; }
    public double getSalary() { return salary; }
    
    // Setters
    public void setRole(String role) { this.role = role; }
    public void setSalary(double salary) { this.salary = salary; }
    
    @Override
    public String toString() {
        return String.format("Staff[ID=%s, Name=%s, Role=%s, Salary=R%.2f]", 
                           staffID, name, role, salary);
    }
}
