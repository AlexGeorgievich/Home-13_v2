package cloud.config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "classpath:config/local.properties"
})

public interface ProjectConfig extends Config {

    String user();
    String password();
    String getUrl();
    @DefaultValue("chrome")
    String browser();
    @DefaultValue("1920x1080")
    String size();
    @DefaultValue("91.0")
    String browserVersion();
    @DefaultValue("selenoid.autotests.cloud")
    String remoteUrl();
}
