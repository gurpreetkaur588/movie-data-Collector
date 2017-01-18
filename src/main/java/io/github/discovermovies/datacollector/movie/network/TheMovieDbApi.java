package io.github.discovermovies.datacollector.movie.network;

import io.github.discovermovies.datacollector.movie.Application;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.*;
import java.util.Properties;

/*
 *   Copyright (C) 2017 Sidhin S Thomas
 *
 *   This file is part of movie-data-Collector.
 *
 *    movie-data-Collector is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   movie-data-Collector is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with movie-data-Collector.  If not, see <http://www.gnu.org/licenses/>.
 */
public class TheMovieDbApi {
    private static final String URL = "https://api.themoviedb.org/3/movie/";
    private static final String LATEST = "latest?";
    private static final String API_KEY = "api_key=";


    private CloseableHttpClient client;
    private String apiKey = null;

    public TheMovieDbApi(){
        client = HttpClients.createDefault();
        Properties properties = new Properties();
        try {
            InputStream in = new FileInputStream(Application.CONFIG_FILE_NAME);
            properties.load(in);
        } catch (java.io.IOException e) {
            System.err.println("Fatal error: unable to open config file");
            System.exit(1);
        }
        apiKey = properties.getProperty("Key");
        apiKey = apiKey==null?"":apiKey;
    }

    public void getLatestMovie() throws IOException {
        String output = getResponseFromServer(URL+LATEST+API_KEY+apiKey);
        System.out.println("Output from server: \n" + output);
    }

    private String getResponseFromServer(String url) throws IOException {
        HttpGet request = new HttpGet(url);
        try {
            CloseableHttpResponse response = client.execute(request);
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (response.getEntity().getContent())));

            String line,output = "";
            while ((line = br.readLine()) != null) {
                output += line;
            }
            return output;
        } catch (IOException e) {
            System.err.println("Error: Unable to connect to server");
            throw e;
        }
    }


}