package com.trivago.casestudy;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PriceController {

  @GetMapping("/prices/{accommodationId}")
  public List<?> getPrices(int accommodationId) {
    // do your work and have fun.
    return null;
  }
}
