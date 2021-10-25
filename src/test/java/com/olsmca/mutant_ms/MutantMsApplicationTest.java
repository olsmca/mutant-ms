package com.olsmca.mutant_ms;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MutantMsApplicationTest {

    @Test
    void applicationContextLoaded() {
    }

    @Test
    public void applicationContextTest() {
        MutantMsApplication.main(new String[] {});
    }
}
