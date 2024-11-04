package fornari.nucleo.service;

import fornari.nucleo.domain.entity.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExportService {
    public static void exportar(List<Usuario> usuarios) {
        try (
                OutputStream file = new FileOutputStream("usuarios.csv");
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(file));
        ) {
            writer.write("ID;Nome;CPF;Email;Funcao\n");
            String pattern = "%d;%s;%s;%s;%s\n";

            usuarios.stream()
                    .map(usuario -> pattern.formatted(
                            usuario.getId(),
                            usuario.getNome(),
                            usuario.getCpf(),
                            usuario.getEmail(),
                            usuario.getFuncao()))
                    .forEach(linha -> {
                        try {
                            writer.write(linha);
                        } catch (IOException e) {
                            System.out.println("Erro ao escrever no arquivo: " + e.getMessage());
                        }
                    });

        } catch (FileNotFoundException erro) {
            System.out.println("Erro ao encontrar o arquivo");
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo");
        }
    }

    public static void main(String[] args) {
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(new Usuario(1, "Felipe", "12322465543", "felipe.teste@gmail.com", "Professor"));
        usuarios.add(new Usuario(2, "Ana", "98765432100", "ana.exemplo@gmail.com", "Professor"));
        usuarios.add(new Usuario(3, "Carlos", "45678912301", "carlos.exemplo@gmail.com","Responsavel"));
        usuarios.add(new Usuario(4, "Julia", "32145698710", "julia.exemplo@gmail.com", "Secretario"));
        usuarios.add(new Usuario(5, "Marcos", "65412398710", "marcos.exemplo@gmail.com","Responsavel"));
        exportar(usuarios);
    }
}
