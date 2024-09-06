package gc.cafe.docs;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.restdocs.snippet.Attributes;

import static org.springframework.restdocs.snippet.Attributes.*;

@TestConfiguration
public class RestDocsConfig {

    public static final Attribute field(
        final String key,
        final String value){
        return new Attribute(key,value);
    }
}
