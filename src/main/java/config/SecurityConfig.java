package config;

import jwt.LoginFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationConfiguration authenticationConfiguration;
    //AuthenticationManager Bean 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }
    //BCryptPasswordEncoder : Spring Security 에서 제공하는 암호화 알고리즘
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        //csrf disable -> JWT 를 발급 받아 stateless 상태로 사용할거기 때문에 csrf 를 disable 해줘도 된다.
        http
                .csrf((auth) -> auth.disable());

        //Form 로그인 방식 disable -> JWT 방식으로 로그인 할 것이기 때문에 disable 시켜준다.
        http
                .formLogin((auth) -> auth.disable());

        //http basic 인증 방식 disable -> 위와 같은 이유로 disable 시켜준다.
        http
                .httpBasic((auth) -> auth.disable());

        //경로별 인가 작업
        http
                .authorizeHttpRequests((auth)-> auth
                        .requestMatchers("/login", "/","join").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .anyRequest().permitAll());

        http
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration)), UsernamePasswordAuthenticationFilter.class);

        //세션 설정 -> JWT 방식에서 세션은 항상 stateless 상태로 관리하기 때문에 설정해줘야 한다. (가장 중요)
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
