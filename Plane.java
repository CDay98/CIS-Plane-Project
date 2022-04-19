import java.text.DecimalFormat;
import java.util.Random;

public class Plane {
    String airline;
    String model;
    int year;
    double flyingHours;
    double gph;
    double gallons;
    double inspection;
    Boolean engine = false;
    Boolean landed = true;

    final double TANK_CAPACITY = 50000;
    final int HOURS_BETWEEN_INSPECTION = 48;
    final private static DecimalFormat df = new DecimalFormat("###,##0.00");

    public Plane() {
        airline = "Southwest";
        model = "747";
        year = 2021;
        flyingHours = 0.00;
        gph = 3600.0;
        gallons = TANK_CAPACITY;
        inspection = HOURS_BETWEEN_INSPECTION;

    }

    public Plane(String airline, String model, int y, double
            gph) {
        this.airline = airline;
        this.model = model;
        this.year = y;
        this.gph = gph;
        flyingHours = 0.00;
        gallons = TANK_CAPACITY;
        inspection = HOURS_BETWEEN_INSPECTION;
    }

    public String getAirline() {
        return airline;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public double getGph() {
        return gph;
    }

    public double getHoursNextInspection() {
        return inspection;
    }

    public double checkFlyingHours() {
        return flyingHours;
    }

    public double checkGasGauge() {
        return gallons;
    }

    public String toString() {
        return getAirline() + " " + getModel() + " " + getYear() + " Flight Hours: " + df.format(flyingHours) + " JetFuel in Tank: " + df.format(gallons);

    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setGph(double value) {
        this.gph = value;
    }

    public void setYear(int y) {
        this.year = y;
    }

    public void fly(double hours) {
        double gasRemaining = gallons - (gph * hours); // Variable to how much gas will be left after flight, used to see if it can't make it.

        if (engine) {
            if (hours < 0) {
                System.out.println(getAirline() + " " + getModel() + " " + getYear() + " cannot fly negative miles.");

            } else if (gasRemaining <= 0) { // Checks if flight will run out of fuel using gasRemaining variable.
                for (int i = 0; i < (hours * 10); i += 1) {
                    double n = i / 10.0; // Using the for loop to get flight hour precision of one tenth.
                    gallons -= 0.1 * gph;
                    landed = false;
                    if (gallons <= 0) { // When the fuel runs out the distance will be recorded and fuel set to 0
                        flyingHours += n;
                        gallons = 0;
                        inspection -= n;
                        System.out.println(airline + " " + getModel() + " " + getYear() + " ran out of gas after flying for " + (n) + " hours.");
                        landAndStopEngine();
                        break; // Ends the loop to not update variables after the plane has run out of fuel
                    }
                }
            } else { // If the plane has enough gas to fly for the amount of hours
                inspection -= hours;
                flyingHours += hours;
                gallons -= hours * gph;
                landed = false;
                System.out.println(getAirline() + " " + getModel() + " " + getYear() + " flew " + hours + " hours.");
            }
        } else { // If engine is off
            System.out.println(getAirline() + " " + getModel() + " " + getYear() + " must be ON to fly.");
        }
    }

    public void addGas(int gallons) {
        if (!engine && landed) {
            if (gallons <= 0) {
                System.out.println(getAirline() + " " + getModel() + " " + getYear() + " gallons cannot be a negative number - Fuel in Tank after the fill up: " + df.format(this.gallons));
            } else if (gallons + this.checkGasGauge() > TANK_CAPACITY) {
                this.gallons = TANK_CAPACITY;
                System.out.println(getAirline() + " " + getModel() + " " + getYear() + " tank overflowed - Gas in Tank after the fill up: " + df.format(this.gallons));
            } else {
                this.gallons = this.checkGasGauge() + gallons;
                System.out.println(getAirline() + " " + getModel() + " " + getYear() + " added gas: " + gallons + " - Gas in Tank after the fill up: " + df.format(this.gallons));
            }
        } else {
            System.out.println(getAirline() + " " + getModel() + " " + getYear() + " - must be landed and OFF to add fuel.");
        }
    }

    public void inspect() {
        inspection = 48;
        System.out.println(getAirline() + " " + getModel() + " " + getYear() + " has been inspected, next inspection is: " + inspection); // not sure how to make round
    }

    public void checkForInspection() {
        if (inspection <= 0) {
            System.out.println(getAirline() + " " + getModel() + " " + getYear() + " - It is time to inspect.");
        } else {
            System.out.println(getAirline() + " " + getModel() + " " + getYear() + " - Plane is OK, no need to inspect.");
        }
    }

    public boolean equals(Plane other) {
        return other.getAirline().equals(this.getAirline()) && other.getModel().equals(this.getModel()) && other.getYear() == this.getYear();
    }

    public void startEngine() {
        engine = true;
        System.out.println(getAirline() + " " + getModel() + " " + getYear() + " - Engine started.");
    }

    public void landAndStopEngine() {
        engine = false;
        landed = true;
        System.out.println(getAirline() + " " + getModel() + " " + getYear() + " - was landed and stopped.");
    }

    public boolean isEngineOn() {
        return engine;
    }

    private double calcRange() {
        return (gallons / gph);
    }

    private double calcGasNeededToFillTank() {
        return (TANK_CAPACITY - gallons);
    }

    public void simulateMultiCityTrip(int numberCities) {
        Random rand = new Random();
        for (int i = 1; i <= numberCities; i++){
            double max_hours = calcRange();
            int distance = rand.nextInt((int) max_hours);
            if (distance <= 0){
                System.out.println(getAirline() + " " + getModel() + " " + getYear() + " does not have enough fuel to take off for flight " + (i) + ".");
                break;
            }
            startEngine();
            fly(distance);
            landAndStopEngine();

        }
    }

    public static void main(String[] args) {
        Plane p = new Plane("Delta", "616", 2022, 1500.0);
        p.simulateMultiCityTrip(4);


    }
}
