package com.iescavanilles;

import java.util.Scanner;

public class LogFilterEngine {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while(sc.hasNextLine()){
            String linea = sc.nextLine().toUpperCase();

            if(linea.contains("CRITICAL") || linea.contains("WARNING") || linea.contains("JAVA")){
                System.out.println(linea);
            }
        }
        sc.close();
    }
}
