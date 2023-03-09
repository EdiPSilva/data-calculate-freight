package br.com.java.datacalculatefreight.company;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
@Slf4j
public class CompanyRestCotroller {

    @GetMapping
    public String get() {
        return "Ok";
    }
}
