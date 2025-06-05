package fornari.nucleo.service;

import fornari.nucleo.configuration.FileStorageConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageConfig fileStorageConfig) {
        this.fileStorageLocation = Paths.get(fileStorageConfig.getUploadDir()).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Não foi possível criar o diretório onde os arquivos serão armazenados.", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Validar o nome do arquivo
            if (originalFilename.contains("..")) {
                throw new RuntimeException("O nome do arquivo contêm uma sequência inválida: " + originalFilename);
            }

            // Adicionar timestamp ao nome do arquivo
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            String fileExtension = "";
            String baseName = originalFilename;

            // Verificar e separar a extensão, se houver
            int dotIndex = originalFilename.lastIndexOf(".");
            if (dotIndex != -1) {
                fileExtension = originalFilename.substring(dotIndex);
                baseName = originalFilename.substring(0, dotIndex);
            }

            String newFilename = baseName + "_" + timestamp + fileExtension;

            // Armazenar o arquivo com o novo nome
            Path targetLocation = this.fileStorageLocation.resolve(newFilename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return newFilename;
        } catch (Exception ex) {
            throw new RuntimeException("Não foi possível armazenar o arquivo. " + originalFilename + ". Por favor, tente novamente!", ex);
        }
    }


    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return resource;
            }

            throw new RuntimeException("File not found " + fileName);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Arquivo não encontrado " + fileName);
        }
    }

    public Path getFileStorageLocation() {
        return fileStorageLocation;
    }
}
