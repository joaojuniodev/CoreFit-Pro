package br.com.joaojuniodev.corefitpro.config;

import com.backblaze.b2.client.B2StorageClient;
import com.backblaze.b2.client.B2StorageClientFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@EnableConfigurationProperties(B2Properties.class)
public class B2Config {

    @Bean(destroyMethod = "close")
    public B2StorageClient b2StorageClient(B2Properties props) {
        return B2StorageClientFactory
            .createDefaultFactory()
            .create(props.getKeyId(), props.getApplicationKey(), props.getUserAgent());
    }

    @Bean(name = "b2UploadExecutor", destroyMethod = "shutdown")
    ExecutorService b2UploadExecutor() {
        return Executors.newFixedThreadPool(4);
    }
}
