package gradproject.demo.config;

import gradproject.demo.security.JwtFilter;
import gradproject.demo.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//WebSecurityConfig olarak geciyor
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private CustomUserDetailsService userDetailsService;


    // configure metodu (AuthenticationManagerBuilder parametresini alan) override edilir.
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.userDetailsService(userDetailsService); Parametre olarak oluşturmuş olduğumuz CustomUserDetailsService instance ı veririz.
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    // PasswordEncoder bean i ekleyerek şifre için herhangi bir hash işleminin yapmaması sağlanır.
    // Sistemi ayağa kaldırıp tanımlamış olduğumuz kullanıcılardan biri ile sisteme giriş yapabilirsiniz.
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    //AuthenticationManager hatası icin inject edildi
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    //  Her istekte gönderdiğimizde Spring Security bizden authentication bilgisi isteyecektir.
    //  Fakat bizim login olabilmek için /login url ini authentication kontrolü dışına çıkarmalıyız.
    //  Bu işlemi de SpringSecurityConfig dosyasına asagıdaki metodu override ederek sağlarız.

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests().antMatchers("/login", "/welcome","/swagger-ui/**", "/openapi/**", "/v3/**", "/api-docs/**","/swagger-ui.html", "/credit/ui/**", "/styles/cssandjs/**", "/images/**", "/user/ui/**", "/credit-admin/**").permitAll()
                .anyRequest().authenticated()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

// " .sessionCreationPolicy(SessionCreationPolicy.STATELESS); "
// Spring Security in session yönetimini devre dışı bırakması için yukarıdaki satır eklenir.
}