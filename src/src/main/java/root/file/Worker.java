package root.file;

import com.sun.tools.javac.util.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class Worker {
    private final String path;

    public Worker(String path) {
        this.path = path;
    }

    public Optional<String> executeWorkload(Function<Integer, Boolean> f) throws IOException {
        return Files.walk(Paths.get(this.path)).parallel().flatMap(path -> {
            try {
                return Files.readAllLines(path).stream().map(l -> Pair.of(path.getFileName().toString(), Integer.parseInt(l)));
            } catch (IOException e) {
                e.printStackTrace();
            }

            return Stream.empty();
        }).filter(p -> f.apply(p.snd)).map(p -> p.fst).findAny();
    }
}
