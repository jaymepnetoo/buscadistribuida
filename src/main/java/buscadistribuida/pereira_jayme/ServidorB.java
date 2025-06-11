package buscadistribuida.pereira_jayme;

import utils.BuscaUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class ServidorB {
    public static void main(String[] args) throws IOException {
        ServerSocket servidor = new ServerSocket(5001);
        System.out.println("Servidor B pronto na porta 5001");

        while (true) {
            Socket socket = servidor.accept();

            new Thread(() -> {
                try (
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
                ) {
                    String termo = in.readLine();
                    List<String> resultados = BuscaUtils.buscar(termo, "src/main/resources/dados_servidor_b.json");

                    for (String r : resultados) out.println(r);
                    out.println("FIM");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
