import java.util.LinkedList;
import java.util.Scanner;

// Class representing an intersection (Node)
class Intersection {
    String name;

    public Intersection(String name) {
        this.name = name;
    }
}

// Class representing a road (Edge)
class Road {
    Intersection from;
    Intersection to;
    int distance;

    public Road(Intersection from, Intersection to, int distance) {
        this.from = from;
        this.to = to;
        this.distance = distance;
    }

    @Override
    public String toString() {
        return from.name + " → " + to.name + " (Distance: " + distance + ")";
    }
}

// Class representing a vehicle
class Vehicle {
    String id;
    Intersection start;
    Intersection destination;

    public Vehicle(String id, Intersection start, Intersection destination) {
        this.id = id;
        this.start = start;
        this.destination = destination;
    }
}

// Main class
public class SmartCityTrafficManagement {
    static LinkedList<Intersection> intersections = new LinkedList<>();
    static LinkedList<Road> roads = new LinkedList<>();
    static LinkedList<Vehicle> vehicles = new LinkedList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        initializeCity();

        while (true) {
            System.out.println("\n--- Smart City Traffic Management ---");
            System.out.println("1. View Roads");
            System.out.println("2. Add Vehicle");
            System.out.println("3. Show Vehicles & Routes");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    viewRoads();
                    break;
                case 2:
                    addVehicle(sc);
                    break;
                case 3:
                    showVehicles();
                    break;
                case 4:
                    System.out.println("Exiting system. Thank you!");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // Initialize city with intersections and roads
    public static void initializeCity() {
        Intersection A = new Intersection("A");
        Intersection B = new Intersection("B");
        Intersection C = new Intersection("C");

        intersections.add(A);
        intersections.add(B);
        intersections.add(C);

        roads.add(new Road(A, B, 5));
        roads.add(new Road(B, C, 3));
        roads.add(new Road(A, C, 8));
    }

    // View all roads
    public static void viewRoads() {
        System.out.println("\nAvailable Roads:");
        for (Road r : roads) {
            System.out.println(r);
        }
    }

    // Add a new vehicle
    public static void addVehicle(Scanner sc) {
        System.out.print("Enter Vehicle ID: ");
        String id = sc.nextLine();

        System.out.print("Enter Start Intersection (A/B/C): ");
        String start = sc.nextLine().toUpperCase();

        System.out.print("Enter Destination Intersection (A/B/C): ");
        String destination = sc.nextLine().toUpperCase();

        Intersection startNode = findIntersection(start);
        Intersection destNode = findIntersection(destination);

        if (startNode == null || destNode == null) {
            System.out.println("Invalid intersections! Vehicle not added.");
            return;
        }

        vehicles.add(new Vehicle(id, startNode, destNode));
        System.out.println("Vehicle " + id + " added successfully.");
    }

    // Find intersection by name
    public static Intersection findIntersection(String name) {
        for (Intersection i : intersections) {
            if (i.name.equalsIgnoreCase(name)) {
                return i;
            }
        }
        return null;
    }

    // Show vehicles and their routes
    public static void showVehicles() {
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles available.");
            return;
        }

        for (Vehicle v : vehicles) {
            System.out.println("Vehicle " + v.id + " traveling from " + v.start.name +
                    " to " + v.destination.name);
            findRoute(v);
        }
    }

    // Simple route finding
    public static void findRoute(Vehicle v) {
        for (Road r : roads) {
            if (r.from == v.start && r.to == v.destination) {
                System.out.println("Route found: " + r);
                return;
            }
        }
        System.out.println("No direct route found for this vehicle.");
    }
}