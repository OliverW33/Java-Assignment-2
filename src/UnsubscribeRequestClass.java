import org.json.simple.JSONObject;

public class UnsubscribeRequestClass extends RequestClass {

    private String identity = "";
    private String channel = "";

    public UnsubscribeRequestClass(String newIdentity, String newChannel)
    {
        _class = "UnsubscribeRequest";
        identity = newIdentity;
        channel = newChannel;
    }

    @Override
    public JSONObject newJSONObj()
    {
        JSONObject newObject = new JSONObject();
        newObject.put("_class", _class);
        newObject.put("identity", identity);
        newObject.put("channel", channel);
        return newObject;
    }

    public String getIdentity()
    {
        return identity;
    }

    public String getChannel()
    {
        return channel;
    }
}
