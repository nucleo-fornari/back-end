package fornari.nucleo.controller;

import fornari.nucleo.service.ExportService;
import fornari.nucleo.service.FileStorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes unitários para FileController")
public class FileControllerTest {

    @Mock
    private FileStorageService fileStorageService;

    @Mock
    private ExportService exportService;

    @InjectMocks
    private FileController fileController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        // Inicializa o MockMvc antes de cada teste
        mockMvc = MockMvcBuilders.standaloneSetup(fileController).build();
    }

    @Test
    void testUploadFile() throws Exception {
        // Mockando o comportamento do serviço de upload de arquivo
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "test content".getBytes());
        when(fileStorageService.storeFile(file)).thenReturn("test.txt");

        // Enviar a requisição de upload e verificar o status
        mockMvc.perform(multipart("/files/upload")
                        .file(file))
                .andExpect(status().isOk());  // Verifica se o status é 200 OK
    }
}
