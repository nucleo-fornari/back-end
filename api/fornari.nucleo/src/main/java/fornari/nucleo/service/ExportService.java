package fornari.nucleo.service;

import fornari.nucleo.domain.dto.sala.SalaResponseDto;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class ExportService {

    public void exportar(SalaResponseDto sala) {
        try (
                OutputStream file = new FileOutputStream("C:\\uploads\\pessoas-autorizadas-" + sala.getNome() + ".csv");
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(file));
        ) {
            // Cabeçalho


            // Iterando pelos alunos e suas filiações
            for (var aluno : sala.getAlunos()) {
                writer.write("ALUNO/A: " + aluno.getNome() + "\n");
                writer.write("NOME;PARENTESCO;TEL\n");
                for (var filiacao : aluno.getFiliacoes()) {
                    var responsavel = filiacao.getResponsavel();
                    writer.write(String.format(
                            "%s;%s;%s\n\n",
                            responsavel.getNome(),
                            filiacao.getParentesco(),
                            responsavel.getTelefone()
                    ));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
