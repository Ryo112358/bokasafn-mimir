package dev.koicreek.bokasafn.mimirs.gateway;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    @Autowired
    private Environment env;

    private final static String BEARER = "Bearer ";

    public static class Config {}

    public AuthorizationHeaderFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            ServerHttpRequest request = exchange.getRequest();
            if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "Missing auth header.", HttpStatus.UNAUTHORIZED);
            }

            String jwt = null;
//            final String BEARER = "Bearer ";
            final String authorization = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

            if(authorization.startsWith(BEARER)) {
                jwt = authorization.substring(BEARER.length());
            }

            if(!isJwtValid(jwt)) {
                return onError(exchange, "Invalid auth token.", HttpStatus.UNAUTHORIZED);
            }

            return chain.filter(exchange);
        };
    }

    private static Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private boolean isJwtValid(String token) {
        if(token == null) return false;

        final String globalSecret = env.getProperty("token.secret");
        final String secret = globalSecret != null ? globalSecret : "Walk slowly through the doors of joy.";

        String subject;

        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm).build();

        try {
            DecodedJWT jwt = verifier.verify(token);
            subject = jwt.getSubject();
        } catch(Exception ex) {
            return false;
        }

        if(subject != null && !subject.isBlank()) {
            return true;
        }

        return false;
    }
}
