package br.com.gestaocomercial.app.src.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErroController {

    @GetMapping("/acesso-negado")
    public String acessoNegado() {
        return "erro/403"; // arquivo 403.html
    }
}
