package xyz.silencelurker.project.deploy;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Silence_Lurker
 */
@EnableJpaRepositories
@SpringBootApplication
public class DeployApplication {

	public static void main(String[] args) {
		System.setProperty("xyz.silencelurker.response.savedir",
				"/media/silencelurker/PublicData/project/save/response");

		File dir = new File(System.getProperty("xyz.silencelurker.response.savedir"));

		if (!dir.exists()) {
			dir.mkdirs();
		}

		SpringApplication.run(DeployApplication.class, args);
	}

}
