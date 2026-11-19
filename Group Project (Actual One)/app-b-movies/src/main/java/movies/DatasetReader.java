package movies;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Reads the MovieLens dataset of Application B (movies.csv and ratings.csv).
 * Dataset: https://grouplens.org/datasets/movielens/ (GroupLens, University of Minnesota).
 *
 * <p>Counters below are filled by the last call of readMovies, so a main method can
 * report how many lines were skipped and how many titles had no year.
 */
public final class DatasetReader {

    /** One line of ratings.csv (only the fields this application needs). */
    public record Rating(int movieId, double rating) { }

    private static final Pattern YEAR = Pattern.compile("\\((\\d{4})\\)\\s*$");

    /** Lines of movies.csv that could not be parsed in the last call of readMovies. */
    public static int skippedMovieLines;
    /** Movies of the last call of readMovies whose title has no year (stored with year 0). */
    public static int moviesWithoutYear;
    /** Lines of ratings.csv that could not be parsed in the last call of readRatings. */
    public static int skippedRatingLines;

    private DatasetReader() { }   // no objects: only static methods

    /** Reads every movie of movies.csv, in file order. */
    public static List<Movie> readMovies(Path moviesCsv) throws IOException {
        skippedMovieLines = 0;
        moviesWithoutYear = 0;
        List<Movie> movies = new ArrayList<>();
        try (BufferedReader in = Files.newBufferedReader(moviesCsv)) {
            String line = in.readLine();                 // header: skip it
            while ((line = in.readLine()) != null) {
                Movie m = parseMovieLine(line);
                if (m == null) {
                    skippedMovieLines++;
                    continue;
                }
                if (m.year() == 0) {
                    moviesWithoutYear++;
                }
                movies.add(m);
            }
        }
        return movies;
    }

    /** Parses "id,title,genres". The ID is before the first comma, the genres after the last. */
    static Movie parseMovieLine(String line) {
        int first = line.indexOf(',');
        int last = line.lastIndexOf(',');
        if (first <= 0 || last <= first) {
            return null;
        }
        try {
            int id = Integer.parseInt(line.substring(0, first).trim());
            String title = line.substring(first + 1, last).trim();
            if (title.length() >= 2 && title.startsWith("\"") && title.endsWith("\"")) {
                title = title.substring(1, title.length() - 1).replace("\"\"", "\"");
            }
            String genreText = line.substring(last + 1).trim();

            int year = 0;
            Matcher m = YEAR.matcher(title);
            if (m.find()) {
                year = Integer.parseInt(m.group(1));
                title = title.substring(0, m.start()).trim();
            }

            List<String> genres = new ArrayList<>();
            if (!genreText.isEmpty() && !genreText.equals("(no genres listed)")) {
                for (String g : genreText.split("\\|")) {
                    genres.add(g);
                }
            }
            return new Movie(id, title, year, genres);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Reads at most maxLines ratings of ratings.csv, in file order.
     * To replay the whole 32M-line file without storing it, use {@link #replayRatings}.
     */
    public static List<Rating> readRatings(Path ratingsCsv, int maxLines) throws IOException {
        List<Rating> ratings = new ArrayList<>();
        replayRatings(ratingsCsv, maxLines, (movieId, rating) -> ratings.add(new Rating(movieId, rating)));
        return ratings;
    }

    /** Receives one rating at a time. */
    @FunctionalInterface
    public interface RatingConsumer {
        void accept(int movieId, double rating);
    }

    /** Streams up to maxLines ratings to the consumer, without keeping them in memory. Returns the count read. */
    public static long replayRatings(Path ratingsCsv, long maxLines, RatingConsumer consumer) throws IOException {
        skippedRatingLines = 0;
        long count = 0;
        try (BufferedReader in = Files.newBufferedReader(ratingsCsv)) {
            String line = in.readLine();                 // header: skip it
            while (count < maxLines && (line = in.readLine()) != null) {
                // userId,movieId,rating,timestamp
                int c1 = line.indexOf(',');
                int c2 = c1 < 0 ? -1 : line.indexOf(',', c1 + 1);
                int c3 = c2 < 0 ? -1 : line.indexOf(',', c2 + 1);
                if (c3 < 0) {
                    skippedRatingLines++;
                    continue;
                }
                try {
                    int movieId = Integer.parseInt(line.substring(c1 + 1, c2));
                    double rating = Double.parseDouble(line.substring(c2 + 1, c3));
                    consumer.accept(movieId, rating);
                    count++;
                } catch (NumberFormatException e) {
                    skippedRatingLines++;
                }
            }
        }
        return count;
    }
}