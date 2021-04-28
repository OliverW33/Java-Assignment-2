import org.json.simple.*;

public class PublishRequestClass extends RequestClass {

    String identity = "";
    MessageClass userMessage = null;

    public PublishRequestClass(String newIdentity, MessageClass newUserMessage)
    {
        _class = "PublishRequest";
        identity = newIdentity;
        userMessage = newUserMessage;
    }

    @Override
    public JSONObject newJSONObj()
    {
        JSONObject newObject = new JSONObject();
        newObject.put("_class", _class);
        newObject.put("identity", identity);
        newObject.put("message", userMessage);
        return newObject;
    }

    public String getIdentity()
    {
        return identity;
    }

    public MessageClass getMessage()
    {
        return userMessage;
    }

    public String getBody()
    {
        return userMessage.getMessageBody();
    }

    public String getFrom()
    {
        return userMessage.getMessageRecipient();
    }

    public float getDelivered()
    {
        return userMessage.getMessageTimestamp();
    }



}
