package com.servidor.api;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * Contexto inicial dos recursos REST,
 * deve acessar com localhost:8080/api/{path do controller desejado}
 *
 * @author Jean Maciel
 */
@ApplicationPath("/")
public class Api extends Application {

}
