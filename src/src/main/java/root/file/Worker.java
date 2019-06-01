package root.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.function.Function;

public class Worker {
    private final String path;

    Worker(String path) {
        this.path = path;
    }

    Optional<Integer> executeWorkload(Function<Integer, Boolean> f) throws IOException {
        return Files.lines(Paths.get(this.path)).parallel().map(Integer::parseInt).filter(f::apply).findAny();
    }
}
