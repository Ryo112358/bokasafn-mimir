package dev.koicreek.bokasafn.mimirs.users.cfg;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.koicreek.bokasafn.mimirs.users.UserEntity;
import dev.koicreek.bokasafn.mimirs.users.contracts.LoginCM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;


public class CustomAuthFilter extends UsernamePasswordAuthenticationFilter {

    private final Environment env;

    @Autowired
    public CustomAuthFilter(Environment environment,
                            AuthenticationManager authenticationManager
                            ) {
        this.env = environment;
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest req, HttpServletResponse res) throws AuthenticationException
    {
        try {
            LoginCM loginRequest = new ObjectMapper()
                    .readValue(req.getInputStream(), LoginCM.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword(),
                            new ArrayList<>()
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    protected void successfulAuthentication(
            HttpServletRequest req,
            HttpServletResponse res,
            FilterChain chain,
            Authentication auth) throws IOException, ServletException
    {
        UserEntity principal = (UserEntity) auth.getPrincipal();

        final long NOW = System.currentTimeMillis();
        final long FIVE_MINUTES = 1000 * 60 * 5;

        final String globalSecret = env.getProperty("token.secret");
        final String secret = globalSecret != null ? globalSecret : "Walk slowly through the doors of joy.";

        Algorithm algoHS = Algorithm.HMAC256(secret);

        String token = JWT.create()
                .withSubject(principal.getPublicId())
                .withIssuedAt(new Date(NOW))
                .withNotBefore(new Date(NOW))
                .withExpiresAt(new Date(NOW + FIVE_MINUTES))
                .sign(algoHS);

        res.addHeader("authToken", token);
        res.addHeader("userId", principal.getPublicId());
    }
}
