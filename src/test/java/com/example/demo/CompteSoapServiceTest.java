package com.example.demo;

import com.example.demo.entities.Compte;
import com.example.demo.entities.TypeCompte;
import com.example.demo.repositories.CompteRepository;
import com.example.demo.ws.CompteSoapService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CompteSoapServiceTest {
    @Autowired CompteSoapService service;
    @Autowired CompteRepository repository;
    @Autowired TestRestTemplate http;
    @LocalServerPort int port;

    @Test void createsListsFindsAndDeletesCompte() {
        Compte saved = service.createCompte(900.0, TypeCompte.COURANT);
        assertThat(saved.getId()).isNotNull();
        assertThat(service.getCompteById(saved.getId()).getSolde()).isEqualTo(900.0);
        assertThat(service.getComptes()).extracting(Compte::getId).contains(saved.getId());
        assertThat(service.deleteCompte(saved.getId())).isTrue();
        assertThat(service.deleteCompte(saved.getId())).isFalse();
        assertThat(repository.findById(saved.getId())).isEmpty();
        String wsdl = http.getForObject("http://localhost:" + port + "/services/ws?wsdl", String.class);
        assertThat(wsdl).contains("BanqueWS", "getComptes", "createCompte", "updateCompte", "deleteCompte");
    }
}
