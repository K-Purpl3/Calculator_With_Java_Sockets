package org.example;


import java.io.*;
import java.net.*;


public class Server {
    public static void main(String[] args) {


        final int PUERTO = 12345; // Puerto donde escucha el servidor


        /*
         * ServerSocket escucha conexiones entrantes.
         * accept() se queda bloqueado hasta que un cliente se conecta.
         */
        try (ServerSocket servidor = new ServerSocket(PUERTO)) {
            System.out.println("Servidor esperando conexiones...");


        // El servidor nunca se cierra (bucle infinito)
            while (true) {
        // Espera a que un cliente se conecte
                Socket socketCliente = servidor.accept();
                System.out.println("Nuevo cliente conectado");


        // Creamos un hilo para atender a este cliente
                Thread hilo = new Thread(new HiloServidor(socketCliente));
                hilo.start(); // Ejecuta run() en paralelo
            }


        } catch (IOException e) {
            System.out.println("Error en el servidor: " + e.getMessage());
        }
    }
}