package com.jaruizes.functionalinterfaces;

import com.jaruizes.common.Car;

import java.util.List;
import java.util.Scanner;

@FunctionalInterface
public interface CarsProcessor {
    List<Car> process(Scanner carData);
}
