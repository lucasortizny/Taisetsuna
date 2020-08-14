package taisetsuna;


import net.dv8tion.jda.api.*;

import java.io.File;


public class Main {

    public static File input = new File("./config.json");
    public static JDABuilder jdbuilder;
    public static Configuration mainconfig;

    /**
     * This is the main method. This is meant to instantiate the configuration and the bot.
     * @param args - Commandline Arguments, used in the future to change configuration files on-the-go.
     */
    public static void main(String[] args){
        mainconfig = instantiateConfiguration();

    }

    /**
     * This method is going to be instantiating the Configuration file based on how the config.json is read.
     * @return - a Configuration object to use for the settings of the bot.
     */
    public static Configuration instantiateConfiguration(){
        Configuration config = new Configuration();

        //If a configuration file exists, we are going to load from there rather than start from scratch.
        if (input.exists()) {
            config.loadFromConfig();
        }
        //Handle the case where the file does not exist, start from scratch and create a new Configuration file.
        else {
            config = new Configuration();
            config.createNewConfig();
        }
        return config;

    }

}
