package com.jaruizes.streams;

import com.jaruizes.common.Car;
import com.jaruizes.functionalinterfaces.CarsProcessor;
import com.jaruizes.functionalinterfaces.ReadAndProcessCars;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

public class StreamsBasic {

    private static List<Car> readAndProcess(String file, CarsProcessor carsProcessor) {
        List<Car> cars = new ArrayList<>();
        try (Scanner scanner = new Scanner(Paths.get(file).toFile())) {
            cars = carsProcessor.process(scanner);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return cars;
    }

    private static List<String> readCarsAndFilterByBrand(List<Car> cars, String brand) {
        // List with records containing "brand" and only with the name
        List<String> carsByBrand = cars.stream()
                .filter((car) -> car.getName().contains(brand))     // Filter by brand
                .map(Car::getName)                                  // Get only the name
                .collect(toList());                                 // Collect into a List

        return carsByBrand;
    }

    private static Map<String, List<String>> readCarsAndFilterByPowerGreaterThanArgumentAndGroupingByBrand(List<Car> cars, int power) {
        Map<String, List<String>> result = cars.stream()
                .filter((car) -> car.getPower() >= power)                               // Filter by brand
                .map(Car::getName)
                .collect(groupingBy((carName) -> carName.split("\\s+")[0]));       // Group by brand

        return result;

    }

    private static boolean readCarsAndFindIfAnyCarHavingPowerGreaterThanArgument(List<Car> cars, int power) {
        return cars.stream().anyMatch((car) -> car.getPower() > power);
    }

    private static void readCarsAndFindIfAnyCarHavingPowerGreaterThanArgumentAndWriteTheResult(List<Car> cars, int power) {
        cars.stream()
            .filter((car) -> car.getPower() > power)
            .findAny()
            .ifPresent((car) -> System.out.println("There are cars having power greater than 400 CV: " + car));
    }

    private static int getAllThePower(List<Car> cars) {
        return cars.stream().map(Car::getPower).reduce(0, (power1, power2) -> power1 + power2);
    }

    private static int getAllThePowerPrimitive(List<Car> cars) {
        return cars.stream().mapToInt(Car::getPower).sum();
    }

    private static void getTheCarWithMaxPower(List<Car> cars) {
        cars.stream()
                .mapToInt(Car::getPower)
                .max()
                .ifPresent((value) -> cars.stream()
                        .filter((car) -> car.getPower() == value)
                        .findAny()
                        .ifPresent((car) -> System.out.println("The car with max power is: " + car)));
    }

    private static String getTheCarWithMaxPowerWithReturn(List<Car> cars) {
        return cars.stream()
                .filter((car) -> car.getPower() == cars.stream().mapToInt(Car::getPower).max().getAsInt())
                .map(Car::getName)
                .findFirst()
                .get();
    }

    public static void main(String[] args) {
        // Original list
        final List<Car> cars = readAndProcess("resources/cars.csv", ReadAndProcessCars.processorOne);

        readCarsAndFilterByBrand(cars,"Merc").forEach((carName) -> System.out.println(carName));
        System.out.println("\n----------------------");

        readCarsAndFilterByPowerGreaterThanArgumentAndGroupingByBrand(cars,300)
                .forEach((key,value) -> {
                    System.out.println(key);
                    value.forEach((car) -> System.out.println("\t " + car));
                });

        System.out.println("\n----------------------");
        if (readCarsAndFindIfAnyCarHavingPowerGreaterThanArgument(cars,400)) {
            System.out.println("There are cars having power greater than 400 CV");
        }

        System.out.println("\n----------------------");
        readCarsAndFindIfAnyCarHavingPowerGreaterThanArgumentAndWriteTheResult(cars,400);

        System.out.println("\n----------------------");
        System.out.println("Total power is: " + getAllThePower(cars));

        System.out.println("\n----------------------");
        System.out.println("Total power is: " + getAllThePowerPrimitive(cars));

        System.out.println("\n----------------------");
        getTheCarWithMaxPower(cars);

        System.out.println("\n----------------------");
        System.out.println("The car with max power is: " + getTheCarWithMaxPowerWithReturn(cars));
    }
}
