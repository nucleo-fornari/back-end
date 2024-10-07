package fornari.nucleo.service;

import fornari.nucleo.domain.dto.EnderecoApiExternaDto;
import fornari.nucleo.domain.dto.EnderecoDto;
import fornari.nucleo.domain.entity.Endereco;
import fornari.nucleo.models.interfaces.BuilderRestStrategy;
import fornari.nucleo.repository.EnderecoRepository;
import fornari.nucleo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService implements BuilderRestStrategy {
    private final UsuarioRepository usuarioRepository;

    private final EnderecoRepository repository;

    public EnderecoService (
            UsuarioRepository usuarioRepository,
            EnderecoRepository enderecoRepository
    ) {
        this.usuarioRepository = usuarioRepository;
        this.repository = enderecoRepository;
    }

    @Override
    public RestClient builderRest(String url) {
        return RestClient.builder()
                .baseUrl(url)
                .messageConverters(httpMessageConverters -> httpMessageConverters.add(new MappingJackson2HttpMessageConverter()))
                        .build();
    }

    public List<EnderecoDto> organizeByLogradouro(List<EnderecoDto> lista) {
        List<EnderecoDto> listaMutavel = new ArrayList<>(lista);

        for (int i = 0; i < listaMutavel.size(); i++) {
            int indMenor = i;
            for (int j = i + 1; j < listaMutavel.size(); j++) {
                if (listaMutavel.get(j).getLogradouro().compareTo(listaMutavel.get(indMenor).getLogradouro()) < 0) {
                    indMenor = j;
                }
            }

            EnderecoDto aux = listaMutavel.get(i);
            listaMutavel.set(i, listaMutavel.get(indMenor));
            listaMutavel.set(indMenor, aux);
        }
        return listaMutavel;
    }

    public boolean alreadyExists(Endereco endereco) {
        return this.repository.existsByCepAndNumeroAndComplemento(endereco.getCep(), endereco.getNumero(), endereco.getComplemento());
    }

    public Optional<Endereco> findExistentEndereco(Endereco endereco) {
        return this.repository.findOneByCepAndNumeroAndComplemento(endereco.getCep(), endereco.getNumero(), endereco.getComplemento());
    }

    public void delete(Endereco endereco) {
        this.repository.delete(endereco);
    }
}
