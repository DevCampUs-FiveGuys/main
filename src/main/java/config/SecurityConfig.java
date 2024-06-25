package config;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jwt.JWTFilter;
import jwt.JWTUtil;
import jwt.LoginFilter;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;
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
        /*
        //csrf disable -> JWT 를 발급 받아 stateless 상태로 사용할거기 때문에 csrf 를 disable 해줘도 된다.
        http
                .csrf((auth) -> auth.disable());

        //Form 로그인 방식 disable -> JWT 방식으로 로그인 할 것이기 때문에 disable 시켜준다.
        http
                .formLogin((auth) -> auth.disable());

        //http basic 인증 방식 disable -> 위와 같은 이유로 disable 시켜준다.
        http
                .httpBasic((auth) -> auth.disable());

        http
                .cors((cors -> cors.configurationSource(new CorsConfigurationSource() {

                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                        CorsConfiguration configuration = new CorsConfiguration();
                        
                        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:8080"));
                        configuration.setAllowedMethods(Collections.singletonList("*"));
                        configuration.setAllowCredentials(true);
                        configuration.setAllowedHeaders(Collections.singletonList("*"));
                        configuration.setMaxAge(3600L);

                        configuration.setExposedHeaders(Collections.singletonList("Authorization"));

                        return configuration;
                    }
                })));
        //경로별 인가 작업

         */
        http
                .authorizeHttpRequests((auth)-> auth
                        // Spring Security 실행시 static(css, js, image 등) 파일도 권한을 주지 않으면 layout 실행시 오류가 발생
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/fonts/**", "/scss/**","/error").permitAll()
                        // 로그인, 기본, 회원가입, 리뷰, 포트폴리오, 질문 게시판은 로그인을 하지 않아도 보여야 하기 때문에 권한을 준다.
                        .requestMatchers("/login", "/","/signup","/review/list","/portfolio/list","/qna/list","/api/user/**").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/student/**").hasAnyRole("STUDENT")
                        .requestMatchers("/teacher/**").hasAnyRole("TEACHER")
                        .requestMatchers("/admin/**").hasAnyRole("ADMIN")
                        .anyRequest().authenticated()
                );
        // csrf (사이트 위조변경 security) -> post 를 보내줄 때 csrf 토큰도 같이 보내줘야 하기 때문에 disable 시켜준다. 토큰을 보내주지 않으면 오류발생 !
        http
                .csrf((auth) -> auth.disable());
        // 권한이 없는 상태에서 권한이 필요한 페이지로 이동하려고 하면 오류가 뜨는것을 방지하기 위해 넣어줌.
        http
                .formLogin((auth) -> auth.loginPage("/login")
                        /* login 주소가 호출되면 security 가 낚아채서 대신 로그인을 진행 */
                        .loginProcessingUrl("/api/user/login")
                        .successHandler(customAuthenticationSuccessHandler()) /* 성공했을때 실행시키는 함수 */
                        .failureHandler(customAuthenticationFailureHandler())); /* 실패했을때 실행시키는 함수 */
        /*
        http
                .addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);
        http
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class);

        //세션 설정 -> JWT 방식에서 세션은 항상 stateless 상태로 관리하기 때문에 설정해줘야 한다. (가장 중요)
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http
                .formLogin((form) -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/api/user/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/"));
         */
        return http.build();
    }
    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                String role = authentication.getAuthorities().stream()
                        .map(grantedAuthority -> grantedAuthority.getAuthority())
                        .findFirst()
                        .orElse("");

                if (role.equals("ROLE_STUDENT")) {
                    response.sendRedirect("/");
                } else if (role.equals("ROLE_TEACHER")) {
                    response.sendRedirect("/");
                } else if (role.equals("ROLE_ADMIN")) {
                    response.sendRedirect("/");
                } else {
                    response.sendRedirect("/default");
                }
            }
        };
    }
    @Bean
    public AuthenticationFailureHandler customAuthenticationFailureHandler() {
        return new SimpleUrlAuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                String errorMessage;
                if (exception instanceof BadCredentialsException) {
                    errorMessage = "아이디 또는 비밀번호가 맞지 않습니다. 다시 확인해주세요.";
                } else {
                    errorMessage = "아이디 또는 비빌번호를 확인하세요.";
                }
                errorMessage = URLEncoder.encode(errorMessage, StandardCharsets.UTF_8); /* 한글 인코딩 깨짐 방지 */
                setDefaultFailureUrl("/api/user/login?error=true&exception=" + errorMessage);
                super.onAuthenticationFailure(request, response, exception);
            }
        };
    }
}
