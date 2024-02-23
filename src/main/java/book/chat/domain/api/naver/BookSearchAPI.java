package book.chat.domain.api.naver;

import book.chat.web.DTO.BookDTO;
import com.fasterxml.jackson.core.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class BookSearchAPI {

    private final String BOOK_URL = "https://openapi.naver.com/v1/search/book.json?query=";

    public List<BookDTO> bookSearch(String keyword, Integer displayAmount, int start) throws IOException, ParseException {
        JSONArray jsonArray = null;
        try{
            URL apiURL = new URL(BOOK_URL + URLEncoder.encode( keyword ) + "&display=" + displayAmount + "&start=" +start);
            String response = getResponse(sendRequest(apiURL));
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(response);
             jsonArray = (JSONArray) jsonObject.get("items");
        }catch (Exception e){
            log.error("exception = {}", e.getMessage());
        }
        return (List<BookDTO>) jsonArray.stream()
                .map(o -> {
                    JSONObject book = (JSONObject)o;
                    return BookDTO.builder()
                            .name((String) book.get("title"))
                            .image((String) book.get("image"))
                            .isbn((String) book.get("isbn"))
                            .author((String) book.get("author"))
                            .publisher((String) book.get("publisher"))
                            .publishDate((String) book.get("pubdate"))
                            .description((String) book.get("description"))
                            .shoppingLink((String) book.get("link"))
                            .build();})
                .collect(Collectors.toList());

    }

    public List<BookDTO> bookSearch(String keyword) {
        JSONArray jsonArray = null;
        try{
            URL apiURL = new URL(BOOK_URL + URLEncoder.encode( keyword ) );
            String response = getResponse(sendRequest(apiURL));
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(response);
            jsonArray = (JSONArray) jsonObject.get("items");
        }catch (Exception e){
            log.error("exception = {}", e.getMessage());
        }
        return (List<BookDTO>) jsonArray.stream()
                .map(o -> {
                    JSONObject book = (JSONObject)o;
                     return BookDTO.builder()
                            .name((String) book.get("title"))
                            .image((String) book.get("image"))
                            .isbn((String) book.get("isbn"))
                            .author((String) book.get("author"))
                            .publisher((String) book.get("publisher"))
                            .publishDate((String) book.get("pubdate"))
                            .description((String) book.get("description"))
                             .shoppingLink((String) book.get("link"))
                            .build();})
                .collect(Collectors.toList());
    }

    public String getResponse(HttpURLConnection connection) throws IOException {
        InputStream inputStream = connection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer stringBuffer = new StringBuffer();
        String inputLine;
        while ((inputLine = bufferedReader.readLine()) != null)  {
            stringBuffer.append(inputLine);
        }
        bufferedReader.close();
        String response = stringBuffer.toString();
        return response;
    }

    public HttpURLConnection sendRequest(URL apiURL) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) apiURL.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("X-Naver-Client-Id", "jgYZagha6X71eBAcMkhS");
        connection.setRequestProperty("X-Naver-Client-Secret", "u8QcFEvsUD");
        connection.setDoOutput(true);
        return connection;
    }
}
