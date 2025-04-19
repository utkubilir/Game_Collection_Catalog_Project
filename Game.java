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
        
        
        this.title = (title != null && !title.isEmpty()) ? title : "Unknown Title";
        this.genre = (genre != null && !genre.isEmpty()) ? genre : List.of("Unknown Genre");
        this.developer = (developer != null && !developer.isEmpty()) ? developer : "Unknown Developer";
        this.publisher = (publisher != null && !publisher.isEmpty()) ? publisher : "Unknown Publisher";
        this.platforms = (platforms != null && !platforms.isEmpty()) ? platforms : List.of("Unknown Platform");
        this.translators = (translators != null && !translators.isEmpty()) ? translators : List.of("Unknown Translator");
        this.steamid = (steamid != null && !steamid.isEmpty()) ? steamid : "Unknown SteamID";
        this.releaseYear = (releaseYear > 0) ? releaseYear : 0; 
        this.playtime = (playtime > 0) ? playtime : 0;  
        this.format = (format != null && !format.isEmpty()) ? format : "Unknown Format";
        this.language = (language != null && !language.isEmpty()) ? language : "Unknown Language";
        this.rating = (rating != null && !rating.isEmpty()) ? rating : "Unknown Rating";
        this.tags = (tags != null && !tags.isEmpty()) ? tags : List.of("Unknown Tag");
        this.coverImage = (coverImage != null && !coverImage.isEmpty()) ? coverImage : "default.jpg";  
    }

    
    public String getTitle() {
        return title;
    }

    
}


}