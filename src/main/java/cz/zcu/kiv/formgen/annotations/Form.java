package cz.zcu.kiv.formgen.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 *
 * @author Jakub Krauz
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Form {
    String value();
}
