package fornari.nucleo.service;

import fornari.nucleo.dto.EnderecoApiExternaDto;
import fornari.nucleo.dto.EnderecoDto;
import fornari.nucleo.entity.Endereco;
import fornari.nucleo.models.interfaces.BuilderRestStrategy;
import fornari.nucleo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class EnderecoService implements BuilderRestStrategy {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public RestClient builderRest(String url) {
        return RestClient.builder()
                .baseUrl(url)
                .messageConverters(httpMessageConverters -> httpMessageConverters.add(new MappingJackson2HttpMessageConverter()))
                        .build();
    }

    public EnderecoDto getEnderecoApi(String cep) {
        RestClient client = builderRest("https://viacep.com.br/ws/");

        EnderecoApiExternaDto enderecoApiExternaDto = client.get()
                .uri(cep + "/json")
                .retrieve()
                .body(EnderecoApiExternaDto.class);

        EnderecoDto enderecoDto = new EnderecoDto();
        enderecoDto.setBairro(enderecoApiExternaDto.getBairro());
        enderecoDto.setCep(enderecoApiExternaDto.getCep());
        enderecoDto.setLocalidade(enderecoApiExternaDto.getLocalidade());
        enderecoDto.setUf(enderecoApiExternaDto.getUf());
        enderecoDto.setLogradouro(enderecoApiExternaDto.getLogradouro());

        return enderecoDto;
    }
}
