/*
 * Softstudio LTDA
 * Copyrigth .2015.
 */
package co.gov.policia.dinae.siedu.sdo;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.inject.Qualifier;

/**
 *
 * Qualifier used to help determine the intended injection object. This particular qualifier will be used to specify types which should be used for the default SDO.
 *
 * @author: Diego Poveda.
 * @name:
 * @descripcion:
 * @version: 1.0
 * @since: JDK_1.7
 * @see SDO
 */
@Qualifier
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ArchivoSDOQualifier {
}
