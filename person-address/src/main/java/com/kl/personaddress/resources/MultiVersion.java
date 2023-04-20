package com.kl.personaddress.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/multi-version")
public class MultiVersion {

    @GetMapping("/v1/person")
    public String getFirstVersionOfPerson() {
        return "Version 1 => @GetMapping(\"/v1/person\")";
    }

    @GetMapping("/v2/person")
    public String getSecondVersionOfPerson() {
        return "Version 2 => @GetMapping(\"/v2/person\")";
    }

    @GetMapping(path = "/person", params = "version=1")
    public String getFirstVersionOfPersonRequestParameter() {
        return "Version 1 => @GetMapping(path = \"/person\", params = \"version=1\")";
    }

    @GetMapping(path = "/person", params = "version=2")
    public String getSecondVersionOfPersonRequestParameter() {
        return "Version 2 => @GetMapping(path = \"/person\", params = \"version=2\")";
    }

    @GetMapping(path = "/person/header", headers = "X-API-VERSION=1")
    public String getFirstVersionOfPersonRequestHeader() {
        return "Version 1 using header =>  @GetMapping(path = \"/person/header\", headers = \"X-API-VERSION=1\")";
    }

    @GetMapping(path = "/person/header", headers = "X-API-VERSION=2")
    public String getSecondVersionOfPersonRequestHeader() {
        return "Version 2 using header => @GetMapping(path = \"/person/header\", headers = \"X-API-VERSION=2\")";
    }

    @GetMapping(path = "/person/accept", produces = "application/vnd.company.app-v1+json")
    public String getFirstVersionOfPersonAcceptHeader() {
        return "Version 1 => @GetMapping(path = \"/person/accept\", produces = \"application/vnd.company.app-v1+json\")";
    }

    @GetMapping(path = "/person/accept", produces = "application/vnd.company.app-v2+json")
    public String getSecondVersionOfPersonAcceptHeader() {
        return "Version 2 => @GetMapping(path = \"/person/accept\", produces = \"application/vnd.company.app-v2+json\")";
    }
}
