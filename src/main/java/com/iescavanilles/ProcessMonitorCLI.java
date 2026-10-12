/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.iescavanilles;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author vicen
 */
public class ProcessMonitorCLI {

    public static void main(String[] args) {

        ejecutarHito1();
        ejecutarHito2();
        ejecutarHito3();
        ejecutarHito4();
    }

    public static void ejecutarHito1(){
        String os = System.getProperty("os.name").toLowerCase().trim();
        List<String> comando = new ArrayList<>();

        if(os.contains("linux")){
            comando.add("linux");
            comando.add("aux");
        }else{
            comando.add("tasklist");
        }

        try{
            ProcessBuilder pb = new ProcessBuilder(comando);
            Process p =pb.start();

            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            int lineas =0;
            boolean contieneJava = false;
            String linea;

            while((linea = br.readLine()) != null){
                lineas++;
                if(linea.toLowerCase().contains("java")){
                    contieneJava = true;
                }
            }
            System.out.println("Total lineas: "+lineas);
            System.out.println("Contiene Java: "+contieneJava);
        } catch(IOException e){
            System.out.println("Error: "+e.getMessage());
        }
    }

    public static void ejecutarHito2(){
        try {
            ProcessBuilder pb = new ProcessBuilder("java", "-cp", "target/classes", "com.iescavanilles.LogFilterEngine");
            Process p = pb.start();

            PrintWriter out = new PrintWriter(p.getOutputStream());
            out.println("Error 404 en el servidor");
            out.println("critical system failure in database");
            out.println("Update completed");
            out.println("java heap space warning");
            out.flush();
            out.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String respuesta;
            while((respuesta = in.readLine()) != null){
                System.out.println("Hijo respondio: " +respuesta);
            }
        } catch(IOException e){
            System.out.println("Error: "+e.getMessage());
        }
    }


    public static void ejecutarHito3(){
        try{
            ProcessBuilder pb = new ProcessBuilder("java", "-version");
            pb.redirectErrorStream(true);
            pb.redirectOutput(ProcessBuilder.Redirect.to(new File("reporte_sistema.log")));

            Process p = pb.start();
            p.waitFor();
            System.out.println("Reporte generado en reporte_sistema.log");
        } catch(IOException | InterruptedException e){
            System.out.println("Error: "+e.getMessage());
        }
    }

    public static void ejecutarHito4(){
        String os = System.getProperty("os.name").toLowerCase();
        ProcessBuilder pb = os.contains("linux")
                ? new ProcessBuilder("ping", "-c", "10", "127.0.0.1")
                : new ProcessBuilder("ping", "-n", "10", "127.0.0.1");

        try{
            Process p = pb.start();

            boolean finalizado = p.waitFor(3, TimeUnit.SECONDS);

            if(!finalizado){
                System.out.println("Advertencia: El proceso excedió el tiempo limite.");
                p.destroyForcibly();
                System.out.println("Proceso cerrado forzosa y limpiamente");
            } else{
                System.out.println("Proceso finalizado correctamente.");
            }
        } catch(IOException | InterruptedException e){
            System.out.println("Error: "+e.getMessage());
        }
    }
}
