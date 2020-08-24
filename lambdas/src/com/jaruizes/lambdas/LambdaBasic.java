package com.jaruizes.lambdas;

import com.jaruizes.common.Car;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class LambdaBasic {
    private static List<Car> initCars() {
        List<Car> cars = new ArrayList<>();
        cars.add(new Car("Seat", 100));
        cars.add(new Car("BMW",60));
        cars.add(new Car("Mercedes",300));
        cars.add(new Car("Ferrari",245));

        return cars;
    }

    private static void printCars(List<Car> cars) {
        cars.forEach((car -> System.out.println(car)));
    }

    /**
     * Sorts a list of Car objects using a comparator object
     * @param cars
     */
    private static void sortByPowerUsingADeclaredComparatorObject(List<Car> cars) {
        // Comparator by Power: lambda returning an integer (power1 - power2)
        final Comparator<Car> comparatorByPower = (car1, car2) -> car1.getPower() - car2.getPower();

        System.out.println("-------- Sorting by power ---------");
        cars.sort(comparatorByPower);
        printCars(cars);
        System.out.println("-----------\n\n");
    }

    /**
     * Sorts cars by name, without declaring a Comparator object
     * @param cars
     */
    private static void sortByNameWithoutUsingADeclaredComparatorObject(List<Car> cars) {
        System.out.println("-------- Sorting by name -------- ");
        cars.sort((car1, car2) -> car1.getName().compareTo(car2.getName()));
        printCars(cars);
        System.out.println("-----------\n\n");
    }

    /**
     * Sorts cars by a custom algorithm using two lambdas
     * @param cars
     */
    private static void sortByCustomAlgorithmUsingADeclaredComparatorObject(List<Car> cars) {
        // Comparator by Power: lambda returning an integer (power1 - power2)
        final Comparator<Car> comparatorByPowerInv = (car1, car2) -> car2.getPower() - car1.getPower();

        // Sorting by "custom algorithm": if name is SEAT is greater than other. If not, compare by power
        final Comparator<Car> comparatorByAlgorithm = (car1, car2) -> {
            if (car1.getName().equalsIgnoreCase("SEAT")) {
                return -1;
            }
            if (car2.getName().equalsIgnoreCase("SEAT")) {
                return 1;
            }

            return comparatorByPowerInv.compare(car1, car2);
        };

        System.out.println("-------- Sorting by custom algorithm -------- ");
        cars.sort(comparatorByAlgorithm);
        printCars(cars);
        System.out.println("-----------\n\n");
    }

    /**
     * Finds a car using a Predicate defined by a lambda function
     * @param cars
     * @param brand
     */
    private static void findCarByBrand(List<Car> cars, String brand) {
        // Using a lambda to define a Predicate object
        Predicate<Car> carByBrand = (car -> car.getName().equalsIgnoreCase(brand));

        final List<Car> carsByBrand = cars.stream().filter(carByBrand).collect(Collectors.toList());
        System.out.println("-------- Finding cars by brand: " + brand + " -------- ");
        printCars(carsByBrand);
        System.out.println("-----------\n\n");
    }

    public static void main(String[] args) {
        // Init list
        final List<Car> cars = initCars();

        // Sorting
        sortByPowerUsingADeclaredComparatorObject(cars);
        sortByNameWithoutUsingADeclaredComparatorObject(cars);
        sortByCustomAlgorithmUsingADeclaredComparatorObject(cars);

        // Filtering
        findCarByBrand(cars, "SEAT");

    }
}
