package com.example.demo.config;

import com.example.demo.ws.CompteSoapService;
import jakarta.xml.ws.Endpoint;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CxfConfig {
    @Bean
    public Endpoint endpoint(Bus bus, CompteSoapService service) {
        EndpointImpl endpoint = new EndpointImpl(bus, service);
        endpoint.publish("/ws");
        return endpoint;
    }
}
