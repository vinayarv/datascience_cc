#!/bin/bash
echo "Hello Terminal"
javac -cp dependencies/json-simple-1.1.1.jar src/tweets_cleaned.java
java -cp "./src;dependencies/json-simple-1.1.1.jar" tweets_cleaned tweet_input/tweets.txt tweet_output/ft1.txt
javac -cp "./src;dependencies/json-simple-1.1.1.jar" src/average_degree.java
java -cp "./src;dependencies/json-simple-1.1.1.jar" average_degree tweet_input/tweets.txt tweet_output/ft2.txt