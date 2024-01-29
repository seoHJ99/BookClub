package book.chat.domain.api.naver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

@Slf4j
@Component
public class BookSearchAPI {

    private final String BOOK_API = "https://openapi.naver.com/v1/search/book.json?query=";

    public void bookSearch(String keyword) throws IOException {
        
        URL apiURL = new URL(BOOK_API + URLEncoder.encode( keyword ));
        HttpURLConnection connection = (HttpURLConnection) apiURL.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("X-Naver-Client-Id", "jgYZagha6X71eBAcMkhS");
        connection.setRequestProperty("X-Naver-Client-Secret", "u8QcFEvsUD");
        connection.setDoOutput(true);

        InputStream inputStream = connection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer stringBuffer = new StringBuffer();
        String inputLine;

        while ((inputLine = bufferedReader.readLine()) != null)  {
            stringBuffer.append(inputLine);
        }
        bufferedReader.close();

        String response = stringBuffer.toString();
        log.info("response={}", response);
    }
}
