package edu.northeastern.cs4500.spoiledTomatillos.account;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AccountController {
	@RequestMapping(value = "/")
	public String index() {
		return "index";
	}
}
