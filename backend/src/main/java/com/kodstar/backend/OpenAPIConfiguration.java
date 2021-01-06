package com.kodstar.backend;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI customOpenAPI(@Value("${springdoc.version}") String appVersion) {
        return new OpenAPI()
                .components(new Components())
                .info(metaData(appVersion))
                .servers(getServers())
                .addTagsItem(new Tag().name("issue"))
                .addTagsItem(new Tag().name("label"))
                .addTagsItem(new Tag().name("project"));
    }

    private Info metaData(String appVersion){
        Info info = new Info();

        info.version(appVersion);
        info.title("Issue Tracker API - Kodstar");
        info.contact(new Contact().name("AGA").email("adamkopf0@gmail.com"));
        info.license(new License().name("Apache License Version 2.0").url("https://www.apache.org/licenses/LICENSE-2.0"));

        return info;
    }

    private List<Server> getServers(){
        List<Server> servers = new ArrayList<>();
        Server server1 = new Server();
        Server server2 = new Server();

        server1.setUrl("http://localhost:8080");
        server2.setUrl("https://mini-track.herokuapp.com");

        servers.add(server1);
        servers.add(server2);

        return servers;
    }

}