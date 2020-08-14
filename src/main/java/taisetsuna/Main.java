package taisetsuna;


import net.dv8tion.jda.api.*;

import java.io.File;


public class Main {

    public static File input = new File("./config.json");
    public static JDABuilder jdbuilder;
    public static Configuration config;

    //Class to initialize the Bot
    public static void main(String[] args){

        //If a configuration file exists, we are going to load from there rather than start from scratch.
        if (input.exists()) {
            config = Configuration.loadFromConfig(input);
        }
        //Handle the case where the file does not exist, start from scratch and create a new Configuration file.
        else {
            config = new Configuration();
            config.createNewConfig(input);
        }







    }

}
