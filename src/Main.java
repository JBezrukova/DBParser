import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.print.Doc;
import java.io.IOException;
import java.sql.*;

/**
 * Created by JuliaBezrukova on 13.06.2016.
 */
public class Main {

    public static void main(String[] args) {

        try {
            Document page = Jsoup.connect("http://makeup.com.ua/categorys/324237/#offset=108").get();
            Elements links = page.select("li[data-id~=.*]");
            System.setProperty("jdbc.drivers", "org.postgresql.Driver");
            java.sql.Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5433/parfumes", "postgres", "postgres");
            Statement statement = connection.createStatement();
            String query;
            for (int i = 0; i < links.size(); i++) {
                Elements links2 = links.select("img");
                query = "insert into parfume values ('" + links2.get(i).attr("title").replaceAll("'", " ") +
                        "'," + links.get(i).attr("data-price") + ")";
                System.out.println(query);
                statement.execute(query);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
