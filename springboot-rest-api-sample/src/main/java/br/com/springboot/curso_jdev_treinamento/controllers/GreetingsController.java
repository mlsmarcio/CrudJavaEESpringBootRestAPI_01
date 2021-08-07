package br.com.springboot.curso_jdev_treinamento.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.springboot.curso_jdev_treinamento.model.Usuario;
import br.com.springboot.curso_jdev_treinamento.repository.UsuarioRepository;

/**
 *
 * A sample greetings controller to return greeting text
 */
@RestController
public class GreetingsController {
	
	@Autowired /* IC/CD ou CDI - Injeção de dependência */
	private UsuarioRepository usuarioRepository;
	
    /**
     *
     * @param name the name to greet
     * @return greeting text
     */
    //@RequestMapping(value = "/{name}", method = RequestMethod.GET)
    @RequestMapping(value = "/mostrarnome/{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String greetingText(@PathVariable String name) {
        return "Curso Spring Boot API: " + name + "!";
    }
    
    // MAPEAMENTO DE REQUISIÇÃO
    @RequestMapping(value = "/salvausuario/{nome}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)   // RESPOSTA PADRÃO PARA A REQUISIÇÃO
    public String exibeMensagem(@PathVariable String nome) {
    	Usuario usuario = new Usuario();
    	usuario.setNome(nome);
    	
    	usuarioRepository.save(usuario);
    	
    	return "Salvou " + nome;
    }
    
    /* No postman - GET, Params */
    @GetMapping(value = "listatodos")
    @ResponseBody /* retorna os dados no corpo da resposta*/  
    public ResponseEntity<List<Usuario>> listaUsuario(){
    	List<Usuario> usuarios = usuarioRepository.findAll(); /* spring data executa a consulta no bd*/
    	return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK); /* retorna no formato json*/
    }
    
    /* No postman - POST, BODY, raw, jason */
    @PostMapping(value = "salvar") /* Intercepta o método POST, mapeamento da url*/
    @ResponseBody  /* retorna - descrição da resposta*/
    public ResponseEntity<Usuario> salvar (@RequestBody Usuario usuario){ /* Recebe os dados (json) para salvar */
    	Usuario user = usuarioRepository.save(usuario);
    	return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);
    }

   
    
    /* No postman - DELETE, BODY, x-www-form-urlencoded */
    @DeleteMapping(value = "delete") /* mapeamento da url*/
    @ResponseBody  /* retorna - descrição da resposta*/
    public ResponseEntity<String> delete (@RequestParam Long idUser){ /* Recebe os dados para deletar */
    	usuarioRepository.deleteById(idUser);
    	return new ResponseEntity<String>("Usuário deletado com sucesso!", HttpStatus.OK);
    }
    
    /* No postman - GET, BODY, form-data */
    @GetMapping(value = "buscaruserid") /* mapeamento da url*/
    @ResponseBody  /* retorna - descrição da resposta*/
    public ResponseEntity<Usuario> buscaruserid (@RequestParam(name = "idUser") Long idUser){ /* Recebe os dados para consultar*/
    	 Usuario usuario = usuarioRepository.findById(idUser).get();
    	return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
    }

}
