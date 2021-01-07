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
                .tags(getTags());
    }

    private Info metaData(String appVersion){
        Info info = new Info();

        info.version(appVersion);
        info.title("Issue Tracker API - Kodstar");
        info.contact(new Contact().name("AGA").email("adamkopf0@gmail.com"));
        info.license(new License().name("Apache License Version 2.0").url("https://www.apache.org/licenses/LICENSE-2.0"));

        return info;
    }

    private List<Tag> getTags(){
        return new ArrayList(){{
            add(new Tag().name("issue").description("Issue related endpoints"));
            add(new Tag().name("label").description("Label related endpoints"));
            add(new Tag().name("project").description("Project related endpoints"));
            add(new Tag().name("user").description("User related endpoints"));
            add(new Tag().name("auth").description("Auth related endpoints"));
        }};
    }

    private List<Server> getServers(){
        return new ArrayList(){{
            add(new Server().url("http://localhost:8080"));
            add(new Server().url("https://mini-track.herokuapp.com"));
        }};
    }

}