package configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@Slf4j
public class TestContext {
    private final Map<String, Object> allConfigs = new HashMap<>();

    public void loadConfigurations(String directoryPath) {
        try (Stream<Path> paths = Files.walk(Paths.get(directoryPath))) {
            paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".yaml"))
                    .forEach(this::loadConfigFile);
        } catch (IOException e) {
            log.error("Error loading configurations", e);
        }
    }

    private void loadConfigFile(Path filePath) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            Map<String, Object> properties = mapper.readValue(new File(filePath.toString()), Map.class);
            allConfigs.putAll(properties);
            log.debug("Loaded configuration from: " + filePath);
        } catch (IOException e) {
            log.error("Error loading file: " + filePath, e);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getProperty(String keyPath) {
        String[] keys = keyPath.split("\\.");
        Map<String, Object> currentMap = allConfigs;
        Object currentValue = null;

        for (String key : keys) {
            currentValue = currentMap.get(key);
            if (currentValue instanceof Map<?, ?>) {
                currentMap = (Map<String, Object>) currentValue;
            } else {
                break;
            }
        }

        return (T) currentValue;
    }
}