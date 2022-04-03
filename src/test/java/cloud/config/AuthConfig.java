package cloud.config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:config/auth.properties"
})

public interface AuthConfig extends Config {

    @Key("username")
    String username();

    @Key("password")
    String password();

}
