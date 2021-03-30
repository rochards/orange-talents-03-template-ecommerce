package br.com.zupacademy.desafiomercadolivre.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/usuarios").permitAll()
                .antMatchers(HttpMethod.POST, "/auth").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable();
        /** Eu preciso desabilitar o csrf para o POST funcionar, pq ele nao eh Idempotente, ou seja, ele modifica o
         * estado da aplicacao servidora.
         * SPRING DOC -> Our recommendation is to use CSRF protection for any request that could be processed by a
         * browser by normal users. If you are only creating a service that is used by non-browser clients,
         * you will likely want to disable CSRF protection */
    }
}
