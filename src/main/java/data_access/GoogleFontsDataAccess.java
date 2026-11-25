package data_access;

import org.json.JSONArray;
import org.json.JSONObject;
import use_case.load_fonts.FontDataAccessInterface;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Talks to the Google Web Fonts Developer API. */
public class GoogleFontsDataAccess implements FontDataAccessInterface {
    private static final String LIST_URL =
            "https://www.googleapis.com/webfonts/v1/webfonts?sort=popularity&key=";
    private static final String[] VARIANTS = {"regular", "700", "italic", "700italic"};
    private static final int MAX_FONTS = 100;

    private final String apiKey;
    private final HttpClient client = HttpClient.newHttpClient();
    private final Map<String, JSONObject> filesByFamily = new HashMap<>();
    private final Set<String> registered = new HashSet<>();
    private boolean listLoaded = false;

    public GoogleFontsDataAccess(String apiKey) {
        this.apiKey = apiKey == null ? "" : apiKey;
    }

    @Override
    public synchronized List<String> getFontFamilies() {
        listLoaded = true;
        try {
            byte[] bytes = fetch(LIST_URL + apiKey);
            JSONArray items = new JSONObject(new String(bytes, StandardCharsets.UTF_8))
                    .getJSONArray("items");
            List<String> families = new ArrayList<>();
            for (int i = 0; i < Math.min(items.length(), MAX_FONTS); i++) {
                JSONObject item = items.getJSONObject(i);
                String family = item.getString("family");
                filesByFamily.put(family, item.getJSONObject("files"));
                families.add(family);
            }
            return families;
        } catch (Exception e) {
            // no key / no internet: fall back to fonts installed on this computer
            return new ArrayList<>(Arrays.asList(GraphicsEnvironment
                    .getLocalGraphicsEnvironment().getAvailableFontFamilyNames()));
        }
    }

    @Override
    public synchronized boolean makeAvailable(String family) {
        if (!listLoaded) {
            getFontFamilies();
        }
        if (registered.contains(family)) {
            return true;                       // already downloaded
        }
        JSONObject files = filesByFamily.get(family);
        if (files == null) {
            return true;                       // not a Google font, so it is installed locally
        }
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            for (String variant : VARIANTS) {  // regular, bold, italic, bold italic
                if (files.has(variant)) {
                    byte[] bytes = fetch(files.getString(variant));
                    ge.registerFont(Font.createFont(Font.TRUETYPE_FONT,
                            new ByteArrayInputStream(bytes)));
                }
            }
            registered.add(family);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private byte[] fetch(String url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(
                URI.create(url.replace("http://", "https://"))).GET().build();
        HttpResponse<byte[]> response =
                client.send(request, HttpResponse.BodyHandlers.ofByteArray());
        if (response.statusCode() != 200) {
            throw new IOException("HTTP " + response.statusCode());
        }
        return response.body();
    }
}