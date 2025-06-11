package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BuscaUtils {
    public static List<String> buscar(String termo, String caminhoJson) {
        List<String> resultados = new ArrayList<>();

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(new File(caminhoJson));

            for (JsonNode artigo : root) {
                String titulo = artigo.get("title").asText("");
                String intro = artigo.get("abstract").asText("");

                if (titulo.toLowerCase().contains(termo.toLowerCase()) ||
                    intro.toLowerCase().contains(termo.toLowerCase())) {
                    resultados.add("- " + titulo);
                }
            }

        } catch (Exception e) {
            resultados.add("Erro ao processar JSON: " + e.getMessage());
        }

        return resultados;
    }
}
