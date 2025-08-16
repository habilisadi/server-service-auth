package com.habilisadi.auth.config


import com.github.f4b6a3.ulid.UlidCreator
import com.habilisadi.auth.entity.RegisteredClientEntity
import com.habilisadi.auth.repository.CustomRegisteredClientRepository
import com.habilisadi.auth.repository.JpaRegisteredClientRepository
import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.SecurityContext
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.core.annotation.Order
import org.springframework.http.MediaType
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod
import org.springframework.security.oauth2.core.oidc.OidcScopes
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey


@Configuration
class SecurityConfig(
    private val jpaRegisteredClientRepository: JpaRegisteredClientRepository,
    @Value("\${registered.client.id}") private val CLIENT_ID: String,
    @Value("\${registered.client.secret}") private val CLIENT_SECRET: String,
    @Value("\${registered.client.redirect-uri}") private val REDIRECT_URI: String
) {

    @Bean
    @Order(1)
    fun authorizationServerSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.securityMatcher("/oauth2/**", "/.well-known/**")
            .with(OAuth2AuthorizationServerConfigurer(), withDefaults())
            .authorizeHttpRequests { authorize ->
                authorize
                    .anyRequest().authenticated()
            }
            .csrf { csrf -> csrf.disable() }
            .exceptionHandling { exceptions ->
                exceptions.defaultAuthenticationEntryPointFor(
                    LoginUrlAuthenticationEntryPoint("/login"),
                    MediaTypeRequestMatcher(MediaType.TEXT_HTML)
                )
            }
            .oauth2ResourceServer { resourceServer ->
                resourceServer.jwt(withDefaults())
            }

        return http.formLogin(withDefaults()).build()
    }

    @Bean
    @Order(2)
    fun defaultSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.authorizeHttpRequests { authorize ->
            authorize.requestMatchers("/register", "/css/**", "/js/**", "/images/**", "/login")
                .permitAll()
            authorize.anyRequest().authenticated()
        }
            .formLogin { formLogin ->
                formLogin
                    .loginPage("/login")
                    .defaultSuccessUrl("/", true)
                    .permitAll()
            }
            .logout { logout ->
                logout
                    .logoutSuccessUrl("/login?logout")
                    .permitAll()
            }
        return http.build()
    }


    @Bean
    @Primary
    fun registeredClientRepository(passwordEncoder: PasswordEncoder): RegisteredClientRepository {

        if (jpaRegisteredClientRepository.count() != 0L) {
            return CustomRegisteredClientRepository(jpaRegisteredClientRepository)
        }

        val registeredClient = RegisteredClient.withId(UlidCreator.getUlid().toString())
            .clientId(CLIENT_ID)
            .clientSecret(passwordEncoder.encode(CLIENT_SECRET))
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
            .redirectUri("$REDIRECT_URI/login/oauth2/code/$CLIENT_ID")
            .scopes {
                it.addAll(
                    listOf(
                        OidcScopes.OPENID,
                        "roles"
                    )
                )
            }
            .build()

        jpaRegisteredClientRepository.save(RegisteredClientEntity.from(registeredClient))

        return CustomRegisteredClientRepository(jpaRegisteredClientRepository)
    }

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration) =
        authenticationConfiguration.authenticationManager

    @Bean
    fun authorizationServerSettings() =
        AuthorizationServerSettings.builder().build()

    @Bean
    fun corsConfigurationSource(): UrlBasedCorsConfigurationSource {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()

        config.allowedMethods = listOf(
            "GET",
            "POST",
        )
        config.allowedHeaders = listOf(
            "Authorization",
            "Content-Type",
        )
        config.allowCredentials = true
        config.maxAge = 3600L
        source.registerCorsConfiguration("/**", config)
        return source
    }

    @Bean
    fun jwtDecoder(jwkSource: JWKSource<SecurityContext>) =
        OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource)

    @Bean
    fun jwkSource(): JWKSource<SecurityContext> {
        val keyPair = generateRsaKey()
        val publicKey = keyPair.public as RSAPublicKey
        val privateKey = keyPair.private as RSAPrivateKey
        val rsaKey = RSAKey.Builder(publicKey)
            .privateKey(privateKey)
            .keyID(UlidCreator.getUlid().toString())
            .build()

        val jwkSet = JWKSet(rsaKey)
        return ImmutableJWKSet(jwkSet)
    }

    companion object {
        private fun generateRsaKey(): KeyPair {
            return try {
                val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
                keyPairGenerator.initialize(2048)
                keyPairGenerator.generateKeyPair()
            } catch (ex: Exception) {
                throw IllegalStateException(ex)
            }
        }
    }
}