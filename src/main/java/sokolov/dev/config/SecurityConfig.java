package sokolov.dev.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import sokolov.dev.entity.User;
import sokolov.dev.entity.UserRole;
import sokolov.dev.repository.UserRepository;


import java.util.Collections;
import java.util.Set;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserRepository userRepository;

    @Autowired
    public SecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)

                .securityContext(sc -> sc.securityContextRepository(securityContextRepository()))

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/favicon.ico").permitAll()
                        .requestMatchers("/", "/registration", "/error").permitAll()
                        .requestMatchers("/account/**").hasAnyRole(UserRole.USER.name(), UserRole.ADMIN.name(), UserRole.SUPER_ADMIN.name())
                        .requestMatchers("/admin/**").hasAnyRole(UserRole.ADMIN.name(), UserRole.SUPER_ADMIN.name())
                        .requestMatchers("/super-admin/**").hasRole(UserRole.SUPER_ADMIN.name())
                        .requestMatchers("/error/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                        .usernameParameter("email")
                        .defaultSuccessUrl("/account")
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll()
                )
                .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                User user = userRepository
                        .findByEmailIgnoreCase(username)
                        .orElseThrow(()-> new UsernameNotFoundException("user with emal = " + username + " not found"));
                Set<SimpleGrantedAuthority> roles = Collections.singleton(user.getRole().toAuthority());
                return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), roles);
            }
        };
    }
}
