
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WriteToFileClass
{
    private static String directory = "./channels/"; //Initialise directory String to my working channels folder

    public static ChannelClass getChannelFile(File newFile) //Get the channel from file
    {
        ChannelClass newClass; //Instantiate new Channel object

        try
        {
            Scanner newScanner = new Scanner(newFile); //Initialise scanner object to read the file passed in

            String currentLine = "";
            String foundChannelIdentity = "";
            foundChannelIdentity = newScanner.nextLine(); //This line in the file will the channel identity
            newScanner.nextLine();
            newScanner.nextLine();

            newClass = new ChannelClass(foundChannelIdentity); //set newClass to equal the string of the channel found

            while(newScanner.hasNextLine())
            {
                currentLine = newScanner.nextLine(); //Current line to equal the next item to return

                if (currentLine.equals("Channel Subscribers:")) //If it equals Channel subscribers, break the loop
                {
                    break;
                }

                //NSD Client1,1.0,Hello Message 1
                //(String newMessageRecipient, String newMessageBody, long newMessageDelivered)

                String [] messageValues = currentLine.split(",", 3); //String array which is set to the current line read by the scanner and split 3 times

                try
                {
                    newClass.addMsg(new MessageClass(messageValues[0], messageValues[2], Long.parseLong(messageValues[1]))); //Add message data to the class

                }catch (ArrayIndexOutOfBoundsException oob) //E exception
                {
                    System.out.println("Array out of bounds");
                }
            }

            while(newScanner.hasNextLine()) //Whilst there is another line to read
            {
                currentLine = newScanner.nextLine();
                newClass.addSub(currentLine); //add the current line to the subCount
            }

        }catch(FileNotFoundException fnf) //FNF exception
        {
            System.out.println("File not found");
            return null;
        }

        return new ChannelClass(newClass.getIdentity(), newClass.getUserMessages(), newClass.getSubs()); //Return the Channel data
    }

    public static void saveChannelToFile(ChannelClass newChannel) //Save the channel to the file
    {
        try
        {
            PrintWriter newPrintWriter = new PrintWriter(directory + newChannel.getIdentity() + ".txt"); //Instantiate new Print Writer object to write to text file in correct directory

            newPrintWriter.println("Channel Name:");
            newPrintWriter.println(newChannel.getIdentity()); //get identity
            newPrintWriter.println("");
            newPrintWriter.println("Channel Messages:");

            for (MessageClass newMessage : newChannel.getUserMessages()) //For each message in the getUserMessages collection
            {
                newPrintWriter.println(newMessage.getMessageRecipient() + "," + newMessage.getMessageTimestamp() + "," + newMessage.getMessageBody()); //Write to the text file
            }

            newPrintWriter.println("Channel Subscribers:");

            for (String newSub : newChannel.getSubs()) //For every sub in subscriber collection
            {
                newPrintWriter.println(newSub); //Write to the text file
            }

            newPrintWriter.close(); //Prevent memory leaks

        }catch (FileNotFoundException fnf) //FNF exception
        {
            System.out.println("File not found");
        }
    }

    public static List <File> getChannelFiles ()
    {
        File channelPath = new File(directory); //Instantiate channel path for files
        File[] channelFiles = channelPath.listFiles(); //Instantiate new list of channelFiles via the directory

        List<File> foundChannelFiles = new ArrayList<File>();

        for (File file : channelFiles) //For every file found in the channel path
        {
            if (file.isFile())
            {
                foundChannelFiles.add(file); //Add file to collection
            }
        }

        return foundChannelFiles; //Return the file created
    }

}
