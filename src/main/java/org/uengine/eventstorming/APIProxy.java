package org.uengine.eventstorming;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.nio.charset.Charset;


/**
 * Created by uengine on 2020. 4. 20..
 */
public class APIProxy {

    String apiServerUrl;
    String token;

    public APIProxy(String apiServerUrl, String token) {
        this.apiServerUrl = apiServerUrl;
        this.token = token;
    }

    public String call(HttpMethod httpMethod, String path, String payload) {

        try {
            RestTemplate restTemplate;

            restTemplate = noValidatingRestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(new MediaType("application", "yaml"));//MediaType.APPLICATION_JSON);
//            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + token);

            HttpEntity<String> body = new HttpEntity<String>(payload, headers);

            restTemplate.getMessageConverters()
                    .add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
            // send request and parse result
            ResponseEntity<String> response = restTemplate
                    .exchange(apiServerUrl + path, httpMethod, body, String.class);

            String result = response.getBody();
            
            return result;

        } catch (HttpClientErrorException e) {
            throw e;
        }
    }

    private RestTemplate noValidatingRestTemplate() {

        try{
            TrustStrategy acceptingTrustStrategy = new TrustStrategy() {
                public boolean isTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws java.security.cert.CertificateException {
                    return true;
                }

            };

            SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
            SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
            CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
            HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient);
            RestTemplate restTemplate = new RestTemplate(requestFactory);

            return restTemplate;
        } catch (Exception e) {
            throw new RuntimeException("failed to establish a RestTemplate for no-validating certificate");
        }

    }
}

