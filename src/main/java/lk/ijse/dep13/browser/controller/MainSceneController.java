package lk.ijse.dep13.browser.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class MainSceneController {
    public AnchorPane root;
    public WebView wbView;
    public TextField txtWebAddress;

    public void initialize() {
        txtWebAddress.setText("https://www.google.com");
        loadPage(txtWebAddress.getText());
        txtWebAddress.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) Platform.runLater(txtWebAddress::requestFocus);
        });
    }

    private record urlElements(
            String protocol,
            String host,
            String port,
            String path
    ){}

    private urlElements parseUrl(String url) {
        System.out.println(url);
        int i = 0;
        String protocol = null;
        String restOfUrl = null;

        // protocol
        if ((i = url.indexOf("://")) != -1) {
            protocol = url.substring(0, i);
            System.out.println("PROTOCOL: " + protocol);
            restOfUrl = url.substring(i + 3);
        }

        // host
        String host = null;
        int hostEndIndexNo = restOfUrl.indexOf("/");
        if (hostEndIndexNo != -1) {
            host = restOfUrl.substring(0, hostEndIndexNo);
        }

        // Port
        String port = switch (protocol) {
            case "http" -> "80";
            case "https" -> "443";
            case "jdbc:mysql" -> "3306";
            case "jdbc:postgresql" -> "5432";
            default -> "80";
        };
        int portIndexNo = restOfUrl.indexOf(":");
        if (portIndexNo != -1) {
            host = host.substring(0, portIndexNo);
            String portPart = restOfUrl.substring(portIndexNo + 1);
            int portEndIndex = portPart.indexOf('/');
            port = portEndIndex != -1 ? portPart.substring(0, portEndIndex) : portPart;
        }

        // Path
        String path = "/";
        if (hostEndIndexNo != -1) {
            path = restOfUrl.substring(hostEndIndexNo);
        }

        return new urlElements(protocol, host, port, path);
    }


    public void loadPage(String url) {

    }

    public void txtWebAddressOnAction(ActionEvent actionEvent) {
        String url = txtWebAddress.getText();

        if (!url.startsWith("https://") && !url.startsWith("http://")) {
            url = "http://" + url;
        }

        WebEngine engine = wbView.getEngine();
        engine.setUserAgent("Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
        engine.load(url);
        engine.setJavaScriptEnabled(true);
    }
}
