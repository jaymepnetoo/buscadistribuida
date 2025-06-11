package buscadistribuida.pereira_jayme;

import utils.BuscaUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class ServidorC {
    public static void main(String[] args) throws IOException {
        ServerSocket servidor = new ServerSocket(5002);
        System.out.println("Servidor C pronto na porta 5002");

        while (true) {
            Socket socket = servidor.accept();

            new Thread(() -> {
                try (
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
                ) {
                    String termo = in.readLine();
                    List<String> resultados = BuscaUtils.buscar(termo, "src/main/resources/dados_servidor_c.json");

                    for (String r : resultados) out.println(r);
                    out.println("FIM");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
