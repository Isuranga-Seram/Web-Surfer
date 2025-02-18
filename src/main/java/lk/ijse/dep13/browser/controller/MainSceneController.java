package lk.ijse.dep13.browser.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainSceneController {
    public AnchorPane root;
    public WebView wbView;
    public TextField txtWebAddress;

    public void initialize() {
        wbView.getEngine().locationProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null || newValue.isBlank()) return;
            ((Stage)(root.getScene().getWindow())).setTitle("Web-Browser - " + wbView.getEngine().getTitle());
            txtWebAddress.setText(wbView.getEngine().getLocation());
        });

        txtWebAddress.setText("https://www.google.com");
        loadPage(txtWebAddress.getText());
        txtWebAddress.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) Platform.runLater(txtWebAddress::requestFocus);
        });
    }

    public void txtWebAddressOnAction(ActionEvent actionEvent) {
        String url = txtWebAddress.getText();
        if (url.isBlank()) return;

        WebEngine engine = wbView.getEngine();
        engine.setUserAgent("Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
        loadPage(txtWebAddress.getText());
        txtWebAddress.selectAll();
        engine.setJavaScriptEnabled(true);
    }

    private void loadPage(String url) {
        int i = 0;
        int j = 0;
        String protocol = null;
        String host = null;
        int port = -1;
        String path = "/";

        try {
            // get the protocol
            if (protocol == null) protocol = "http";
            if ((i = url.indexOf("://")) != -1) {
                protocol = url.substring(0, i);
                System.out.println("PROTOCOL: " + protocol);
            }

            // get the host
            j = url.indexOf("/", protocol == null ? i = 0 : (i = i + 3));
            host = (j != -1 ? url.substring(i, j) : url.substring(i));
            System.out.println("HOST: " + host);

            // get the port
            if ((i = host.indexOf(":")) != -1) {
                port = Integer.parseInt(url.substring(i + 1));
                host = host.substring(0, i);
            } else {
                port = switch (protocol){
                    case "http" -> 80;
                    case "https" -> 443;
                    case "jdbc:mysql" -> 3306;
                    case "jdbc:postgresql" -> 5432;
                    default -> -1;
                };
            }
            System.out.println("PORT: " + port);

            // get the path
            if (j != -1 && j != url.length()) {
                path = url.substring(j);
                System.out.println("PATH: " + path);
            }

            if (host.isBlank() || port == -1) throw new RuntimeException("Invalid URL");
            if (!(protocol.equals("http") || protocol.equals("https"))) throw new RuntimeException("Invalid protocol");

            // Establish the connection
            Socket socket = new Socket(host, port);
            String originalUrl = protocol + "://" + host + ":" + port + "/" + path;

            // Read the server response



        } catch (RuntimeException | UnknownHostException e){
            errorMessage("400 Bad Request: Invalid URL");
        } catch (IOException e){
            errorMessage("Connection Interrupted");
        }

    }

    private void errorMessage(String message) {
        Platform.runLater(() -> {
            wbView.getEngine().loadContent("""
                    <!DOCTYPE html>
                    <html>
                    <body>
                    <h1 style="text-align: center;">%s</h1>
                    </body>
                    </html>
                    """.formatted(message));
        });
    }
}
