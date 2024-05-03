package book.chat.api.naver;

import book.chat.common.dto.BookDTO;
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

    /**
     * [네이버 api를 이용한 책 검색]
     * @param keyword (제목, 작가, isbn 등 검색 키워드)
     * @param displayAmount (검색 결과 개수)
     * @param start (몇번째 검색 결과부터 검색할건지. <br/>
     *              ex) 어떤 키워드의 총 검색 결과가 100건이고 displayAmount 가 10이라면, 두번째 검색에는 start값 11로 시작해야 함)
     * @return List (검색된 BookDTO 리스트)
     */
    
    public List<BookDTO> bookSearch(String keyword, Integer displayAmount, int start) throws IOException, ParseException {
        JSONArray jsonArray = null;
        try {
            Thread.sleep(100);
            URL apiURL = new URL(BOOK_URL + URLEncoder.encode(keyword) + "&display=" + displayAmount + "&start=" + start);
            String response = getResponse(sendRequest(apiURL));
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(response);
            jsonArray = (JSONArray) jsonObject.get("items");
        } catch (Exception e) {
            log.error("exception = {}", e.getMessage());
        }
        return (List<BookDTO>) jsonArray.stream()
                .map(o -> {
                    JSONObject book = (JSONObject) o;
                    return BookDTO.builder()
                            .name((String) book.get("title"))
                            .image((String) book.get("image"))
                            .isbn((String) book.get("isbn"))
                            .author((String) book.get("author"))
                            .publisher((String) book.get("publisher"))
                            .publishDate((String) book.get("pubdate"))
                            .description((String) book.get("description"))
                            .shoppingLink((String) book.get("link"))
                            .build();
                })
                .collect(Collectors.toList());

    }
    
    /**
     * [네이버 api를 이용한 책 검색. displayAmount 가 10, start가 1로 고정되어 있음]
     * @param keyword (제목, 작가, isbn 등 검색 키워드)
     * @return List (검색된 BookDTO 리스트)
     */
    public List<BookDTO> bookSearch(String keyword) {
        JSONArray jsonArray = null;
        try {
            if(keyword.isEmpty()){
                List<BookDTO> list = new ArrayList<>();
                return list;
            }
            Thread.sleep(100);
            URL apiURL = new URL(BOOK_URL + URLEncoder.encode(keyword));
            String response = getResponse(sendRequest(apiURL));
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(response);
            jsonArray = (JSONArray) jsonObject.get("items");

            if (jsonArray.isEmpty() || jsonArray == null) {
                List<BookDTO> list = new ArrayList<>();
                list.add(
                BookDTO.builder()
                        .name("알수 없음")
                        .image("/free-icon-book-828370.png")
                        .isbn("알수 없음")
                        .author("알수 없음")
                        .publisher("알수 없음")
                        .publishDate("알수 없음")
                        .description("알수 없음")
                        .shoppingLink("알수 없음")
                        .build());
                return list;

            }
        } catch (Exception e) {
            log.error("exception = {}", e.getMessage());
        }
        return (List<BookDTO>) jsonArray.stream()
                .map(o -> {
                    JSONObject book = (JSONObject) o;
                    return BookDTO.builder()
                            .name((String) book.get("title"))
                            .image((String) book.get("image"))
                            .isbn((String) book.get("isbn"))
                            .author((String) book.get("author"))
                            .publisher((String) book.get("publisher"))
                            .publishDate((String) book.get("pubdate"))
                            .description((String) book.get("description"))
                            .shoppingLink((String) book.get("link"))
                            .build();
                })
                .collect(Collectors.toList());
    }

    public String getResponse(HttpURLConnection connection) throws IOException {
        InputStream inputStream = connection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer stringBuffer = new StringBuffer();
        String inputLine;
        while ((inputLine = bufferedReader.readLine()) != null) {
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
