import org.json.simple.*;
import org.json.simple.JSONObject;

import java.net.*;
import java.io.*;
import java.util.*;

class ServerClass {

    private static List<ChannelClass> newChannelList = new ArrayList<>(); //Instantiate a new Channel List

    public static boolean findChannelID(String newChannel) //Finds the channel ID if the channel exists
    {
        for (ChannelClass channel : newChannelList) //For every channel stored in the Channel List
        {
            if (channel.getIdentity().equals(newChannel)) //If the identity of the locally instantiated channel equals the same identity as the channel passed in
            {
                //System.out.println("\nChannel ID " + channel.getIdentity() + " has been found! :)\n");
                return true; //return true
            }
        }
        System.out.println("\nNo channel ID has been found with this request\n");
        return false; //return false
    }


    public static void main(String args[]) { //catches IO exception if thrown

        //set the server socket to null - no connection established

        ServerSocket server = null;
        int clientCount = 0; //integer variable to count the amount of clients currently on the server

        try {
            server = new ServerSocket(12345);
            System.out.println("Server is running!"); //DEBUG
            server.setReuseAddress(true);

            while (true) {
                Socket clientSocket = server.accept();
                System.out.println("New client connected " + clientSocket.getInetAddress().getHostAddress());
                MultithreadedServer newClient = new MultithreadedServer(clientSocket, "client " + clientCount, newChannelList); //Call Multithreaded server class to optimise server for multiple instance use.
                new Thread(newClient).start(); //Start runnable method in relevant class
                clientCount++; //Each time a client connects, add that to the variable
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (server != null) {
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

            for (File newFile : WriteToFileClass.getChannelFiles())
            {
                newChannelList.add(WriteToFileClass.getChannelFile(newFile));
            }

    }


    private static class MultithreadedServer implements Runnable {

        private final Socket clientConnected; //Instantiate new Socket
        private final String clientID; //Instantiate new String
        private static List<ChannelClass> channelListReference = new ArrayList<ChannelClass>(); //Instantiate new List of Channels

        PrintWriter newOutput; //Instantiate new PrintWriter
        BufferedReader newReader; //Instantiate new BufferedReader

        String rID; //Instantiate new String
        String rClass; //Instantiate new String

        public MultithreadedServer(Socket socket, String newClientID, List<ChannelClass> newChannelListReference) throws IOException //Client Handler constructor - passing in a socket and a client
        {
            this.clientConnected = socket; //refers to current object in method scope
            this.clientID = newClientID; //refers to current object in method scope
            this.channelListReference = newChannelListReference; //Holds a reference to the Servers Channel List
        }

        @Override
        public void run() { //Method declaration is intended

            try {

                newOutput = new PrintWriter(clientConnected.getOutputStream(), true); //declare new Print Writer object - auto flush ensures that data is sent to the output stream efficiently
                newReader = new BufferedReader(new InputStreamReader(clientConnected.getInputStream())); //declare new Buffer Reader object - retrieve input stream from inherited client socket

                System.out.println("Connection accepted at :" + clientConnected); //sys out - show TCP data
                String userInput; //Declare new String

                while ((userInput = newReader.readLine()) != null) //whilst the program is running, do this - Declare relevant variable to equal the stored contents in readInput (Buffer Reader), in result for the value to be read
                {
                    System.out.println("\n\nNext Request -----------------------------------------------------------------------------------\n\n");

                    Object newObject = JSONValue.parse(decodeRequest(userInput)); //Instantiate a new object parsing the String into JSON
                    JSONObject JSONObj = (JSONObject)newObject; //Instantiate a new JSON object to store the parsed JSON string (userInput)

                    rID = (String) JSONObj.get("identity"); //String variable as JSON object casted to a string to be read by desired request
                    rClass = (String) JSONObj.get("_class"); //String variable as JSON object casted to a string to be read by desired request

                    if (rClass.equals("OpenRequest")) //If the sent request of the Demo client is equal to Open Request
                    {
                        OpenRequestClass newOpenRequest = new OpenRequestClass(rID); //Instantiate new Open Request if request is equal to

                        if (newOpenRequest != null) //Safe testing
                        {
                            System.out.println("Open Request has been made!\n");
                        }

                        synchronized (ServerClass.class) //Ensures one entity/client is accessing the relevant class at once to prevent duplication errors
                        {
                            boolean channelFound = false; //If the channel has been found or not by the request

                            for (ChannelClass newChannel : channelListReference) //For every Channel stored in channelList
                            {
                                if (newChannel.getIdentity().equals(newOpenRequest.getIdentity())) //If the identity of the newChannel matches the open request channel identity
                                {
                                    System.out.println("Channel " + newOpenRequest.getIdentity() + " has been found! :)\n"); //A channel has been found
                                    channelFound = true; //Channel has been found
                                    break; //break out of for loop
                                }
                            }

                            if (!channelFound) //If a channel is not found
                            {
                                channelListReference.add(new ChannelClass(newOpenRequest.getIdentity())); //Create a new Instance of a Channel requested by the client and add to list
                                System.out.println("New Channel has been created: " + rID + "\n"); //Display created channel to user
                            }
                        }

                        successResponseReceived(); //Call relevant response to acknowledge the request has been successful
                    }
                    else if (JSONObj.get("_class").equals("PublishRequest")) //If the sent request of the Demo client is equal to Publish Request
                    {
                        PublishRequestClass newPublishRequest = new PublishRequestClass((String)JSONObj.get("identity"), new MessageClass((JSONObject) JSONObj.get("message"))); ///Instantiate new Publish Request if request is equal to

                            if (newPublishRequest != null) //Safe testing
                            {
                                System.out.println("Publish Request has been made!\n");
                            }

                            synchronized (ServerClass.class) //Ensures one entity/client is accessing the relevant class at once to prevent duplication errors
                            {

                                if (newPublishRequest.getBody().length() > 1234) //If the Publish request message is greater than 1234 characters, deny the request
                                {
                                    System.out.println("This message is too big for the server! Must be below 1234 characters ");
                                    errorResponseReceived("Message too big for server"); //Call relevant response to acknowledge the request has been interrupted
                                }

                                ChannelClass newChannelClass = findChannel(newPublishRequest.getIdentity());
                                //Instantiate new Channel object which accesses my external method in Server to find the Identity of the Publish Request

                                if(newChannelClass != null) //If the identity is not null/contains parsed JSON data
                                {
                                    newChannelClass.addMsg(newPublishRequest.getMessage()); //Add the message received from the publish request to the channel class

                                    System.out.println("--------------------");

                                    System.out.println("Message Body: " + newPublishRequest.getBody()); //Get message body

                                    System.out.println("Message From: " + newPublishRequest.getFrom()); //Get message recipient

                                    System.out.println("Message Timestamp: " + newPublishRequest.getDelivered()); //Get message timestamp

                                    synchronized (WriteToFileClass.class)
                                    {
                                        WriteToFileClass.saveChannelToFile(newChannelClass);
                                    }

                                    successResponseReceived(); //Call relevant response to acknowledge the request has been successful
                                }
                                else
                                {
                                    errorResponseReceived("No Channel " + newPublishRequest.getIdentity() + " has been found\n"); //Call relevant response to acknowledge the request has failed
                                }

                            }
                    }
                    else if (JSONObj.get("_class").equals("SubscribeRequest")) //If the sent request of the Demo client is equal to Subscribe Request
                    {
                        SubscribeRequestClass newSubscribeRequest = new SubscribeRequestClass((String)JSONObj.get("identity"), (String) JSONObj.get("channel")); //Instantiate new Subscribe Request if request is equal to

                        if (newSubscribeRequest != null) //Safe testing
                        {
                            System.out.println("Subscribe Request has been made!\n");
                        }

                        synchronized (ServerClass.class) //Ensures one entity/client is accessing the relevant class at once to prevent duplication errors
                        {
                            String foundChannel = newSubscribeRequest.getSubscribedChannel(); //Instantiate new String to equal the channel fetched by the subscribe request

                            if (ServerClass.findChannelID(foundChannel)) //If the findChannelID method is equal to the channel requested
                            {

                                findChannel(foundChannel).addSub(newSubscribeRequest.getIdentity()); //Add subscriber to the identity of the found channel

                                if (findChannel(foundChannel).findSub(newSubscribeRequest.getIdentity())) //If the channel contains the subscriber, display to the user they have been added
                                {
                                    System.out.println("Subscriber has been added to the channel " + newSubscribeRequest.getIdentity() + " :)\n");
                                }
                                else
                                {
                                    System.out.println("Subscriber has not been added :(\n");
                                }

                                synchronized (WriteToFileClass.class)
                                {
                                    WriteToFileClass.saveChannelToFile(findChannel(foundChannel));
                                }

                                successResponseReceived(); //Call relevant response to acknowledge the request has been successful
                            }
                            else
                            {
                                errorResponseReceived("No Channel " + newSubscribeRequest.getIdentity() + " has been found\n"); //Call relevant response to acknowledge the request has failed
                            }

                        }
                    }
                    else if (JSONObj.get("_class").equals("UnsubscribeRequest")) //If the sent request of the Demo client is equal to Unsubscribe Request
                    {
                        UnsubscribeRequestClass newUnsubscribeRequest = new UnsubscribeRequestClass((String)JSONObj.get("identity"), (String) JSONObj.get("channel")); //Instantiate new Unsubscribe Request if request is equal to

                        if (newUnsubscribeRequest != null) //Safe testing
                        {
                            System.out.println("Unsubscribe Request has been made!\n");
                        }

                        synchronized (ServerClass.class) //Ensures one entity/client is accessing the relevant class at once to prevent duplication errors
                        {
                            ChannelClass newChannelClass = findChannel(newUnsubscribeRequest.getChannel());
                            //Instantiate new Channel object which accesses my external method in Server to find the Channel of the Unsubscribe Request

                            if (newChannelClass != null)
                            {
                                newChannelClass.removeSub(newUnsubscribeRequest.getIdentity()); //If the channel contains a subscriber, get the identity and remove it from the channel
                                successResponseReceived();

                                synchronized (WriteToFileClass.class)
                                {
                                    WriteToFileClass.saveChannelToFile(newChannelClass);
                                }
                            }
                            else
                            {
                                errorResponseReceived("No Channel " + newUnsubscribeRequest.getIdentity() + " has been found\n"); //Call relevant response to acknowledge the request has failed
                            }
                        }
                    }
                    else if (rClass.equals("GetRequest")) //If the sent request of the Demo client is equal to Get Request
                    {
                        GetRequestClass newGetRequest = new GetRequestClass((String)JSONObj.get("identity"), (long) JSONObj.get("after")); //Instantiate new Get Request if request is equal to

                        if (newGetRequest != null) //Safe testing
                        {
                            System.out.println("Get Request has been made!\n");
                        }

                        synchronized (ServerClass.class) //Ensures one entity/client is accessing the relevant class at once to prevent duplication errors
                        {

                            List <MessageClass> messagesCollected = new ArrayList<MessageClass>(); //Instantiate new Message List to collect all messages from channel

                            List <ChannelClass> channelsMatched = findChannelSubs(newGetRequest.getIdentity()); //Instantiate new Channel List to call method to find amount of subs to a channel

                                if (newGetRequest.getAfter() == 0) //Server will only retrieve messages that were published after the timestamp, so if after value is equal to 0, return message set
                                {
                                    for (ChannelClass newChannel : channelsMatched) //For every channel that is subscribed to
                                    {
                                        messagesCollected.addAll(newChannel.getUserMessages()); //Add all messages in that channel to the messages collected List
                                    }
                                }
                                else
                                {
                                    errorResponseReceived("No Messages in Channel"); //Call relevant response to acknowledge the request has failed
                                }

                                for (MessageClass newMsg : messagesCollected)
                                {
                                    System.out.println("--------------------");

                                    System.out.println("Message Body: " + newMsg.getMessageBody()); //Get message body

                                    System.out.println("Message From: " + newMsg.getMessageRecipient()); //Get message recipient

                                    System.out.println("Message Timestamp: " + newMsg.getMessageTimestamp()); //Get message timestamp
                                }

                                messageListResponseReceived(messagesCollected); //Call relevant response to acknowledge the request has received a message list response

                        }

                        //successResponseReceived(); //Call relevant response to acknowledge the request has been successful
                    }

                }
                newOutput.close(); //close writer/out stream
                newReader.close(); //close reader/in

            } catch (IOException e)
            {
                System.out.println("Failed to accept the server connection");
                System.out.println(e);
            } catch (NoSuchElementException nse)
            {
                System.out.println("No element has been found");
            } catch (NullPointerException npe)
            {
                System.out.println("Null method found");
            }
            finally
            {
                try {
                    if (newOutput != null) {
                        newOutput.close();
                    }
                    if (newReader != null) {
                        newReader.close();
                        clientConnected.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }

        public static List<ChannelClass> findChannelSubs(String newIdentity) //Finds the subs allocated to a channel
        {
            List<ChannelClass> totalSubs = new ArrayList<>(); //Instantiate new Channel list

            for (ChannelClass newChannel : channelListReference) //For every channel stored in the Channel List
            {
                for (String newSub : newChannel.getSubs()) //For every sub allocated to a channel
                {
                    if (newSub.equals(newIdentity)) //If the identity of the locally instantiated sub equals the same identity as the sub passed in
                    {
                        totalSubs.add(newChannel); //Add the totalSubs to the channel
                    }
                }
            }
            return totalSubs; //Return the array list
        }

        public static ChannelClass findChannel(String newChannel) //Finds if channel exists
        {
            for (ChannelClass channel : channelListReference) //For every channel stored in the Channel List
            {
                if (channel.getIdentity().equals(newChannel)) //If the identity of the locally instantiated channel equals the same identity as the channel passed in
                {
                    //System.out.println("\nChannel " + channel.getIdentity() + " has been found! :)\n");
                    return channel; //return the channel instance
                }
            }
            System.out.println("\nNo channel exists\n");
            return null; //return nothing
        }

        private void successResponseReceived() //Encode Request and return Success Response if received
        {
            System.out.println("Success Response has been received at: " + rID);
            SuccessResponseClass success = new SuccessResponseClass();
            JSONObject newObject = success.newJSONObj();
            String encodedRequest = encodeRequest(newObject.toJSONString().getBytes());
            newOutput.println(encodedRequest);
        }

        private void errorResponseReceived(String newErrorResponse) //Encode Request and return Error Response if not received
        {
            System.out.println("Error Response has been received at: " + rID);
            ErrorResponseClass error = new ErrorResponseClass(newErrorResponse);
            JSONObject newObject = error.newJSONObj();
            String encodedRequest = encodeRequest(newObject.toJSONString().getBytes());
            newOutput.println(encodedRequest);
        }

        private void messageListResponseReceived(List<MessageClass> newList) //Encode Request and return Message List Response if collection has been received
        {
            System.out.println("MessageList Response has been received at: " + rID);
            MessageListResponseClass messageList = new MessageListResponseClass(newList);
            JSONObject newObject = messageList.newJSONObj();
            String encodedRequest = encodeRequest(newObject.toJSONString().getBytes());
            newOutput.println(encodedRequest);
        }

        private String decodeRequest(String encodedRequest) //Decode Request made from Client
        {
            return new String(Base64.getDecoder().decode(encodedRequest));
        }

        private static String encodeRequest(byte[] decodedRequestBytes) //Encode Request made from Client
        {
            return new String(Base64.getEncoder().encode(decodedRequestBytes));
        }
    }
}