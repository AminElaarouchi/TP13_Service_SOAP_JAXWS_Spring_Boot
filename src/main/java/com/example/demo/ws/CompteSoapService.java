package com.example.demo.ws;

import com.example.demo.entities.Compte;
import com.example.demo.entities.TypeCompte;
import com.example.demo.repositories.CompteRepository;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.List;

@Component
@WebService(serviceName = "BanqueWS", targetNamespace = "http://ws.demo.example.com/")
public class CompteSoapService {
    private final CompteRepository comptes;
    public CompteSoapService(CompteRepository comptes) { this.comptes = comptes; }

    @WebMethod public List<Compte> getComptes() { return comptes.findAll(); }
    @WebMethod public Compte getCompteById(@WebParam(name = "id") Long id) {
        return comptes.findById(id).orElse(null);
    }
    @WebMethod public Compte createCompte(@WebParam(name = "solde") double solde, @WebParam(name = "type") TypeCompte type) {
        return comptes.save(new Compte(solde, new Date(), type));
    }
    @WebMethod public Compte updateCompte(@WebParam(name = "id") Long id, @WebParam(name = "solde") double solde, @WebParam(name = "type") TypeCompte type) {
        Compte compte = comptes.findById(id).orElse(null);
        if (compte == null) return null;
        compte.setSolde(solde);
        compte.setType(type);
        return comptes.save(compte);
    }
    @WebMethod public boolean deleteCompte(@WebParam(name = "id") Long id) {
        if (!comptes.existsById(id)) return false;
        comptes.deleteById(id);
        return true;
    }
}
