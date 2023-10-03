# Plates Management System

## Description
This is a simple plates management system that allows you to generate, edit, match and identify plates.
It includes eight methods overall, which are:
- `createRandomPlate()` - creates a random plate given a serial format
- `nextPlate()` - returns the next plate in the sequence
- `getSerial()` - returns the serial format of the plate
- `isLegalVanityPlate()` - checks if the plate is a legal vanity plate
- `getMonthStats()` - returns the respective frequency of plates created in each month
- `getSerialStats()` - returns the respective frequency of plates created in each serial format
- `matchPlate()` - returns the plates that match the given plate segments

## Testing
1. To test the program, run the `PlatesTest.java` file. This file contains corresponding methods that test all the methods in the `Plates.java` file.
2. Lombok is used to generate getters and setters for the `Plates.java` file, so please ensure that you have lombok installed in your IDE.
3. JUnit 5 is used to test the program, so please ensure that you have JUnit 5 installed in your IDE.

## Authors
- [Zhenxu Chen](https://github.com/Realchenz)


