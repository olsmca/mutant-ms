

package com.olsmca.mutant_ms.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("com.olsmca.mutant_ms.repository.domain")
@EnableTransactionManagement
public class DomainConfig {
}