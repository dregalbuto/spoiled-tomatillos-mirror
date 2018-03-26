package edu.northeastern.cs4500.spoiledtomatillos;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller to forward traffic to react.
 */
@Controller
public class ReactController {

  /**
   * Forward all unfound resource to index for react to handle.
   * @return always "index" loading react page.
   */
  @RequestMapping("/*")
  public String index() {
    return "index";
  }

  /**
   * Forward movie pages to index as well.
   * @return always "index" loading react page.
   */
  @RequestMapping(value = "/Movie/*")
  public String movie() {
    return "/index";
  }
}
