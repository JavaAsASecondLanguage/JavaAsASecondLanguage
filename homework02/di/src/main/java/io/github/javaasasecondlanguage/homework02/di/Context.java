package io.github.javaasasecondlanguage.homework02.di;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Context {
    private static class QualifierProcPair {
        public Supplier<? extends Object> proc;
        public String qualifier;

        public QualifierProcPair(Supplier<? extends Object> proc, String qualifier) {
            this.proc = proc;
            this.qualifier = qualifier;
        }
    }

    public class UnresolvableDepsError extends RuntimeException {
        public UnresolvableDepsError(String dependency) {
            super("Can't resolve dependency: " + dependency);
        }
    }

    private static HashMap<String, Object> objects = new HashMap<>();
    private static HashMap<Class, Object> classes = new HashMap<>();
    private static ArrayList<QualifierProcPair> procs = new ArrayList<>();

    public <T> Context register(Supplier<T> proc, String qualifier) {
        procs.add(new QualifierProcPair(proc, qualifier));
        return this;
    }

    public <T> Context register(Supplier<T> proc) {
        procs.add(new QualifierProcPair(proc, null));
        return this;
    }

    public void resolve() {
        var prev_size = procs.size();

        while (prev_size > 0) {
            resolutionStep();
            if (procs.size() == prev_size) {
                var names = procs.stream()
                        .map((p) -> p.qualifier).filter((s) -> s != null)
                        .collect(Collectors.toList());
                throw new UnresolvableDepsError(String.join(", ", names));
            }

            prev_size = procs.size();
        }
    }

    private void resolutionStep() {
        for (var i = 0; i < procs.size(); i++) {
            try {
                while (procs.size() > 0) {
                    var pair = procs.get(0);
                    var object = pair.proc.get();

                    if (pair.qualifier != null) {
                        objects.put(pair.qualifier, object);
                    } else {
                        var clazz = object.getClass();

                        while (!clazz.equals(Object.class)) {
                            addInterface(clazz, object);
                            clazz = clazz.getSuperclass();
                        }
                    }

                    procs.remove(0);
                }
            } catch (Exception e) {
                // Move failed element to end
                procs.add(procs.remove(0));
            }
        }
    }

    private <T> void addInterface(Class<T> interfaze, Object value) {
        classes.put(interfaze, value);

        for (var ci : interfaze.getInterfaces()) {
            addInterface(ci, value);
        }
    }

    public Object findByQualifier(String qualifier) throws UnresolvableDepsError {
        return checkResolution(objects.get(qualifier), qualifier);
    }

    public Object findByClass(Class clazz) throws UnresolvableDepsError {
        return checkResolution(classes.get(clazz), clazz.getName());
    }

    private <T> T checkResolution(T object, String name) throws UnresolvableDepsError {
        if (object != null) {
            return object;
        } else {
            throw new UnresolvableDepsError(name);
        }
    }

    public void clear() {
        classes.clear();
        objects.clear();
    }
}
