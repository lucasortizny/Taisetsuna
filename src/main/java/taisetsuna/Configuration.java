package taisetsuna;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.ObjectInputFilter;
import java.util.Scanner;

/**
 * This class will maintain information of the configuration of the bot. The primary function is to allow settings to be
 * read from a file and be held in a configuration object. This class is to provide the communication between the object
 * of the configuration and the "config.json" configuration file.
 */

public class Configuration {
    //This is going to be the class where the config.json is going to be serialized.
    private String token; //Token for the bot
    public String statusmsg; //Message for the status message
    public int activitystatus; //Will be depicted as an Integer

    /**
     * Default constructor with the default value.
     */
    Configuration (){
        this.token = "";
        this.statusmsg = "default status message";
        this.activitystatus = 0;
    }

    /**
     * Enhanced constructor that is here just in case individual values need to be modified outside of json file.
     * @param token - Token for the discord bot to function properly.
     * @param statusmsg - This is going to be the message for the status bar, only active in some activitystatus types.
     * @param activitystatus - This determines the prefix "Playing", "Watching", "Listening to", or any other status
     *                       that discord has for the status prefix.
     */
    Configuration (String token, String statusmsg, int activitystatus) {
        this.token = token;
        this.statusmsg = statusmsg;
        this.activitystatus = activitystatus;
    }

    //Getters + Setters for token || This will not be necessary so long as the JSON file exist and can be read.
    //Therefore, these two methods are really for internal use only.

    /**
     * We want to make the token for the bot a private matter so we are going to make the token modifiable thru get/set.
     * @return - The token as a string value.
     */
    public String getToken(){
        return this.token;
    }

    /**
     * We want to make the token for the bot a private matter so we are going to make the token modifiable thru get/set.
     * @param input - Takes in a string that will change the value of the token.
     */
    public void setToken(String input){
        this.token = input;
    }

    /**
     * This method is do create a new configuration file. Normally it is called in the scneario it can't be loaded.
     * This method comes in useful for first time startup or from an error in formatting.
     * @param input - This is the file we are going to be writing to that will contain default values of a Configuration
     *              object so that it can be modified and loaded in later via "loadConfig"
     */
    public void createNewConfig(File input){
        //Try to get the file if it exists and write to it.

        try {
            File configurationfile = input;

            configurationfile.createNewFile(); //If there is any issue doing this, exception will be thrown and we exit.

            Gson jsonconverter = new GsonBuilder().setPrettyPrinting().create();
            FileWriter writer = new FileWriter(configurationfile);
            writer.write(jsonconverter.toJson(this));
            writer.flush();
            writer.close();


        } catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }

    }

    /**
     * This method is to read from a configuration file and build an object based on the file's contents.
     * This method will come in useful to resume after we have shut the bot down in the past, we save the settings.
     * @param input - This is going to be the file we are reading from.
     * @return - Returns a configuration object already set with the parameters loaded from the file input.
     */
    public static Configuration loadFromConfig(File input){
        try {
            File configjson = input;
            Gson gson = new Gson();
            String fileread = readFileToString(configjson);

            //Set the current object equal to a new object with the proper JSON config read.

            Configuration retconf = gson.fromJson(fileread, Configuration.class);
            retconf.createNewConfig(input);
            return retconf;



        } catch (Exception e){
            e.printStackTrace();
        }

        Configuration newconfig = new Configuration();
        newconfig.createNewConfig(input);
        return newconfig;

    }

    /**
     * This method is so that it is possible to write back to the Config file with the current values.
     * This method will be useful for if there are any changes done via Discord and we update the file.
     * @param input -  This is going to be the file input that will be updated.
     * @return - Returns true if successful, false if not successful. Probably won't be used but still useful to have.
     */
    public boolean writeConfig (File input) {
        try {
            File configurationfile = input;
            Gson jsonconverter = new GsonBuilder().setPrettyPrinting().create();
            FileWriter writer = new FileWriter(configurationfile);
            writer.write(jsonconverter.toJson(this));
            writer.flush();
            writer.close();
            return true;
        } catch (Exception e){
            //The case where it is unable to write to file, it will not save the data.
            System.out.println("The file was unable to be saved to, please check permissions if proper.");
            return false;
        }
    }


    //Method to Pull Activity based on Integer Value
    public static String readFileToString (File input){
        try {
            StringBuilder converted = new StringBuilder();
            Scanner filereader = new Scanner(input); //This should trigger a FileNotFoundException if not found.
            while (filereader.hasNext()){
                converted.append(filereader.next());
            }
            return converted.toString();
        } catch (Exception e){
            //This is going to catch if there is any issues with reading or FileWriting.
            e.printStackTrace();
            System.exit(1);
        }
        //Returns an empty StringBuilder if it is unable to read the file so that it is
        return "";
    }


}
