import java.util.List;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONObject;

    //Created a class named game to carry all of the informations of the games as described in the project description
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

    // we had given default names for the null or empty values because of the requirement
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

    public List<String> getTags() {
        return tags;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public int getPlaytime() {
        return playtime;
    }

    public String getDeveloper() {
        return developer;
    }

    public List<String> getGenre() {
        return genre;
    }

    public String getPublisher() {
        return publisher;
    }

    public List<String> getPlatforms() {
        return platforms;
    }

    public List<String> getTranslators() {
        return translators;
    }

    public String getSteamid() {
        return steamid;
    }

    public String getFormat() {
        return format;
    }

    public String getLanguage() {
        return language;
    }

    public String getRating() {
        return rating;
    }

    public String getCoverImage() {
        return coverImage;
    }

    //changes the read fson files games to game object that we created
    public static Game fromJSONObject(JSONObject obj) {
        List<String> genre = jsonArrayToList(obj.optJSONArray("genre"));
        List<String> platforms = jsonArrayToList(obj.optJSONArray("platforms"));
        List<String> translators = jsonArrayToList(obj.optJSONArray("translators"));
        List<String> tags = jsonArrayToList(obj.optJSONArray("tags"));

        return new Game(
                obj.optString("title", null),
                genre,
                obj.optString("developer", null),
                obj.optString("publisher", null),
                platforms,
                translators,
                obj.optString("steamid", null),
                obj.optInt("releaseYear", 0),
                obj.optInt("playtime", 0),
                obj.optString("format", null),
                obj.optString("language", null),
                obj.optString("rating", null),
                tags,
                obj.optString("coverImage", null)
        );
    }

    //changes game object to be able to write to the json file
    public JSONObject toJSONObject() {
        JSONObject obj = new JSONObject();
        obj.put("title", title);
        obj.put("genre", genre);
        obj.put("developer", developer);
        obj.put("publisher", publisher);
        obj.put("platforms", platforms);
        obj.put("translators", translators);
        obj.put("steamid", steamid);
        obj.put("releaseYear", releaseYear);
        obj.put("playtime", playtime);
        obj.put("format", format);
        obj.put("language", language);
        obj.put("rating", rating);
        obj.put("tags", tags);
        obj.put("coverImage", coverImage);
        return obj;
    }

    //changes the jsonarray to java list
    private static List<String> jsonArrayToList(JSONArray arr) {
        if (arr == null) return List.of();
        return arr.toList().stream().map(Object::toString).collect(Collectors.toList());
    }

}
