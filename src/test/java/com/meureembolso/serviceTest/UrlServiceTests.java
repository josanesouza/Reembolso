package com.meureembolso.serviceTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.meureembolso.exception.UrlNotFoundException;
import com.meureembolso.model.Url;
import com.meureembolso.repository.ReembolsoRepository;
import com.meureembolso.repository.UrlRepository;
import com.meureembolso.service.UrlService;

@SpringBootTest
public class UrlServiceTests {

    @MockBean
    private UrlRepository urlRepository;

    @MockBean
    private ReembolsoRepository reembolsoRepository;

    @Autowired
    private UrlService urlService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Garantir que os mocks estão inicializados
    }

    @Test
    public void testExitsUrlByIdExists() {
        String id = "1";
        when(urlRepository.existsById(id)).thenReturn(true);

        boolean result = urlService.exitsUrlById(id);

        assertThat(result).isTrue();
    }

    @Test
    public void testExitsUrlByIdNotExists() {
        String id = "2";
        when(urlRepository.existsById(id)).thenReturn(false);

        boolean result = urlService.exitsUrlById(id);

        assertThat(result).isFalse();
    }

    @Test
    public void testSave() {
        Url url = new Url();
        url.setId("1");
        url.setFullUrl("http://example.com");

        when(urlRepository.save(url)).thenReturn(url);

        Url result = urlService.save(url);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo("1");
        assertThat(result.getFullUrl()).isEqualTo("http://example.com");
    }

    @Test
    public void testFindByIdSuccess() {
        String id = "1";
        Url url = new Url();
        url.setId(id);
        url.setFullUrl("http://example.com");

        // Garantir que o mock não é nulo
        when(urlRepository.findById(id)).thenReturn(Optional.of(url));

        Url result = urlService.findById(id);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getFullUrl()).isEqualTo("http://example.com");
    }

    @Test
    public void testFindByIdNotFound() {
        String id = "2";

        // Garantir que o mock não é nulo
        when(urlRepository.findById(id)).thenReturn(Optional.empty());

        try {
            urlService.findById(id);
        } catch (UrlNotFoundException ex) {
            assertThat(ex.getMessage()).isEqualTo("Url não encontrada");
        }
    }
}
