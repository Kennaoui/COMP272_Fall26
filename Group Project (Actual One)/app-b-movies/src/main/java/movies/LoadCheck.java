package movies;

import java.nio.file.Path;
import java.util.List;

/**
 * Proof-of-parsing for the proposal: prints how many records were loaded and skipped.
 * Run from the app-b-movies folder, with movies.csv and ratings.csv in data/.
 * Optional argument: the data folder (default "data").
 */
public final class LoadCheck {
    public static void main(String[] args) throws Exception {
        Path dir = Path.of(args.length > 0 ? args[0] : "data");

        List<Movie> movies = DatasetReader.readMovies(dir.resolve("movies.csv"));
        System.out.println("Movies loaded:               " + movies.size());
        System.out.println("Movie lines skipped:         " + DatasetReader.skippedMovieLines);
        System.out.println("Movies with no year (-> 0):  " + DatasetReader.moviesWithoutYear);

        long[] count = {0};
        long total = DatasetReader.replayRatings(dir.resolve("ratings.csv"), Long.MAX_VALUE,
                (movieId, rating) -> count[0]++);
        System.out.println("Ratings read:                " + total);
        System.out.println("Rating lines skipped:        " + DatasetReader.skippedRatingLines);

        if (!movies.isEmpty()) {
            System.out.println("First movie:                 " + movies.get(0));
        }
    }
}