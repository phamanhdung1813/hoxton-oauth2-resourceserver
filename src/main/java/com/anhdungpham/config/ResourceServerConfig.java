package com.anhdungpham.config;

import net.minidev.json.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.Filter;
import java.security.KeyFactory;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.stream.Collectors;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

    @Value("${publicKey}")
    private String publicKey;

    @Value("${secretKey}")
    private String secretKey;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.oauth2ResourceServer(request -> request.jwt(
                        jwt -> jwt.decoder(jwtDecoder())
                                .jwtAuthenticationConverter(jwtAuthenticationConverter())))

                .authorizeRequests().anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().httpBasic();
    }

//    @Bean
//    // parse token for by using secret Key
//    public JwtDecoder jwtDecoder() {
//        byte[] key = secretKey.getBytes();
//        SecretKey secretKeySpec = new SecretKeySpec(key, 0, key.length, "AES");
//        return NimbusJwtDecoder.withSecretKey(secretKeySpec).build();
//    }

    @Bean
    // parse token by using jks certification
    public JwtDecoder jwtDecoder() {
        try {
            byte[] decodedKey = Base64.getDecoder().decode(publicKey
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
            );

            X509EncodedKeySpec x509 = new X509EncodedKeySpec(decodedKey);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            var rsaKey = (RSAPublicKey) keyFactory.generatePublic(x509);

            return NimbusJwtDecoder.withPublicKey(rsaKey)
                    .signatureAlgorithm(SignatureAlgorithm.RS256)
                    .build();

        } catch (Exception e) {
            throw new RuntimeException(String.format("WRONG PUBLIC KEY %s", publicKey));
        }
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(
                jwtToken -> {
                    JSONArray jsonArray = (JSONArray) jwtToken.getClaims().get("authorities");
//                    String username = (String) jwtToken.getClaims().get("user_name");
//                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
//                    jsonArray.stream().map(String::valueOf).forEach(
//                            j -> authorities.add(new SimpleGrantedAuthority(j))
//                    );
//
//                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
//                            new UsernamePasswordAuthenticationToken(
//                                    username, null, authorities);
//
//                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                    return jsonArray.stream().map(
                            String::valueOf
                    ).map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
                }
        );
        return jwtAuthenticationConverter;
    }

}
