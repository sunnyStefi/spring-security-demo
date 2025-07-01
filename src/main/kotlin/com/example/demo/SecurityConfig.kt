    package com.example.demo

    import org.springframework.context.ApplicationListener
    import org.springframework.context.annotation.Bean
    import org.springframework.context.annotation.Configuration
    import org.springframework.security.authentication.AuthenticationManager
    import org.springframework.security.authentication.ProviderManager
    import org.springframework.security.authentication.dao.DaoAuthenticationProvider
    import org.springframework.security.authentication.event.AuthenticationSuccessEvent
    import org.springframework.security.config.Customizer
    import org.springframework.security.config.annotation.web.builders.HttpSecurity
    import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
    import org.springframework.security.core.userdetails.UserDetailsService
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
    import org.springframework.security.crypto.password.DelegatingPasswordEncoder
    import org.springframework.security.crypto.password.PasswordEncoder
    import org.springframework.security.provisioning.InMemoryUserDetailsManager
    import org.springframework.security.web.SecurityFilterChain
    import org.springframework.security.web.access.intercept.AuthorizationFilter
    import org.springframework.security.web.authentication.AuthenticationFilter
    import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

    @Configuration
    @EnableWebSecurity
    class SecurityConfig {

        @Bean
        fun securityFilterChain(http: HttpSecurity,
                                hunterAuthenticationProvider: HunterAuthenticationProvider,
                                safeZoneFilter: SafeZoneFilter): SecurityFilterChain {
            http
                .authorizeHttpRequests {
                    c -> c.requestMatchers("/private").authenticated()
                    .anyRequest().permitAll()
                }
                //.httpBasic(Customizer.withDefaults())
                .authenticationProvider(hunterAuthenticationProvider)
                .addFilterAfter(safeZoneFilter, AuthenticationFilter::class.java)
                .oauth2Login(Customizer.withDefaults())

            return http.build()
        }

        @Bean
        fun authenticationSuccessListener(): ApplicationListener<AuthenticationSuccessEvent> =
            ApplicationListener { event -> println("Successfully authenticated with ${event.authentication.javaClass.simpleName} 🎉") }

    }