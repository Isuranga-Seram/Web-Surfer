package lk.ijse.dep13.browser.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        txtWebAddress.setText("http://google.com");
        loadPage(txtWebAddress.getText());
        txtWebAddress.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) Platform.runLater(txtWebAddress::selectAll);
        });
    }

    public void txtWebAddressOnAction(ActionEvent actionEvent) {
        String url = txtWebAddress.getText();
        if (url.isBlank()) return;
        loadPage(txtWebAddress.getText());
        txtWebAddress.selectAll();

        WebEngine engine = wbView.getEngine();
        engine.setUserAgent("Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
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
            if ((i = url.indexOf("://")) != -1) {
                protocol = url.substring(0, i);
                System.out.println("PROTOCOL: " + protocol);
            }
            if (protocol == null) protocol = "http";

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

            System.out.println("----------------------");

            if (host.isBlank() || port == -1) throw new RuntimeException("Invalid URL");
            if (!(protocol.equals("http") || protocol.equals("https"))) throw new RuntimeException("Invalid protocol");

            // Establish the connection
            Socket socket = new Socket(host, port);
            String originalUrl = protocol + "://" + host + ":" + port + "/";

            // Read the server response
            new Thread(() -> {
                try {
                    InputStream is = socket.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);

                    String statusLine = br.readLine();
                    int statusCode = Integer.parseInt(statusLine.split(" ")[1]);
                    boolean redirection = statusCode >= 300 && statusCode < 400;

                    String line;
                    String contentType = null;
                    boolean chunked = false;

                    while ((line = br.readLine()) != null && !line.isBlank()) {
                        String header = line.split(":")[0].strip();
                        String value = line.substring(line.indexOf(":") + 1).strip();
                        if (redirection) {
                            if (!header.equalsIgnoreCase("Location")) continue;
                            System.out.println("Redirection: " + value);
                            Platform.runLater(() -> txtWebAddress.setText(value));
                            loadPage(value);
                            return;
                        } else {
                            if (header.equalsIgnoreCase("Content-Type")) {
                                contentType = value;
                            } else if (header.equalsIgnoreCase("Transfer-Encoding")) {
                                chunked = value.equalsIgnoreCase("chunked");
                            }
                        }
                    }

                    if (contentType == null || !contentType.contains("text/html")) {
                        errorMessage("Sorry, content type not supported");
                    } else {
                        if (chunked) br.readLine();    // Skip the chunk size
                        StringBuilder sb = new StringBuilder();
                        while ((line = br.readLine()) != null) {
                            sb.append(line);
                        }
                        if (chunked) sb.deleteCharAt(sb.length() - 1); // Delete the chunk boundary

                        // Let's fix the relative url issue with the base url
                        if (!Pattern.compile("<head>.*<base .*</head>").matcher(sb).find()) {
                            Matcher headMatcher = Pattern.compile("<head>").matcher(sb);
                            if (headMatcher.find()) {
                                sb.insert(headMatcher.end(), "<base href='%s'>".formatted(originalUrl));
                            }
                        }

                        // Let's set the title
                        Matcher titleMatcher = Pattern.compile("<title>(.+)</title>", Pattern.CASE_INSENSITIVE).matcher(sb);
                        if (titleMatcher.find()) {
                            Platform.runLater(() -> {
                                ((Stage) (root.getScene().getWindow())).setTitle("DEP Browser - " + titleMatcher.group(1));
                            });
                        }


                        Platform.runLater(() -> wbView.getEngine().loadContent(sb.toString()));
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    errorMessage("Connection Failed");
                }
            }).start();

            String httpRequest = """
                    GET %s HTTP/1.1
                    HOST: %s
                    User-Agent: Mozilla/5.0
                    Connection: close
                    Accept: text/html;
                    
                    """.formatted(path, host);
            socket.getOutputStream().write(httpRequest.getBytes());
            socket.getOutputStream().flush();

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
