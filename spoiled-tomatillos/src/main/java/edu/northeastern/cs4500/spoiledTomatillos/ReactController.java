package edu.northeastern.cs4500.spoiledTomatillos;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller to forward traffic to react
 */
@Controller
public class ReactController {

  @RequestMapping(value = "/*")
  public String index() {
    return "index";
  }
}
