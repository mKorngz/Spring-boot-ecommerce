package org.example.e_commerce_api.Config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Slf4j
public class AuditorAwareImpl implements AuditorAware<Long> {
    @Override
    public Optional<Long> getCurrentAuditor() {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null &&
                auth.getPrincipal() instanceof CustomUserDetails userDetails) {

            return Optional.of(userDetails.getId());
        }

        return Optional.of(1L);
    }
}
