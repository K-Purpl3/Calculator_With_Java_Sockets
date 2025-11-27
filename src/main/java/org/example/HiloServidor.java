package org.example;

import java.io.*;
import java.net.*;

public class HiloServidor implements Runnable {

    private Socket socketCliente;

    public HiloServidor(Socket socketCliente) {
        this.socketCliente = socketCliente;
    }

    @Override
    public void run() {
        try (
                BufferedReader entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
                PrintWriter salida = new PrintWriter(socketCliente.getOutputStream(), true)
        ) {

            String mensaje;

            //el cliente enviará cadenas tipo "op#n1#n2"
            while ((mensaje = entrada.readLine()) != null) {

                if (mensaje.equalsIgnoreCase("0")) {
                    //cliente quiere cerrar la conexión
                    break;
                }

                String[] partes = mensaje.split("#");

                if (partes.length != 3) {
                    salida.println("ERROR: Formato incorrecto");
                    continue;
                }

                int op = Integer.parseInt(partes[0]);
                double n1 = Double.parseDouble(partes[1]);
                double n2 = Double.parseDouble(partes[2]);

                double resultado;

                switch (op) {
                    case 1:
                        resultado = n1 + n2;
                        break;
                    case 2:
                        resultado = n1 - n2;
                        break;
                    case 3:
                        resultado = n1 * n2;
                        break;
                    case 4:
                        if (n2 == 0) {
                            salida.println("ERROR: División entre 0");
                            continue;
                        }
                        resultado = n1 / n2;
                        break;
                    default:
                        salida.println("ERROR: Operación no válida");
                        continue;
                }

                salida.println("RESULTADO:" + resultado);
            }

        } catch (Exception e) {
            System.out.println("Error con cliente: " + e.getMessage());
        } finally {
            try {
                socketCliente.close();
            } catch (IOException e) {}
            System.out.println("Cliente desconectado");
        }
    }
}
