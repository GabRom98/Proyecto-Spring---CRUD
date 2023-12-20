package com.dosideas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Esta clase contiene la configuración general de Spring. En particular,
 * estamos usando Spring Boot, un componente de Spring que nos facilita la
 * configuración general del framework.
 *
 * Esta clase tiene una única anotación (aunque podría tener varias otras
 * cosas).
 *
 * La anotación @SpringBootApplication es un "atajo" a escribir las siguientes
 * anotaciones en esta clase:
 *
 * @Configuration: Le indica a Spring que esta es una clase de configuración.
 *
 * @EnableAutoConfiguration: Habilita la autoconfiguración de Spring Boot.
 *
 * @ComponentScan: Esta anotación le indica a Spring desde que paquete empezar a
 * escanear. Si no se le indica ningún paquete, (como en este caso), toma como
 * paquete base el de esta clase
 *
 */
@SpringBootApplication
public class ApplicationConfig {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationConfig.class, args);
    }

}
