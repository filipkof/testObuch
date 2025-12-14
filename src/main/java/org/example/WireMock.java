package org.example;

import com.github.tomakehurst.wiremock.WireMockServer;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;


public class WireMock {

    private final WireMockServer server;
    private final int port;

    public WireMock(int port) {
        this.port = port;
        this.server = new WireMockServer(wireMockConfig().port(port));
    }

    public void startCheckGrade() {
        server.start();
        configureFor("localhost", port);

        stubFor(get(urlPathEqualTo("/checkGrade"))
                .atPriority(1)
                .withQueryParam("grade", matching("^[2-5]$"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/plain; charset=utf-8")
                        .withBody("true")));

        stubFor(get(urlPathEqualTo("/checkGrade"))
                .atPriority(10)
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/plain; charset=utf-8")
                        .withBody("false")));
    }

    public void stopWireMock() {
        server.stop();
    }
}