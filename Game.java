import java.util.List;

public class Game {
    private String title;
    private List<String> genre;
    private String developer;
    private String publisher;
    private List<String> platforms;
    private List<String> translators;
    private String steamid;
    private int releaseYear;
    private int playtime;
    private String format;
    private String language;
    private String rating;
    private List<String> tags;
    private String coverImage;

    public Game(String title, List<String> genre, String developer, String publisher, List<String> platforms,
                List<String> translators, String steamid, int releaseYear, int playtime, String format,
                String language, String rating, List<String> tags, String coverImage) {
        this.title = title;
        this.genre = genre;
        this.developer = developer;
        this.publisher = publisher;
        this.platforms = platforms;
        this.translators = translators;
        this.steamid = steamid;
        this.releaseYear = releaseYear;
        this.playtime = playtime;
        this.format = format;
        this.language = language;
        this.rating = rating;
        this.tags = tags;
        this.coverImage = coverImage;
    }


}