package projeto.integrador3.senac.mediotec.pi3_mediotec;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
	    registry.addMapping("/swagger-ui/**")
	        .allowedOrigins("*")
	        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
	        .allowedHeaders("*")
	        .allowCredentials(true);  // Permite credenciais (tokens JWT)

	    registry.addMapping("/v3/api-docs/**")
	        .allowedOrigins("*")
	        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
	        .allowedHeaders("*")
	        .allowCredentials(true);  // Permite credenciais (tokens JWT)
	    
	    // Permite acessos de outros caminhos, como o front-end (ajustar conforme necessário)
	    registry.addMapping("/**")
	        .allowedOrigins("http://10.0.0.116:3000") 
	        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
	        .allowedHeaders("*")
	        .allowCredentials(true);  // Permite credenciais (tokens JWT)
	}

}



