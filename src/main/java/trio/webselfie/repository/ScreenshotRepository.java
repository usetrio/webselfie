package trio.webselfie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import trio.webselfie.model.Screenshot;

public interface ScreenshotRepository extends JpaRepository<Screenshot, Long> {
}
