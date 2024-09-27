package fornari.nucleo.service;

import fornari.nucleo.domain.dto.EnderecoApiExternaDto;
import fornari.nucleo.domain.dto.EnderecoDto;
import fornari.nucleo.models.interfaces.BuilderRestStrategy;
import fornari.nucleo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
@Service
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
}
