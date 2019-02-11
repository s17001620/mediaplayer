
package edu.glyndwr.mediaplayer.backend.mediaservice.integration.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 *
 * @author Alexander Bruckbauer s17001620
 */
@Configuration
@PropertySource(value="classpath:api.properties")
@Getter
@Setter
public class MediaPlayerApiConfiguration {
                @Value("${youtube.api.key}")
		private String key;
}
