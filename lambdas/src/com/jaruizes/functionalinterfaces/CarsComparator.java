package com.jaruizes.functionalinterfaces;

import com.jaruizes.common.Car;

/**
 * A functional interface is an interface declaring only one (abstract) method in order to be allowed to implemented using lambdas
 */
@FunctionalInterface
public interface CarsComparator {
    int compare(Car car1, Car car2);
}
