package com.jaruizes.functionalinterfaces;

import com.jaruizes.common.Car;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;

import static java.util.Comparator.comparing;

public class ReadAndProcessCars {

    private static List<Car> readAndProcess(String file, CarsProcessor carsProcessor) {
        List<Car> cars = new ArrayList<>();
        try (Scanner scanner = new Scanner(Paths.get(file).toFile())) {
            cars = carsProcessor.process(scanner);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return cars;
    }

    private static void printCars(List<Car> cars) {
        cars.forEach((car -> System.out.println(car)));
        System.out.println("------------------------------------\n\n");
    }

    private static Car stringToCar(String data) {
        List<String> values = Arrays.asList(data.split(","));

        String name = values.get(0);
        int power = Integer.valueOf(values.get(1));
        return new Car(name, power);
    }

    public static void main(String[] args) {
        /**
         * Processor used to process file "cars.csv"
         */
        final CarsProcessor processorOne = (carsData) -> {
            final List<Car> cars = new ArrayList<>();

            while (carsData.hasNext()) {
                String carData = carsData.nextLine();
                String name = carData.substring(1, carData.lastIndexOf('"'));
                List<String> values = Arrays.asList(carData.substring(carData.lastIndexOf('"') + 2).split(","));
                int power = Integer.parseInt(values.get(2));
                cars.add(new Car(name, power));
            }

            return cars;
        };

        /**
         * Processor used to process file "cars2.txt"
         */
        final CarsProcessor processorTwo = (carsData) -> {
            final List<Car> cars = new ArrayList<>();

            while (carsData.hasNext()) {
                String carData = carsData.nextLine();
                cars.add(stringToCar(carData));
            }

            return cars;
        };

        /**
         * Function object (java.util.function.Function<T, R>) used to convert data in String to Car object
         */
        final Function<String, Car> stringToCarFunction = (data) -> stringToCar(data);

        /**
         * Processor using a Function object to process file "cars2.txt"
         */
        final CarsProcessor processorThree = (carsData) -> {
            final List<Car> cars = new ArrayList<>();

            while (carsData.hasNext()) {
                cars.add(stringToCarFunction.apply(carsData.nextLine()));
            }

            return cars;
        };

        List<Car> cars1 = readAndProcess("resources/cars.csv", processorOne);
        cars1.sort(comparing(Car::getPower));
        printCars(cars1);

        List<Car> cars2 = readAndProcess("resources/cars2.txt", processorTwo);
        cars2.sort(comparing(Car::getPower));
        printCars(cars2);

        List<Car> cars3 = readAndProcess("resources/cars2.txt", processorThree);
        cars3.sort(comparing(Car::getPower));
        printCars(cars3);
    }
}
