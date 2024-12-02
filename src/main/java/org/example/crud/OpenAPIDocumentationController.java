package org.example.crud;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;

@RestController
public class OpenAPIDocumentationController {

    @GetMapping(value = "/openapi", produces = MediaType.TEXT_PLAIN_VALUE)
    public String getOpenAPIDocumentation() throws IOException {
        var resource = new ClassPathResource("resources/openapi.yaml");
        return new String(Files.readAllBytes(resource.getFile().toPath()));
    }
}
