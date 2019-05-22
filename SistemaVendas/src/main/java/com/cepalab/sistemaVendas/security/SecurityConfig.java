package com.cepalab.sistemaVendas.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public AppUserDetailsService userDetailsService() {
		return new AppUserDetailsService();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		JsfLoginUrlAuthenticationEntryPoint jsfLoginEntry = new JsfLoginUrlAuthenticationEntryPoint();
		jsfLoginEntry.setLoginFormUrl("/Login.xhtml");
		jsfLoginEntry.setRedirectStrategy(new JsfRedirectStrategy());
		
		JsfAccessDeniedHandler jsfDeniedEntry = new JsfAccessDeniedHandler();
		jsfDeniedEntry.setLoginPath("/AcessoNegado.xhtml");
		jsfDeniedEntry.setContextRelative(true);
		
		http
			.csrf().disable()
			.headers().frameOptions().sameOrigin()
			.and()
			
		.authorizeRequests()
			.antMatchers("/Login.xhtml", "/Erro.xhtml", "/javax.faces.resource/**").permitAll()
			.antMatchers("/Home.xhtml", "/AcessoNegado.xhtml", "/cadastro/alteraSenha.xhtml").authenticated()
			.antMatchers("/consultas/resumoVendedor.xhtml", "/cadastro/cliente.xhtml", "/cadastro/pesquisaCliente.xhtml" ).hasAnyRole("VENDEDORES","ADMINISTRADORES", "ROOT")
			.antMatchers("/fechamento/**").hasAnyRole("ADMINISTRADORES", "VENDEDORES","ROOT")
			.antMatchers("/operacao/operacao.xhtml", "/operacao/pesquisaOperacao.xhtml" ).hasAnyRole("EXPEDICAO","ROOT","VENDEDORES","ADMINISTRADORES")
			.antMatchers("/expedicao/pesquisaExpedicao.xhtml").hasAnyRole("ROOT","EXPEDICAO","VENDEDORES","ADMINISTRADORES")
			.antMatchers("/expedicao/**").hasAnyRole("EXPEDICAO","ROOT")
			.antMatchers("/operacao/**").hasAnyRole("ROOT","ADMINISTRADORES","VENDEDORES")	
			.antMatchers("/cadastro/**", "/consultas/**").hasAnyRole("ADMINISTRADORES", "ROOT")
			.antMatchers("/financeiro/**").hasAnyRole("FINANCEIRO", "ROOT")	
			.and()
		
		.formLogin()
			.loginPage("/Login.xhtml")
			.failureUrl("/Login.xhtml?invalid=true")
			.and()
		
		.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.and()
		
		.exceptionHandling()
			.accessDeniedPage("/AcessoNegado.xhtml")
			.authenticationEntryPoint(jsfLoginEntry)
			.accessDeniedHandler(jsfDeniedEntry);
	}
	
}
