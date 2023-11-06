package com.vd.university.di.module;

import com.vd.university.di.annotation.SingletonScope;

import java.util.Scanner;

@SingletonScope
public class ScannerModule {

    private Scanner scanner;

    public Scanner getScanner() {
        if (scanner == null) scanner = createScanner();
        return scanner;
    }

    private Scanner createScanner() {
        return new Scanner(System.in);
    }
}