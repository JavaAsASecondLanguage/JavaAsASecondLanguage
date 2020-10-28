package io.github.javaasasecondlanguage.processor;

import com.google.auto.service.AutoService;
import io.github.javaasasecondlanguage.annotation.Immutable;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic;
import java.util.Set;

@AutoService(Processor.class)
public class ImmutableAnnotationProcessor extends AbstractProcessor {
    private Messager messager;

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of("io.github.javaasasecondlanguage.annotation.Immutable");
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_14;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
    }

    @Override
    public boolean process(
            Set<? extends TypeElement> annotations,
            RoundEnvironment roundEnv) {

        var immEl = roundEnv.getElementsAnnotatedWith(Immutable.class);

        for (Element element : immEl) {
//            messager.printMessage(Diagnostic.Kind.NOTE, element.getSimpleName());
            if (element instanceof TypeElement) {
                var type = (TypeElement) element;
//                messager.printMessage(Diagnostic.Kind.NOTE, type.getSimpleName());
                var enclosedEl = type.getEnclosedElements();
//                enclosedEl.forEach(it -> {
//                            messager.printMessage(Diagnostic.Kind.NOTE, it.getClass().getName());
//                        });
                for (Element el : enclosedEl) {
                    if (el instanceof VariableElement) {
                        var variable = (VariableElement) el;
                        messager.printMessage(Diagnostic.Kind.NOTE, variable.getSimpleName());
                        var mod = variable.getModifiers();
                        if (!mod.contains(Modifier.FINAL)) {
                            messager.printMessage(Diagnostic.Kind.ERROR,
                                    ((VariableElement) el).getSimpleName() + " is broken");
                        }
                    }
                }
            }
        }

        return false;
    }
}
