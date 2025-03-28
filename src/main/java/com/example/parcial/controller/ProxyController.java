package com.example.parcial.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class ProxyController {

    private final List<String> backendUrls;
    private final AtomicInteger currentIndex = new AtomicInteger(0);

    public ProxyController(@Value("${BACKEND_URLS:}") String backendUrlsEnv) {
        if (backendUrlsEnv.isEmpty()) {
            throw new IllegalStateException("Las URLs de backend no están configuradas en la variable de entorno BACKEND_URLS.");
        }
        this.backendUrls = List.of(backendUrlsEnv.split(","));
    }

    @GetMapping("/proxy/lucas")
    public Object forwardRequest(@RequestParam int n) {
        String backendUrl = getNextBackendUrl();
        String url = backendUrl + "/hello?value=" + n;
        return new RestTemplate().getForObject(url, Object.class);
    }

    private String getNextBackendUrl() {
        int index = currentIndex.getAndUpdate(i -> (i + 1) % backendUrls.size());
        return backendUrls.get(index);
    }
}
