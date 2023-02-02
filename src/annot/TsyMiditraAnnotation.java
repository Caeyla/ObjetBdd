/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/AnnotationType.java to edit this template
 */
package annot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author caeyla
 */
@Target({ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface TsyMiditraAnnotation {
    boolean tsymiditra() default true;
}
