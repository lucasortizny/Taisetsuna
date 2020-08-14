package taisetsuna;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
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
    public int statuscode; //Will be depicted as an Integer
    public transient File input; //This parameter is to allow changing between different configuration files.

    /**
     * Default constructor with the default value.
     */
    Configuration (){
        this.token = "";
        this.statusmsg = "default status message";
        this.statuscode = 0;
        this.input = new File ("./config.json");
    }

    /**
     * Constructor for internal use. Most likely will not be used but it is here for internal use.
     * @param token - Token for the discord bot to function properly.
     * @param statusmsg - This is going to be the message for the status bar, only active in some activitystatus types.
     * @param activitystatus - This determines the prefix "Playing", "Watching", "Listening to", or any other status
     *                       that discord has for the status prefix.
     */
    Configuration (String token, String statusmsg, int activitystatus, File input) {
        this.token = token;
        this.statusmsg = statusmsg;
        this.statuscode = activitystatus;
        this.input = input;
    }

    //Getters + Setters for token || This will not be necessary so long as the JSON file exist and can be read.

    //Therefore, these two methods are really for internal use only.
    /**
     * We want to make the token for the bot a private matter so we are going to make the token modifiable in get/set.
     * @return - The token as a string value.
     */
    public String getToken(){
        return this.token;
    }

    /**
     * This method is to return the current file of the Bot Configuration, in case it is needed.
     * @return - Returns and object of type 'File' for
     */
    public File getFile() {return this.input;}

    /**
     * This method is to change the File used by the bot so that a different set of configuration files is used.
     * @param path - this is a String representation of a path to a file where the configuration file is held.
     */
    public void changeFile (String path) { this.input = new File(path);}



    /**
     * We want to make the token for the bot a private matter so we are going to make the token modifiable in get/set.
     * @param input - Takes in a string that will change the value of the token.
     */
    public void setToken(String input){
        this.token = input;
    }

    /**
     * This method is do create a new configuration file. Normally it is called in the scneario it can't be loaded.
     * This method comes in useful for first time startup or from an error in formatting.
     */
    public void createNewConfig(){
        //Try to get the file if it exists and write to it.

        try {

            if (!input.exists())

                input.createNewFile(); //If there is any issue doing this, exception will be thrown and we exit.

            Gson jsonconverter = new GsonBuilder().setPrettyPrinting().create();
            FileWriter writer = new FileWriter(input);
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
     */
    public void loadFromConfig(){

        try {

            Gson gson = new Gson();
            String fileread = readFileToString(input);
            //Set the current object equal to a new object with the proper JSON config read.

            Configuration loaded = gson.fromJson(fileread, Configuration.class);
            this.setToken(loaded.getToken());
            this.statuscode = loaded.statuscode;
            this.statusmsg = loaded.statusmsg;




        } catch (Exception e){
            e.printStackTrace();
            Configuration newconfig = new Configuration();
            newconfig.createNewConfig();
        }




    }

    /**
     * This method is so that it is possible to write back to the Config file with the current values.
     * This method will be useful for if there are any changes done via Discord and we update the file.
     * @param input -  This is going to be the file input that will be updated.
     * @return - Returns true if successful, false if not successful. Probably won't be used but still useful to have.
     */
    public boolean updateConfig (File input) {
        try {

            Gson jsonconverter = new GsonBuilder().setPrettyPrinting().create();
            FileWriter writer = new FileWriter(input);
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


    /**
     * This is meant to read the entirety of the file in a raw format and append it to a string via the StringBuilder
     * to return.
     * @param input - This method is going to ask for the file to read from. It will utilize the Scanner to read it.
     * @return - It will return a String of the file in String format.
     */
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
        //Returns an empty String if it is unable to read the file so that it is
        return "";
    }


}
