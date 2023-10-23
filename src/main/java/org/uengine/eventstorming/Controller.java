package org.uengine.eventstorming;

import com.google.common.collect.Iterators;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by uengine on 2020. 4. 20..
 */

//@CrossOrigin(origins = {"http://www.msaez.io", "http://www.msastudy.io", "http://www.msaez.io:8081", "http://www.msastudy.io:8081", "http://labs.msaez.io", "http://labs.msaez.io:8081", "http://www.gklabs.co.kr"})
@RestController
public class Controller {

    APIProxy apiProxy;
    APIProxy defaultApiProxy;
    APIProxy gcpApiProxy;
    Integer maxIDE = 15;

    public boolean checkUsage(APIProxy apiProxy) throws ParseException {

        String result = apiProxy.call(HttpMethod.GET, "/api/v1/nodes", null);
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(result);
        JSONObject jsonObj = (JSONObject) obj;
        Boolean returnResult = false;
        JSONArray resultArray = (JSONArray) jsonObj.get("items");
        Iterator iterator = resultArray.iterator();

        if(maxIDE >= Iterators.size(iterator)) {
            returnResult = true;
        }

        return returnResult;
    }

    @RequestMapping(value = "/apis/**", method = RequestMethod.GET)
    public String forwardGetAPIs(@RequestBody KubernetesVO kubernetesVO, @RequestParam Map<String, String> requestParams, HttpServletRequest request) throws Exception {
        String serverUrl = kubernetesVO.getServerUrl();
        String token = kubernetesVO.getToken();
        if ((serverUrl != null && !serverUrl.equals("undefined")) && (token != null && !token.equals("undefined")) && (serverUrl.length() != 0 && token.length() != 0)) {
            apiProxy = new APIProxy(serverUrl, token);
        } else {
            throw new Exception();
        }
        String queryParams = "";

        if (requestParams.size() > 0) {
            Iterator<String> iterator = requestParams.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                if (key != "serverUrl" && key != "token") {
                    String value = requestParams.get(key);
                    String tmp = "&" + key + "=" + value;
                    queryParams = queryParams + tmp;
                }
            }
            queryParams = queryParams.substring(1);
        }
        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);

        String response = apiProxy.call(HttpMethod.GET, path + "?" + queryParams, null);

        return response;
    }

    @RequestMapping(value = "/metrics/**", method = RequestMethod.GET)
    public String forwardMetrics(@RequestBody KubernetesVO kubernetesVO, @RequestParam Map<String, String> requestParams, HttpServletRequest request) throws Exception {

        String serverUrl = kubernetesVO.getServerUrl();
        String token = kubernetesVO.getToken();
        if ((serverUrl != null && !serverUrl.equals("undefined")) && (token != null && !token.equals("undefined")) && (serverUrl.length() != 0 && token.length() != 0)) {
            apiProxy = new APIProxy(serverUrl, token);
        } else {
            throw new Exception();
        }
        String queryParams = "";

        if (requestParams.size() > 0) {
            Iterator<String> iterator = requestParams.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                if (key != "serverUrl" && key != "token") {
                    String value = requestParams.get(key);
                    String tmp = "&" + key + "=" + value;
                    queryParams = queryParams + tmp;
                }
            }
            queryParams = queryParams.substring(1);
        }
        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);

        String response = apiProxy.call(HttpMethod.GET, path + "?" + queryParams, null);

        return response;

    }

    @RequestMapping(value = "/apis/**", method = RequestMethod.POST)
    public String forwardPostAPIs(@RequestBody KubernetesVO kubernetesVO, @RequestParam Map<String, String> requestParams, HttpServletRequest request) throws Exception {

        String serverUrl = kubernetesVO.getServerUrl();
        String token = kubernetesVO.getToken();
        String yaml = kubernetesVO.getYaml();
        if ((serverUrl != null && !serverUrl.equals("undefined")) && (token != null && !token.equals("undefined")) && (serverUrl.length() != 0 && token.length() != 0)) {
            apiProxy = new APIProxy(serverUrl, token);
        } else {
            throw new Exception();
        }
        String queryParams = "";

        if (requestParams.size() > 0) {
            Iterator<String> iterator = requestParams.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                if (key != "serverUrl" && key != "token") {
                    String value = requestParams.get(key);
                    String tmp = "&" + key + "=" + value;
                    queryParams = queryParams + tmp;
                }
            }
            queryParams = queryParams.substring(1);
        }

        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);

        String response = apiProxy.call(HttpMethod.POST, path + "?" + queryParams, yaml);

        return response;

    }

    @RequestMapping(value = "/apis/**", method = RequestMethod.PUT)
    public String forwardPutAPIs(@RequestBody KubernetesVO kubernetesVO, @RequestParam Map<String, String> requestParams, HttpServletRequest request) throws Exception {

        String serverUrl = kubernetesVO.getServerUrl();
        String token = kubernetesVO.getToken();
        String yaml = kubernetesVO.getYaml();
        if ((serverUrl != null && !serverUrl.equals("undefined")) && (token != null && !token.equals("undefined")) && (serverUrl.length() != 0 && token.length() != 0)) {
            apiProxy = new APIProxy(serverUrl, token);
        } else {
            throw new Exception();
        }
        String queryParams = "";

        if (requestParams.size() > 0) {
            Iterator<String> iterator = requestParams.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                if (key != "serverUrl" && key != "token") {
                    String value = requestParams.get(key);
                    String tmp = "&" + key + "=" + value;
                    queryParams = queryParams + tmp;
                }
            }
            queryParams = queryParams.substring(1);
        }
        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);

        String response = apiProxy.call(HttpMethod.PUT, path + "?" + queryParams, yaml);

        return response;

    }

    @RequestMapping(value = "/apis/**", method = RequestMethod.DELETE)
    public String forwardDeleteAPIs(@RequestBody KubernetesVO kubernetesVO, @RequestParam Map<String, String> requestParams, HttpServletRequest request) throws Exception {

        String serverUrl = kubernetesVO.getServerUrl();
        String token = kubernetesVO.getToken();
        if ((serverUrl != null && !serverUrl.equals("undefined")) && (token != null && !token.equals("undefined")) && (serverUrl.length() != 0 && token.length() != 0)) {
            apiProxy = new APIProxy(serverUrl, token);
        } else {
            throw new Exception();
        }
        String queryParams = "";

        if (requestParams.size() > 0) {
            Iterator<String> iterator = requestParams.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                if (key != "serverUrl" && key != "token") {
                    String value = requestParams.get(key);
                    String tmp = "&" + key + "=" + value;
                    queryParams = queryParams + tmp;
                }
            }
            queryParams = queryParams.substring(1);
        }

        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);

        String response = apiProxy.call(HttpMethod.DELETE, path + "?" + queryParams, null);

        return response;

    }

    @RequestMapping(value = "/api/**", method = RequestMethod.GET)
    public String forwardGetAPI(@RequestBody KubernetesVO kubernetesVO, @RequestParam Map<String, String> requestParams, HttpServletRequest request) throws Exception {
        String serverUrl = kubernetesVO.getServerUrl();
        String token = kubernetesVO.getToken();
        if ((serverUrl != null && !serverUrl.equals("undefined")) && (token != null && !token.equals("undefined")) && (serverUrl.length() != 0 && token.length() != 0)) {
            apiProxy = new APIProxy(serverUrl, token);
        } else {
            throw new Exception();
        }
        String queryParams = "";

        if (requestParams.size() > 0) {
            Iterator<String> iterator = requestParams.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                if (key != "serverUrl" && key != "token") {
                    String value = requestParams.get(key);
                    String tmp = "&" + key + "=" + value;
                    queryParams = queryParams + tmp;
                }
            }
            queryParams = queryParams.substring(1);
        }

        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);

        String response = apiProxy.call(HttpMethod.GET, path + "?" + queryParams, null);

        return response;

    }

    @RequestMapping(value = "/api/**", method = RequestMethod.POST)
    public String forwardPostAPI(@RequestBody KubernetesVO kubernetesVO, @RequestParam Map<String, String> requestParams, HttpServletRequest request) throws Exception {

        String serverUrl = kubernetesVO.getServerUrl();
        String token = kubernetesVO.getToken();
        String yaml = kubernetesVO.getYaml();
        if ((serverUrl != null && !serverUrl.equals("undefined")) && (token != null && !token.equals("undefined")) && (serverUrl.length() != 0 && token.length() != 0)) {
            apiProxy = new APIProxy(serverUrl, token);
        } else {
            throw new Exception();
        }
        String queryParams = "";

        if (requestParams.size() > 0) {
            Iterator<String> iterator = requestParams.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                if (key != "serverUrl" && key != "token") {
                    String value = requestParams.get(key);
                    String tmp = "&" + key + "=" + value;
                    queryParams = queryParams + tmp;
                }
            }
            queryParams = queryParams.substring(1);
        }

        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);

        String response = apiProxy.call(HttpMethod.POST, path + "?" + queryParams, yaml);

        return response;

    }

    @RequestMapping(value = "/api/**", method = RequestMethod.PUT)
    public String forwardPutAPI(@RequestBody KubernetesVO kubernetesVO, @RequestParam Map<String, String> requestParams, HttpServletRequest request) throws Exception {

        String serverUrl = kubernetesVO.getServerUrl();
        String token = kubernetesVO.getToken();
        String yaml = kubernetesVO.getYaml();
        if ((serverUrl != null && !serverUrl.equals("undefined")) && (token != null && !token.equals("undefined")) && (serverUrl.length() != 0 && token.length() != 0)) {
            apiProxy = new APIProxy(serverUrl, token);
        } else {
            throw new Exception();
        }
        String queryParams = "";

        if (requestParams.size() > 0) {
            Iterator<String> iterator = requestParams.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                if (key != "serverUrl" && key != "token") {
                    String value = requestParams.get(key);
                    String tmp = "&" + key + "=" + value;
                    queryParams = queryParams + tmp;
                }
            }
            queryParams = queryParams.substring(1);
        }

        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);

        String response = apiProxy.call(HttpMethod.PUT, path + "?" + queryParams, yaml);

        return response;

    }

    @RequestMapping(value = "/api/**", method = RequestMethod.DELETE)
    public String forwardDeleteAPI(@RequestBody KubernetesVO kubernetesVO, @RequestParam Map<String, String> requestParams, HttpServletRequest request) throws Exception {

        String serverUrl = kubernetesVO.getServerUrl();
        String token = kubernetesVO.getToken();
        if ((serverUrl != null && !serverUrl.equals("undefined")) && (token != null && !token.equals("undefined")) && (serverUrl.length() != 0 && token.length() != 0)) {
            apiProxy = new APIProxy(serverUrl, token);
        } else {
            throw new Exception();
        }
        String queryParams = "";

        if (requestParams.size() > 0) {
            Iterator<String> iterator = requestParams.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                if (key != "serverUrl" && key != "token") {
                    String value = requestParams.get(key);
                    String tmp = "&" + key + "=" + value;
                    queryParams = queryParams + tmp;
                }
            }
            queryParams = queryParams.substring(1);
        }

        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);

        String response = apiProxy.call(HttpMethod.DELETE, path + "?" + queryParams, null);

        return response;

    }

}