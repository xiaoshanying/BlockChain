package com.wmz.blockchain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author wmz
 */
@SpringBootApplication
@ServletComponentScan
public class BlockchainApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlockchainApplication.class, args);
    }

}
