package kwiz.backend.api.configs;

// import org.springframework.context.annotation.Bean;
// import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
// import org.springframework.security.crypto.password.NoOpPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;

// @EnableWebSecurity
// public class SecurityConfigs extends WebSecurityConfigurerAdapter {

//     // @Override
//     // protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//     //     auth.inMemoryAuthentication()
//     //                         .withUser("User1")
//     //                         .password("user")
//     //                         .roles("USER");
//     // }

//     // @Bean
//     // public PasswordEncoder getPasswordEncoder() {
//     //     return NoOpPasswordEncoder.getInstance();
//     // }

//     @Override
//     protected void configure(HttpSecurity http) throws Exception {
        
//         // Configure the http object to set authorization to different urls
//         http.csrf().disable()
//                     .authorizeRequests()
//                     .antMatchers("/quiz/v2/admin").hasRole("ADMIN")
//                     .antMatchers("/quiz/v2/api/**").permitAll()
//                     .and().formLogin();
//     }

    
    
// }
