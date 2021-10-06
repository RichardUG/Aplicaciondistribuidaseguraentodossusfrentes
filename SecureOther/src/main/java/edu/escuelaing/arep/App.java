package edu.escuelaing.arep;

import com.google.common.hash.Hashing;
import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
        port(getPort());
        secure("keystores/ecikeystore.p12", "123456", "keystores/myTrustStore","123456");
        get("/hello", (req, res) -> "Hello World");
    }
    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4568; //returns default port if heroku-port isn't set (i.e. on localhost)
    }
}
