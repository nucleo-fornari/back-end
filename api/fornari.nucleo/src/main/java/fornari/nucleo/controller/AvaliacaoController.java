package fornari.nucleo.controller;

import com.lowagie.text.DocumentException;
import fornari.nucleo.domain.dto.AlunoAvaliacao.AlunoAvaliacaoDto;
import fornari.nucleo.domain.dto.AlunoAvaliacao.AlunoAvaliacaoRequestDto;
import fornari.nucleo.domain.dto.AvaliacaoDimensoes.AvaliacaoDimensoesRequestDto;
import fornari.nucleo.domain.dto.AvaliacaoDimensoes.AvaliacaoDimensoesResponseDto;
import fornari.nucleo.domain.entity.AlunoAvaliacao;
import fornari.nucleo.domain.entity.AvaliacaoDimensoes;
import fornari.nucleo.domain.entity.Usuario;
import fornari.nucleo.domain.mapper.AlunoAvaliacaoMapper;
import fornari.nucleo.domain.mapper.AvaliacaoDimensoesMapper;
import fornari.nucleo.service.AvaliacaoDimensoesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/avaliacao")
@RequiredArgsConstructor
public class AvaliacaoController {

    private final AvaliacaoDimensoesService service;

    @GetMapping("/pdf/{id}")
    public ResponseEntity<byte[]> generatePdfFromHtml(@PathVariable Integer id) throws IOException, DocumentException {
        // HTML corrigido (sem xmlns e sem escapes desnecessários)
        AlunoAvaliacao a = this.service.findById(id);

        LocalDate dt = a.getDtCriacao();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy");
        String dataFormatada = "Mauá, " + dt.format(formatter);

        StringBuilder professoras = new StringBuilder();
        List<Usuario> professorasList = a.getAluno().getSala().getProfessores();

        for (int i = 0; i < professorasList.size(); i++) {
            professoras.append(professorasList.get(i).getNome());

            if(! (i + 1 == professorasList.size()))
                professoras.append(", ");
        }

        String htmlContent = """
        <!DOCTYPE html>
          <html>
          <head>
              <meta charset="UTF-8" />
              <style>
                  @page {
                      size: A4 portrait;
                      margin: 20mm;
                  }

                  body {
                      font-family: Arial, sans-serif;
                      font-size: 12px;
                      line-height: 1.5;
                  }

                  #main {
                      width: 100%;
                      border: solid 2px #000;
                      display: flex;
                      align-items: center;
                      justify-content: center;
                      padding: 10px;
                  }

                  .center_container {
                      width: 90%;
                      display: flex;
                      align-items: center;
                      flex-direction: column;
                      text-align: center;
                  }

                  .initial_table {
                      width: 100%;
                      border-collapse: collapse;
                      font-size: 14px;
                      margin-top: 10px;
                  }

                  .initial_table td {
                      padding: 5px;
                      border: 1px solid #000;
                      text-align: left;
                  }

                  .underline {
                      text-decoration: underline;
                      font-weight: bold;
                  }

                  .dimensoes_container {
                      border: solid 2px #000;
                      width: 100%;
                      min-height: 250px;
                      padding: 20px;
                      margin-top: 40px; /* Aumento do espaçamento entre as dimensões */
                  }

                  .dimensoes_container h3 {
                      margin-bottom: 15px;
                  }

                  .dimensoes_container p {
                      margin-bottom: 40px; /* Espaçamento maior entre as seções */
                  }

                  .signature_table {
                      width: 100%;
                      margin-top: 30px;
                      border-collapse: collapse;
                  }

                  .signature_table td {
                      padding: 10px;
                      text-align: center;
                      font-weight: bold;
                      border-bottom: 2px solid #000;
                  }

                  .rodape {
                      width: 100%;
                      text-align: center; /* Centraliza os elementos finais */
                      margin-top: 30px;
                      font-weight: bold;
                  }
              </style>
          </head>
          <body>
              <section id="main">
                  <div class="center_container">
                      <h2>PREFEITURA MUNICIPAL DE MAUÁ</h2>
                      <h3>SECRETARIA DE EDUCAÇÃO</h3>

                      <table class="initial_table">
                          <tr>
                              <td width="20%"><strong>Escola:</strong></td>
                              <td width="80%">E.M. Darci Aparecida Fincatti Fornari</td>
                          </tr>
                          <tr> <!-- Corrigido para garantir que a linha não quebre -->
                              <td><strong class="underline">Aluno:</strong></td>
                              <td>
                              """ + a.getAluno().getNome() + """
                              </td>
                          </tr>
                          <tr>
                              <td><strong class="underline">Grupo:</strong></td>
                              <td>
                              """ + a.getAluno().getSala().getNome() + """
                              </td>
                              <td><strong class="underline">Período:</strong></td>
                              <td>
                              """ + a.getPeriodo() + """
                              </td>
                              <td><strong class="underline">Ano:</strong></td>
                              <td>
                              """ + a.getAno() + """
                              </td>
                          </tr>
                      </table>

                      <h2 class="underline">Relatório Individual de Acompanhamento e Desenvolvimento da Aprendizagem</h2>
                      <h2 class="underline">
                      """ + a.getBimestre() + """
                      ° Bimestre</h2>

                      <div class="dimensoes_container">
                          <h3>Dimensão sócio-afetivo-emocional:</h3>
                          <p>
                          """ + a.getTextoSocioAfetivaEmocional() + """
                          </p>

                          <h3>Dimensão físico-motora:</h3>
                          <p>
                          """ + a.getTextoFisicoMotora() +"""
                          </p>

                          <h3>Dimensão Cognitiva:</h3>
                          <p>
                          """ + a.getTextoCognitiva() + """
                          </p>
                      </div>

                      <table class="signature_table">
                          <tr>
                              <td>Professoras:</td>
                              <td>
                              """ + professoras + """
                              </td>
                          </tr>
                          <tr>
                              <td>Professor(a) Coordenador Pedagógico</td>
                              <td>________________________</td>
                          </tr>
                          <tr>
                              <td>Pais ou Responsável</td>
                              <td>________________________</td>
                          </tr>
                      </table>

                      <div class="rodape">
                          """ + dataFormatada + """
                      </div>
                  </div>
              </section>
          </body>
          </html>
            """;

        // Gerando PDF com Flying Saucer
        ByteArrayOutputStream pdfOutputStream = new ByteArrayOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(htmlContent);
        renderer.layout();
        renderer.createPDF(pdfOutputStream);

        // Configurar headers para o download do PDF
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/pdf");
        headers.add("Content-Disposition", "attachment; filename=generated.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfOutputStream.toByteArray());
    }


    @PostMapping
    public ResponseEntity<AlunoAvaliacaoDto> createAvaliacao(@RequestBody AlunoAvaliacaoRequestDto body) {
        return ResponseEntity.status(201).body(
                AlunoAvaliacaoMapper.toDto(this.service.createAvaliacao(AlunoAvaliacaoMapper.toDomain(body), body.getAlunoId()))
        );
    }

    @PostMapping("/dimensao")
    public ResponseEntity<AvaliacaoDimensoesResponseDto> create(@RequestBody AvaliacaoDimensoesRequestDto body) {
        return ResponseEntity.status(201).body(
                AvaliacaoDimensoesMapper.toDto(this.service.create(AvaliacaoDimensoesMapper.toDomain(body), body.getUserId()))
        );
    }

    @GetMapping("/dimensao/{userId}/{tipo}")
    public ResponseEntity<List<AvaliacaoDimensoesResponseDto>> getById(@PathVariable Integer userId, @PathVariable String tipo) {
        List<AvaliacaoDimensoes> res = this.service.findByUserAndTipo(userId, tipo);

        if (res.isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok().body(res.stream().map(AvaliacaoDimensoesMapper::toDto).toList());
    }

    @DeleteMapping("/dimensao/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        this.service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAvaliacao(@PathVariable Integer id) {
        this.service.deleteAvaliacao(id);
        return ResponseEntity.noContent().build();
    }
}
