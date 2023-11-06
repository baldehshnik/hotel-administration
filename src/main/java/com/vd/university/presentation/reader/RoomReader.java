package com.vd.university.presentation.reader;

import com.vd.university.presentation.entity.RoomType;

import java.util.Scanner;

public record RoomReader(Scanner scanner) {

    public RoomType readRoomType() {
        System.out.println(
                """
                        Select room type:
                            1. VIP
                            2. Standard
                            3. For children (6-14 years old)
                        """
        );
        System.out.print("Enter your choice: ");

        try {
            int selection = scanner.nextInt();
            scanner.nextLine();
            return switch (selection) {
                case 1 -> RoomType.VIP;
                case 2 -> RoomType.STANDARD;
                case 3 -> RoomType.CHILDREN;
                default -> throw new Exception();
            };
        } catch (Exception exception) {
            scanner.nextLine();
            System.out.println("Sorry, but we didn't find any matches for your selection. Try again!");
        }

        return readRoomType();
    }

    public int readBedsCount() {
        System.out.print("Enter the desired number of beds: ");
        try {
            int bedsCount = Integer.parseInt(scanner.nextLine());
            if (bedsCount > 0 && bedsCount <= 3) {
                return bedsCount;
            } else {
                throw new Exception();
            }
        } catch (Exception exception) {
            System.out.println("Sorry, but there are no rooms with this many beds. Try again!");
            return readBedsCount();
        }
    }
}