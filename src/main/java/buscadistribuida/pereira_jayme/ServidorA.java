package buscadistribuida.pereira_jayme;

import java.io.*;
import java.net.*;

public class ServidorA {
    public static void main(String[] args) throws IOException {
        ServerSocket servidorA = new ServerSocket(5000);
        System.out.println("Servidor A escutando na porta 5000");

        while (true) {
            Socket cliente = servidorA.accept();

            new Thread(() -> {
                try (
                    BufferedReader in = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                    PrintWriter out = new PrintWriter(cliente.getOutputStream(), true)
                ) {
                    String termo = in.readLine();
                    String respostaB = consultarServidor("localhost", 5001, termo);
                    String respostaC = consultarServidor("localhost", 5002, termo);

                    out.println("Resultados do Servidor B:\n" + respostaB);
                    out.println("Resultados do Servidor C:\n" + respostaC);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    private static String consultarServidor(String host, int porta, String termo) {
        StringBuilder resultado = new StringBuilder();

        try (
            Socket socket = new Socket(host, porta);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
        ) {
            out.println(termo);
            String linha;
            while ((linha = in.readLine()) != null && !linha.equals("FIM")) {
                resultado.append(linha).append("\n");
            }

        } catch (IOException e) {
            resultado.append("Erro com servidor ").append(porta).append(": ").append(e.getMessage());
        }

        return resultado.toString();
    }
}
