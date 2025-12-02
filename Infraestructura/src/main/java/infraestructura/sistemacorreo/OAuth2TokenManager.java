/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package infraestructura.sistemacorreo;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.GmailScopes;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

/**
 * Gestiona la autenticación OAuth2 para Gmail.
 * 
 * @author System
 */
public class OAuth2TokenManager {

    private static final String APPLICATION_NAME = "La Vida es Bella - Sistema de Adopciones";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Scopes de Gmail necesarios para enviar correos.
     */
    private static final List<String> SCOPES = Collections.singletonList(GmailScopes.GMAIL_SEND);

    /**
     * Path al archivo de credenciales en resources.
     */
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    /**
     * Crea un objeto Credential autorizado.
     * 
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return Un objeto Credential autorizado.
     * @throws IOException Si el archivo credentials.json no existe.
     */
    public static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
            throws IOException {
        // Cargar credenciales desde el archivo
        InputStream in = OAuth2TokenManager.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Archivo de credenciales no encontrado: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Configurar el flujo de autorización
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();

        // Crear un receptor local para recibir el código de autorización
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();

        // Autorizar (abrirá navegador la primera vez)
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");

        return credential;
    }

    /**
     * Obtiene un objeto Credential válido para usar con Gmail API.
     * 
     * @return Credential autorizado.
     * @throws IOException              Si hay error cargando credenciales.
     * @throws GeneralSecurityException Si hay error de seguridad.
     */
    public static Credential getAuthorizedCredential() throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        return getCredentials(HTTP_TRANSPORT);
    }

    /**
     * Limpia los tokens almacenados (útil para re-autenticar).
     */
    public static void clearTokens() {
        java.io.File tokensDir = new java.io.File(TOKENS_DIRECTORY_PATH);
        if (tokensDir.exists() && tokensDir.isDirectory()) {
            for (java.io.File file : tokensDir.listFiles()) {
                file.delete();
            }
            tokensDir.delete();
            System.out.println("Tokens OAuth2 eliminados.");
        }
    }
}
