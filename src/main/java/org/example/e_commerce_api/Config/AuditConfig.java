package org.example.e_commerce_api.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@RequiredArgsConstructor
public class AuditConfig {
    @Bean
    public AuditorAware<Long> auditorProvider(){
        return new AuditorAwareImpl();
    }

}
